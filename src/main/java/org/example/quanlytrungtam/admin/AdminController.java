package org.example.quanlytrungtam.admin;

import org.example.quanlytrungtam.classes.AddClassRequest;
import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.fee.AddFeeRequest;
import org.example.quanlytrungtam.fee.FeeService;
import org.example.quanlytrungtam.grade.GradeService;
import org.example.quanlytrungtam.student.AddStudentRequest;
import org.example.quanlytrungtam.student.LecturerClassStudentCountProjectionResponse;
import org.example.quanlytrungtam.student.StudentService;
import org.example.quanlytrungtam.subject.AddSubjectRequest;
import org.example.quanlytrungtam.subject.SubjectService;
import org.example.quanlytrungtam.user.AddUserRequest;
import org.example.quanlytrungtam.user.FindUserResponse;
import org.example.quanlytrungtam.user.Role;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {
    private final SubjectService subjectService;
    private final UserService userService;
    private final ClassService classService;
    private final FeeService feeService;
    private final StudentService studentService;
    private final GradeService gradeService;

    public AdminController(SubjectService subjectService, UserService userService, ClassService classService, FeeService feeService, StudentService studentService, GradeService gradeService) {
        this.subjectService = subjectService;
        this.userService = userService;
        this.classService = classService;
        this.feeService = feeService;
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    @PostMapping("/api/v1/admin/register")
    public ResponseEntity<?> register(@RequestBody AddUserRequest request) {
        try {
            userService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/admin/new-users")
    public ResponseEntity<List<NewUserByMonthResponse>> getUserNumberByMonthOfYear(@RequestParam(name = "year") int year) {
        List<NewUserByMonthResponse> data = userService.getUserNumberByMonthOfYear(year);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PutMapping("api/v1/admin/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable int id) {
        userService.updateActive(id);
        return ResponseEntity.ok("Đã khóa tài khoản");
    }

    @PostMapping("/api/v1/admin/subjects")
    public ResponseEntity<?> saveSubject(@RequestBody AddSubjectRequest request) {
        try {
            subjectService.saveSubject(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/admin/all-teacher")
    public ResponseEntity<?> getAllTeachers() {
        List<NewFindAllTeacherResponse> data = userService.findAllTeacher();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/admin/all-students")
    public ResponseEntity<?> getAllStudent() {
        List<NewFindAllTeacherResponse> data = userService.findAllStudent();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping("/api/v1/admin/add-class")
    public ResponseEntity<?> addClass(@RequestBody AddClassRequest request) {
        try {
            classService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Thêm thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi hệ thống: " + e.getMessage());
        }
    }

    @PostMapping("/api/v1/admin/add-student")
    public ResponseEntity<?> addStudent(@RequestBody AddStudentRequest request) {
        try {
            studentService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Thêm thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/admin/teacher-student-counts")
    public ResponseEntity<?> getAllStudents() {
        List<LecturerClassStudentCountProjectionResponse> data = userService.findTeacherStudentCounts();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping("/api/v1/admin/add-fee")
    public ResponseEntity<?> addFee(@RequestBody AddFeeRequest request) {
        try {
            feeService.save(request);
            return ResponseEntity.ok("thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/admin/list-avg-class")
    public ResponseEntity<?> getAvgClass() {
        List<NewAverageGradesByClassResponse> data = gradeService.listAvgGradesByClass();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/admin/list-avg-student-class/{idClass}")
    public ResponseEntity<?> getAvgStudentClass(@PathVariable Integer idClass) {
        List<NewAvgGradeStudentClassResponse> data = gradeService.listAvgGradeStudentClass(idClass);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
    @GetMapping("/api/v1/admin/users")
    public ResponseEntity<?> findFriendsByName(@RequestParam(name = "name", required = false) String name) {
        try {
            List<FindUserResponse> findFriendsByName = userService.findByName(name);
            if (findFriendsByName.isEmpty()) {
                return ResponseEntity.ok("Không tìm thấy người dùng.");
            }
            return ResponseEntity.ok(findFriendsByName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Tìm kiếm bạn bè thất bại.");
        }
    }

}
