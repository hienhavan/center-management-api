package org.example.quanlytrungtam.dailystudent;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.student.Student;
import org.example.quanlytrungtam.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;


    private String context;
}
