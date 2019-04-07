package com.csv.csvreader.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CourseProcessor implements ItemProcessor<CourseDTO, CourseDTO> {

    private static final Logger log = LoggerFactory.getLogger(CourseProcessor.class);

    @Override
    public CourseDTO process(final CourseDTO CourseDTO) throws Exception {

        final Integer id = CourseDTO.getId();
        final String name = CourseDTO.getName();
        final String teacher = CourseDTO.getTeacher();

        final CourseDTO transformedCourseDTO = new CourseDTO(id, name, teacher);

        log.info("Converting (" + CourseDTO + ") into (" + transformedCourseDTO + ")");

        return transformedCourseDTO;
    }
}
