package com.ptit.ktx.repository;

import com.ptit.ktx.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

  
  Optional<Bill> findTopByStudent_CodeOrderByCreatedDateDesc(String studentCode);

 
  List<Bill> findByStudent_CodeOrderByCreatedDateDesc(String studentCode);

 
  List<Bill> findByStudent_Code(String studentCode);
}