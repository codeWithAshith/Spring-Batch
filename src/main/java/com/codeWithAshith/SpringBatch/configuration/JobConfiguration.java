package com.codeWithAshith.SpringBatch.configuration;


import com.codeWithAshith.SpringBatch.data.Employee;
import com.codeWithAshith.SpringBatch.data.EmployeeMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Employee> cursorItemReader() {
        //Create reader instance
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();
        reader.setResource(new ClassPathResource("employee.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] { "id", "firstName", "lastName" });

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new EmployeeMapper());
        lineMapper.afterPropertiesSet();

        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public ItemWriter<Employee> personItemWriter() {
        return new ItemWriter<Employee>() {
            @Override
            public void write(List<? extends Employee> items) throws Exception {
                for (Employee employee : items) {
                    System.out.println(employee.toString());
                }
            }
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Employee, Employee>chunk(10)
                .reader(cursorItemReader())
                .writer(personItemWriter())
                .build();
    }

    @Bean
    public Job interfaceJob() {
        return jobBuilderFactory.get("interfaceJob")
                .start(step1())
                .build();
    }
}
