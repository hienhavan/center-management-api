package org.example.quanlytrungtam.academicaffairs;

import org.example.quanlytrungtam.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicAffairsService {
    @Autowired
    private StudentRepository studentRepository;

    public List<NewFinAllClassStudentResponse> listStudent(Integer classId) {
        return studentRepository.listClassStudent(classId);
    }
}
