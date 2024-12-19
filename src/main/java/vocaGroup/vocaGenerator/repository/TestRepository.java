package vocaGroup.vocaGenerator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.*;

import java.util.List;

@Repository
public class TestRepository {
    @PersistenceContext
    EntityManager em;


    //TestRepository에서 콜렉션타입 객체도 DB에 넣어주어야 한다.
    public void saveVocaBlock(VocaBlock vocaBlock) {
        em.persist(vocaBlock);
    }

    //등록 삭제 조회
    public void save(Test test) {
        em.persist(test);
    }

    public void delete(Test test) {
        em.remove(test);
    }

    public Test findById(Long id) {
        return em.find(Test.class, id);
    }

    public List<Test> findAll() {
        List<Test> findTests = em.createQuery("select t from Test t ", Test.class)
                .getResultList();
        return findTests;
    }


    public void saveStudentTest(StudentTest studentTest) {
        em.persist(studentTest);
    }

    public List<Test> findTestByTeam(Long id) {  //찾는 대상: StudentTest에 있으면서 & StudentTest.student.Team = :team 이고& 중복을 없애고 & Team_Id&로 묶은 Test 객체.
        List<Test> tests = em.createQuery("select distinct t from Test t inner join StudentTest s on t.id = s.test.id " +
                        "where s.student.team.id = :teamId " +
                        "group by t.id", Test.class)
                .setParameter("teamId", id)
                .getResultList();

        return tests;

    }
}
