package com.globits.da.repository;

import com.globits.da.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    @Query("SELECT c FROM District c WHERE " +
            "(:id IS NULL OR c.id = :id) AND " +
            "(:code IS NULL OR c.code = :code) AND " +
            "(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:idProvince IS NULL OR c.province.id = :idProvince)")
    List<District> searchDistrict(
            @Param("id") Integer id,
            @Param("code") String code,
            @Param("name") String name,
            @Param("idProvince") Integer idProvince
    );

    List<District> findByProvinceId(Integer provinceId);
}
