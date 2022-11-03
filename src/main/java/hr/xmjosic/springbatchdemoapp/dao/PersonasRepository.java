package hr.xmjosic.springbatchdemoapp.dao;

import hr.xmjosic.springbatchdemoapp.entity.Personas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonasRepository extends JpaRepository<Personas, Integer> {

  @Query("select p from Personas p where p.rnum = :rnum")
  Optional<Personas> findByRnum(@Param("rnum") String rnum);
}
