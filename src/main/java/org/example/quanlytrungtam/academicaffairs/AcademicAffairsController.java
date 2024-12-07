package org.example.quanlytrungtam.academicaffairs;

import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.grade.AddGradeRequest;
import org.example.quanlytrungtam.grade.GradeService;
import org.example.quanlytrungtam.grade.ShowStudentGradeResponse;
import org.example.quanlytrungtam.grade.UpdateGradeRequest;
import org.example.quanlytrungtam.student.ChangeStatusRequest;
import org.example.quanlytrungtam.student.StudentService;
import org.example.quanlytrungtam.subject.Subject;
import org.example.quanlytrungtam.subject.SubjectService;
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
    private final SubjectService subjectService;
    private final StudentService studentService;

    public AcademicAffairsController(ClassService classService, AcademicAffairsService academicAffairsService, GradeService gradesService, SubjectService subjectService, StudentService studentService) {
        this.classService = classService;
        this.academicAffairsService = academicAffairsService;
        this.gradesService = gradesService;
        this.subjectService = subjectService;
        this.studentService = studentService;
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

    @GetMapping("/api/v1/academic-affairs/delete-grade/{idGrade}")
    public ResponseEntity<?> deleteGrade(@PathVariable("idGrade") Integer idGrade) {
        gradesService.deleteGrade(idGrade);
        return ResponseEntity.ok("thành công");
    }

    @GetMapping("/api/v1/academic-affairs/all-subject")
    public ResponseEntity<?> getAllSubject() {
        List<Subject> data = subjectService.getAllSubjects();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping("/api/v1/academic-affairs/add-grade")
    public ResponseEntity<?> addGrade(@RequestBody AddGradeRequest request) {
        try {
            gradesService.saveGrade(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/academic-affairs/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatusRequest request) {
        studentService.changeStatus(request);
        return ResponseEntity.ok("thành công");
    }
}
