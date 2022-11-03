package hr.xmjosic.springbatchdemoapp.dao;

import hr.xmjosic.springbatchdemoapp.entity.PersonasFullEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonasFullRepository
    extends PagingAndSortingRepository<PersonasFullEntity, String> {}
