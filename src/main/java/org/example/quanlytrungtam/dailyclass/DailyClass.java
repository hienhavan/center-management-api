package org.example.quanlytrungtam.dailyclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dailyclass")
public class DailyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dailyclassid")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "classid", nullable = false)
    private Classes classes;

    @ManyToOne
    @JoinColumn(name = "lecturerid", nullable = false)
    private User lecturer;

    @Column(name = "creationdate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    private String context;
}
