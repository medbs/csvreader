package com.csv.csvreader.mark;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "marks")
public class Mark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String testId;
    private String studentId;
    private String mark;

    public String getTestId() {
        return testId;
    }

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

    public Mark(String studentId, String mark) {
        this.studentId = studentId;
        this.mark = mark;
    }

    public Mark() {
    }
}
