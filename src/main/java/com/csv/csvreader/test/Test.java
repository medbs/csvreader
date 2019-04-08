package com.csv.csvreader.test;

import com.csv.csvreader.course.Course;
import com.csv.csvreader.mark.Mark;
import com.csv.csvreader.student.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tests")
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer weight;


    @OneToMany(mappedBy = "test")
    Set<Mark> marks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Test(Integer weight) {
        this.weight = weight;
    }

    public Test() {
    }
}
