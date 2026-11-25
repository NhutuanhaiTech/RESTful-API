package com.globits.da.dto.search;

import java.time.LocalDate;

public class CertificateSearchDto {
    private Integer id;
    private String code;
    private String name;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;

    public CertificateSearchDto() {
    }

    public CertificateSearchDto(String code, String name, LocalDate effectiveDate, LocalDate expirationDate, Integer id) {
        this.code = code;
        this.name = name;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.id = id;
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
