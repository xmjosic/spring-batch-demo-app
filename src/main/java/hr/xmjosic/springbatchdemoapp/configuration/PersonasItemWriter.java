package hr.xmjosic.springbatchdemoapp.configuration;

import hr.xmjosic.springbatchdemoapp.dao.PersonasRepository;
import hr.xmjosic.springbatchdemoapp.entity.Personas;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonasItemWriter implements ItemWriter<Personas> {

  private final PersonasRepository repository;

  @Override
  public void write(List<? extends Personas> list) {
    log.info("Personas Item writer..");
    repository.saveAll(list);
  }
}
