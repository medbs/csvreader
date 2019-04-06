package com.csv.csvreader.mark;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MarkDTO {

    private String testId;
    private String studentId;
    private String mark;

    public String getTestId() { return testId;  }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
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

    public MarkDTO(String testId, String studentId, String mark) {
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
