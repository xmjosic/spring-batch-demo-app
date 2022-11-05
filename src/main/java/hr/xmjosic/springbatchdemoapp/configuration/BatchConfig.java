package hr.xmjosic.springbatchdemoapp.configuration;

import hr.xmjosic.springbatchdemoapp.dao.PersonasFullRepository;
import hr.xmjosic.springbatchdemoapp.dao.PersonasRepository;
import hr.xmjosic.springbatchdemoapp.entity.Personas;
import hr.xmjosic.springbatchdemoapp.entity.PersonasFullEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final PersonasItemProcessor personasItemProcessor;

  @StepScope
  @Bean
  public FlatFileItemReader<PersonasFullEntity> reader() {
    log.info("Calling flat file item reader..");
    return new FlatFileItemReaderBuilder<PersonasFullEntity>()
        .name("mockDataReader")
        .resource(new ClassPathResource("MOCK_DATA.csv"))
        .delimited()
        .names("rnum", "first_name", "last_name", "email")
        .linesToSkip(1)
        .fieldSetMapper(
            new BeanWrapperFieldSetMapper<>() {
              {
                setTargetType(PersonasFullEntity.class);
              }
            })
        .build();
  }

  @StepScope
  @Bean
  public RepositoryItemReader<PersonasFullEntity> repositoryItemReader(
      PersonasFullRepository repository) {
    return new RepositoryItemReaderBuilder<PersonasFullEntity>()
        .name("repositoryDataReader")
        .repository(repository)
        .methodName("findAll")
        .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
        .build();
  }

  @StepScope
  @Bean
  public RepositoryItemReader<Personas> repositoryPersonasItemReader(
      PersonasRepository repository) {
    return new RepositoryItemReaderBuilder<Personas>()
        .name("repositoryDataReader")
        .repository(repository)
        .methodName("findAll")
        .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  public Step step(
      PersonasItemWriter writer,
      PersonasItemProcessor processor,
      RepositoryItemReader<PersonasFullEntity> repositoryItemReader) {
    return stepBuilderFactory
        .get("step1")
        .<PersonasFullEntity, Personas>chunk(10)
        .reader(repositoryItemReader)
        .processor(processor)
        .writer(writer)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Step stepCleanup(
      RepositoryItemReader<Personas> repositoryPersonasItemReader,
      PersonasCleanupProcessor cleanupProcessor,
      PersonasRepository repository) {
    return stepBuilderFactory
        .get("stepCleanup")
        .<Personas, Personas>chunk(10)
        .reader(repositoryPersonasItemReader)
        .processor(cleanupProcessor)
        .writer(repository::deleteAll)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Step stepZero(
      CsvPersonasImportWriter writer, FlatFileItemReader<PersonasFullEntity> reader) {
    return stepBuilderFactory
        .get("step-0")
        .<PersonasFullEntity, PersonasFullEntity>chunk(10)
        .reader(reader)
        .writer(writer)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Job job(Step step, Step stepCleanup) {
    return jobBuilderFactory.get("job1").flow(step).next(stepCleanup).end().build();
  }

  @Bean
  public Job jobZero(Step stepZero) {
    return jobBuilderFactory.get("jobZero").flow(stepZero).end().build();
  }
}
