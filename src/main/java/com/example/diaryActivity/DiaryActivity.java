package com.example.diaryActivity;

import com.example.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Clase que representa una actividad diaria en un diario de actividades.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "diary_activities")
public class DiaryActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activity_id;
    private LocalDate activity_date;
    @Enumerated(EnumType.STRING)
    private PracticeType practice_type;
    private Integer total_hours;
    private String activity_description;
    private String observations_incidents;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;

    /**
     * Método toString que representa en forma de Strings a la entidad.
     *
     * @return Representación de cadena de la entidad DiaryActivity.
     */
    @Override
    public String toString() {
        return "DiaryActivity{" +
                "activity_id: " + activity_id +
                ", activity_date: '" + activity_date + '\'' +
                ", practice_type: " + practice_type +
                ", total_hours: " + total_hours +
                ", activity_description: '" + activity_description + '\'' +
                ", observations_incidents: '" + observations_incidents + '\'' +
                ", student: " + student +
                '}';
    }
}