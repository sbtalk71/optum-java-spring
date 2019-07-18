package com.demo.spring;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.demo.spring.batch.Emp;
import com.demo.spring.batch.EmpItemProcessor;
import com.demo.spring.batch.EmpMapper;

@Configuration
@ComponentScan(basePackages = "com.demo.spring")
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilder;

	@Autowired
	private StepBuilderFactory stepBuilder;

	@Value("input/employees.csv")
	Resource inputFile;

	@Value("file:xml/employees.xml")
	Resource outputFile;

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/springdb");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}

	@Bean
	public ItemReader<Emp> itemReader() {
		JdbcCursorItemReader<Emp> reader = new JdbcCursorItemReader<>();
		reader.setRowMapper(new EmpMapper());
		reader.setDataSource(dataSource());
		reader.setSql("select * from emp");
		return reader;

	}

	@Bean
	public ItemProcessor<Emp, Emp> itemProcessor() {
		return new EmpItemProcessor();
	}

	@Bean
	public ItemWriter<Emp> itemWriter() {
		StaxEventItemWriter<Emp> writer = new StaxEventItemWriter<>();
		writer.setRootTagName("employee-data");
		writer.setMarshaller(marshaller());
		writer.setResource(outputFile);
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
