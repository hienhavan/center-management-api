package org.example.quanlytrungtam.dailystudent;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.student.Student;
import org.example.quanlytrungtam.user.User;

import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dailystudent")
public class DailyStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dailystudentid")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "classid", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lecturerid", nullable = false)
    private User lecturer;

    @Column(name = "creationdate", nullable = false)
    private LocalDate creationDate = LocalDate.now();

    private String context;
}
