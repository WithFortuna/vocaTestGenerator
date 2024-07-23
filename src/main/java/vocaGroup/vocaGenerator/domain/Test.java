package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.*;

@Entity
@Getter
public class Test {
    @Id@GeneratedValue
    @Column(name = "TEST_ID" )
    private Long id;

    private String week;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "HANDOUT_ID")
    private Handout handout;
    @ElementCollection @CollectionTable(name = "TEST_VOCABS", joinColumns = @JoinColumn(name = "TEST_ID"))
    private List<VocaBlock> testVocabs = new ArrayList<>(); //collection 타입 변수.( 이때 VocaBlock 클래스는 @Embeddable 이 필요함)

    public Test() {
    }

    public Test(Handout handout) {
        this.handout=handout;
    }
    public Test(String week, Handout handout) {
        this.week = week;
        this.handout = handout;
    }

    /*비즈니스 로직*/

    //단어 문제 만들기-1: Choose 설정하기 =========>TestService를 통해서 구현함.
    public void createVocaList1(){
        for (VocaHandout v : handout.getVocabs()) {
            Voca voca = v.getVoca();
            Scanner scanner = new Scanner(System.in);//단어를 (한국어,영어) 중 어떤걸 blank로 할지 결정하는 걸 view를 통해서 구현할거임. but 일단은 사용자에게 입력을 받아보자.
            System.out.println("현재 단어는: ");
            voca.toString();
            System.out.println("korean 또는 english 중 하나를 입력하시오");
            String str = scanner.nextLine();
            if (str == "korean") {
                voca.setChoose(Choose.KOREAN);
            }
            else {
                voca.setChoose(Choose.ENGLISH);
            }

        }
    }

    //단어 문제 만들기-2: Choose를 바탕으로 시험 단어 출제하기
    public void createVocaList2() {
        for (VocaHandout v : handout.getVocabs()) {
            Voca voca = v.getVoca();
            VocaBlock vocaBlock = new VocaBlock(voca.getEnglish(),voca.getKorean(),voca.getChoose());
            testVocabs.add(vocaBlock);
        }}

}
