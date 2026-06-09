package com.ptit.ktx.service.impl;

import com.ptit.ktx.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
  @Override
  public String login(String username, String password) {
    if ("admin".equals(username) && "123456".equals(password)) {
      return "demo-token-" + UUID.randomUUID();
    }
    throw new IllegalArgumentException("Sai tài khoản hoặc mật khẩu");
  }
}
