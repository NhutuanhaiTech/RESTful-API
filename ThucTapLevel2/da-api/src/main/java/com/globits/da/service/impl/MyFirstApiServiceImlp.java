package com.globits.da.service.impl;

import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.MyFirstApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyFirstApiServiceImlp implements MyFirstApiService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String getMyFirstApi() {
        return "MyFirstApiService";
    }
}
