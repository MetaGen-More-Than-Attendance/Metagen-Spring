package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Department;
import com.hst.metagen.repository.DepartmentRepository;
import com.hst.metagen.service.abstracts.DepartmentService;
import com.hst.metagen.service.dtos.DepartmentDto;
import com.hst.metagen.service.requests.department.CreateDepartmentRequest;
import com.hst.metagen.util.exception.NotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final ModelMapperService modelMapperService;

    @Override
    public DepartmentDto save(CreateDepartmentRequest createDepartmentRequest) {
        Department department = modelMapperService.dtoToEntity(createDepartmentRequest,Department.class);
        return modelMapperService.forRequest().map(departmentRepository.save(department),DepartmentDto.class);
    }

    @Override
    public DepartmentDto findById(Long departmentId) {
        return modelMapperService.dtoToEntity(departmentRepository.findById(departmentId).orElseThrow(NotFoundException::new),DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentDto> departments = modelMapperService.entityToDtoList(departmentRepository.findAll(),DepartmentDto.class);
        return departments;
    }

    @Override
    public Boolean deleteDepartments(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(NotFoundException::new);
        departmentRepository.delete(department);
        return true;
    }

    @Override
    public DepartmentDto update(Long id,CreateDepartmentRequest createDepartmentRequest) {
        Department department = departmentRepository.findById(id).orElseThrow(NotFoundException::new);
        department.setDepartmentName(createDepartmentRequest.getDepartmentName());
        DepartmentDto departmentDto = modelMapperService.entityToDto(departmentRepository.save(department),DepartmentDto.class);
        return departmentDto;
    }
}
