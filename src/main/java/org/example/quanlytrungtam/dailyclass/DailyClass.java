package org.example.quanlytrungtam.dailyclass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.user.User;

import java.time.LocalDate;

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
    private LocalDate creationDate = LocalDate.now();

    private String content;
}
