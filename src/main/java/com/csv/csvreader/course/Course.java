package com.csv.csvreader.course;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Integer id;
    private String name;
    private String teacher;


    @OneToMany(
            mappedBy = "courses",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
