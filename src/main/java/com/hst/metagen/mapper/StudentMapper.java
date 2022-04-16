package com.hst.metagen.mapper;

import com.hst.metagen.service.dtos.StudentDto;
import com.hst.metagen.entity.Student;
import com.hst.metagen.service.requests.CreateStudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student studentToDto(CreateStudentRequest studentDto);
    StudentDto dtoToStudent(Student student);
}
