package com.csv.csvreader.mark;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "marks")
public class Mark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer testId;
    private Integer studentId;
    private String mark;

    public Integer getTestId() {
        return testId;
    }

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

    public Mark(Integer studentId, String mark) {
        this.studentId = studentId;
        this.mark = mark;
    }

    public Mark() {
    }
}
