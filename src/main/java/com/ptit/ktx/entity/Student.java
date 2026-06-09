package com.ptit.ktx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // code: thường không cần Unicode
    @Column(unique = true, nullable = false, length = 50)
    private String code;

    // ✅ có tiếng Việt -> NVARCHAR
    @Nationalized
    @Column(nullable = false, length = 255)
    private String name;

    @Nationalized
    @Column(name = "clazz", length = 100)
    private String clazz;

    @Nationalized
    @Column(length = 150)
    private String major;

    @Nationalized
    @Column(length = 20)
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;

    @Nationalized
    @Column(length = 150)
    private String birthplace;

    @Nationalized
    @Column(length = 100)
    private String nationality;

    @Nationalized
    @Column(length = 100)
    private String ethnicity;

    @Nationalized
    @Column(length = 100)
    private String religion;

    // phone/email/cccd thường không cần Unicode
    @Column(length = 30)
    private String phone;

    @Column(length = 150)
    private String email;

    @Nationalized
    @Column(length = 300)
    private String address;

    @Column(length = 30)
    private String cccd;

    @Nationalized
    @Column(length = 50)
    private String status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expiry;

    // bed kiểu G1/G2... không cần Unicode
    @Column(length = 20)
    private String bed;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}