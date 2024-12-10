package org.example.quanlytrungtam.dailyclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyClassRepository extends JpaRepository<DailyClass, Integer> {
    @Query("SELECT d.id AS idDaily, d.lecturer.fullName AS nameTeacher, d.creationDate AS dueDate, d.context AS context " +
            "FROM DailyClass d WHERE d.classes.classId = :idClass")
    List<NewfindListDailyResponse> findDailyClassesByClassId(@Param("idClass") Integer idClass);
}
