package org.example.quanlytrungtam.user;

import jakarta.servlet.http.HttpSession;
import org.example.quanlytrungtam.config.jwt.JwtService;
import org.example.quanlytrungtam.email.EmailRequest;
import org.example.quanlytrungtam.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private HttpSession httpSession;
    private final JwtService jwtService;
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;


    public UserController(JwtService jwtService, UserService userService, RedisTemplate<String, Object> redisTemplate) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        userService.findById(id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/api/v1/me")
    public ResponseEntity<?> informationUser(Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
            }
            return ResponseEntity.ok(userService.informationUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }
    }


    @PutMapping("/api/v1/me")
    public ResponseEntity<String> updateUser(@ModelAttribute FormUpdateRequest profilePicture, Principal principal) {
        try {
            int id = userService.findByEmail(principal.getName()).getId();
            userService.update(id, profilePicture);
            return ResponseEntity.ok("cập nhật thành công");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("lỗi cập nhât user");
        }
    }

    @PutMapping("/api/v1/me/password")
    public ResponseEntity<String> updatePassword(
            Principal principal,
            @RequestBody UpdatePasswordRequest request) {
        try {
            int id = userService.findByEmail(principal.getName()).getId();
            if (userService.updatePassword(id, request.getNewPassword(), request.getCurrentPassword()) != null) {
                return ResponseEntity.ok("cập nhật thành công");
            }
            return ResponseEntity.badRequest().body("Mật khẩu không chính xác");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu không hợp lệ");
        }
    }

    @PostMapping("/api/v1/user/find-email")
    public ResponseEntity<String> findPassword(@RequestBody EmailRequest email) {
        try {
            userService.findPassword(email.getEmail());
            return ResponseEntity.ok("Email khôi phục mật khẩu đã được gửi.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body("Không tìm thấy người dùng");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("lỗi");
        }
    }

    @PostMapping("/api/v1/user/reset-password/{emailReq}")
    public ResponseEntity<String> resetPassword(@PathVariable String emailReq, @RequestBody RestPasswordRequest request) {
        String email = (String) redisTemplate.opsForValue().get("email:" + emailReq);
        String key = "email:" + email;
        if (!userService.isRequestAllowed(key)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(" gửi quá nhiều yêu cầu");
        }
        try {
            userService.changePassword(email, request.getCode(), request.getNewPassword());
            return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404)
                    .body("Invalid recovery code: " + request.getCode());
        }
    }
}
