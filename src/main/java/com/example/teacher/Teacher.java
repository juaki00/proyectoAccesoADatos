package com.example.teacher;

import com.example.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacher_id;

    private String first_name;
    private String last_name;
    private String access_password;
    private String email_address;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private List<Student> students;
}
