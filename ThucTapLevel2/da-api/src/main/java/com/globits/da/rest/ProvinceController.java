package com.globits.da.rest;

import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ProvinceDto>> findAll() {
        List<ProvinceDto> list = provinceService.finAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<ProvinceDto> create(@RequestBody ProvinceDto pro) {
        ProvinceDto provinceDto = provinceService.createProvinceAndDistrict(pro);
        return ResponseEntity.ok(provinceDto);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProvinceDto>> search(@RequestBody ProvinceSearchDto dto) {
        List<ProvinceDto> list = provinceService.searchProvinces(dto);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProvinceDto> update(@PathVariable("id") Integer id, @RequestBody ProvinceDto dto) {
        ProvinceDto list = provinceService.updateProvince(id, dto);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProvince(@PathVariable("id") Integer id) {
        Boolean deleteProvince = provinceService.deleteProvince(id);
        return ResponseEntity.ok(deleteProvince);
    }
}
