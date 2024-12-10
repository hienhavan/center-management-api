package org.example.quanlytrungtam.dailystudent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyStudentRepository extends JpaRepository<DailyStudent, Integer> {
    @Query("SELECT d.id AS idDaily, d.lecturer.fullName AS nameTeacher, d.creationDate AS dueDate, d.context AS context " +
            "FROM DailyStudent d WHERE d.student.userID = :idStudent")
    List<NewfindListDailyStudentResponse> findDailyStudentByClassId(@Param("idStudent") Integer idStudent);
}
