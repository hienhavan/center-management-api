package org.example.quanlytrungtam.fee;

import org.example.quanlytrungtam.student.Student;
import org.example.quanlytrungtam.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeService {
    @Autowired
    private FeeRepository feeRepository;
    private final StudentService studentService;

    public FeeService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void save(AddFeeRequest request) {
        Student student = studentService.findById(request.getStudentId());
        var req = Fee.builder()
                .student(student)
                .amount(request.getAmount())
                .dueDate(request.getDueDate())
                .status(FeeStatus.UNPAID)
                .build();
        feeRepository.save(req);
    }

    public List<NewFindFeeResponse> getFeesByStudent(Integer studentId) {
        return feeRepository.findListFee(studentId);
    }

    public List<NewFindFeeResponse> getFeesByStudentHistory(Integer studentId) {
        return feeRepository.findListFeeHistory(studentId);
    }

    public Fee findById(Integer idFee) {
        return feeRepository.findById(idFee).orElse(null);
    }

    public void update(Integer idFee) {
        Fee fee = findById(idFee);
        fee.setStatus(FeeStatus.ACTIVE);
        feeRepository.save(fee);
    }
}
