package com.globits.da.repository;

import com.globits.da.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    @Query("SELECT c FROM Certificate c WHERE " +
            "(:id IS NULL OR c.id = :id) AND " +
            "(:code IS NULL OR c.code = :code) AND " +
            "(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:fromDate IS NULL OR c.effectiveDate >= :fromDate) AND " +
            "(:toDate IS NULL OR c.expirationDate <= :toDate)")
    List<Certificate> searchCertificates(
            @Param("id") Integer id,
            @Param("code") String code,
            @Param("name") String name,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
