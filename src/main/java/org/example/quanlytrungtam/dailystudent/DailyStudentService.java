package org.example.quanlytrungtam.dailystudent;

import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.dailyclass.AddDailyClassRequest;
import org.example.quanlytrungtam.dailyclass.DailyClass;
import org.example.quanlytrungtam.student.Student;
import org.example.quanlytrungtam.student.StudentService;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyStudentService {
    @Autowired
    private DailyStudentRepository dailyStudentRepository;
    private final UserService userService;
    private final StudentService studentService;

    public DailyStudentService(UserService userService, StudentService studentService) {
        this.userService = userService;
        this.studentService = studentService;
    }

    public void save(AddDailyStudentRequest request) {
        Student student = studentService.findById(request.getStudentId());
        User user = userService.findById(request.getTeacherId());
        var dailyStudent = DailyStudent.builder()
                .student(student)
                .lecturer(user)
                .context(request.getContext())
                .build();
        dailyStudentRepository.save(dailyStudent);
    }
}
