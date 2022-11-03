package hr.xmjosic.springbatchdemoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchController {

  private final JobLauncher jobLauncher;
  private final Job job;
  private final Job jobZero;

  @PostMapping("/start")
  public void start() {
    try {
      jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
    } catch (JobExecutionAlreadyRunningException
        | JobRestartException
        | JobInstanceAlreadyCompleteException
        | JobParametersInvalidException e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping("/start/zero")
  public void startZero() {
    try {
      jobLauncher.run(jobZero, new JobParametersBuilder().toJobParameters());
    } catch (JobExecutionAlreadyRunningException
        | JobRestartException
        | JobInstanceAlreadyCompleteException
        | JobParametersInvalidException e) {
      throw new RuntimeException(e);
    }
  }
}
