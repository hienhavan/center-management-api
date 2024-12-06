package org.example.quanlytrungtam.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public Grades findById(Integer idGrade) {
        return gradeRepository.findById(idGrade).orElse(null);
    }

    public List<ShowStudentGradeResponse> getAllStudentGrades(Integer idStudent) {
        return gradeRepository.findStudentGradesByStudentId(idStudent);
    }

    public Optional<ShowStudentGradeResponse> getGrade(Integer idGrade) {
        return gradeRepository.findStudentGradeDetailsByGradeId(idGrade);
    }

    public void updateGrade(UpdateGradeRequest request) {
        Grades grade = findById(request.getGradeId());
        grade.setPracticalGrade(request.getPracticalGrade() != null ? request.getPracticalGrade() : grade.getPracticalGrade());
        grade.setTheoryGrade(request.getTheoryGrade() != null ? request.getTheoryGrade() : grade.getTheoryGrade());
        grade.setAverageGrade(request.getAverageGrade());
        gradeRepository.save(grade);
    }
}
