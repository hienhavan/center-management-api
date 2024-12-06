package org.example.quanlytrungtam.classes;

import org.example.quanlytrungtam.academicaffairs.NewFindAllClassResponse;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;
    private final UserService userService;

    public ClassService(UserService userService) {
        this.userService = userService;
    }

    public List<NewFindAllClassResponse> listClass() {
        return classRepository.findAlls();
    }

    public Classes findById(Integer id) {
        return classRepository.findById(id).orElse(null);
    }

    public void save(AddClassRequest request) {
        User lecturer = userService.findById(request.getTeacherId());
        var classes = Classes.builder()
                .className(request.getClassName())
                .lecturer(lecturer)
                .build();
        classRepository.save(classes);
    }

}