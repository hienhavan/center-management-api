package org.example.quanlytrungtam.student;

import org.example.quanlytrungtam.fee.Fee;
import org.example.quanlytrungtam.fee.FeeService;
import org.example.quanlytrungtam.fee.NewFindFeeResponse;
import org.example.quanlytrungtam.grade.GradeService;
import org.example.quanlytrungtam.grade.ShowStudentGradeResponse;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StudentController {
    private final FeeService feeService;
    private final UserService userService;
    private final GradeService gradeService;

    public StudentController(FeeService feeService, UserService userService, GradeService gradeService) {
        this.feeService = feeService;
        this.userService = userService;
        this.gradeService = gradeService;
    }

    @GetMapping("/api/v1/student/list-fee")
    public ResponseEntity<?> getListFee(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Integer idUser = user.getId();
        List<NewFindFeeResponse> data = feeService.getFeesByStudent(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/student/list-fee-history")
    public ResponseEntity<?> getListFeeHistory(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Integer idUser = user.getId();
        List<NewFindFeeResponse> data = feeService.getFeesByStudentHistory(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/v1/student/payFee")
    public ResponseEntity<?> payFee(@RequestParam(name = "idFee", required = false) Integer idFee) {
        feeService.update(idFee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/v1/student/list-grade")
    public ResponseEntity<?> getStudentGrades(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Integer idUser = user.getId();
        List<ShowStudentGradeResponse> data = gradeService.getAllStudentGrades(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
