package org.example.quanlytrungtam.dailyclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyClassRepository extends JpaRepository<DailyClass, Integer> {
}
