package org.example.quanlytrungtam.teacher;

import org.example.quanlytrungtam.academicaffairs.AcademicAffairsService;
import org.example.quanlytrungtam.academicaffairs.NewFindAllClassStudentResponse;
import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.classes.NewListClassTeachResponse;
import org.example.quanlytrungtam.config.page.PageResponse;
import org.example.quanlytrungtam.dailyclass.AddDailyClassRequest;
import org.example.quanlytrungtam.dailyclass.DailyClassService;
import org.example.quanlytrungtam.dailyclass.NewfindListDailyClassResponse;
import org.example.quanlytrungtam.dailystudent.AddDailyStudentRequest;
import org.example.quanlytrungtam.dailystudent.DailyStudentService;
import org.example.quanlytrungtam.dailystudent.NewfindListDailyStudentResponse;
import org.example.quanlytrungtam.student.NewFindStudentResponse;
import org.example.quanlytrungtam.student.StudentService;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.data.domain.Slice;
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
    private final DailyStudentService dailyStudentService;
    private final DailyClassService dailyClassService;

    public TeacherController(UserService userService, ClassService classService, AcademicAffairsService academicAffairsService, StudentService studentService, DailyStudentService dailyStudentService, DailyClassService dailyClassService) {
        this.userService = userService;
        this.classService = classService;
        this.academicAffairsService = academicAffairsService;
        this.studentService = studentService;
        this.dailyStudentService = dailyStudentService;
        this.dailyClassService = dailyClassService;
    }

    @GetMapping("/api/v1/teacher/all-classes")
    public ResponseEntity<?> listClass(Principal principal,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User không tồn tại");
        } else {
            Integer idUser = user.getId();
            Slice<NewListClassTeachResponse> data = classService.findAllClassDetails(idUser, page, size);
            return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(data));
        }
    }

    @GetMapping("/api/v1/teacher/list-student")
    public ResponseEntity<?> getAllListStudents(@RequestParam(name = "idClass", required = false) Integer idClass,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Slice<NewFindAllClassStudentResponse> data = academicAffairsService.listStudent(idClass, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(data));
    }

    @GetMapping("/api/v1/teacher/delete-daily-class/{idDaily}")
    public ResponseEntity<?> deleteDailyClass(@PathVariable Integer idDaily) {
        dailyClassService.delete(idDaily);
        return ResponseEntity.ok("thanh cong");
    }

    @GetMapping("/api/v1/teacher/delete-daily-student/{idDaily}")
    public ResponseEntity<?> deleteDailyStudent(@PathVariable Integer idDaily) {
        dailyStudentService.delete(idDaily);
        return ResponseEntity.ok("thanh cong");
    }


    @GetMapping("/api/v1/teacher/list-daily/{idClass}")
    public ResponseEntity<?> getAllListDailyClass(@PathVariable Integer idClass) {
        List<NewfindListDailyClassResponse> data = dailyClassService.getNewFindListDaily(idClass);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/teacher/list-daily-student/{idStudent}")
    public ResponseEntity<?> getAllListDailyStudent(@PathVariable Integer idStudent) {
        List<NewfindListDailyStudentResponse> data = dailyStudentService.getNewFindListDaily(idStudent);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/teacher/profile-student/{idStudent}")
    public ResponseEntity<?> getProfileStudent(@PathVariable Integer idStudent) {
        NewFindStudentResponse data = studentService.showProfileStudent(idStudent);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping("/api/v1/teacher/add-daily-class")
    public ResponseEntity<?> addDailyClass(@RequestBody AddDailyClassRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Integer idTeacher = user.getId();
        try {
            dailyClassService.save(request, idTeacher);
            return ResponseEntity.status(HttpStatus.CREATED).body("thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @PostMapping("/api/v1/teacher/add-daily-student")
    public ResponseEntity<?> addDailyStudent(@RequestBody AddDailyStudentRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Integer idTeacher = user.getId();
        try {
            dailyStudentService.save(request, idTeacher);
            return ResponseEntity.status(HttpStatus.CREATED).body("thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("L��i hệ thống: " + e.getMessage());
        }
    }
}

