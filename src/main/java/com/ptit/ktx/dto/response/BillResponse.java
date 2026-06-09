package com.ptit.ktx.dto.response;

public record BillResponse(
    String billNo,
    String studentCode,
    String studentName,
    String clazz,
    String dob,
    String amount,
    String paymentDate,
    String createdDate,
    String note,
    String paidStatus
) {}
