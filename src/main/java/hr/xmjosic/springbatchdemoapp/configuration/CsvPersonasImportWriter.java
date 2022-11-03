package hr.xmjosic.springbatchdemoapp.configuration;

import hr.xmjosic.springbatchdemoapp.dao.PersonasFullRepository;
import hr.xmjosic.springbatchdemoapp.entity.PersonasFullEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvPersonasImportWriter implements ItemWriter<PersonasFullEntity> {

    private final PersonasFullRepository repository;
    @Override
    public void write(List<? extends PersonasFullEntity> list) throws Exception {
        log.info("CsvPersonasImport Item writer..");
        repository.saveAll(list);
    }
}
