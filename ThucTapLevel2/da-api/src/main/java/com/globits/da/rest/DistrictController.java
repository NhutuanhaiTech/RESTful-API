package com.globits.da.rest;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/district")
public class DistrictController {
    @Autowired
    private DistrictService DistrictService;

    @GetMapping("/get-all")
    public ResponseEntity<List<DistrictDto>> findAll() {
        List<DistrictDto> list = DistrictService.finAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<DistrictDto> create(@RequestBody DistrictDto pro) {
        DistrictDto DistrictDto = DistrictService.createDistrict(pro);
        return ResponseEntity.ok(DistrictDto);
    }

    @PostMapping("/search")
    public ResponseEntity<List<DistrictDto>> search(@RequestBody DistrictSearchDto dto) {
        List<DistrictDto> list = DistrictService.searchDistricts(dto);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DistrictDto> update(@PathVariable("id") Integer id, @RequestBody DistrictDto dto) {
        DistrictDto list = DistrictService.updateDistrict(id, dto);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteDistrict(@PathVariable("id") Integer id) {
        Boolean deleteDistrict = DistrictService.deleteDistrict(id);
        return ResponseEntity.ok(deleteDistrict);
    }
}
