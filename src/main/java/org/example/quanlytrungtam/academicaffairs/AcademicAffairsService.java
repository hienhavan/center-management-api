package org.example.quanlytrungtam.academicaffairs;

import org.example.quanlytrungtam.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicAffairsService {
    @Autowired
    private StudentRepository studentRepository;

    public Slice<NewFindAllClassStudentResponse> listStudent(Integer classId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.listClassStudent(classId,pageable);
    }
}
