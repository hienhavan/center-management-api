package org.example.quanlytrungtam.user;

import org.example.quanlytrungtam.admin.NewUserByMonthResponse;
import org.example.quanlytrungtam.admin.NewFindAllTeacherResponse;
import org.example.quanlytrungtam.student.LecturerClassStudentCountProjectionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    List<User> findByFullNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%' ,:name, '%'))")
    List<User> findByName(@Param("name") String name);

    @Query(value = "WITH Months AS (SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12) SELECT Months.month, coalesce(u.newUser, 0) as newUsers FROM Months LEFT JOIN  (SELECT MONTH(creation_date) AS `month`, COUNT(*) AS newUser FROM users WHERE YEAR(creation_date) = :year GROUP BY MONTH(creation_date)) AS u ON u.month = Months.month", nativeQuery = true)
    List<NewUserByMonthResponse> getUserNumberByMonthOfYear(@Param("year") int year);

    @Query(value = "WITH Months AS ( " +
            "  SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL " +
            "  SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL " +
            "  SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL " +
            "  SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 " +
            ") " +
            "SELECT Months.month, COALESCE(u.newUser, 0) AS newUsers " +
            "FROM Months " +
            "LEFT JOIN ( " +
            "  SELECT MONTH(users.creation_date) AS `month`, COUNT(*) AS newUser " +
            "  FROM users " +
            "  JOIN user_roles ur ON users.id = ur.user_id " +
            "  WHERE YEAR(users.creation_date) = :year AND ur.roles = :role " +
            "  GROUP BY MONTH(users.creation_date) " +
            ") AS u " +
            "ON u.month = Months.month",
            nativeQuery = true)
    List<NewUserByMonthResponse> getUserNumberByMonthOfYearRole(@Param("year") int year, @Param("role") Role role);

    @Query("SELECT u.id as idUser, u.fullName as fullName FROM User u JOIN u.roles r WHERE r = :role")
    List<NewFindAllTeacherResponse> findByRoles(@Param("role") Role role);

    @Query("SELECT " +
            "c.id AS lecturerId, " +
            "c.fullName AS lecturerName, " +
            "cl.className AS className, " +
            "COUNT(s) AS studentCount " +
            "FROM Student s " +
            "JOIN s.classID cl " +
            "JOIN cl.lecturer c " +
            "GROUP BY c, cl")
    List<LecturerClassStudentCountProjectionResponse> getLecturerClassStudentCounts();
}
