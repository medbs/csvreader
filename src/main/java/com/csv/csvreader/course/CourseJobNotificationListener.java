package com.csv.csvreader.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseJobNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(CourseJobNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseJobNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("============ JOB DONE ============ Verifying the results....\n");

            List<CourseDTO> results = jdbcTemplate.query("SELECT id, name, teacher FROM courses", new RowMapper<CourseDTO>() {
                @Override
                public CourseDTO mapRow(ResultSet rs, int row) throws SQLException {
                    return new CourseDTO(rs.getString(1), rs.getString(2), rs.getString(3));
                }
            });

            for (CourseDTO CourseDTO : results) {
                log.info("Discovered <" + CourseDTO + "> in the database.");
            }

        }
    }
}
