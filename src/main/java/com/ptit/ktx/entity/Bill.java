package com.ptit.ktx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name = "bills",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_bill_student_period", columnNames = {"student_id", "billing_period"})
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Bill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "bill_no", unique = true, nullable = false, length = 80)
  private String billNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(precision = 15, scale = 2)
  private BigDecimal amount;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "payment_date")
  private LocalDate paymentDate;

  @Column(length = 255)
  private String note;

  @Column(name = "paid_status", length = 20)
  private String paidStatus; // PAID/UNPAID

  // ✅ Kỳ thu (VD: "2026-03")
  @Column(name = "billing_period", length = 7, nullable = false)
  private String billingPeriod;
}