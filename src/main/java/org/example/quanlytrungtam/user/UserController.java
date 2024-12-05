package org.example.quanlytrungtam.user;

import jakarta.servlet.http.HttpSession;
import org.example.quanlytrungtam.config.jwt.JwtService;
import org.example.quanlytrungtam.email.EmailRequest;
import org.example.quanlytrungtam.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private HttpSession httpSession;
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<?> register(@RequestBody AddUserRequest request) {
        try {
            userService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("api/v1/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        userService.findById(id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("api/v1/me")
    public ResponseEntity<?> informationUser(Principal principal) {
        try {
            String user = principal.getName();
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
            }
//            return ResponseEntity.ok(userService.informationUser(user));
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }
    }

    @PostMapping("api/v1/user/find-email")
    public ResponseEntity<String> findPassword(@RequestBody EmailRequest email) {
        try {
            userService.findPassword(email.getEmail(), httpSession);
            return ResponseEntity.ok("Password recovery email has been sent.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body("User not found with the provided email.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing your request.");
        }
    }

    @PostMapping("api/v1/user/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody RestPasswordRequest request) {
        String email = (String) httpSession.getAttribute("resetEmail");
        if (email == null) {
            return ResponseEntity.status(400).body("No email found in session.");
        }
        try {
            userService.changePassword(email, request.getCode(), request.getNewPassword(), httpSession);
            httpSession.removeAttribute("resetEmail");
            httpSession.removeAttribute("code");
            return ResponseEntity.ok("Password has been successfully reset.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404)
                    .body("Invalid recovery code: " + request.getCode());
        }
    }
}
