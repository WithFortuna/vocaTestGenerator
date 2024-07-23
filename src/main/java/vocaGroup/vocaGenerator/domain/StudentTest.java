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

    public StudentTest() {
    }

    public StudentTest(Test test) {
        this.test = test;
    }

    public void setStudent(Student student) {
        this.student=student;
    }
}
