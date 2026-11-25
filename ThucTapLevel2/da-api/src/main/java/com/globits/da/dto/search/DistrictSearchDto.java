package com.globits.da.dto.search;

public class DistrictSearchDto {
    private Integer id;
    private String code;
    private String name;
    private Integer provinceId;

    public DistrictSearchDto() {
    }

    public DistrictSearchDto(Integer id, String code, String name, Integer provinceId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.provinceId = provinceId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}