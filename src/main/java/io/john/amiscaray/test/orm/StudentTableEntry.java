package io.john.amiscaray.test.orm;

import io.john.amiscaray.quak.data.query.DatabaseQuery;
import io.john.amiscaray.quak.data.query.NativeQuery;
import io.john.amiscaray.quak.generator.api.APINativeQuery;
import io.john.amiscaray.quak.generator.api.APIQuery;
import io.john.amiscaray.quak.generator.api.ModelGenerator;
import io.john.amiscaray.quak.http.request.DynamicPathRequest;
import io.john.amiscaray.test.models.StudentDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Map;

import static io.john.amiscaray.quak.data.query.QueryCriteria.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class StudentTableEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String major;
    private String name;
    private Float gpa;

    @ModelGenerator
    public static StudentDTO createDTO(StudentTableEntry studentDataEntry) {
        return new StudentDTO(studentDataEntry.name, studentDataEntry.major, studentDataEntry.gpa);
    }

    @APIQuery(path="/cs")
    public static DatabaseQuery queryStudentsInCS(DynamicPathRequest<Void> request) {
        return DatabaseQuery.builder()
                .withCriteria(valueOfField("major", is("cs")))
                .build();
    }

    @APIQuery(path="/gpa/{gpa}")
    public static DatabaseQuery queryStudentsWithGPAGreaterThan(DynamicPathRequest<Void> request) {
        return DatabaseQuery.builder()
                .withCriteria(valueOfField("gpa", isGreaterThan(Float.parseFloat(request.pathVariables().get("gpa")))))
                .build();
    }

    @APINativeQuery(path="/major/{name}")
    public static NativeQuery queryStudentsByMajor(DynamicPathRequest<Void> request) {
        return new NativeQuery(
                "FROM StudentTableEntry WHERE major = :name",
                Map.of("name", request.pathVariables().get("name"))
        );
    }


}
