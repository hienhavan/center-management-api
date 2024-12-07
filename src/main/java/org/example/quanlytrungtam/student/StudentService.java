package org.example.quanlytrungtam.student;

import org.example.quanlytrungtam.academicaffairs.NewFindAllClassStudentResponse;
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

    public Student findById(Integer idStudent) {
        return studentRepository.findById(idStudent).orElse(null);
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

    public void changeStatus(ChangeStatusRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("No student found with id: " + request.getStudentId()));
        student.setStatus(Status.valueOf(request.getStatus()));
        studentRepository.save(student);
    }

    public List<NewFindAllClassStudentResponse> findAllStudentStatus(String status) {
        Status statusEnum = Status.valueOf(status);
        return studentRepository.listStudentStatus(statusEnum);
    }

    public NewFindStudentResponse showProfileStudent(Integer idStudent) {
        return studentRepository.profileStudent(idStudent);
    }
}
