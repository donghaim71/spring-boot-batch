package com.batch.example.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.example.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchChunkConfig {
	
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    
    @Bean(name="batchChunkJob")
    public Job batcChunkJob() throws Exception{

        return jobBuilderFactory
        		.get("batchChunkJob")
        		.start(step())
                .build();

    }
    
    
    @Bean
    @JobScope
    public Step step() throws Exception {
        return stepBuilderFactory.get("Step")
                .<User, User>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
    
    
    @Bean
    @StepScope
    public JpaPagingItemReader<User> reader() throws Exception {
    	
    	log.info("ItemReader<User> reader()");

        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("userId", "1");

        return new JpaPagingItemReaderBuilder<User>()
                .pageSize(10)
                .parameterValues(parameterValues)
                .entityManagerFactory(entityManagerFactory)
                // 오류 발생 쿼리(원인 분석 필요) 
                //.queryString("SELECT userId, userName FROM User as u WHERE userId >= :userId ORDER BY userId ASC")
                .queryString("SELECT u FROM User as u WHERE user_id >= :userId ORDER BY user_id ASC")
                .name("JpaPagingItemReader")
                .build();
    }
    
    
	@Bean
	@StepScope
	public ItemProcessor<User, User> processor() {

		return user -> {
			// user.setUserName("홍길");
			log.info("ItemProcessor<User, User> processor()");
			log.info("user: {}", user);
			log.info("user: {}", user.getClass());

			return user;
		};

	}
    
    @Bean
    @StepScope
    public JpaItemWriter<User> writer(){
    	
    	log.info("JpaItemWriter<User> writer()");
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
