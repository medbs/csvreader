package com.csv.csvreader.course;


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
public class CsvCourseToDbConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean(name="CoursecsvCourseReader")
    public FlatFileItemReader<CourseDTO> csvCourseReader(){
        FlatFileItemReader<CourseDTO> reader = new FlatFileItemReader<CourseDTO>();
        reader.setResource(new ClassPathResource("courses.csv"));
        reader.setLineMapper(new DefaultLineMapper<CourseDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "name", "teacher" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<CourseDTO>() {{
                setTargetType(CourseDTO.class);
            }});
        }});
        return reader;
    }

    @Bean(name="CoursecsvCourseProcessor")
    ItemProcessor<CourseDTO, CourseDTO> csvCourseProcessor() {
        return new CourseProcessor();
    }

    @Bean(name="CoursecsvCourseWriter")
    public JdbcBatchItemWriter<CourseDTO> csvCourseWriter() {
        JdbcBatchItemWriter<CourseDTO> csvCourseWriter = new JdbcBatchItemWriter<CourseDTO>();
        csvCourseWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<CourseDTO>());
        csvCourseWriter.setSql("INSERT INTO courses (id, name, teacher) VALUES (:id, :name, :teacher)");
        csvCourseWriter.setDataSource(dataSource);
        return csvCourseWriter;
    }

    // end reader, writer, and processor

    // begin job info
    @Bean(name="CoursecsvFileToDatabaseStep")
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<CourseDTO, CourseDTO>chunk(1)
                .reader(csvCourseReader())
                .processor(csvCourseProcessor())
                .writer(csvCourseWriter())
                .build();
    }

    @Bean(name="CoursecsvFileToDatabaseJob")
    Job csvFileToDatabaseJob(CourseJobNotificationListener listener) {
        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep())
                .end()
                .build();
    }
    // end job info



}
