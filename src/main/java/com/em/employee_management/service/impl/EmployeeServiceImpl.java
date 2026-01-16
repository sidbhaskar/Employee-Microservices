package com.em.employee_management.service.impl;

import com.em.employee_management.model.dto.EmployeeDto;
import com.em.employee_management.model.entity.Employee;
import com.em.employee_management.repository.EmployeeRepository;
import com.em.employee_management.service.EmployeeService;
import com.em.employee_management.config.AppConfig.*;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl  implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if(employeeDto.getId() != null){
            throw new RuntimeException("Employee already exists");
        }
        Employee entity =  modelMapper.map(employeeDto, Employee.class);
        Employee savedEntity = employeeRepository.save(entity);

        return modelMapper.map(savedEntity, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if(id == null || employeeDto.getId() == null){
            throw new RuntimeException("Please provide employee id");
        }
        if(!Objects.equals(id, employeeDto.getId())) {
            throw new RuntimeException("Id mismatch");
        }
        employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        Employee entity = modelMapper.map(employeeDto, Employee.class);
//        entity.setUpdatedAt(LocalDateTime.now());
        Employee updatedEmployee = employeeRepository.save(entity);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getSingleEmployee(Long id) {
        return null;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return List.of();
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {
        return null;
    }
}
