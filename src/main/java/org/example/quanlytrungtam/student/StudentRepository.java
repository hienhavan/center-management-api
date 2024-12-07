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

    @Query("SELECT s.studentId AS idStudent, s.userID.fullName AS fullName, s.userID.email AS email, s.userID.phoneNumber AS phoneNumber,s.status AS status " +
            "FROM Student s WHERE s.classID.classId = :classId")
    List<NewFindAllClassStudentResponse> listClassStudent(@Param("classId") Integer classId);

    @Query("SELECT s.studentId AS idStudent, s.userID.fullName AS fullName, s.userID.email AS email, s.userID.phoneNumber AS phoneNumber,s.status AS status " +
            "FROM Student s WHERE s.status = :status")
    List<NewFindAllClassStudentResponse> listStudentStatus(@Param("status") Status status);

    @Query("SELECT s.studentId AS idStudent, s.userID.fullName AS fullName, s.userID.email AS email, s.userID.phoneNumber AS phoneNumber,s.userID.dateOfBirth AS dateOfBirth,s.userID.address AS address,s.userID.gender AS gender,s.status AS status " +
            "FROM Student s WHERE s.userID.id = :userID")
    NewFindStudentResponse profileStudent(@Param("userID") Integer userID);
}
