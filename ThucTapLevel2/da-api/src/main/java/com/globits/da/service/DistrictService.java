package com.globits.da.service;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.DistrictSearchDto;

import java.util.List;

public interface DistrictService {
    List<DistrictDto> finAll();

    DistrictDto createDistrict(DistrictDto dto);

    List<DistrictDto> searchDistricts(DistrictSearchDto dto);

    DistrictDto updateDistrict(Integer id, DistrictDto dto);

    Boolean deleteDistrict(Integer id);
}
