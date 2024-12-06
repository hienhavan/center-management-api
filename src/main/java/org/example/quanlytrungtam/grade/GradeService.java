package org.example.quanlytrungtam.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public List<ShowStudentGradeResponse> getAllStudentGrades(Integer idStudent) {
        return gradeRepository.findStudentGradesByStudentId(idStudent);
    }
    public Optional<ShowStudentGradeResponse> getGrade (Integer idGrade){
        return gradeRepository.findStudentGradeDetailsByGradeId(idGrade);
    }
}
