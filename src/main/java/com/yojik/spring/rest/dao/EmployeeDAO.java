package com.yojik.spring.rest.dao;

import com.yojik.spring.rest.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees();
    List<String> getDepartments();

    void saveOrUpdateEmployee(Employee employee);

    Employee getEmployee(int id);

    void deleteEmployee(int id);
}
