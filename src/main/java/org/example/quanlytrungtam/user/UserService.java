package org.example.quanlytrungtam.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.quanlytrungtam.admin.NewUserByMonthResponse;
import org.example.quanlytrungtam.email.EmailService;
import org.example.quanlytrungtam.exception.UserNotFoundException;
import org.example.quanlytrungtam.admin.NewFindAllTeacherResponse;
import org.example.quanlytrungtam.student.LecturerClassStudentCountProjectionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Value("${upload.image}")
    private String uploadDir;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Integer id) throws UserNotFoundException {

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public UpdateUserRequest informationUser(User request) {
        return UpdateUserRequest.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .gender(request.getGender())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .img(request.getImg()).build();
    }

    public void save(AddUserRequest request) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate1 = formatter.format(date);
        LocalDate sqlDate1 = java.sql.Date.valueOf(formattedDate1).toLocalDate();
        var user = User.builder()
                .email(request.getEmail())
                .password(encodePassword(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .creationDate(sqlDate1)
                .img("")
                .active(request.getActive() != null ? request.getActive() : true)
                .roles(request.getRoles())
                .build();
        userRepository.save(user);
        String email = request.getEmail();
        String username = request.getFullName();
        String password = request.getPassword();
        String subject = "Your account has been created";
        String text = String.format("Hello %s,\n\nYour account has been created successfully.\nAccount: %s\nPassword: %s\n\nBest regards", username, email, password);
        emailService.sendEmail(email, subject, text);
    }

    public void update(int id, FormUpdateRequest request) throws UserNotFoundException {
        User user = findById(id);

        user.setFullName(request.getFullName() != null ? request.getFullName() : user.getFullName());
        user.setGender(request.getGender() != null ? request.getGender() : user.getGender());
        user.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth() != null ? request.getDateOfBirth() : user.getDateOfBirth());
        user.setAddress(request.getAddress() != null ? request.getAddress() : user.getAddress());
        MultipartFile profilePicture = request.getImg();
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String fileName = profilePicture.getOriginalFilename();
            File file = new File(uploadDir + fileName);
            try {
                profilePicture.transferTo(file);
                user.setImg(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving file");
            }
        }

        userRepository.save(user);
    }

    public void delete(int id) throws UserNotFoundException {
        findById(id);
        userRepository.deleteById(id);
    }

    public User updatePassword(int id, String newPassword, String oldPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(encodePassword(newPassword));
            return userRepository.save(user);
        }
        return null;
    }

    public List<FindUserResponse> findByName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(this::convertToFindUserRequest)
                    .collect(Collectors.toList());
        } else {
            List<User> users = userRepository.findByFullNameContainingIgnoreCase(name);
            if (users == null || users.isEmpty()) {
                throw new Exception("User not found");
            }
            return users.stream()
                    .map(this::convertToFindUserRequest)
                    .collect(Collectors.toList());
        }
    }

    public List<UserDTO> findUserByName(String name) throws IOException {
        if (name != null) {
            List<User> userDTOS = userRepository.findByName(name);
            return userDTOS.stream()
                    .map(this::convertToUserDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private FindUserResponse convertToFindUserRequest(User user) {
        return FindUserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .active(user.getActive())
                .img(user.getImg())
                .address(user.getAddress())
                .build();
    }

    private UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getFullName())
                .img(user.getImg())
                .build();
    }

    public User updateActive(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setActive(!userEntity.getActive());
            return userRepository.save(userEntity);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void findPassword(String email, HttpSession session) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        Random random = new Random();
        String code = String.valueOf(random.nextInt(9000) + 1000);
        session.setAttribute("code", code);
        session.setMaxInactiveInterval(30 * 60);
        String name = user.getFullName();
        String subject = "Request Password Recovery";
        String text = String.format("Hello %s,\n\nYour password recovery request has been successful, please enter the following code in the Code section of the website to continue:\nCode: %s\n\nIf it's not you, there's no need to do anything.", name, code);
        emailService.sendEmail(email, subject, text);
        session.setAttribute("resetEmail", email);
    }

    public void changePassword(String email, String enteredCode, String newPassword, HttpSession session) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        String code = (String) session.getAttribute("code");
        if (code != null && code.equals(enteredCode)) {
            user.setPassword(encodePassword(newPassword));
            userRepository.save(user);
            String name = user.getFullName();
            String subject = "Password change successful";
            String text = String.format("Hello %s,\n\nYour password recovery request has been successful, Your new password is:\nPassword: %s\n\nPlease protect it and do not share it with anyone.", name, newPassword);
            emailService.sendEmail(email, subject, text);
        } else {
            throw new UserNotFoundException("Invalid recovery code: " + enteredCode);
        }
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("not found" + email));
    }

    public List<NewFindAllTeacherResponse> findAllTeacher() throws UserNotFoundException {
        return userRepository.findByRoles(Role.ROLE_TEACHER);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public List<NewUserByMonthResponse> getUserNumberByMonthOfYear(int year) {
        return userRepository.getUserNumberByMonthOfYear(year);
    }

    public List<LecturerClassStudentCountProjectionResponse> findTeacherStudentCounts() {
        return userRepository.getLecturerClassStudentCounts();
    }
}