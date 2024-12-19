package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Student {
    @Id@GeneratedValue
    @Column(name = "STUDENT_ID")
    private Long id;

    private String name;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "TEAM_ID")
    private Team team;
    public Student() {
    }

    public Student(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }
}