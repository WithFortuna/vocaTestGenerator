package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class StudentTest {
    @Id@GeneratedValue
    @Column(name = "STUDENT_TEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "STUDENT_ID")
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "TEST_ID")
    private Test test;
    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;
    public StudentTest() {
    }

    public StudentTest(Test test, User user) {
        this.test = test;
        this.user = user;
    }

    public void setStudent(Student student) {
        this.student=student;
    }
}
