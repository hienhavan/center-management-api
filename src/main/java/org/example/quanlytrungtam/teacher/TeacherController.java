package org.example.quanlytrungtam.teacher;

import org.example.quanlytrungtam.academicaffairs.AcademicAffairsService;
import org.example.quanlytrungtam.academicaffairs.NewFindAllClassStudentResponse;
import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.classes.NewListClassTeachResponse;
import org.example.quanlytrungtam.student.NewFindStudentResponse;
import org.example.quanlytrungtam.student.StudentService;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TeacherController {
    private final UserService userService;
    private final ClassService classService;
    private final AcademicAffairsService academicAffairsService;
    private final StudentService studentService;

    public TeacherController(UserService userService, ClassService classService, AcademicAffairsService academicAffairsService, StudentService studentService) {
        this.userService = userService;
        this.classService = classService;
        this.academicAffairsService = academicAffairsService;
        this.studentService = studentService;
    }

    @GetMapping("/api/v1/teacher/all-classes")
    public ResponseEntity<?> listClass(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User không tồn tại");
        } else {
            Integer idUser = user.getId();
            List<NewListClassTeachResponse> data = classService.findAllClassDetails(idUser);
            return ResponseEntity.status(HttpStatus.OK).body(data);
        }
    }

    @GetMapping("/api/v1/teacher/list-student")
    public ResponseEntity<?> getAllListStudents(@RequestParam(name = "idClass", required = false) Integer idClass) {
        List<NewFindAllClassStudentResponse> data = academicAffairsService.listStudent(idClass);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/teacher/list-student-status")
    public ResponseEntity<?> getAllListStudentsStatus(@RequestParam(name = "status", required = false) String status) {
        List<NewFindAllClassStudentResponse> data = studentService.findAllStudentStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/teacher/profile-student/{idStudent}")
    public ResponseEntity<?> getProfileStudent(@PathVariable Integer idStudent) {
        NewFindStudentResponse data = studentService.showProfileStudent(idStudent);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}

