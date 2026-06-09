package com.ptit.ktx.controller;

import com.ptit.ktx.dto.request.LoginRequest;
import com.ptit.ktx.dto.response.LoginResponse;
import com.ptit.ktx.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest req) {
    String token = authService.login(req.username(), req.password());
    return new LoginResponse(token, req.username());
  }
}
