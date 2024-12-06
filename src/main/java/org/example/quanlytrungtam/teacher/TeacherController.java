package org.example.quanlytrungtam.teacher;

import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TeacherController {
    private final UserService userService;

    public TeacherController(UserService userService) {
        this.userService = userService;
    }

}

