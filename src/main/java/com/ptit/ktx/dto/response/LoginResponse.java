package com.ptit.ktx.dto.response;

public record LoginResponse(
    String token,
    String username
) {}
