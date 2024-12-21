package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Handout {
    @Id@GeneratedValue
    @Column(name = "HANDOUT_ID")
    private Long id;
    private String week;

    @OneToMany(mappedBy = "handout")
    private List<VocaHandout> vocabs = new ArrayList<>(); //단어 추가는 메서드로.
    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;

    public Handout() {
    }

    public Handout(String week, User user) {
        this.week = week;
        this.user=user;

    }


    /*비즈니스 로직*/

    //show voca List (현 단어 리스트 보여주기)
    public void showVocabs() {
        for (VocaHandout v : vocabs) {
            v.showVoca();
        }
    }

    //add voca(단어 하나)
    public void addVoca(VocaHandout vocaHandout) {
        vocabs.add(vocaHandout);
    }
    //add voca list(단어 여러개)
    public void addVocaList(List<VocaHandout> vocabs) {
        for (VocaHandout v : vocabs) {
            addVoca(v);
        }
    }




}
