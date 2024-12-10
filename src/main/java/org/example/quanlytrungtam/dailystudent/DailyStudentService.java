package org.example.quanlytrungtam.dailystudent;

import org.example.quanlytrungtam.dailyclass.NewfindListDailyClassResponse;
import org.example.quanlytrungtam.student.Student;
import org.example.quanlytrungtam.student.StudentService;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


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

    public void delete(Integer idDaily) {
        dailyStudentRepository.deleteById(idDaily);
    }

    public List<NewfindListDailyStudentResponse> getNewFindListDaily(Integer idStudent) {
        return dailyStudentRepository.findDailyStudentByClassId(idStudent);
    }

    LocalDateTime currentDateTime = LocalDateTime.now();

    public void save(AddDailyStudentRequest request, Integer idTeacher) {
        Student student = studentService.findById(request.getIdStudent());
        User user = userService.findById(idTeacher);
        var dailyStudent = DailyStudent.builder()
                .student(student)
                .lecturer(user)
                .context(request.getContext())
                .creationDate(currentDateTime)
                .build();
        dailyStudentRepository.save(dailyStudent);
    }
}
