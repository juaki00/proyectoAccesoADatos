package com.example.company;

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
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long company_id;
    private String company_name;
    private String phone_number;
    private String email;
    private String company_contact;
    private String incidents_observations;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Student> student;

    @Override
    public String toString() {
        return "Company{" +
                "company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", company_contact='" + company_contact + '\'' +
                ", incidents_observations='" + incidents_observations + '\'' +
                ", student=" + student +
                '}';
    }
}
