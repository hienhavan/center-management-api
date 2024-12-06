package org.example.quanlytrungtam.classes;

import org.example.quanlytrungtam.academicaffairs.NewFindAllClassResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassRepository extends JpaRepository<Classes, Integer> {
    @Query("SELECT c.classId AS idClass, c.className AS className FROM Classes c")
    List<NewFindAllClassResponse> findAlls();
}

