package org.example.quanlytrungtam.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.user.User;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classid")
    private Integer classId;

    @Column(name = "classname", nullable = false)
    private String className;

    @ManyToOne
    @JoinColumn(name = "teacherid")
    private User lecturer;
}