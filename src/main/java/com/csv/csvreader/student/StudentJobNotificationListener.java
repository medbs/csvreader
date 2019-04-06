package com.csv.csvreader.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class StudentJobNotificationListener extends JobExecutionListenerSupport {


    private static final Logger log = LoggerFactory.getLogger(StudentJobNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentJobNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("============ JOB DONE ============ Verifying the results....\n");

            List<StudentDTO> results = jdbcTemplate.query("SELECT id, name  FROM students", new RowMapper<StudentDTO>() {
                @Override
                public StudentDTO mapRow(ResultSet rs, int row) throws SQLException {
                    return new StudentDTO(rs.getString(1), rs.getString(2));
                }
            });

            for (StudentDTO StudentDTO : results) {
                log.info("Discovered <" + StudentDTO + "> in the database.");
            }

        }
    }
}
