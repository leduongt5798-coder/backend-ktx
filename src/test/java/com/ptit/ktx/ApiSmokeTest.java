package com.ptit.ktx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApiSmokeTest {

  @Autowired MockMvc mvc;

  @Test
  void login_ok() throws Exception {
    mvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"admin\",\"password\":\"123456\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token", startsWith("demo-token-")))
        .andExpect(jsonPath("$.username", is("admin")));
  }

  @Test
  void rooms_overview_ok() throws Exception {
    mvc.perform(get("/api/rooms"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].room", is("A1")));
  }

  @Test
  void room_members_ok() throws Exception {
    mvc.perform(get("/api/rooms/A1/members"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].code", notNullValue()));
  }

  @Test
  void add_and_remove_member_ok() throws Exception {
    String body = "{\"code\":\"N23TEST999\",\"name\":\"Test Student\",\"clazz\":\"D23CQVT01-N\",\"dob\":\"02/02/2005\",\"bed\":\"G9\"}";

    mvc.perform(post("/api/rooms/A1/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk());

    mvc.perform(get("/api/students/N23TEST999"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.room", is("A1")));

    mvc.perform(delete("/api/rooms/A1/members/N23TEST999"))
        .andExpect(status().isOk());
  }

  @Test
  void bills_by_student_ok() throws Exception {
    mvc.perform(get("/api/bills/by-student/N23DCVT001"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].billNo", is("HĐ-N23DCVT001")));
  }
}