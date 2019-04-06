package com.csv.csvreader.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tests")
public class Test  implements Serializable {

    @Id
    @GeneratedValue

    private String id;
    private String courseId;
    private String weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Test(String courseId, String weight) {
        this.courseId = courseId;
        this.weight = weight;
    }

    public Test() {
    }
}
