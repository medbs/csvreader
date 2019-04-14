package com.csv.csvreader.student;

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
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration

public class CsvStudentToDbConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;


    @Bean(name="csvStudentReader")
    public FlatFileItemReader<StudentDTO> csvMarkReader(){
        FlatFileItemReader<StudentDTO> reader = new FlatFileItemReader<StudentDTO>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("students.csv"));
        reader.setLineMapper(new DefaultLineMapper<StudentDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "name" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentDTO>() {{
                setTargetType(StudentDTO.class);
            }});
        }});
        return reader;
    }

    @Bean(name="csvStudentProcessor")
    ItemProcessor<StudentDTO, StudentDTO> csvStudentProcessor() {
        return new StudentProcessor();
    }

    @Bean(name="csvStudentWriter")
    public JdbcBatchItemWriter<StudentDTO> csvStudentWriter() {
        JdbcBatchItemWriter<StudentDTO> csvStudentWriter = new JdbcBatchItemWriter<StudentDTO>();
        csvStudentWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<StudentDTO>());
        csvStudentWriter.setSql("INSERT INTO students (id, name) VALUES (:id, :name)");
        csvStudentWriter.setDataSource(dataSource);
        return csvStudentWriter;
    }

    // end reader, writer, and processor

    // begin job info
    @Bean(name="csvStudentFileToDatabaseStep")
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvStudentFileToDatabaseStep")
                .<StudentDTO, StudentDTO>chunk(1)
                .reader(csvMarkReader())
                .processor(csvStudentProcessor())
                .writer(csvStudentWriter())
                .build();
    }

    @Bean(name="csvStudentFileToDatabaseJob")
    Job csvFileToDatabaseJob(StudentJobNotificationListener listener) {
        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep())
                .end()
                .build();
    }
    // end job info


}
