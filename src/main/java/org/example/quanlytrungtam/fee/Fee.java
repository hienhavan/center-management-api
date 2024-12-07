package org.example.quanlytrungtam.fee;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.student.Student;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feeid")
    private Integer feeId;

    @ManyToOne
    @JoinColumn(name = "studentid")
    private Student student;

    @Column(name = "duedate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Column(name = "Amount", nullable = false)
    private Double amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FeeStatus status;
}