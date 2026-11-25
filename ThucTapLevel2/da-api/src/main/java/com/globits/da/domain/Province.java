package com.globits.da.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<District> district = new ArrayList<>();

    public Province() {
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> districts) {
        this.district = districts;
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
}
