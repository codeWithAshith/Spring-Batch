package com.codeWithAshith.SpringBatch.configuration;


import com.codeWithAshith.SpringBatch.data.Person;
import com.codeWithAshith.SpringBatch.data.PersonRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;
    @Bean
    public JdbcCursorItemReader<Person> cursorItemReader(){
        JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<Person>();

        reader.setSql("SELECT id, name, location, birth_date FROM person");
        reader.setDataSource(dataSource);
        reader.setRowMapper(new PersonRowMapper());

        return reader;
    }

    @Bean
    public ItemWriter<Person> personItemWriter(){
        return new ItemWriter<Person>() {
            @Override
            public void write(List<? extends Person> items) throws Exception {
                for(Person person: items){
                    System.out.println(person.toString());
                }
            }
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(cursorItemReader())
                .writer(personItemWriter())
                .build();
    }

    @Bean
    public Job interfaceJob(){
        return jobBuilderFactory.get("interfaceJob")
                .start(step1())
                .build();
    }
}
