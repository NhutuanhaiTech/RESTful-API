package com.globits.da.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.globits.da.domain.Employee;

import javax.validation.Valid;
import javax.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {
    private Integer id;
    @NotBlank(message = "Mã nhân viên bắt buộc phải nhập")
    @Size(min = 6, max = 10, message = "Mã nhân viên phải từ 6 đến 10 ký tự")
    @Pattern(regexp = "^\\S+$", message = "Mã nhân viên không được chứa khoảng trắng")
    private String code;
    @NotBlank(message = "Tên nhân viên bắt buộc phải nhập")
    private String name;
    @NotBlank(message = "Email bắt buộc phải nhập")
    @Email(message = "Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Số điện thoại bắt buộc phải nhập")
    @Pattern(regexp = "^[0-9]{1,11}$", message = "Số điện thoại chỉ gồm các chữ số và không vượt quá 11 ký tự")
    private String phone;
    @Min(value = 0, message = "Tuổi không được âm")
    private Integer age;
    @NotNull(message = "Vui lòng nhập đầy đủ thông tin Tỉnh")
    @Valid // Yêu cầu validate tiếp vào bên trong ProvinceDto
    private ProvinceDto province;
    @NotNull(message = "Vui lòng nhập đầy đủ thông tin Huyện")
    @Valid
    private DistrictDto district;
    @NotNull(message = "Vui lòng nhập đầy đủ thông tin Xã")
    @Valid
    private CommuneDto commune;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.age = entity.getAge();
            if (entity.getProvince() != null) {
                this.province = new ProvinceDto(entity.getProvince());
            }
            if (entity.getDistrict() != null) {
                this.district = new DistrictDto(entity.getDistrict());
            }
            if (entity.getCommune() != null) {
                this.commune = new CommuneDto(entity.getCommune());
            }
        }
    }

    public ProvinceDto getProvince() {
        return province;
    }

    public void setProvince(ProvinceDto province) {
        this.province = province;
    }

    public DistrictDto getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDto district) {
        this.district = district;
    }

    public CommuneDto getCommune() {
        return commune;
    }

    public void setCommune(CommuneDto commune) {
        this.commune = commune;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
