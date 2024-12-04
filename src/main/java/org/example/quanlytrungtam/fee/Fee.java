package org.example.quanlytrungtam.fee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.quanlytrungtam.student.Student;

import java.util.Date;

@Data
@Entity
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
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "Amount", nullable = false)
    private Double amount;

    @NotNull

    private String status;
}