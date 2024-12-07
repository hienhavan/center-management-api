package org.example.quanlytrungtam.fee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {
    @Query("SELECT f.feeId AS idFee, s.userID.fullName AS studentName, f.dueDate AS dueDate, f.amount AS amount, f.status AS status " +
            "FROM Fee f JOIN f.student s WHERE s.studentId = :studentId")
    List<NewFindFeeResponse> findListFee(@Param("studentId") Integer studentId);

    @Query("SELECT f.feeId AS idFee, s.userID.fullName AS studentName, f.dueDate AS dueDate, f.amount AS amount, f.status AS status " +
            "FROM Fee f JOIN f.student s WHERE f.status = 'ACTIVE' AND  s.studentId = :studentId ORDER BY f.dueDate ASC")
    List<NewFindFeeResponse> findListFeeHistory(@Param("studentId") Integer studentId);
}

