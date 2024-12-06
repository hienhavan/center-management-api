package org.example.quanlytrungtam.student;

import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    private final UserService userService;

    private final ClassService classService;

    public StudentService(UserService userService, ClassService classService) {
        this.userService = userService;
        this.classService = classService;
    }

    public void flush() {

    }


    public <S extends Student> long count(Example<S> example) {
        return 0;
    }

    public <S extends Student> boolean exists(Example<S> example) {
        return false;
    }


    public void save(AddStudentRequest request) {
        User user = userService.findById(request.getUserId());
        Classes classes = classService.findById(request.getClassId());
        var student = Student.builder()
                .userID(user)
                .classID(classes)
                .status(Status.valueOf(request.getStatus()))
                .build();
        studentRepository.save(student);
    }

    public Student findById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public long count() {
        return 0;
    }


    public void delete(Student entity) {

    }

    public Student getById(Integer id) {
        return studentRepository.findByUserId(id)
                .orElseThrow(() -> new NoSuchElementException("No student found with user id: " + id));
    }

public void updateStatus (String status) {

}
}
