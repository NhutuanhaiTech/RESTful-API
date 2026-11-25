package com.globits.da.service;

import com.globits.da.dto.ApiResponse;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.EmployeeSearchDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    //#19
    List<EmployeeDto> findAll();

    //createEmployee
    ApiResponse<EmployeeDto> createEmployee(EmployeeDto dto);

    //#20
    List<EmployeeDto> searchEmployee(EmployeeSearchDto dto);

    //update
    ApiResponse<EmployeeDto> updateEmployee(Integer id, EmployeeDto dto);

    //#21
    Boolean deleteEmployee(Integer id);

    //#22
    byte[] exportEmployeesToExcel();

    ApiResponse<List<String>> importEmployeesFromExcel(MultipartFile file);
}
