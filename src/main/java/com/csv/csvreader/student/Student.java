package com.csv.csvreader.student;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "students")
public class Student  implements Serializable {

    @Id
    @GeneratedValue

    private Integer id;
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "marks",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))

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
