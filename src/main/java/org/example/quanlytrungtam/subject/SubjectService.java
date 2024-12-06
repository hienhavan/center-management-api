package org.example.quanlytrungtam.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public void saveSubject(AddSubjectRequest request) {
        var subject = Subject.builder()
                .subjectName(request.getSubjectName()).build();
        subjectRepository.save(subject);
    }
}