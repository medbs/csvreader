package com.csv.csvreader.student;

public class StudentDTO {

    private Integer id;
    private String name;

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

    public StudentDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudentDTO() {
    }
}
