package com.ptit.ktx.config;

import com.ptit.ktx.entity.Bill;
import com.ptit.ktx.entity.Room;
import com.ptit.ktx.entity.Student;
import com.ptit.ktx.repository.BillRepository;
import com.ptit.ktx.repository.RoomRepository;
import com.ptit.ktx.repository.StudentRepository;
import com.ptit.ktx.util.DateUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Configuration
public class DataSeeder {

  @Bean
  CommandLineRunner seed(RoomRepository roomRepo, StudentRepository studentRepo, BillRepository billRepo) {
    return args -> {
      String[] rooms = {"A1", "A2", "B1", "B2"};
      for (String code : rooms) {
        roomRepo.findByCode(code).orElseGet(() -> roomRepo.save(Room.builder().code(code).build()));
      }

      Map<String, Object[][]> seeds = Map.of(
          "A1", new Object[][]{{"N23DCVT001", "Nguyen Van A", "G1"}, {"N23DCVT002", "Nguyen Van B", "G2"}},
          "A2", new Object[][]{{"N23DCVT003", "NGuyen tương vi", "G3"}, {"N23DCVT004", "Nguyen Van D", "G4"}},
          "B1", new Object[][]{{"N23DCVT005", "Nguyen Van E", "G5"}},
          "B2", new Object[][]{{"N23DCVT006", "Nguyen Van F", "G6"}}
      );

      // Ngày seed
      LocalDate d = LocalDate.of(2025, 10, 7);
      // Kỳ thu dạng YYYY-MM (vd: 2025-10)
      String billingPeriod = d.getYear() + "-" + String.format("%02d", d.getMonthValue());

      for (var e : seeds.entrySet()) {
        Room room = roomRepo.findByCode(e.getKey()).orElseThrow();

        for (Object[] s : e.getValue()) {
          String code = (String) s[0];

          if (!studentRepo.existsByCode(code)) {
            Student st = Student.builder()
                .code(code)
                .name((String) s[1])
                .clazz("D23CQVT01-N")
                .major("CNTT")
                .gender("Nam")
                .dob(DateUtil.parseDMY("01/01/2005"))
                .status("Chưa đóng tiền")
                .expiry(DateUtil.parseDMY("31/12/2026"))
                .bed((String) s[2])
                .room(room)
                .build();
            studentRepo.save(st);

            Bill bill = Bill.builder()
                .billNo("HĐ-" + code)
                .student(st)
                .amount(new BigDecimal("1900000"))
                .createdDate(d)
                .paymentDate(d)
                .note("Học phí")
                .paidStatus("UNPAID")
                .billingPeriod(billingPeriod) // ✅ FIX: không để NULL nữa
                .build();

            billRepo.save(bill);
          }
        }
      }
    };
  }
}