package com.globits.da.repository;

import com.globits.da.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    //#20
    @Query("SELECT e FROM Employee e WHERE " +
            "(:id IS NULL OR e.id = :id) AND " +
            "(:code IS NULL OR e.code = :code) AND " +
            "(:name IS NULL OR e.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:email IS NULL OR e.email = :email) AND " +
            "(:phone IS NULL OR e.phone = :phone) AND " +
            "(:age IS NULL OR e.age = :age)")
    List<Employee> searchEmployee(
            @Param("id") String id,
            @Param("code") String code,
            @Param("name") String name,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("age") Integer age
    );

    //check mã
    Employee findByCode(String code);

    boolean existsByCode(String code);
}
