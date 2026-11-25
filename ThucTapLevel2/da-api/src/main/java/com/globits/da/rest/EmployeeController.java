package com.globits.da.rest;

import com.globits.da.dto.ApiResponse;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.EmployeeSearchDto;
import com.globits.da.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //#19
    @GetMapping("/get-all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(@Valid @RequestBody EmployeeDto dto) {
        ApiResponse<EmployeeDto> newEmployee = employeeService.createEmployee(dto);
        if (!"SUCCESS".equals(newEmployee.getErrorCode())) {
            return ResponseEntity.badRequest().body(newEmployee);
        }
        return ResponseEntity.ok(newEmployee);
    }

    @PostMapping(value = "/import-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<String>>> importEmployees(
            @RequestParam("file") MultipartFile file) {

        ApiResponse<List<String>> response = employeeService.importEmployeesFromExcel(file);
        if ("SUCCESS".equals(response.getErrorCode())) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(@PathVariable("id") Integer id, @Valid @RequestBody EmployeeDto dto) {
        ApiResponse<EmployeeDto> newEmployee = employeeService.updateEmployee(id, dto);
        if (!"SUCCESS".equals(newEmployee.getErrorCode())) {
            return ResponseEntity.badRequest().body(newEmployee);
        }
        return ResponseEntity.ok(newEmployee);

    }

    //#20
    @PostMapping("/search")
    public ResponseEntity<List<EmployeeDto>> search(@RequestBody EmployeeSearchDto dto) {
        return ResponseEntity.ok(employeeService.searchEmployee(dto));
    }

    // #21
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteEmployees(@PathVariable("id") Integer id) {
        Boolean result = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(result);
    }

    // #22 - API Xuất Excel dùng ByteArrayResource
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        byte[] excelBytes = employeeService.exportEmployeesToExcel();
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(excelBytes);
        outputStream.flush();
        outputStream.close();
    }
}
