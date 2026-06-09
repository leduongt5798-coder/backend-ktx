package com.ptit.ktx.service.impl;

import com.ptit.ktx.dto.response.BillResponse;
import com.ptit.ktx.repository.BillRepository;
import com.ptit.ktx.service.BillService;
import com.ptit.ktx.util.DateUtil;
import com.ptit.ktx.entity.Bill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepo;

    public BillServiceImpl(BillRepository billRepo) {
        this.billRepo = billRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> billsByStudent(String studentCode) {
        List<Bill> bills = billRepo.findByStudent_CodeOrderByCreatedDateDesc(studentCode);
        return bills.stream().map(b -> new BillResponse(
                b.getBillNo(),
                b.getStudent().getCode(),
                b.getStudent().getName(),
                b.getStudent().getClazz(),
                DateUtil.formatDMY(b.getStudent().getDob()),
                b.getAmount() == null ? null : b.getAmount().toPlainString(),
                DateUtil.formatDMY(b.getPaymentDate()),
                DateUtil.formatDMY(b.getCreatedDate()),
                b.getNote(),
                b.getPaidStatus()
        )).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BillResponse> latestBillByStudent(String studentCode) {
        Optional<Bill> billOpt = billRepo.findTopByStudent_CodeOrderByCreatedDateDesc(studentCode);

        return billOpt.map(b -> new BillResponse(
                b.getBillNo(),
                b.getStudent().getCode(),
                b.getStudent().getName(),
                b.getStudent().getClazz(),
                DateUtil.formatDMY(b.getStudent().getDob()),
                b.getAmount() == null ? null : b.getAmount().toPlainString(),
                DateUtil.formatDMY(b.getPaymentDate()),
                DateUtil.formatDMY(b.getCreatedDate()),
                b.getNote(),
                b.getPaidStatus()
        ));
    }
}