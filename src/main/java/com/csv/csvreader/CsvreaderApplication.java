package com.csv.csvreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CsvreaderApplication {


    public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        ApplicationContext context = SpringApplication.run(CsvreaderApplication.class, args);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");

        Job studentJob = (Job) context.getBean("csvStudentFileToDatabaseJob");
        Job courseJob = (Job) context.getBean("csvCourseFileToDatabaseJob");
        Job testJob = (Job) context.getBean("csvTestFileToDatabaseJob");
        Job markJob = (Job) context.getBean("csvMarkFileToDatabaseJob");

        JobExecution studentJobExecution = jobLauncher.run(studentJob, new JobParameters());
        System.out.println("Status : " + studentJobExecution.getStatus());

        JobExecution courseJobExecution = jobLauncher.run(courseJob, new JobParameters());
        System.out.println(" Status : " + courseJobExecution.getStatus());

        JobExecution testJobExecution = jobLauncher.run(testJob, new JobParameters());
        System.out.println(" Status : " + testJobExecution.getStatus());

        JobExecution markJobExecution = jobLauncher.run(markJob, new JobParameters());
        System.out.println(" Status : " + markJobExecution.getStatus());

    }
}
