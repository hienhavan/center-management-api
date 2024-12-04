package org.example.quanlytrungtam.classes;

import jakarta.persistence.*;
import lombok.Data;
import org.example.quanlytrungtam.user.User;

@Data
@Entity
@Table(name = "Classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classid")
    private Long classId;

    @Column(name = "classname", nullable = false)
    private String className;

    @ManyToOne
    @JoinColumn(name = "teacherid")
    private User lecturer;

    @Override
    public String toString() {
        return "Class{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", lecturer=" + lecturer +
                '}';
    }
}