package org.example.quanlytrungtam.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grades, Integer> {
    @Query("SELECT new org.example.quanlytrungtam.grade.ShowStudentGradeResponse(c.className, s.userID.fullName, sub.subjectName, " +
            "new org.example.quanlytrungtam.grade.ShowGradeResponse(g.theoryGrade, g.practicalGrade, " +
            "(g.theoryGrade + g.practicalGrade) / 2)) " +
            "FROM Grades g " +
            "JOIN g.student s " +
            "JOIN g.subject sub " +
            "JOIN s.classID c " +
            "WHERE s.studentId = :studentId")
    List<ShowStudentGradeResponse> findStudentGradesByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT new org.example.quanlytrungtam.grade.ShowStudentGradeResponse(c.className, s.userID.fullName, sub.subjectName, " +
            "new org.example.quanlytrungtam.grade.ShowGradeResponse(g.theoryGrade, g.practicalGrade, " +
            "(g.theoryGrade + g.practicalGrade) / 2)) " +
            "FROM Grades g " +
            "JOIN g.student s " +
            "JOIN g.subject sub " +
            "JOIN s.classID c " +
            "WHERE g.gradeId = :gradeId")
    Optional<ShowStudentGradeResponse> findStudentGradeDetailsByGradeId(@Param("gradeId") Integer idGrade);

}
