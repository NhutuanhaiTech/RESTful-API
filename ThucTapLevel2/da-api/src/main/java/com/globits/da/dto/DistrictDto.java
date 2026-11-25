package com.globits.da.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.globits.da.domain.District;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrictDto {
    @NotNull(message = "ID Huyện không được để trống")
    private Integer id;
    private String code;
    private String name;
    private ProvinceDto province;
    private List<CommuneDto> commune;

    public DistrictDto() {
    }

    public DistrictDto(District distric) {
        if (distric != null) {
            this.id = distric.getId();
            this.code = distric.getCode();
            this.name = distric.getName();
//            if (distric.getProvince() != null) {
//                ProvinceDto provinceDto = new ProvinceDto();
//                provinceDto.setId(distric.getProvince().getId());
//                provinceDto.setCode(distric.getProvince().getCode());
//                provinceDto.setName(distric.getProvince().getName());
//                this.province = provinceDto;
//            }
//            List<CommuneDto> list = new ArrayList<>();
//            if (distric.getCommune() != null) {
//                for (Commune com : distric.getCommune()) {
//                    list.add(new CommuneDto(com));
//                }
//                this.commune = list;
//            }
        }
    }

    public List<CommuneDto> getCommune() {
        return commune;
    }

    public void setCommune(List<CommuneDto> commune) {
        this.commune = commune;
    }

    public ProvinceDto getProvince() {
        return province;
    }

    public void setProvince(ProvinceDto province) {
        this.province = province;
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