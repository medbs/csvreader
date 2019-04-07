package com.csv.csvreader.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor implements ItemProcessor<StudentDTO, StudentDTO> {

    private static final Logger log = LoggerFactory.getLogger(StudentProcessor.class);

    @Override
    public StudentDTO process(final StudentDTO StudentDTO) throws Exception {

        final Integer id = StudentDTO.getId();
        final String studentName = StudentDTO.getName();

        final StudentDTO transformedStudentDTO = new StudentDTO(id, studentName);

        log.info("Converting (" + StudentDTO + ") into (" + transformedStudentDTO + ")");

        return transformedStudentDTO;
    }
}
