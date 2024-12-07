package org.example.quanlytrungtam.dailystudent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStudentRepository extends JpaRepository<DailyStudent, Integer> {
}
