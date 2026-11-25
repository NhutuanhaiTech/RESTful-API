package com.globits.da.service;

import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.ApiResponse;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;

import java.util.List;

public interface CertificateService {
    List<CertificateDto> showCertificate();

    CertificateDto createCertificate(CertificateDto certificate);

    List<CertificateDto> searchCertificate(CertificateSearchDto dto);

    CertificateDto updateCertificate(Integer id, CertificateDto dto);

    Boolean delete(Integer id);

    ApiResponse<EmployeeCertificateDto> createEmployeeCertificate(EmployeeCertificateDto dto);
}
