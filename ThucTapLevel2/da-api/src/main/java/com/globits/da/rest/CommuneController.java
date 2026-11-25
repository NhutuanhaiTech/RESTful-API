package com.globits.da.rest;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.search.CommuneSearchDto;
import com.globits.da.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commune")
public class CommuneController {
    @Autowired
    private CommuneService CommuneService;

    @GetMapping("/get-all")
    public ResponseEntity<List<CommuneDto>> findAll() {
        List<CommuneDto> list = CommuneService.finAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<CommuneDto> create(@RequestBody CommuneDto pro) {
        CommuneDto CommuneDto = CommuneService.createCommune(pro);
        return ResponseEntity.ok(CommuneDto);
    }

    @PostMapping("/search")
    public ResponseEntity<List<CommuneDto>> search(@RequestBody CommuneSearchDto dto) {
        List<CommuneDto> list = CommuneService.searchCommunes(dto);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommuneDto> update(@PathVariable("id") Integer id, @RequestBody CommuneDto dto) {
        CommuneDto list = CommuneService.updateCommune(id, dto);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCommune(@PathVariable("id") Integer id) {
        Boolean deleteCommune = CommuneService.deleteCommune(id);
        return ResponseEntity.ok(deleteCommune);
    }
}
