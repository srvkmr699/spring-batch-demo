package com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.tasks.MyTaskOne;
import com.batch.tasks.MyTaskTwo;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobs;
	
	@Autowired
	private StepBuilderFactory steps;
	
	@Bean
	public Step stepOne() {
		return steps.get("Task one").tasklet(new MyTaskOne()).build();
	}
	
	@Bean
	public Step stepTwo() {
		return steps.get("Task two").tasklet(new MyTaskTwo()).build();
	}
	
	@Bean
	public Job demoJob() {
		return jobs.get("Demo job")
				.incrementer(new RunIdIncrementer())
				.start(stepOne())
				.next(stepTwo())
				.build();
	}
}
