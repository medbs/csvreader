package com.csv.csvreader.test;

public class TestDTO {

    private Integer id;
    private Integer courseId;
    private Integer weight;

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


    public TestDTO(Integer id, Integer courseId, Integer weight) {
        this.id = id;
        this.courseId = courseId;
        this.weight = weight;
    }

    public TestDTO() {
    }
}
