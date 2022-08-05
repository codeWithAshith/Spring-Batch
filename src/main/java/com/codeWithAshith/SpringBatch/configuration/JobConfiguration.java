package com.codeWithAshith.SpringBatch.configuration;

import com.codeWithAshith.SpringBatch.reader.StateLessItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public StateLessItemReader stateLessItemReader() {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");

        return new StateLessItemReader(data);
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(3)
                .reader(stateLessItemReader())
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {
                        for (String item : items) {
                            System.out.println("item - " + item);
                        }
                    }
                })
                .build();
    }

    @Bean
    public Job interfaceJob(){
        return jobBuilderFactory.get("interfaceJob")
                .start(step1())
                .build();
    }

}
