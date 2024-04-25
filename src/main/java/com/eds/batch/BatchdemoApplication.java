package com.eds.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing  // this annotation will cause several beans to be registered by default within Spring IoC container
public class BatchdemoApplication {

	@Autowired
	public JobBuilderFactory jobBuilderFactory; 
	//replaced by JobBuilder
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	//replaced by StepBuilder
	
	@Bean
	public Step packageItemStep() {
		return this.stepBuilderFactory.get("packageItemStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, 
					ChunkContext chunkContext) throws Exception {
				System.out.println("The item has bean packaged.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	@Bean
	public Job deliverPackageJob() {
		return this.jobBuilderFactory.get("deliverPackageJob").start(packageItemStep()).build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BatchdemoApplication.class, args);
	}

}
