package org.example.quanlytrungtam.academicaffairs;

import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.grade.GradeService;
import org.example.quanlytrungtam.grade.ShowGradeResponse;
import org.example.quanlytrungtam.grade.ShowStudentGradeResponse;
import org.example.quanlytrungtam.grade.UpdateGradeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class AcademicAffairsController {
    private final ClassService classService;
    private final AcademicAffairsService academicAffairsService;
    private final GradeService gradesService;

    public AcademicAffairsController(ClassService classService, AcademicAffairsService academicAffairsService, GradeService gradesService) {
        this.classService = classService;
        this.academicAffairsService = academicAffairsService;
        this.gradesService = gradesService;
    }

    @GetMapping("/api/v1/academic-affairs/list-class")
    public ResponseEntity<?> getAllListClasses() {
        List<NewFindAllClassResponse> data = classService.listClass();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/academic-affairs/list-student")
    public ResponseEntity<?> getAllListStudents(@RequestParam(name = "idClass", required = false) Integer idClass) {
        List<NewFindAllClassStudentResponse> data = academicAffairsService.listStudent(idClass);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/academic-affairs/list-grade")
    public ResponseEntity<?> getAllListGrade(@RequestParam(name = "idStudent", required = false) Integer idStudent) {
        List<ShowStudentGradeResponse> data = gradesService.getAllStudentGrades(idStudent);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/academic-affairs/grade")
    public ResponseEntity<?> getGrade(@RequestParam(name = "idGrade", required = false) Integer idGrade) {
        Optional<ShowStudentGradeResponse> data = gradesService.getGrade(idGrade);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PutMapping("/api/v1/academic-affairs/update-grade")
    public ResponseEntity<?> updateGrade(@RequestBody UpdateGradeRequest request) {
        gradesService.updateGrade(request);
        return ResponseEntity.ok("thành công");
    }

}
