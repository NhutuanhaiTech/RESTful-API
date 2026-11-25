package com.globits.da.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.globits.da.domain.Commune;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommuneDto {
    @NotNull(message = "ID Tỉnh không được để trống")
    private Integer id;
    private String code;
    private String name;
    private DistrictDto district;

    public CommuneDto() {
    }

    public CommuneDto(Commune commune) {
        if (commune != null) {
            this.id = commune.getId();
            this.code = commune.getCode();
            this.name = commune.getName();
//            if (commune.getDistrict() != null) {
//                DistrictDto district = new DistrictDto();
//                district.setId(commune.getDistrict().getId());
//                district.setCode(commune.getDistrict().getCode());
//                district.setName(commune.getDistrict().getName());
//                this.district = district;
//            }
        }
    }

    public DistrictDto getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDto districtDto) {
        this.district = districtDto;
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
