package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import vocaGroup.vocaGenerator.domain.enumType.Choose;

@Entity
@Getter
public class Voca {
    @Id
    @GeneratedValue
    @Column(name = "VOCA_ID")
    private Long id;
    private String english;
    private String korean;
    @Enumerated(EnumType.STRING)
    private Choose choose;
    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;

    public Voca() {
    }

    public Voca(String english, String korean, User user) {
        this.english = english;
        this.korean = korean;
        this.user = user;
    }

    public void setChoose(Choose choose) {
        this.choose=choose;
    }

    @Override
    public String toString() {
        return "Voca{" +
                "english='" + english + '\'' +
                ", korean='" + korean + '\'' +
                '}';
    }
}

