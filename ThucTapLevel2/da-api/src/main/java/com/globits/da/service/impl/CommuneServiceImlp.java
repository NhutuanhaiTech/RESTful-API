package com.globits.da.service.impl;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.search.CommuneSearchDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommuneServiceImlp implements CommuneService {
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<CommuneDto> finAll() {
        List<Commune> commune = communeRepository.findAll();
        List<CommuneDto> list = new ArrayList<>();
        for (Commune c : commune) {
            list.add(new CommuneDto(c));
        }
        return list;
    }

    @Override
    @Transactional
    public CommuneDto createCommune(CommuneDto dto) {
        Commune commune = new Commune();
        if (dto != null) {
            if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
                commune.setName(dto.getName().trim());
            }
            if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
                commune.setCode(dto.getCode().trim());
            }
            if (dto.getDistrict() != null && dto.getDistrict().getId() != null) {
                District findDistrict = districtRepository.findById(dto.getDistrict().getId()).orElse(null);
                if (findDistrict != null) {
                    commune.setDistrict(findDistrict);
                    findDistrict.getCommune().add(commune);
                }
            }
            communeRepository.save(commune);
            return new CommuneDto(commune);
        }
        return null;
    }

    @Override
    public List<CommuneDto> searchCommunes(CommuneSearchDto dto) {
        Integer id = (dto != null) ? dto.getId() : null;
        String code = (dto != null && dto.getCode() != null && !dto.getCode().trim().isEmpty()) ? dto.getCode().trim() : null;
        String name = (dto != null && dto.getName() != null && !dto.getName().trim().isEmpty()) ? dto.getName().trim() : null;
        Integer idDistrict = (dto != null) ? dto.getDistrictId() : null;
        List<Commune> communeList = communeRepository.searchCommune(id, code, name, idDistrict);
        List<CommuneDto> list = new ArrayList<>();
        for (Commune c : communeList) {
            list.add(new CommuneDto(c));
        }
        return list;
    }

    @Override
    public CommuneDto updateCommune(Integer id, CommuneDto dto) {
        Commune commune = communeRepository.findById(id).orElse(null);
        if (commune != null) {
            if (dto != null) {
                if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
                    commune.setName(dto.getName().trim());
                }
                if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
                    commune.setCode(dto.getCode().trim());
                }
                communeRepository.save(commune);
                return new CommuneDto(commune);
            }
        }
        return null;
    }

    @Override
    public Boolean deleteCommune(Integer id) {
        Commune commune = communeRepository.findById(id).orElse(null);
        if (commune != null) {
            communeRepository.delete(commune);
            return true;
        }
        return false;
    }
}
