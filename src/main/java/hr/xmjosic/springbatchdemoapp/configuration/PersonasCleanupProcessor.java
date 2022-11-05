package hr.xmjosic.springbatchdemoapp.configuration;

import hr.xmjosic.springbatchdemoapp.entity.Personas;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class PersonasCleanupProcessor implements ItemProcessor<Personas, Personas> {

  @Value("#{jobParameters['jobId']}")
  long jobIdParam;

  @Override
  public Personas process(Personas personas) {
    return jobIdParam == personas.getJobID() ? null : personas;
  }
}
