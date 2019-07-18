package com.demo.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
		Job job = (Job) ctx.getBean("firstBatchJob");

		System.out.println("Starting the batch job");
		try {
			// To enable multiple execution of a job with the same parameters
			JobParameters jobParameters = new JobParametersBuilder()
					.addString("jobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
			final JobExecution execution = jobLauncher.run(job, jobParameters);
			System.out.println("Job Status : " + execution.getStatus());
			System.out.println("Job succeeded");
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Job failed");
		}

	}

}
