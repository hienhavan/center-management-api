package org.example.quanlytrungtam.academicaffairs;

import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.classes.Classes;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AcademicAffairsController {
    private final ClassService classService;
    private final AcademicAffairsService academicAffairsService;

    public AcademicAffairsController(ClassService classService, AcademicAffairsService academicAffairsService) {
        this.classService = classService;
        this.academicAffairsService = academicAffairsService;
    }

    @GetMapping("/api/v1/academic-affairs/list-class")
    public ResponseEntity<?> getAllListClasses() {
        List<NewFindAllClassResponse> data = classService.listClass();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/academic-affairs/list-student")
    public ResponseEntity<?> getAllListStudents(@RequestParam(name = "idClass", required = false) Integer idClass) {
        List<NewFinAllClassStudentResponse> data = academicAffairsService.listStudent(idClass);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
