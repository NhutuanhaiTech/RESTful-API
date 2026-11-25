package com.globits.da.service;

import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDto> finAll();

    ProvinceDto createProvinceAndDistrict(ProvinceDto dto);

    List<ProvinceDto> searchProvinces(ProvinceSearchDto dto);

    ProvinceDto updateProvince(Integer id, ProvinceDto dto);

    Boolean deleteProvince(Integer id);
}
