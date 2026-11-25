package com.globits.da.service.impl;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.dto.ApiResponse;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.EmployeeSearchDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.EmployeeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private CommuneRepository communeRepository;

    //#19
    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> listEmployee = employeeRepository.findAll();
        List<EmployeeDto> list = new ArrayList<>();
        for (Employee employee : listEmployee) {
            EmployeeDto employeeDto = new EmployeeDto(employee);
            list.add(employeeDto);
        }
        return list;
    }

    @Override
    public ApiResponse<EmployeeDto> createEmployee(EmployeeDto dto) {
        if (dto == null) {
            return new ApiResponse<>("BAD_REQUEST", "Dữ liệu không được để rỗng");
        }
        String errorMessage = validateEmployee(dto, false);
        if (errorMessage != null) {
            return new ApiResponse<>("INVALID_DATA", errorMessage);
        }
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setCode(dto.getCode());
        employee.setEmail(dto.getEmail());
        employee.setAge(dto.getAge());
        employee.setPhone(dto.getPhone());
        Province province = provinceRepository.findById(dto.getProvince().getId()).orElse(null);
        employee.setProvince(province);
        District district = districtRepository.findById(dto.getDistrict().getId()).orElse(null);
        employee.setDistrict(district);
        Commune commune = communeRepository.findById(dto.getCommune().getId()).orElse(null);
        employee.setCommune(commune);
        employeeRepository.save(employee);
        return new ApiResponse<>(new EmployeeDto(employee));
    }

    @Override
    @Transactional
    public ApiResponse<EmployeeDto> updateEmployee(Integer id, EmployeeDto dto) {
        if (dto == null) {
            return new ApiResponse<>("BAD_REQUEST", "Dữ liệu gửi lên không được để rỗng");
        }
        Employee foundEmployee = employeeRepository.findById(id).orElse(null);
        if (foundEmployee == null) {
            return new ApiResponse<>("NOT_FOUND", "Không tìm thấy Employee để update");
        }
        dto.setId(id);
        String errorMessage = validateEmployee(dto, true);
        if (errorMessage != null) {
            return new ApiResponse<>("INVALID_DATA", errorMessage);
        }
        foundEmployee.setName(dto.getName().trim());
        foundEmployee.setCode(dto.getCode().trim());
        foundEmployee.setEmail(dto.getEmail().trim());
        foundEmployee.setAge(dto.getAge());
        foundEmployee.setPhone(dto.getPhone().trim());
        Province province = provinceRepository.findById(dto.getProvince().getId()).orElse(null);
        foundEmployee.setProvince(province);
        District district = districtRepository.findById(dto.getDistrict().getId()).orElse(null);
        foundEmployee.setDistrict(district);
        Commune commune = communeRepository.findById(dto.getCommune().getId()).orElse(null);
        foundEmployee.setCommune(commune);
        foundEmployee = employeeRepository.save(foundEmployee);
        return new ApiResponse<>(new EmployeeDto(foundEmployee));
    }

    //#20
    @Override
    public List<EmployeeDto> searchEmployee(EmployeeSearchDto dto) {
        String id = (dto != null) ? dto.getCode() : null;
        String code = (dto != null && dto.getCode() != null && !dto.getCode().trim().isEmpty()) ? dto.getCode().trim() : null;
        String name = (dto != null && dto.getName() != null && !dto.getName().trim().isEmpty()) ? dto.getName().trim() : null;
        String email = (dto != null && dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) ? dto.getEmail().trim() : null;
        String phone = (dto != null && dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) ? dto.getPhone().trim() : null;
        Integer age = (dto != null) ? dto.getAge() : null;
        List<Employee> entities = employeeRepository.searchEmployee(id, code, name, email, phone, age);
        List<EmployeeDto> resultList = new ArrayList<>();
        for (Employee entity : entities) {
            resultList.add(new EmployeeDto(entity));
        }
        return resultList;
    }

    //#21
    @Override
    public Boolean deleteEmployee(Integer id) {

        Employee foundEmployees = employeeRepository.findById(id).orElse(null);
        if (foundEmployees != null) {
            employeeRepository.delete(foundEmployees);
            return true;
        }

        return false;
    }

    //#22
    @Override
    public byte[] exportEmployeesToExcel() {
        String[] HEADERS = {"STT", "Tên", "Mã", "Email", "Phone", "Age"};
        List<Employee> employees = employeeRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employees");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowIndex = 1;
            for (Employee emp : employees) {
                Row row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(rowIndex);
                row.createCell(1).setCellValue(emp.getName() != null ? emp.getName() : "");
                row.createCell(2).setCellValue(emp.getCode() != null ? emp.getCode() : "");
                row.createCell(3).setCellValue(emp.getEmail() != null ? emp.getEmail() : "");
                row.createCell(4).setCellValue(emp.getPhone() != null ? emp.getPhone() : "");
                row.createCell(5).setCellValue(emp.getAge() != null ? emp.getAge() : 0);
                rowIndex++;
            }
            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Lỗi xuất Excel: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<String>> importEmployeesFromExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new ApiResponse<>("INVALID_FILE", "File excel không được để trống");
        }

        List<String> errorMessages = new ArrayList<>();
        List<Employee> employeesToSave = new ArrayList<>();
        Set<String> codesInExcel = new HashSet<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;
                int rowNum = rowIndex + 1;
                String code = getCellValueAsString(row.getCell(0));
                String name = getCellValueAsString(row.getCell(1));
                String email = getCellValueAsString(row.getCell(2));
                String phone = getCellValueAsString(row.getCell(3));
                Integer age = getCellValueAsInteger(row.getCell(4));
                Integer provinceId = getCellValueAsInteger(row.getCell(5));
                Integer districtId = getCellValueAsInteger(row.getCell(6));
                Integer communeId = getCellValueAsInteger(row.getCell(7));
                if (code.isEmpty()) {
                    errorMessages.add("Dòng " + rowNum + ": Mã nhân viên bắt buộc nhập");
                } else {
                    if (code.length() < 6 || code.length() > 10 || code.contains(" ")) {
                        errorMessages.add("Dòng " + rowNum + ": Mã nhân viên [" + code + "] phải từ 6-10 ký tự và không chứa khoảng trắng");
                    }
                    if (employeeRepository.existsByCode(code)) {
                        errorMessages.add("Dòng " + rowNum + ": Mã nhân viên [" + code + "] đã tồn tại trong hệ thống");
                    }
                    if (codesInExcel.contains(code)) {
                        errorMessages.add("Dòng " + rowNum + ": Mã nhân viên [" + code + "] bị trùng lặp trong chính file Excel");
                    } else {
                        codesInExcel.add(code);
                    }
                }
                if (name.isEmpty()) {
                    errorMessages.add("Dòng " + rowNum + ": Tên nhân viên bắt buộc nhập");
                }
                if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    errorMessages.add("Dòng " + rowNum + ": Email [" + email + "] không đúng định dạng");
                }
                if (phone.isEmpty() || !phone.matches("\\d+") || phone.length() > 11) {
                    errorMessages.add("Dòng " + rowNum + ": Số điện thoại chỉ gồm số và không quá 11 ký tự");
                }
                if (age != null && age < 0) {
                    errorMessages.add("Dòng " + rowNum + ": Tuổi không được âm");
                }
                Province province = null;
                District district = null;
                Commune commune = null;

                if (provinceId != null) {
                    province = provinceRepository.findById(provinceId).orElse(null);
                    if (province == null) {
                        errorMessages.add("Dòng " + rowNum + ": Tỉnh ID [" + provinceId + "] không tồn tại");
                    }
                }
                if (districtId != null) {
                    district = districtRepository.findById(districtId).orElse(null);
                    if (district == null) {
                        errorMessages.add("Dòng " + rowNum + ": Huyện ID [" + districtId + "] không tồn tại");
                    } else if (province != null && !district.getProvince().getId().equals(province.getId())) {
                        errorMessages.add("Dòng " + rowNum + ": Huyện [" + district.getName() + "] không thuộc Tỉnh ID [" + provinceId + "]");
                    }
                }
                if (communeId != null) {
                    commune = communeRepository.findById(communeId).orElse(null);
                    if (commune == null) {
                        errorMessages.add("Dòng " + rowNum + ": Xã ID [" + communeId + "] không tồn tại");
                    } else if (district != null && !commune.getDistrict().getId().equals(district.getId())) {
                        errorMessages.add("Dòng " + rowNum + ": Xã [" + commune.getName() + "] không thuộc Huyện ID [" + districtId + "]");
                    }
                }
                Employee emp = new Employee();
                emp.setCode(code);
                emp.setName(name);
                emp.setEmail(email);
                emp.setPhone(phone);
                emp.setAge(age);
                emp.setProvince(province);
                emp.setDistrict(district);
                emp.setCommune(commune);
                employeesToSave.add(emp);
            }
            if (!errorMessages.isEmpty()) {
                return new ApiResponse<>("IMPORT_ERROR", "Import thất bại do dữ liệu bị lỗi");
            }
            employeeRepository.saveAll(employeesToSave);
            return new ApiResponse<>("SUCCESS", "Import thành công " + employeesToSave.size() + " nhân viên");

        } catch (Exception e) {
            return new ApiResponse<>("FILE_ERROR", "Lỗi xử lý file Excel: " + e.getMessage());
        }
    }

    private String validateEmployee(EmployeeDto dto, boolean isUpdate) {
        String code = dto.getCode().trim();
        Employee existing = employeeRepository.findByCode(code);
        if (!isUpdate && existing != null) {
            return "Mã nhân viên đã tồn tại";
        }
        if (isUpdate && existing != null && !existing.getId().equals(dto.getId())) {
            return "Mã nhân viên đã trùng với nhân viên khác";
        }
        Province province = provinceRepository.findById(dto.getProvince().getId()).orElse(null);
        if (province == null) {
            return "Không tìm thấy Tỉnh bạn muốn thêm";
        }
        District district = districtRepository.findById(dto.getDistrict().getId()).orElse(null);
        if (district == null) {
            return "Không tìm thấy Huyện bạn muốn thêm";
        }
        Commune commune = communeRepository.findById(dto.getCommune().getId()).orElse(null);
        if (commune == null) {
            return "Không tìm thấy Xã bạn muốn thêm";
        }
        boolean foundDistrict = false;
        for (District dis : province.getDistrict()) {
            if (dis.getId().equals(dto.getDistrict().getId())) {
                foundDistrict = true;
                break;
            }
        }
        if (!foundDistrict) {
            return "Bạn đã nhập Id huyện không tồn tại trong tỉnh";
        }

        boolean foundCommune = false;
        for (Commune com : district.getCommune()) {
            if (com.getId().equals(dto.getCommune().getId())) {
                foundCommune = true;
                break;
            }
        }
        if (!foundCommune) {
            return "Bạn đã nhập Id xã không tồn tại trong huyện";
        }

        return null;
    }

    private String validateEmployeeVersion1(EmployeeDto dto, boolean isUpdate) {
        if (dto.getCode() == null || dto.getCode().trim().isEmpty()) {
            return "Mã nhân viên bắt buộc phải nhập";
        }
        String code = dto.getCode().trim();
        if (code.contains(" ")) {
            return "Mã nhân viên không được chứa khoảng trắng";
        }
        if (code.length() < 6 || code.length() > 10) {
            return "Mã nhân viên phải từ 6 đến 10 ký tự";
        }
        Employee existing = employeeRepository.findByCode(code);
        if (!isUpdate && existing != null) {
            return "Mã nhân viên đã tồn tại";
        }
        if (isUpdate && existing != null && !existing.getId().equals(dto.getId())) {
            return "Mã nhân viên đã trùng với nhân viên khác";
        }
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return "Tên nhân viên bắt buộc phải nhập";
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            return "Email bắt buộc phải nhập";
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!dto.getEmail().trim().matches(emailRegex)) {
            return "Email không đúng định dạng";
        }
        if (dto.getPhone() == null || dto.getPhone().trim().isEmpty()) {
            return "Số điện thoại bắt buộc phải nhập";
        }
        String phoneRegex = "^[0-9]{1,11}$";
        if (!dto.getPhone().trim().matches(phoneRegex)) {
            return "Số điện thoại chỉ gồm các chữ số và không vượt quá 11 ký tự";
        }
        if (dto.getAge() != null && dto.getAge() < 0) {
            return "Tuổi không được âm";
        }
        if (dto.getProvince() == null || dto.getProvince().getId() == null
                || dto.getDistrict() == null || dto.getDistrict().getId() == null
                || dto.getCommune() == null || dto.getCommune().getId() == null) {
            return "Vui lòng nhập đầy đủ thông tin Id 3 Tỉnh, Huyện, Xã";
        }
        Province province = provinceRepository.findById(dto.getProvince().getId()).orElse(null);
        if (province == null) {
            return "Không tìm thấy Tỉnh bạn muốn thêm";
        }
        District district = districtRepository.findById(dto.getDistrict().getId()).orElse(null);
        if (district == null) {
            return "Không tìm thấy Huyện bạn muốn thêm";
        }
        Commune commune = communeRepository.findById(dto.getCommune().getId()).orElse(null);
        if (commune == null) {
            return "Không tìm thấy Xã bạn muốn thêm";
        }
        boolean foundDistrict = false;
        for (District dis : province.getDistrict()) {
            if (dis.getId().equals(dto.getDistrict().getId())) {
                foundDistrict = true;
                break;
            }
        }
        if (!foundDistrict) {
            return "Bạn đã nhập Id huyện không tồn tại trong tỉnh";
        }
        boolean foundCommune = false;
        for (Commune com : district.getCommune()) {
            if (com.getId().equals(dto.getCommune().getId())) {
                foundCommune = true;
                break;
            }
        }
        if (!foundCommune) {
            return "Bạn đã nhập Id xã không tồn tại trong huyện";
        }
        return null;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private Integer getCellValueAsInteger(Cell cell) {
        String valueStr = getCellValueAsString(cell);
        if (valueStr == null || valueStr.isEmpty()) {
            return null;
        }
        try {
            return (int) Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
