package com.globits.da.dto;

public class MyFirstApiDto {
    private String code;
    private String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public MyFirstApiDto() {
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }

}
