package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Getter;

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

    public Voca() {
    }

    public Voca(String english, String korean) {
        this.english = english;
        this.korean = korean;
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

