package com.demo.spring;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.demo.spring.batch.MockItemGenerator;
import com.demo.spring.batch.MyWordPrinter;
import com.demo.spring.batch.MyWordProcessor;

@Configuration
@ComponentScan(basePackages="com.demo.spring")
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilder;
	
	@Autowired
	private StepBuilderFactory stepBuilder;
	
	@Bean
	public ItemReader<String> itemReader(){
		return new MockItemGenerator();
	}
	
	@Bean
	public ItemProcessor<String, String> itemProcessor(){
		return new MyWordProcessor();
	}
	
	@Bean
	public ItemWriter<String> itemWriter(){
		return new MyWordPrinter();
	}
	
	@Bean
	public Step step1() {
		return stepBuilder.get("Step1")
		.<String,String>chunk(4)
		.reader(itemReader())
		.processor(itemProcessor())
		.writer(itemWriter())
		.build();
	}
	
	@Bean
	public Job job() {
		return jobBuilder.get("helloworld Job").start(step1()).build();
	}
	
	
	
	
	
}
