package com.ptit.ktx.controller;

import com.ptit.ktx.dto.response.BillResponse;
import com.ptit.ktx.service.BillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

  private final BillService billService;

  public BillController(BillService billService) {
    this.billService = billService;
  }

  @GetMapping("/by-student/{studentCode}")
  public List<BillResponse> byStudent(@PathVariable String studentCode) {
    return billService.billsByStudent(studentCode);
  }
}
