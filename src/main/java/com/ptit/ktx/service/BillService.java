package com.ptit.ktx.service;

import com.ptit.ktx.dto.response.BillResponse;

import java.util.List;
import java.util.Optional;

public interface BillService {

    /**
     * Lấy danh sách hóa đơn của 1 sinh viên, mới nhất trước
     */
    List<BillResponse> billsByStudent(String studentCode);

    /**
     * Lấy hóa đơn mới nhất của 1 sinh viên
     */
    Optional<BillResponse> latestBillByStudent(String studentCode);

} // ✅ Kết thúc interface, KHÔNG được bỏ