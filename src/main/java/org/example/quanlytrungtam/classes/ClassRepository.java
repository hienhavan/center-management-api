package org.example.quanlytrungtam.classes;

import org.example.quanlytrungtam.academicaffairs.NewFindAllClassResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ClassRepository extends JpaRepository<Classes, Integer> {
    @Query("SELECT c.classId AS idClass, c.className AS className FROM Classes c")
    List<NewFindAllClassResponse> findAlls();

    @Query("SELECT c.classId AS classId, c.className AS className, u.fullName AS lecturerName FROM Classes c JOIN c.lecturer u WHERE u.id = :teacherId")
    List<NewListClassTeachResponse> findAllClassDetails(@Param("teacherId") Integer idTeacher);
}

