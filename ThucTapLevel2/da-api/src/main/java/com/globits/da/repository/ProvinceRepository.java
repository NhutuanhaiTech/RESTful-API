package com.globits.da.repository;

import com.globits.da.domain.Certificate;
import com.globits.da.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province,Integer> {
    @Query("SELECT c FROM Province c WHERE " +
            "(:id IS NULL OR c.id = :id) AND " +
            "(:code IS NULL OR c.code = :code) AND " +
            "(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%'))"
            )
    List<Province> searchProvince(
            @Param("id") Integer id,
            @Param("code") String code,
            @Param("name") String name

    );
}
