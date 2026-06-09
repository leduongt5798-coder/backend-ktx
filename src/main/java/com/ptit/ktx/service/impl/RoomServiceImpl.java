package com.ptit.ktx.service.impl;

import com.ptit.ktx.dto.request.UpsertStudentRequest;
import com.ptit.ktx.dto.response.RoomOverviewResponse;
import com.ptit.ktx.dto.response.StudentResponse;
import com.ptit.ktx.entity.Student;
import com.ptit.ktx.entity.Room;
import com.ptit.ktx.repository.BillRepository;
import com.ptit.ktx.repository.RoomRepository;
import com.ptit.ktx.repository.StudentRepository;
import com.ptit.ktx.service.RoomService;
import com.ptit.ktx.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepo;
  private final StudentRepository studentRepo;
  private final BillRepository billRepo;

  public RoomServiceImpl(RoomRepository roomRepo, StudentRepository studentRepo, BillRepository billRepo) {
    this.roomRepo = roomRepo;
    this.studentRepo = studentRepo;
    this.billRepo = billRepo;
  }

  @Override
  @Transactional(readOnly = true)
  public List<RoomOverviewResponse> overview() {
    String[] rooms = {"A1","A2","B1","B2"};

    return java.util.Arrays.stream(rooms)
        .map(code -> {
          int count = studentRepo.findByRoom_CodeOrderByBedAsc(code).size();
          String block = code.startsWith("A") ? "Khu A" : "Khu B";
          return new RoomOverviewResponse(code, block, count);
        })
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<StudentResponse> members(String roomCode) {
    ensureRoomExists(roomCode);

    return studentRepo.findByRoom_CodeOrderByBedAsc(roomCode)
        .stream()
        .map(s -> new StudentResponse(
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
            resolveStudentStatus(s),                 
            DateUtil.formatDMY(s.getExpiry()),
            roomCode,
            s.getBed()                              
        ))
        .toList();
  }

  
  private String resolveStudentStatus(Student s) {
    return billRepo.findTopByStudent_CodeOrderByCreatedDateDesc(s.getCode())
        .map(b -> "PAID".equalsIgnoreCase(b.getPaidStatus()) ? "Đã đóng tiền" : "Chưa đóng tiền")
        .orElse(s.getStatus());
  }

  @Override
  @Transactional
  public void addMember(String roomCode, UpsertStudentRequest req) {
    Room room = roomRepo.findByCode(roomCode)
        .orElseThrow(() -> new IllegalArgumentException("Phòng không tồn tại: " + roomCode));

    if (studentRepo.existsByCode(req.code())) {
      throw new IllegalArgumentException("Mã SV đã tồn tại: " + req.code());
    }

    Student s = Student.builder()
        .code(req.code())
        .name(req.name())
        .clazz(req.clazz())
        .major(req.major())
        .gender(req.gender())
        .dob(DateUtil.parseDMY(req.dob()))
        .birthplace(req.birthplace())
        .nationality(req.nationality())
        .ethnicity(req.ethnicity())
        .religion(req.religion())
        .phone(req.phone())
        .email(req.email())
        .address(req.address())
        .cccd(req.cccd())
        .status(req.status())
        .expiry(DateUtil.parseDMY(req.expiry()))
        .bed(req.bed())
        .room(room)
        .build();

    studentRepo.save(s);
  }

  @Override
  @Transactional
  public void removeMember(String roomCode, String studentCode) {
    Student s = studentRepo.findByCode(studentCode)
        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên: " + studentCode));

    if (s.getRoom() == null || !roomCode.equalsIgnoreCase(s.getRoom().getCode())) {
      throw new IllegalArgumentException("Sinh viên không thuộc phòng " + roomCode);
    }

    
    studentRepo.delete(s);
  }

  private void ensureRoomExists(String roomCode) {
    if (!roomRepo.existsByCode(roomCode)) {
      throw new IllegalArgumentException("Phòng không tồn tại: " + roomCode);
    }
  }
}