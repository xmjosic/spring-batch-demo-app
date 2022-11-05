package hr.xmjosic.springbatchdemoapp.configuration;

import hr.xmjosic.springbatchdemoapp.converter.PersonasFullToPersonasConverter;
import hr.xmjosic.springbatchdemoapp.dao.PersonasRepository;
import hr.xmjosic.springbatchdemoapp.entity.Personas;
import hr.xmjosic.springbatchdemoapp.entity.PersonasFullEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@StepScope
@Component
@Slf4j
@RequiredArgsConstructor
public class PersonasItemProcessor implements ItemProcessor<PersonasFullEntity, Personas> {
  private final PersonasFullToPersonasConverter converter;
  private final PersonasRepository repository;

  @Value("#{jobParameters['jobId']}")
  long jobIdParam;

  private List<Personas> personasList;

  @Override
  public Personas process(PersonasFullEntity personas) {
    log.info("Personas item processor..");
    Personas convert = converter.convert(personas);
    assert convert != null;
    convert.setJobID(jobIdParam);
    if (isNull(personasList)) personasList = repository.findAll();
    if (!CollectionUtils.isEmpty(personasList)) {
      Personas personasFromDb =
          personasList.stream()
              .filter(Objects::nonNull)
              .filter(personas1 -> personas1.getRnum().equals(personas.getRnum()))
              .findFirst()
              .orElse(null);

      if (nonNull(personasFromDb)) {
        if (personasFromDb.getHash().equals(convert.getHash())) {
          personasFromDb.setJobID(jobIdParam);
          repository.save(personasFromDb);
          return null;
        } else {
          convert.setId(personasFromDb.getId());
          convert.setCreatedAt(personasFromDb.getCreatedAt());
          convert.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
          repository.save(convert);
        }
      }
    }
    return convert;
  }
}
