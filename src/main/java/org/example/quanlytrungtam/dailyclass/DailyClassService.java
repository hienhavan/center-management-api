package org.example.quanlytrungtam.dailyclass;

import org.example.quanlytrungtam.classes.ClassService;
import org.example.quanlytrungtam.classes.Classes;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyClassService {
    @Autowired
    private DailyClassRepository dailyClassRepository;
    private final UserService userService;
    private final ClassService classService;

    public DailyClassService(UserService userService, ClassService classService) {
        this.userService = userService;
        this.classService = classService;
    }

    public List<NewfindListDailyClassResponse> getNewFindListDaily(Integer idClass) {
        return dailyClassRepository.findDailyClassesByClassId(idClass);
    }

    LocalDateTime currentDateTime = LocalDateTime.now();

    public void delete(Integer idDaily) {
        dailyClassRepository.deleteById(idDaily);
    }

    public void save(AddDailyClassRequest request, Integer idTeacher) {
        Classes classes = classService.findById(request.getIdClass());
        User user = userService.findById(idTeacher);
        var dailyClass = DailyClass.builder()
                .classes(classes)
                .lecturer(user)
                .context(request.getContext())
                .creationDate(currentDateTime)
                .build();
        dailyClassRepository.save(dailyClass);
    }
}
