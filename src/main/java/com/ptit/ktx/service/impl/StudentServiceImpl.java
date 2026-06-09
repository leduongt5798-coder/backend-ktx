package com.ptit.ktx.service.impl;

import com.ptit.ktx.dto.request.UpsertStudentRequest;
import com.ptit.ktx.dto.response.StudentResponse;
import com.ptit.ktx.entity.Room;
import com.ptit.ktx.entity.Student;
import com.ptit.ktx.repository.RoomRepository;
import com.ptit.ktx.repository.StudentRepository;
import com.ptit.ktx.service.StudentService;
import com.ptit.ktx.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepo;
  private final RoomRepository roomRepo;

  public StudentServiceImpl(StudentRepository studentRepo, RoomRepository roomRepo) {
    this.studentRepo = studentRepo;
    this.roomRepo = roomRepo;
  }

  @Override
  @Transactional(readOnly = true)
  public StudentResponse getByCode(String code) {
    Student s = studentRepo.findByCode(code)
        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên: " + code));
    return map(s);
  }

  @Override
  @Transactional
  public StudentResponse update(String code, UpsertStudentRequest req) {
    if (req == null) throw new IllegalArgumentException("Body rỗng");

    Student s = studentRepo.findByCode(code)
        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên: " + code));

    // ===== Update các field cơ bản =====
    // (Giữ nguyên logic của bạn, chỉ thêm chặn null/blank cho date + bed)
    if (req.name() != null) s.setName(req.name());
    s.setClazz(req.clazz());
    s.setMajor(req.major());
    s.setGender(req.gender());

    // ✅ dob: chỉ parse khi có dữ liệu (tránh parse null/blank)
    if (req.dob() != null && !req.dob().isBlank()) {
      s.setDob(DateUtil.parseDMY(req.dob()));
    }

    s.setBirthplace(req.birthplace());
    s.setNationality(req.nationality());
    s.setEthnicity(req.ethnicity());
    s.setReligion(req.religion());
    s.setPhone(req.phone());
    s.setEmail(req.email());
    s.setAddress(req.address());
    s.setCccd(req.cccd());
    s.setStatus(req.status());

    // ✅ expiry: chỉ parse khi có dữ liệu (tránh parse null/blank)
    if (req.expiry() != null && !req.expiry().isBlank()) {
      s.setExpiry(DateUtil.parseDMY(req.expiry()));
    }

    // ✅ bed: chỉ update khi FE có gửi (tránh làm mất giường thành null)
    if (req.bed() != null && !req.bed().isBlank()) {
      s.setBed(req.bed());
    }

    // ✅ room: chỉ update khi có gửi
    if (req.room() != null && !req.room().isBlank()) {
      Room room = roomRepo.findByCode(req.room())
          .orElseThrow(() -> new IllegalArgumentException("Phòng không tồn tại: " + req.room()));
      s.setRoom(room);
    }

    studentRepo.save(s);
    return map(s);
  }

  private StudentResponse map(Student s) {
    return new StudentResponse(
        s.getCode(),
        s.getName(),
        s.getClazz(),
        s.getMajor(),
        s.getGender(),
        DateUtil.formatDMY(s.getDob()),
        s.getBirthplace(),
        s.getNationality(),
        s.getEthnicity(),
        s.getReligion(),
        s.getPhone(),
        s.getEmail(),
        s.getAddress(),
        s.getCccd(),
        s.getStatus(),
        DateUtil.formatDMY(s.getExpiry()),
        s.getRoom() == null ? null : s.getRoom().getCode(),
        s.getBed()
    );
  }
}