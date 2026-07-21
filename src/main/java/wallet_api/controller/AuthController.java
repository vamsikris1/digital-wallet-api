package wallet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import wallet_api.dto.LoginDTO;
import wallet_api.dto.LoginResponseDTO;
import wallet_api.dto.UserRegisterDTO;
import wallet_api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRegisterDTO dto) {

        return userService.register(dto);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        LoginResponseDTO response = userService.login(dto);

        if (response == null) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        return ResponseEntity.ok(response);
    }
}