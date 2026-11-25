package com.globits.da.repository;

import com.globits.da.domain.EmployeeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeCertificateRepository extends JpaRepository<EmployeeCertificate, Integer> {
    List<EmployeeCertificate> findByEmployeeId(Integer employeeId);
}
