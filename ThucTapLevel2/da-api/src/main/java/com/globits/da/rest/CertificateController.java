package com.globits.da.rest;

import com.globits.da.dto.ApiResponse;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import com.globits.da.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;

    @GetMapping("/get-all")
    public ResponseEntity<List<CertificateDto>> findAllCertificate() {
        List<CertificateDto> list = certificateService.showCertificate();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create/certificate-employee")
    public ResponseEntity<ApiResponse<EmployeeCertificateDto>> createEmployeeCertificate(
            @RequestBody EmployeeCertificateDto dto) {
        ApiResponse<EmployeeCertificateDto> response = certificateService.createEmployeeCertificate(dto);
        if ("INVALID_INPUT".equals(response.getErrorCode())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public CertificateDto createCertificate(@RequestBody CertificateDto dto) {
        CertificateDto certificateDto = certificateService.createCertificate(dto);
        return certificateDto;
    }

    @PostMapping("/search")
    public ResponseEntity<List<CertificateDto>> searchCertificates(@RequestBody CertificateSearchDto dto) {
        List<CertificateDto> list = certificateService.searchCertificate(dto);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDto> updateCertificate(@PathVariable("id") Integer id, @RequestBody CertificateDto certificateDto) {
        CertificateDto update = certificateService.updateCertificate(id, certificateDto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCertificate(@PathVariable("id") Integer id) {
        Boolean isDeleted = certificateService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }
}
