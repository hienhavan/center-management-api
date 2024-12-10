package org.example.quanlytrungtam.grade;

import org.example.quanlytrungtam.admin.NewAverageGradesByClassResponse;
import org.example.quanlytrungtam.admin.NewAvgGradeStudentClassResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grades, Integer> {
//    @Query("SELECT new org.example.quanlytrungtam.grade.ShowStudentGradeResponse(c.className,s.userID.fullName, sub.subjectName, " +
//            "new org.example.quanlytrungtam.grade.ShowGradeResponse(g.gradeId, g.theoryGrade, g.practicalGrade, " +
//            "(g.theoryGrade + g.practicalGrade) / 2)) " +
//            "FROM Grades g " +
//            "JOIN g.student s " +
//            "JOIN g.subject sub " +
//            "JOIN s.classID c " +
//            "WHERE s.studentId = :studentId")
//    List<ShowStudentGradeResponse> findStudentGradesByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT new org.example.quanlytrungtam.grade.ShowStudentGradesResponse(s.status,s.userID.fullName, sub.subjectName, " +
            "new org.example.quanlytrungtam.grade.ShowGradeResponse(g.gradeId, g.theoryGrade, g.practicalGrade, " +
            "(g.theoryGrade + g.practicalGrade) / 2)) " +
            "FROM Grades g " +
            "JOIN g.student s " +
            "JOIN g.subject sub " +
            "WHERE s.studentId = :studentId")
    Slice<ShowStudentGradesResponse> findStudentGradesByStudentId(@Param("studentId") Integer studentId, Pageable pageable);

    @Query("SELECT new org.example.quanlytrungtam.grade.FindGradeIdResponse(c.className, s.userID.fullName, sub.subjectName, " +
            "new org.example.quanlytrungtam.grade.ShowGradeResponse(g.gradeId,g.theoryGrade, g.practicalGrade, " +
            "(g.theoryGrade + g.practicalGrade) / 2)) " +
            "FROM Grades g " +
            "JOIN g.student s " +
            "JOIN g.subject sub " +
            "JOIN s.classID c " +
            "WHERE g.gradeId = :gradeId")
    Optional<FindGradeIdResponse> findStudentGradeDetailsByGradeId(@Param("gradeId") Integer idGrade);

    @Query("SELECT g.student.classID.classId AS classId, g.student.classID.className AS className, AVG(g.averageGrade) AS averageGrades,g.student.classID.lecturer.fullName AS teacher,g.student.classID.lecturer.phoneNumber AS phoneNumber, g.student.classID.lecturer.email AS email " +
            "FROM Grades g " +
            "GROUP BY g.student.classID.className")
    List<NewAverageGradesByClassResponse> listAverageGradesByClass();

    @Query("SELECT g.student.userID.fullName AS nameStudent,g.student.userID.phoneNumber AS phoneNumber ,g.student.userID.email AS email, g.student.classID.className AS nameClass, AVG(g.averageGrade) AS averageGrade " +
            "FROM Grades g " +
            "WHERE g.student.classID.classId = :idClass " +
            "GROUP BY g.student.userID.fullName, g.student.classID.className")
    List<NewAvgGradeStudentClassResponse> listAverageGradeByStudentAndClass(@Param("idClass") Integer idClass);


}
