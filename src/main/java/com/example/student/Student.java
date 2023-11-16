package com.example.student;

import com.example.teacher.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long student_id;
    private String first_name;
    private String last_name;
    private String access_password;
    private String dni;
    private String date_of_birth;
    private String email;
    private String contact_phone;
    private int total_dual_hours;
    private int total_fct_hours;
    private String observations;

//    @ManyToOne
//    @JoinColumn(name = "diary_activity")
//    private DiaryActivity diary_activity;
//
//    @ManyToOne
//    @JoinColumn(name = "company")
//    private Company company;

    @ManyToOne
    @JoinColumn(name = "tutor")
    private Teacher tutor;


}
