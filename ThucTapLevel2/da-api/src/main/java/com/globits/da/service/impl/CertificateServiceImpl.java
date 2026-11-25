package com.globits.da.service.impl;

import com.globits.da.domain.Certificate;
import com.globits.da.domain.Employee;
import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.domain.Province;
import com.globits.da.dto.ApiResponse;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import com.globits.da.repository.CertificateRepository;
import com.globits.da.repository.EmployeeCertificateRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private EmployeeCertificateRepository employeeCertificateRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public ApiResponse<EmployeeCertificateDto> createEmployeeCertificate(EmployeeCertificateDto dto) {
        String error = this.validateCertificate(dto);
        if (error != null) {
            return new ApiResponse<>("INVALID_INPUT", error);
        }
        EmployeeCertificate newCertificateEmployee = new EmployeeCertificate();
        if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployee().getId()).orElse(null);
            newCertificateEmployee.setEmployee(employee);
        }
        if (dto.getCertificate() != null && dto.getCertificate().getId() != null) {
            Certificate certificate = certificateRepository.findById(dto.getCertificate().getId()).orElse(null);
            newCertificateEmployee.setCertificate(certificate);
        }
        if (dto.getProvince() != null && dto.getProvince().getId() != null) {
            Province province = provinceRepository.findById(dto.getProvince().getId()).orElse(null);
            newCertificateEmployee.setProvince(province);
        }
        newCertificateEmployee.setStartDate(dto.getStartDate());
        newCertificateEmployee.setEndDate(dto.getEndDate());
        newCertificateEmployee = employeeCertificateRepository.save(newCertificateEmployee);
        EmployeeCertificateDto newCertificateEmployeeDto = new EmployeeCertificateDto(newCertificateEmployee);
        return new ApiResponse<>(newCertificateEmployeeDto);
    }

    @Override
    public List<CertificateDto> showCertificate() {
        List<Certificate> certificate = certificateRepository.findAll();
        List<CertificateDto> list = new ArrayList<>();
        for (Certificate s : certificate) {
            list.add(new CertificateDto(s));
        }
        return list;
    }

    @Override
    public CertificateDto createCertificate(CertificateDto dto) {
        Certificate certificate = new Certificate();
        certificate.setCode(dto.getCode());
        certificate.setName(dto.getName());
        certificate.setEffectiveDate(dto.getEffectiveDate());
        certificate.setExpirationDate(dto.getExpirationDate());
        certificate = certificateRepository.save(certificate);
        return new CertificateDto(certificate);
    }

    @Override
    public List<CertificateDto> searchCertificate(CertificateSearchDto dto) {
        Integer id = (dto != null) ? dto.getId() : null;
        String code = (dto != null && dto.getCode() != null && !dto.getCode().trim().isEmpty()) ? dto.getCode().trim() : null;
        String name = (dto != null && dto.getName() != null && !dto.getName().trim().isEmpty()) ? dto.getName().trim() : null;
        LocalDate fromDate = (dto != null) ? dto.getEffectiveDate() : null;
        LocalDate toDate = (dto != null) ? dto.getExpirationDate() : null;
        List<Certificate> certificate = certificateRepository.searchCertificates(id, code, name, fromDate, toDate);
        List<CertificateDto> list = new ArrayList<>();
        for (Certificate s : certificate) {
            CertificateDto certificateDto = new CertificateDto(s);
            list.add(certificateDto);
        }
        return list;
    }

    @Override
    public CertificateDto updateCertificate(Integer id, CertificateDto dto) {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate != null) {
            if (dto != null) {
                if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
                    findCertificate.setCode(dto.getCode().trim());
                }
                if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
                    findCertificate.setName(dto.getName().trim());
                }
                if (dto.getEffectiveDate() != null) {
                    findCertificate.setEffectiveDate(dto.getEffectiveDate());
                }
                if (dto.getExpirationDate() != null) {
                    findCertificate.setExpirationDate(dto.getExpirationDate());
                }
            }
            certificateRepository.save(findCertificate);
            return new CertificateDto(findCertificate);
        }
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate != null) {
            certificateRepository.delete(findCertificate);
            return true;
        }
        return false;
    }

    private boolean isCertificateValid(EmployeeCertificate cert) {
        if (cert == null || cert.getStartDate() == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        boolean hasStarted = cert.getStartDate().isBefore(today) || cert.getStartDate().isEqual(today);
        boolean notExpired = (cert.getEndDate() == null)
                || cert.getEndDate().isAfter(today)
                || cert.getEndDate().isEqual(today);
        return hasStarted && notExpired;
    }

    private String validateCertificate(EmployeeCertificateDto dto) {
        if (dto == null) {
            return "Thông tin văn bằng không được để trống";
        }
        if (dto.getEmployee() == null || dto.getEmployee().getId() == null) {
            return "Vui lòng chọn nhân viên được cấp văn bằng";
        }
        if (dto.getCertificate() == null || dto.getCertificate().getId() == null) {
            return "Vui lòng chọn loại văn bằng";
        }
        if (dto.getProvince() == null || dto.getProvince().getId() == null) {
            return "Vui lòng chọn tỉnh cấp văn bằng";
        }
        if (dto.getStartDate() == null) {
            return "Ngày cấp văn bằng bắt buộc phải nhập";
        }
        if (dto.getEndDate() != null && dto.getEndDate().isBefore(dto.getStartDate())) {
            return "Ngày hết hạn không được nhỏ hơn ngày cấp";
        }
        if (!employeeRepository.existsById(dto.getEmployee().getId())) {
            return "Nhân viên không tồn tại trong hệ thống";
        }
        if (!certificateRepository.existsById(dto.getCertificate().getId())) {
            return "Loại văn bằng không tồn tại trong hệ thống";
        }
        if (!provinceRepository.existsById(dto.getProvince().getId())) {
            return "Tỉnh/Thành phố không tồn tại trong hệ thống";
        }
        //lấy list nhân viên đã được cấp bằng
        List<EmployeeCertificate> existingCerts = employeeCertificateRepository.findByEmployeeId(dto.getEmployee().getId());
        List<EmployeeCertificate> validCerts = new ArrayList<>();
        //nếu tồn tại nhân viên đó đã sở hữu văn bằng thì duyệt list
        if (existingCerts != null) {
            for (EmployeeCertificate cert : existingCerts) {
                if (isCertificateValid(cert)) {
                    validCerts.add(cert);
                }
            }
        }
        //check văn bằng xem có trùng hoặc là trùng tỉnh hay không
        for (EmployeeCertificate cert : validCerts) {
            boolean isSameCertificate = cert.getCertificate() != null
                    && cert.getCertificate().getId().equals(dto.getCertificate().getId());
            boolean isSameProvince = cert.getProvince() != null
                    && cert.getProvince().getId().equals(dto.getProvince().getId());
            if (isSameCertificate && isSameProvince) {
                return "Nhân viên đã có văn bằng này do tỉnh được chọn cấp và vẫn còn hiệu lực";
            }
        }
        int sameTypeCount = 0;
        for (EmployeeCertificate cert : validCerts) {
            if (cert.getCertificate() != null && cert.getCertificate().getId().equals(dto.getCertificate().getId())) {
                sameTypeCount++;
            }
        }
        if (sameTypeCount >= 3) {
            return "Nhân viên không được có quá 3 văn bằng cùng loại còn hiệu lực";
        }
        return null;
    }
}
