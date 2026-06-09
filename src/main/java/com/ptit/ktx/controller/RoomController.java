package com.ptit.ktx.controller;

import com.ptit.ktx.dto.request.UpsertStudentRequest;
import com.ptit.ktx.dto.response.RoomOverviewResponse;
import com.ptit.ktx.dto.response.StudentResponse;
import com.ptit.ktx.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping
  public List<RoomOverviewResponse> overview() {
    return roomService.overview();
  }

  @GetMapping("/{roomCode}/members")
  public List<StudentResponse> members(@PathVariable String roomCode) {
    return roomService.members(roomCode);
  }

  @PostMapping("/{roomCode}/members")
  public void addMember(@PathVariable String roomCode, @Valid @RequestBody UpsertStudentRequest req) {
    roomService.addMember(roomCode, req);
  }

  @DeleteMapping("/{roomCode}/members/{studentCode}")
  public void removeMember(@PathVariable String roomCode, @PathVariable String studentCode) {
    roomService.removeMember(roomCode, studentCode);
  }
}
