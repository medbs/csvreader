package com.csv.csvreader.mark;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MarkDTO {

    private Integer testId;
    private Integer studentId;
    private String mark;

    public Integer getTestId() { return testId;  }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }


    public MarkDTO() {
    }

    public MarkDTO(Integer testId, Integer studentId, String mark) {
        this.testId = testId;
        this.studentId = studentId;
        this.mark = mark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("testId", this.testId)
                .append("studentId", this.studentId)
                .append("mark", this.mark)
                .toString();
    }

}
