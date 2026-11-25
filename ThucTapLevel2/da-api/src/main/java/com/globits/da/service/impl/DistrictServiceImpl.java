package com.globits.da.service.impl;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CommuneRepository communeRepository;

    @Override
    public Boolean deleteDistrict(Integer id) {
        District district = districtRepository.findById(id).orElse(null);
        if (district != null) {
            districtRepository.delete(district);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public DistrictDto updateDistrict(Integer id, DistrictDto dto) {
        if (id == null || dto == null) {
            return null;
        }
        District findDistrict = districtRepository.findById(id).orElse(null);
        if (findDistrict == null) {
            return null;
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            findDistrict.setName(dto.getName().trim());
        }
        if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
            findDistrict.setCode(dto.getCode().trim());
        }
        if (dto.getProvince() != null && dto.getProvince().getId() != null) {
            Province province = provinceRepository.findById(dto.getProvince().getId()).orElse(null);
            if (province != null) {
                findDistrict.setProvince(province);
            }
        }
        if (dto.getCommune() != null) {
            for (CommuneDto communeDto : dto.getCommune()) {
                if (communeDto.getId() == null) {
                    // check xem id có tồn tại hay không , nếu không có thì là update
                    Commune commune = new Commune();
                    if (communeDto.getName() != null && !communeDto.getName().trim().isEmpty()) {
                        commune.setName(communeDto.getName().trim());
                    }
                    if (communeDto.getCode() != null && !communeDto.getCode().trim().isEmpty()) {
                        commune.setCode(communeDto.getCode().trim());
                    }
                    commune.setDistrict(findDistrict);
                    findDistrict.getCommune().add(commune);
                } else {
                    // nếu có thì là thêm mới
                    Commune communeUpdate = communeRepository.findById(communeDto.getId()).orElse(null);
                    if (communeUpdate != null) {
                        if (communeDto.getName() != null && !communeDto.getName().trim().isEmpty()) {
                            communeUpdate.setName(communeDto.getName().trim());
                        }
                        if (communeDto.getCode() != null && !communeDto.getCode().trim().isEmpty()) {
                            communeUpdate.setCode(communeDto.getCode().trim());
                        }
                        communeUpdate.setDistrict(findDistrict);
                    }
                }
            }
        }
        districtRepository.save(findDistrict);
        return new DistrictDto(districtRepository.findById(id).orElse(null));
    }

    @Override
    public List<DistrictDto> searchDistricts(DistrictSearchDto dto) {
        Integer id = (dto != null) ? dto.getId() : null;
        Integer idProvince = (dto != null) ? dto.getProvinceId() : null;
        String code = (dto != null && dto.getCode() != null && !dto.getCode().trim().isEmpty()) ? dto.getCode().trim() : null;
        String name = (dto != null && dto.getName() != null && !dto.getName().trim().isEmpty()) ? dto.getName().trim() : null;
        List<DistrictDto> list = new ArrayList<>();
        for (District dis : districtRepository.searchDistrict(id, code, name, idProvince)) {
            list.add(new DistrictDto(dis));
        }
        return list;
    }

    @Override
    @Transactional
    public DistrictDto createDistrict(DistrictDto dto) {
        if (dto == null) {
            return null;
        }
        District district = new District();
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            district.setName(dto.getName().trim());
        }
        if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
            district.setCode(dto.getCode().trim());
        }
        if (dto.getProvince() != null && dto.getProvince().getId() != null) {
            Province province = provinceRepository.findById(dto.getProvince().getId()).orElse(null);
            if (province != null) {
                district.setProvince(province);
            }
        }
        if (dto.getCommune() != null && !dto.getCommune().isEmpty()) {
            if (district.getCommune() == null) {
                district.setCommune(new ArrayList<>());
            }
            for (CommuneDto co : dto.getCommune()) {
                Commune commune = new Commune();
                if (co.getName() != null && !co.getName().trim().isEmpty()) {
                    commune.setName(co.getName().trim());
                }
                if (co.getCode() != null && !co.getCode().trim().isEmpty()) {
                    commune.setCode(co.getCode().trim());
                }
                commune.setDistrict(district);
                district.getCommune().add(commune);
            }
        }
        district = districtRepository.save(district);
        return new DistrictDto(district);
    }

    @Override
    public List<DistrictDto> finAll() {
        List<District> district = districtRepository.findAll();
        List<DistrictDto> districtList = new ArrayList<>();
        for (District dis : district) {
            districtList.add(new DistrictDto(dis));
        }
        return districtList;
    }
}
