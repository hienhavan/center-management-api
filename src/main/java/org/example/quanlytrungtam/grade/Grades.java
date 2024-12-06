package org.example.quanlytrungtam.grade;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.student.Student;
import org.example.quanlytrungtam.subject.Subject;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gradeid")
    private Integer gradeId;
    @ManyToOne
    @JoinColumn(name = "studentid", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subjectid", nullable = false)
    private Subject subject;

    @Column(name = "theorygrade")
    private double theoryGrade;

    @Column(name = "practicalgrade")
    private double practicalGrade;

    @Column(name = "averagegrade")
    private double averageGrade;
}
