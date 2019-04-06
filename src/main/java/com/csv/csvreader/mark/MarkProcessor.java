package com.csv.csvreader.mark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MarkProcessor  implements ItemProcessor<MarkDTO, MarkDTO> {

    private static final Logger log = LoggerFactory.getLogger(MarkProcessor.class);

    @Override
    public MarkDTO process(final MarkDTO MarkDTO) throws Exception {

        final String testId = MarkDTO.getTestId();
        final String studentId = MarkDTO.getStudentId();
        final String mark = MarkDTO.getMark();

        final MarkDTO transformedMarkDTO = new MarkDTO(testId, studentId, mark);

        log.info("Converting (" + MarkDTO + ") into (" + transformedMarkDTO + ")");

        return transformedMarkDTO;
    }
}
