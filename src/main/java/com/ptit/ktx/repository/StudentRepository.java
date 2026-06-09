package com.ptit.ktx.repository;

import com.ptit.ktx.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
  Optional<Student> findByCode(String code);
  boolean existsByCode(String code);
  List<Student> findByRoom_CodeOrderByBedAsc(String roomCode);
}
