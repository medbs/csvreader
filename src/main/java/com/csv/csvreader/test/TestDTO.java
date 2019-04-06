package com.csv.csvreader.test;

public class TestDTO {

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


    public TestDTO(String id, String courseId, String weight) {
        this.id = id;
        this.courseId = courseId;
        this.weight = weight;
    }

    public TestDTO() {
    }
}
