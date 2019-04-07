package com.csv.csvreader.test;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tests")
public class Test  implements Serializable {

    @Id
    @GeneratedValue

    private Integer id;
    private Integer courseId;
    private Integer weight;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="course_id")

    @ManyToMany(mappedBy = "tests")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Test(Integer courseId, Integer weight) {
        this.courseId = courseId;
        this.weight = weight;
    }

    public Test() {
    }
}
