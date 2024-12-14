package io.john.amiscaray.test.models;

import io.john.amiscaray.quak.generator.api.EntityGenerator;
import io.john.amiscaray.quak.generator.api.RestModel;
import io.john.amiscaray.test.orm.StudentTableEntry;
import lombok.*;

@RestModel(dataClass = StudentTableEntry.class)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class StudentDTO {

    private String name;
    private String major;
    private Float gpa;

    @EntityGenerator
    public static StudentTableEntry createDataEntry(StudentDTO dto) {
        return new StudentTableEntry(null, dto.major, dto.name, dto.gpa);
    }

}
