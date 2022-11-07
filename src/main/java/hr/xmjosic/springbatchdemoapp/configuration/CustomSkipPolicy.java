package hr.xmjosic.springbatchdemoapp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

@Slf4j
public class CustomSkipPolicy implements SkipPolicy {

  @Override
  public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
    log.info("Exception occurred {}", throwable.getMessage());
    return true;
  }
}
