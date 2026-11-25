package com.globits.da.service.impl;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Override
    public List<ProvinceDto> finAll() {
        List<Province> list = provinceRepository.findAll();
        List<ProvinceDto> listProvince = new ArrayList<>();
        for (Province province : list) {
            listProvince.add(new ProvinceDto(province));
        }
        return listProvince;
    }
    @Override
    @Transactional
    public ProvinceDto createProvinceAndDistrict(ProvinceDto dto) {
        if (dto == null) {
            return null;
        }
        Province province = new Province();
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            province.setName(dto.getName().trim());
        }
        if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
            province.setCode(dto.getCode().trim());
        }
        if (dto.getDistrict() != null && !dto.getDistrict().isEmpty()) {
            for (DistrictDto districtDto : dto.getDistrict()) {
                District district = new District();
                if (districtDto.getName() != null && !districtDto.getName().trim().isEmpty()) {
                    district.setName(districtDto.getName().trim());
                }
                if (districtDto.getCode() != null && !districtDto.getCode().trim().isEmpty()) {
                    district.setCode(districtDto.getCode().trim());
                }
                district.setProvince(province);
                if (province.getDistrict() == null) {
                    province.setDistrict(new ArrayList<>());
                }
                province.getDistrict().add(district);
                if (districtDto.getCommune() != null) {
                    for (CommuneDto commune1 : districtDto.getCommune()) {
                        Commune commune = new Commune();
                        if (commune1.getName() != null && !commune1.getName().trim().isEmpty()) {
                            commune.setName(commune1.getName());
                        }
                        if (commune1.getCode() != null && !commune1.getCode().trim().isEmpty()) {
                            commune.setCode(commune1.getCode());
                        }
                        commune.setDistrict(district);
                        district.getCommune().add(commune);
                    }
                }
            }
        }
        Province savedProvince = provinceRepository.save(province);
        return new ProvinceDto(savedProvince);
    }

    @Override
    public List<ProvinceDto> searchProvinces(ProvinceSearchDto dto) {
        Integer id = (dto != null) ? dto.getId() : null;
        String code = (dto != null && dto.getCode() != null && !dto.getCode().trim().isEmpty()) ? dto.getCode().trim() : null;
        String name = (dto != null && dto.getName() != null && !dto.getName().trim().isEmpty()) ? dto.getName().trim() : null;
        List<Province> provinnce = provinceRepository.searchProvince(id, code, name);
        List<ProvinceDto> provinceDto = new ArrayList<>();
        for (Province pro : provinnce) {
            provinceDto.add(new ProvinceDto(pro));
        }
        return provinceDto;
    }

    @Override
    @Transactional
    public ProvinceDto updateProvince(Integer id, ProvinceDto dto) {
        if (id == null || dto == null) {
            return null;
        }
        Province findProvince = provinceRepository.findById(id).orElse(null);
        if (findProvince == null) {
            return null;
        }
        if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
            findProvince.setCode(dto.getCode().trim());
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            findProvince.setName(dto.getName().trim());
        }
        if (dto.getDistrict() != null) {
            for (DistrictDto dis : dto.getDistrict()) {
                if (dis.getId() == null) {
                    District district = new District();
                    if (dis.getName() != null && !dis.getName().trim().isEmpty()) {
                        district.setName(dis.getName().trim());
                    }
                    if (dis.getCode() != null && !dis.getCode().trim().isEmpty()) {
                        district.setCode(dis.getCode().trim());
                    }
                    district.setProvince(findProvince);
                    findProvince.getDistrict().add(district);
                } else {
                    District districtUpdate = districtRepository.findById(dis.getId()).orElse(null);
                    if (districtUpdate != null) {
                        if (dis.getName() != null && !dis.getName().trim().isEmpty()) {
                            districtUpdate.setName(dis.getName().trim());
                        }
                        if (dis.getCode() != null && !dis.getCode().trim().isEmpty()) {
                            districtUpdate.setCode(dis.getCode().trim());
                        }
                        districtUpdate.setProvince(findProvince);
                    }
                }
            }
        }
        Province savedProvince = provinceRepository.save(findProvince);
        Province updatedProvince = provinceRepository.findById(id).orElse(null);
        return new ProvinceDto(updatedProvince);
    }

    @Override
    public Boolean deleteProvince(Integer id) {
        if (id != null) {
            Province findProvince = provinceRepository.findById(id).orElse(null);
            if (findProvince != null) {
                provinceRepository.delete(findProvince);
                return true;
            }
        }
        return false;
    }
}
