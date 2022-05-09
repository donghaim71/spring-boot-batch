package com.batch.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.example.tasklet.SearchBatchTasklet;
import com.batch.example.tasklet.UpdateBatchTasklet;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchTaskletConfig{

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    
    private final SearchBatchTasklet searchBatchTasklet;
    private final UpdateBatchTasklet updateBatchTasklet;

    @Bean
    public Job batchTaskletJob(){

        return jobBuilderFactory
        		.get("batchTaskletJob")
                .start(searchBatchStep())
                .next(updateBatchStep())
                .build();

    }


    @Bean
    @JobScope
    public Step searchBatchStep() {
		return stepBuilderFactory
				.get("searchBatchStep")
				.tasklet(searchBatchTasklet)
				.build();
    }
    
    
    @Bean
    @JobScope
    public Step updateBatchStep() {
		return stepBuilderFactory
				.get("updateBatchStep")
				.tasklet(updateBatchTasklet)
				.build();
    }
 
}