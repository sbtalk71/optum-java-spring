package com.demo.spring.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.demo.spring.batch.entity.Emp;

@Configuration
@ComponentScan("com.demo.spring.batch")
@EnableBatchProcessing
public class AppConfig {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Value("input/records.csv")
	private Resource inputFile;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/springdb");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}

	@Bean
	public JobRepository jobRepository(PlatformTransactionManager txm) throws Exception {
		JobRepositoryFactoryBean repoBean = new JobRepositoryFactoryBean();
		repoBean.setDataSource(dataSource());
		repoBean.setTransactionManager(txm);
		repoBean.afterPropertiesSet();
		return repoBean.getObject();
	}

	@Bean
	JobLauncher jobLauncher(JobRepository repo) {
		SimpleJobLauncher jl = new SimpleJobLauncher();
		jl.setJobRepository(repo);
		return jl;
	}

	@Bean
	public JdbcBatchItemWriter<Emp> writer() {
		JdbcBatchItemWriter<Emp> itemWriter = new JdbcBatchItemWriter<Emp>();
		itemWriter.setDataSource(dataSource());
		itemWriter.setSql("INSERT INTO EMP (EMPNO, NAME, ADDRESS,SALARY) VALUES (:empId, :name, :city,:salary)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Emp>());
		return itemWriter;
	}

	@Bean
	public ItemReader<Emp> itemReader() throws UnexpectedInputException, ParseException {

		FlatFileItemReader<Emp> itemReader = new FlatFileItemReader<Emp>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		String[] tokens = { "empId", "name", "city", "salary" };
		tokenizer.setNames(tokens);
		itemReader.setResource(inputFile);
		DefaultLineMapper<Emp> lineMapper = new DefaultLineMapper<Emp>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}

	@Bean
	public ItemProcessor<Emp, Emp> itemProcessor() {
		return new EmpProcessor();
	}

	@Bean
	@Qualifier("mystep1")
	public Step step1(ItemReader<Emp> itemReader, ItemProcessor<Emp, Emp> itemProcessor, ItemWriter<Emp> writer) {
		return steps.get("step1")
				.<Emp, Emp>chunk(4)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(writer)
				.faultTolerant()
				.retry(Exception.class)
				.retryLimit(4)
				.build();
	}

	@Bean(name = "firstBatchJob")
	public Job job(@Qualifier("mystep1") Step step1) {
		return jobs.get("firstBatchJob").start(step1).build();
	}

}
