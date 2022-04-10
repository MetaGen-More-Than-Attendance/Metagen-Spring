package com.hst.metagen.mapper;

import com.hst.metagen.dto.StudentDto;
import com.hst.metagen.entity.Student;
import com.hst.metagen.service.requests.CreateStudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student studentToDto(CreateStudentDto studentDto);
    StudentDto dtoToStudent(Student student);
}
