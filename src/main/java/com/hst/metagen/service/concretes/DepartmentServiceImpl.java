package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Department;
import com.hst.metagen.repository.DepartmentRepository;
import com.hst.metagen.service.abstracts.DepartmentService;
import com.hst.metagen.service.dtos.DepartmentDto;
import com.hst.metagen.service.requests.CreateDepartmentRequest;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final ModelMapperService modelMapperService;

    @Override
    public DepartmentDto save(CreateDepartmentRequest createDepartmentRequest) {
        Department department = modelMapperService.forRequest().map(createDepartmentRequest,Department.class);
        return modelMapperService.forRequest().map(departmentRepository.save(department),DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDto> departmentDtos = departments.stream().map(department ->
                modelMapperService.forRequest().map(department,DepartmentDto.class)).collect(Collectors.toList());
        return departmentDtos;
    }
}
