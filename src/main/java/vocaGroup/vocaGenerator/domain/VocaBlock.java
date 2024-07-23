package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Embeddable //embedded 타입은 불변객체가 되도록.
public class VocaBlock {
    @Column(name = "ENGLISH")
    private String english;
    @Column(name = "KOREAN")
    private String korean;

    public VocaBlock() {

    }

    public VocaBlock(String english, String korean, Choose choose) {
        if (choose == Choose.ENGLISH) {
            this.korean=korean;
        } else if (choose == Choose.KOREAN) {
            this.english=english;
        }

    }

}
