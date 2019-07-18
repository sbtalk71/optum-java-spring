package com.demo.spring;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BatchMain {

	public static void main(String[] args) throws Exception{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(BatchConfig.class);

		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);

		Job job = (Job) ctx.getBean("job");

		JobParameters params = new JobParametersBuilder()
				.addString("jobId", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();

		JobExecution exec = jobLauncher.run(job, params);

	}

}
