package com.example.diaryActivity;

import com.example.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "diary_activities")
public class DiaryActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activity_id;

    private String activity_date;
    @Enumerated(EnumType.STRING)
    private PracticeType practice_type;
    private Integer total_hours;
    private String activity_description;
    private String observations_incidents;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;
}
