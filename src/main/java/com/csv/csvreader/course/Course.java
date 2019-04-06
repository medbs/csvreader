package com.csv.csvreader.course;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

    @Id
    @GeneratedValue

    private String id;
    private String name;
    private String teacher;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Course(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public Course() {
    }
}
