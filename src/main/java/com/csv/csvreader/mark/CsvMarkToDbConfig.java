package com.csv.csvreader.mark;

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
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration

public class CsvMarkToDbConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean(name="csvMarkReader")
    public FlatFileItemReader<MarkDTO> csvMarkReader(){
        FlatFileItemReader<MarkDTO> reader = new FlatFileItemReader<MarkDTO>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("marks.csv"));
        reader.setLineMapper(new DefaultLineMapper<MarkDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "testId", "studentId", "mark" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<MarkDTO>() {{
                setTargetType(MarkDTO.class);
            }});
        }});
        return reader;
    }

    @Bean(name="csvMarkProcessor")
    ItemProcessor<MarkDTO, MarkDTO> csvMarkProcessor() {
        return new MarkProcessor();
    }

    @Bean(name="csvMarkWriter")
    public JdbcBatchItemWriter<MarkDTO> csvMarkWriter() {
        JdbcBatchItemWriter<MarkDTO> csvMarkWriter = new JdbcBatchItemWriter<MarkDTO>();
        csvMarkWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<MarkDTO>());
        csvMarkWriter.setSql("INSERT INTO marks (test_id, student_id, mark) VALUES (:testId, :studentId, :mark)");
        csvMarkWriter.setDataSource(dataSource);
        return csvMarkWriter;
    }

    // end reader, writer, and processor

    // begin job info
    @Bean(name="csvMarkFileToDatabaseStep")
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvMarkFileToDatabaseStep")
                .<MarkDTO, MarkDTO>chunk(1)
                .reader(csvMarkReader())
                .processor(csvMarkProcessor())
                .writer(csvMarkWriter())
                .build();
    }

    @Bean(name="csvMarkFileToDatabaseJob")
    Job csvFileToDatabaseJob(MarkJobNotificationListener listener) {
        return jobBuilderFactory.get("csvMarkFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep())
                .end()
                .build();
    }
    // end job info


}
