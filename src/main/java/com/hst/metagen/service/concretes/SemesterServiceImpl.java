package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Semester;
import com.hst.metagen.repository.SemesterRepository;
import com.hst.metagen.service.abstracts.SemesterService;
import com.hst.metagen.service.dtos.SemesterDto;
import com.hst.metagen.service.requests.semester.CreateSemesterRequest;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public SemesterDto save(CreateSemesterRequest createSemesterRequest) {
        Semester semester = modelMapperService.dtoToEntity(createSemesterRequest, Semester.class);
        return modelMapperService.entityToDto(semesterRepository.save(semester), SemesterDto.class);
    }

    @Override
    public List<SemesterDto> getAllSemesters() {
        return modelMapperService.entityToDtoList(semesterRepository.findAll(), SemesterDto.class);
    }

    @Override
    public Semester getLastSemester() {
        List<Semester> semesters = semesterRepository.findAll();
        for (Semester semester: semesters) {
            if (semester.getStartDate().isBefore(LocalDate.now()) && semester.getEndDate().isAfter(LocalDate.now())) {
                return semester;
            }
        }
        return null;
    }
}
