package org.example.quanlytrungtam.subject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectid")
    private Integer subjectId;
    @Column(name = "subjectname")
    private String subjectName;
}