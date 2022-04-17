package com.hst.metagen.service.abstracts;

import com.hst.metagen.service.dtos.DepartmentDto;
import com.hst.metagen.service.requests.CreateDepartmentRequest;

import java.util.List;

public interface DepartmentService {
    DepartmentDto save(CreateDepartmentRequest createDepartmentRequest);
    List<DepartmentDto> getAllDepartments();
}
