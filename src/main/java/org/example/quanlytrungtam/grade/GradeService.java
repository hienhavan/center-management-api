package org.example.quanlytrungtam.grade;

import org.example.quanlytrungtam.admin.NewAverageGradesByClassResponse;
import org.example.quanlytrungtam.admin.NewAvgGradeStudentClassResponse;
import org.example.quanlytrungtam.student.Student;
import org.example.quanlytrungtam.student.StudentRepository;
import org.example.quanlytrungtam.subject.Subject;
import org.example.quanlytrungtam.subject.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Grades findById(Integer idGrade) {
        return gradeRepository.findById(idGrade).orElse(null);
    }

    public Slice<ShowStudentGradesResponse> getAllStudentGrades(Integer idStudent, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gradeRepository.findStudentGradesByStudentId(idStudent,pageable);
    }

    public Optional<FindGradeIdResponse> getGrade(Integer idGrade) {
        return gradeRepository.findStudentGradeDetailsByGradeId(idGrade);
    }

    public void updateGrade(UpdateGradeRequest request) {
        Grades grade = findById(request.getGradeId());
        grade.setPracticalGrade(request.getPracticalGrade() != null ? request.getPracticalGrade() : grade.getPracticalGrade());
        grade.setTheoryGrade(request.getTheoryGrade() != null ? request.getTheoryGrade() : grade.getTheoryGrade());
        grade.setAverageGrade(request.getAverageGrade());
        gradeRepository.save(grade);
    }

    public void deleteGrade(Integer idGrade) {
        gradeRepository.deleteById(idGrade);
    }

    public void saveGrade(AddGradeRequest request) {
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElse(null);
        Student student = studentRepository.findById(request.getStudentId()).orElse(null);
        var grade = Grades.builder()
                .student(student)
                .subject(subject)
                .practicalGrade(request.getPracticalGrade())
                .theoryGrade(request.getTheoryGrade())
                .averageGrade((request.getPracticalGrade() + request.getTheoryGrade()) / 2)
                .build();
        gradeRepository.save(grade);
    }

    public List<NewAverageGradesByClassResponse> listAvgGradesByClass() {
        return gradeRepository.listAverageGradesByClass();
    }

    public List<NewAvgGradeStudentClassResponse> listAvgGradeStudentClass(Integer classId) {
        return gradeRepository.listAverageGradeByStudentAndClass(classId);
    }
}

