package com.ptit.ktx.service;

import com.ptit.ktx.dto.request.UpsertStudentRequest;
import com.ptit.ktx.dto.response.StudentResponse;

public interface StudentService {
  StudentResponse getByCode(String code);
  StudentResponse update(String code, UpsertStudentRequest req);
}
