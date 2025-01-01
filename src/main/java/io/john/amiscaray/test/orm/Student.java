package io.john.amiscaray.test.orm;

import io.john.amiscaray.quak.generator.api.ModelGenerator;
import io.john.amiscaray.test.models.StudentDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String major;
    private String name;
    private Float gpa;

    public Student(String major, String name, Float gpa) {
        this.major = major;
        this.name = name;
        this.gpa = gpa;
    }

    @ModelGenerator
    public static StudentDTO toStudentDTO(Student student) {
        return new StudentDTO(student.getMajor(), student.getName(), student.getGpa());
    }

}
