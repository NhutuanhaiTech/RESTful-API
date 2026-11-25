package com.globits.da.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.globits.da.domain.Province;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceDto {
    @NotNull(message = "ID Tỉnh không được để trống")
    private Integer id;
    private String code;
    private String name;
    private List<DistrictDto> district;

    public ProvinceDto() {
    }

    public ProvinceDto(Province province) {
        if (province != null) {
            this.id = province.getId();
            this.code = province.getCode();
            this.name = province.getName();
//            List<DistrictDto> list = new ArrayList<>();
//            if (province.getDistrict() != null) {
//                for (District dis : province.getDistrict()) {
//                    list.add(new DistrictDto(dis));
//                }
//                this.district = list;
//            }
        }
    }

    public List<DistrictDto> getDistrict() {
        return district;
    }

    public void setDistrict(List<DistrictDto> district) {
        this.district = district;
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
