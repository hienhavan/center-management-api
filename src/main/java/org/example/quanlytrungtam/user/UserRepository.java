package org.example.quanlytrungtam.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    List<User> findByFullNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%' ,:name, '%'))")
    List<User> findByName(@Param("name") String name);

    @Query(value = "WITH Months AS (SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12) SELECT Months.month, coalesce(u.newUser, 0) as newUsers FROM Months LEFT JOIN  (SELECT MONTH(creation_date) AS `month`, COUNT(*) AS newUser FROM users WHERE YEAR(creation_date) = :year GROUP BY MONTH(creation_date)) AS u ON u.month = Months.month", nativeQuery = true)
    List<NewUserByMonthResponse> getUserNumberByMonthOfYear(@Param("year") int year);

}
