package org.example.quanlytrungtam.student;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.user.User;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentid")
    private Integer studentId;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User userID;

    @ManyToOne
    @JoinColumn(name = "classid")
    private Classes classID;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

}
