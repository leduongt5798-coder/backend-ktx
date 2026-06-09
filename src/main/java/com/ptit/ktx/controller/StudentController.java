package com.ptit.ktx.controller;

import com.ptit.ktx.dto.request.UpsertStudentRequest;
import com.ptit.ktx.dto.response.StudentResponse;
import com.ptit.ktx.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/{studentCode}")
  public StudentResponse get(@PathVariable String studentCode) {
    return studentService.getByCode(studentCode);
  }

  @PutMapping("/{studentCode}")
  public StudentResponse update(@PathVariable String studentCode, @Valid @RequestBody UpsertStudentRequest req) {
    return studentService.update(studentCode, req);
  }
}
