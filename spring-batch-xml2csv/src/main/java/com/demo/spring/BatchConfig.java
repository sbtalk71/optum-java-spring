package com.demo.spring;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.demo.spring.batch.Emp;
import com.demo.spring.batch.EmpFieldSetMapper;
import com.demo.spring.batch.EmpItemProcessor;

@Configuration
@ComponentScan(basePackages = "com.demo.spring")
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilder;

	@Autowired
	private StepBuilderFactory stepBuilder;

	@Value("input/employees.xml")
	Resource inputFile;

	@Value("file:csv/employees.csv")
	Resource outputFile;

	@Bean
	public ItemReader<Emp> itemReader() {
		StaxEventItemReader<Emp> reader = new StaxEventItemReader<>();
		reader.setFragmentRootElementName("emp");
		reader.setUnmarshaller(marshaller());
		reader.setResource(inputFile);
		return reader;
	}

	@Bean
	public ItemProcessor<Emp, Emp> itemProcessor() {
		return new EmpItemProcessor();
	}

	@Bean
	public ItemWriter<Emp> itemWriter() {
		FlatFileItemWriter<Emp> writer = new FlatFileItemWriter<>();
		writer.setResource(outputFile);

		BeanWrapperFieldExtractor<Emp> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "empId", "name", "city", "salary" });

		DelimitedLineAggregator<Emp> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");
		lineAggregator.setFieldExtractor(fieldExtractor);

		writer.setLineAggregator(lineAggregator);
		return writer;
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller m = new Jaxb2Marshaller();
		m.setClassesToBeBound(Emp.class);
		return m;
	}

	@Bean
	public Step step1() {
		return stepBuilder.get("Step1").<Emp, Emp>chunk(4).reader(itemReader()).processor(itemProcessor())
				.writer(itemWriter()).build();
	}

	@Bean
	public Job job() {
		return jobBuilder.get("helloworld Job").start(step1()).build();
	}

}
