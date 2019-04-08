package com.csv.csvreader.student;

import com.csv.csvreader.mark.Mark;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Integer id;
    private String name;
    @OneToMany(mappedBy = "student")
    Set<Mark> marks;


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

    public Student(String name) {
        this.name = name;
    }

    public Student() {
    }
}
