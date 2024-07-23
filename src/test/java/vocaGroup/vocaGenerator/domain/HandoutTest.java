package vocaGroup.vocaGenerator.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.repository.HandoutRepository;
import vocaGroup.vocaGenerator.repository.StudentRepository;
import vocaGroup.vocaGenerator.repository.TestRepository;
import vocaGroup.vocaGenerator.repository.VocaRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional

class HandoutTest {



    Student student1 = new Student("olaf",20,Team.A);
    Student student2 = new Student("gno",21,Team.A);
    Student student3 = new Student("nunu", 5, Team.B);
    Voca voca1 = new Voca("King","왕");
    Voca voca2 = new Voca("Bottom","아래");
//    Voca voca3 = new Voca("Top","위");
//    VocaHandout vocaHandout1 = new VocaHandout(voca1);
//    VocaHandout vocaHandout2 = new VocaHandout(voca2);
//    VocaHandout vocaHandout3 = new VocaHandout(voca3);

    List<VocaHandout> vocaHandouts = new ArrayList<>();
    Handout handout = new Handout("1주차");
    vocaGroup.vocaGenerator.domain.Test test = new vocaGroup.vocaGenerator.domain.Test("1주차", handout);
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    HandoutRepository handoutRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    VocaRepository vocaRepository;

    @Test
    void showVocabs_addVoca_addVocaList() {
        vocaHandouts.add(vocaHandout1);
        vocaHandouts.add(vocaHandout2);
        vocaHandouts.add(vocaHandout3);
        System.out.println("=============================================================");
        //when
        handout.addVocaList(vocaHandouts);
        //then
        handout.showVocabs();
        System.out.println("===============================================");

    }


}