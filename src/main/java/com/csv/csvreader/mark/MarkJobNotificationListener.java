package com.csv.csvreader.mark;

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
public class MarkJobNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(MarkJobNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MarkJobNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("============ JOB DONE ============ Verifying the results....\n");

            List<MarkDTO> results = jdbcTemplate.query("SELECT test_id, student_id , mark FROM marks", new RowMapper<MarkDTO>() {
                @Override
                public MarkDTO mapRow(ResultSet rs, int row) throws SQLException {
                    return new MarkDTO(rs.getString(1), rs.getString(2), rs.getString(3));
                }
            });

            for (MarkDTO MarkDTO : results) {
                log.info("Discovered <" + MarkDTO + "> in the database.");
            }

        }
    }
}
