package hr.xmjosic.springbatchdemoapp.configuration;

import hr.xmjosic.springbatchdemoapp.dao.PersonasFullRepository;
import hr.xmjosic.springbatchdemoapp.entity.PersonasFullEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.stereotype.Component;

@StepScope
@Slf4j
@Component
@RequiredArgsConstructor
public class PersonasItemReader implements ItemReader<PersonasFullEntity> {

  private final PersonasFullRepository repository;

  @Override
  public PersonasFullEntity read() {
    log.info("Personas full entity read..");
    return new IteratorItemReader<>(repository.findAll()).read();
  }
}
