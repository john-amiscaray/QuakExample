package io.john.amiscaray.test.models;

import io.john.amiscaray.quak.generator.api.EntityGenerator;
import io.john.amiscaray.quak.generator.api.RestModel;
import io.john.amiscaray.test.orm.Student;
import lombok.*;

@RestModel(dataClass = Student.class)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class StudentDTO {

    private String name;
    private String major;
    private Float gpa;

    @EntityGenerator
    public static Student toEntity(StudentDTO dto) {
        return new Student(dto.major, dto.name, dto.gpa);
    }

}
