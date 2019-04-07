package com.csv.csvreader.test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
public class CsvTestToDbConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;


    @Bean(name="csvTestReader")
    public FlatFileItemReader<TestDTO> csvTestReader(){
        FlatFileItemReader<TestDTO> reader = new FlatFileItemReader<TestDTO>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("tests.csv"));
        reader.setLineMapper(new DefaultLineMapper<TestDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "courseId" , "weight" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TestDTO>() {{
                setTargetType(TestDTO.class);
            }});
        }});
        return reader;
    }

    @Bean(name="csvTestProcessor")
    ItemProcessor<TestDTO, TestDTO> csvTestProcessor() {
        return new TestProcessor();
    }

    @Bean(name="csvTestWriter")
    public JdbcBatchItemWriter<TestDTO> csvTestWriter() {
        JdbcBatchItemWriter<TestDTO> csvTestWriter = new JdbcBatchItemWriter<TestDTO>();
        csvTestWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TestDTO>());
        csvTestWriter.setSql("INSERT INTO tests (id, course_id, weight) VALUES (:id, :courseId, :weight)");
        csvTestWriter.setDataSource(dataSource);
        return csvTestWriter;
    }

    // end reader, writer, and processor

    // begin job info
    @Bean(name="csvTestFileToDatabaseStep")
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<TestDTO, TestDTO>chunk(1)
                .reader(csvTestReader())
                .processor(csvTestProcessor())
                .writer(csvTestWriter())
                .build();
    }

    @Bean(name="csvTestFileToDatabaseJob")
    Job csvFileToDatabaseJob(TestJobNotificationListener listener) {
        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep())
                .end()
                .build();
    }
    // end job info


}
