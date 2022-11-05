package hr.xmjosic.springbatchdemoapp.converter;

import hr.xmjosic.springbatchdemoapp.entity.Personas;
import hr.xmjosic.springbatchdemoapp.entity.PersonasFullEntity;
import hr.xmjosic.springbatchdemoapp.util.HashProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Lazy
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class PersonasFullToPersonasConverter implements Converter<PersonasFullEntity, Personas> {

  private final HashProcessor hashProcessor;

  @Override
  public Personas convert(PersonasFullEntity source) {
    Personas retVal = new Personas();
    retVal.setFirstName(source.getFirstName());
    retVal.setLastName(source.getLastName());
    retVal.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    retVal.setHash(hashProcessor.hashObject(source));
    retVal.setRnum(source.getRnum());
    return retVal;
  }
}
