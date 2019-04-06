package com.csv.csvreader.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class TestProcessor implements ItemProcessor<TestDTO, TestDTO> {

    private static final Logger log = LoggerFactory.getLogger(TestProcessor.class);

    @Override
    public TestDTO process(final TestDTO TestDTO) throws Exception {

        final String id = TestDTO.getId();
        final String courseId = TestDTO.getCourseId();
        final String weight = TestDTO.getCourseId();

        final TestDTO transformedTestDTO = new TestDTO(id, courseId, weight );

        log.info("Converting (" + TestDTO + ") into (" + transformedTestDTO + ")");

        return transformedTestDTO;
    }


}
