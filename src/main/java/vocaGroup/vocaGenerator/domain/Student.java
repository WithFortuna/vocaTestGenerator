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
    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;
    public Student() {
    }

    public Student(String name, int age, Team team, User user) {
        this.name = name;
        this.age = age;
        this.team = team;
        this.user=user;
    }
}
