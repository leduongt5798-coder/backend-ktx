package com.ptit.ktx.service;

import com.ptit.ktx.dto.request.UpsertStudentRequest;
import com.ptit.ktx.dto.response.RoomOverviewResponse;
import com.ptit.ktx.dto.response.StudentResponse;

import java.util.List;

public interface RoomService {
  List<RoomOverviewResponse> overview();
  List<StudentResponse> members(String roomCode);
  void addMember(String roomCode, UpsertStudentRequest req);
  void removeMember(String roomCode, String studentCode);
}
