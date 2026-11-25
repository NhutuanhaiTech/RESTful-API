package com.globits.da.service;


import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.search.CommuneSearchDto;

import java.util.List;

public interface CommuneService {
    List<CommuneDto> finAll();

    CommuneDto createCommune(CommuneDto dto);

    List<CommuneDto> searchCommunes(CommuneSearchDto dto);

    CommuneDto updateCommune(Integer id, CommuneDto dto);

    Boolean deleteCommune(Integer id);
}
