package org.example.quanlytrungtam.academicaffairs;

import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.config.page.PageResponse;
import org.example.quanlytrungtam.grade.*;
import org.example.quanlytrungtam.student.StudentService;
import org.example.quanlytrungtam.subject.Subject;
import org.example.quanlytrungtam.subject.SubjectService;
import org.springframework.data.domain.Slice;
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
    public ResponseEntity<?> getAllListClasses(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Slice<NewFindAllClassResponse> data = classService.listClass(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(data));
    }

    @GetMapping("/api/v1/academic-affairs/list-student")
    public ResponseEntity<?> getAllListStudents(@RequestParam(name = "idClass", required = false) Integer idClass,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Slice<NewFindAllClassStudentResponse> data = academicAffairsService.listStudent(idClass, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(data));
    }

    @GetMapping("/api/v1/academic-affairs/list-grade")
    public ResponseEntity<?> getAllListGrade(@RequestParam(name = "idStudent", required = false) Integer idStudent,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Slice<ShowStudentGradesResponse> data = gradesService.getAllStudentGrades(idStudent, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(data));
    }

    @GetMapping("/api/v1/academic-affairs/grade")
    public ResponseEntity<?> getGrade(@RequestParam(name = "idGrade", required = false) Integer idGrade) {
        Optional<FindGradeIdResponse> data = gradesService.getGrade(idGrade);
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

    @GetMapping("/api/v1/academic-affairs/changeStatus/{idStudent}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Integer idStudent, @PathVariable String status) {
        studentService.changeStatus(idStudent, status);
        return ResponseEntity.ok("thành công");
    }
}
