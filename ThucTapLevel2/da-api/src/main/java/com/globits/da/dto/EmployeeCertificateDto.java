package com.globits.da.dto;

import com.globits.da.domain.EmployeeCertificate;

import java.time.LocalDate;

public class EmployeeCertificateDto {
    private Integer id;
    private EmployeeDto employee;
    private CertificateDto certificate;
    private ProvinceDto province;
    private LocalDate startDate;
    private LocalDate endDate;

    public EmployeeCertificateDto() {
    }

    public EmployeeCertificateDto(EmployeeCertificate employeeCertificate) {
        this.id = employeeCertificate.getId();
        if (employeeCertificate.getEmployee() != null) {
            this.employee = new EmployeeDto(employeeCertificate.getEmployee());
        }
        if (employeeCertificate.getCertificate() != null) {
            this.certificate = new CertificateDto(employeeCertificate.getCertificate());
        }
        if (employeeCertificate.getProvince() != null) {
            this.province = new ProvinceDto(employeeCertificate.getProvince());
        }
        this.startDate = employeeCertificate.getStartDate();
        this.endDate = employeeCertificate.getEndDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public CertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateDto certificate) {
        this.certificate = certificate;
    }

    public ProvinceDto getProvince() {
        return province;
    }

    public void setProvince(ProvinceDto province) {
        this.province = province;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
