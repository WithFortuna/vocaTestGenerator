package vocaGroup.vocaGenerator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.Handout;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.StudentHandout;
import vocaGroup.vocaGenerator.domain.Team;

import java.util.List;

@Repository
public class HandoutRepository {
    @PersistenceContext
    EntityManager em;


    //등록 삭제 조회
    public void save(Handout handout) {
        em.persist(handout);
    }

    public void delete(Long id) {
        Handout findHandout = em.find(Handout.class, id);
        em.remove(findHandout);
    }

    public Handout findById(Long id) {
        return em.find(Handout.class, id);
    }

    public List<Handout> findAll(Long userId) {
        List<Handout> handouts = em.createQuery("select h from Handout h where h.user.id = :userId", Handout.class)
                .setParameter("userId", userId)
                .getResultList();

        return handouts;
    }

    public void saveStudentHandout(StudentHandout studentHandout) {
        em.persist(studentHandout);
    }

    public List<Handout> findStudentHandoutByTeam(Long id) {
        List<Handout> handouts = em.createQuery("select distinct h from Handout h inner join StudentHandout s on h.id = s.handout.id " +
                                "where s.student.team.id = :teamId " +
                                "group by h.id",
                        Handout.class)
                .setParameter("teamId", id)
                .getResultList();

        System.out.println("=========================handout조회결과");
        for (Handout h : handouts) {
            System.out.println(h.getId());
        }
        System.out.println("=========================handout조회결과");
        return handouts;

    }
}
