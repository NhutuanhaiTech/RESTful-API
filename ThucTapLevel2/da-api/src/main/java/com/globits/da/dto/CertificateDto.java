package com.globits.da.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.globits.da.domain.Certificate;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificateDto {
    private Integer id;
    private String code;
    private String name;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;

    public CertificateDto(Certificate certificate) {
        if (certificate != null) {
            this.id = certificate.getId();
            this.code = certificate.getCode();
            this.name = certificate.getName();
            this.effectiveDate = certificate.getEffectiveDate();
            this.expirationDate = certificate.getExpirationDate();
        }
    }

    public CertificateDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
