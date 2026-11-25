package com.globits.da.repository;

import com.globits.da.domain.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, Integer> {
    @Query("SELECT c FROM Commune c WHERE " +
            "(:id IS NULL OR c.id = :id) AND " +
            "(:code IS NULL OR c.code = :code) AND " +
            "(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:idDistrict IS NULL OR c.district.id = :idDistrict)")
    List<Commune> searchCommune(
            @Param("id") Integer id,
            @Param("code") String code,
            @Param("name") String name,
            @Param("idDistrict") Integer idDistrict
    );
}
