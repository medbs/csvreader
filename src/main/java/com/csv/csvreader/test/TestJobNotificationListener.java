package com.csv.csvreader.test;

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
public class TestJobNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(TestJobNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TestJobNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("============ JOB DONE ============ Verifying the results....\n");

            List<TestDTO> results = jdbcTemplate.query("SELECT id, course_id , weight FROM tests", new RowMapper<TestDTO>() {
                @Override
                public TestDTO mapRow(ResultSet rs, int row) throws SQLException {
                    return new TestDTO(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                }
            });

            for (TestDTO TestDTO : results) {
                log.info("Discovered <" + TestDTO + "> in the database.");
            }

        }
    }
}
