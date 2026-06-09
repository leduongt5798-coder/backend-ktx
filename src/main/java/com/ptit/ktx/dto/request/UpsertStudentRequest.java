package com.ptit.ktx.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpsertStudentRequest(
    @NotBlank String code,
    @NotBlank String name,
    String clazz,
    String major,
    String gender,
    String dob, // dd/MM/yyyy
    String birthplace,
    String nationality,
    String ethnicity,
    String religion,
    String phone,
    String email,
    String address,
    String cccd,
    String status,
    String expiry, // dd/MM/yyyy
    String room,
    String bed
) {}