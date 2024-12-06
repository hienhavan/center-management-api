package org.example.quanlytrungtam.student;

import org.example.quanlytrungtam.academicaffairs.NewFindAllClassStudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s WHERE s.userID.id = :userId")
    Optional<Student> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT s.studentId AS idStudent, s.userID.fullName AS fullName, s.userID.email AS email, s.userID.phoneNumber AS phoneNumber " +
            "FROM Student s WHERE s.classID.classId = :classId")
    List<NewFindAllClassStudentResponse> listClassStudent(@Param("classId") Integer classId);
}
