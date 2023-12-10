package com.example.teacher;

import com.example.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase que representa a un profesor.
 */
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

    @OneToMany(mappedBy = "tutor", fetch = FetchType.EAGER)
    private List<Student> students;

    /**
     * Devuelve una representación en forma de String de la instancia de Teacher.
     *
     * @return Cadena que representa la instancia de Teacher.
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "teacher_id=" + teacher_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", access_password='" + access_password + '\'' +
                ", email_address='" + email_address + '\'' +
                ", students=" + students.size() +
                '}';
    }
}