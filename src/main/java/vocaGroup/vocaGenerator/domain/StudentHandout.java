package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;

@Entity
@Getter
public class StudentHandout {
    @Id@GeneratedValue
    @Column(name = "STUDENT_HANDOUT_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "STUDENT_ID")
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "HANDOUT_ID")
    private Handout handout;
    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;
    public StudentHandout() {
    }

    public StudentHandout(Student student, Handout handout, User user) {
        this.student=student;
        this.handout = handout;
        this.user = user;
    }

    public void setStudent(Student student) {
        this.student=student;
    }
}
