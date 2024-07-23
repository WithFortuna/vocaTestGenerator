package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import vocaGroup.vocaGenerator.valueType.VocaHandoutId;

@Entity
@Getter
public class VocaHandout {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "handoutId", column = @Column(name = "HANDOUT_ID"))
            ,
            @AttributeOverride(name = "vocaId", column = @Column(name = "VOCA_ID"))
    })
    private VocaHandoutId id; //복합식별자 + FK를 PK로(식별자관계) -> 이때 하이버네이트가 Id를 자동생성하지 않으므로 내가 넣어줘야한다.

//    private int size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HANDOUT_ID", insertable = false, updatable = false)
    private Handout handout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOCA_ID", insertable = false, updatable = false)
    private Voca voca;

    public VocaHandout() {
    }

    public VocaHandout(Voca voca, Handout handout) { //두 엔티티의 FK를 모두 PK로 하므로 생성자는 두 값을 항상 가져야한다.
        this.id = new VocaHandoutId(handout.getId(),voca.getId());
        this.handout=handout;
        this.voca = voca;
    }

    public void showVoca() {
        System.out.println("영어: "+voca.getEnglish() + "\n한국어: "+voca.getKorean());
    }



}
