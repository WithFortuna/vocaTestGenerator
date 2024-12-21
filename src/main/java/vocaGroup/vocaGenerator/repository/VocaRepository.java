package vocaGroup.vocaGenerator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.Voca;
import vocaGroup.vocaGenerator.domain.VocaHandout;

import java.util.List;


@Repository
public class VocaRepository {

    @PersistenceContext
    private EntityManager em;

    //등록 삭제 조회
    public void save(Voca voca) {
        em.persist(voca);
    }

    public void delete(Long id) {
        Voca voca = em.find(Voca.class, id);
        em.remove(voca);
    }

    public Voca findById(Long id) {
        return em.find(Voca.class, id);
    }

    public List<Voca> findAll(Long userId) {
        List<Voca> findVocas = em.createQuery("select v from Voca v where v.user.id = :userId", Voca.class)
                .setParameter("userId",userId)
                .getResultList();
        return findVocas;
    }

    public void saveVocaHandout(VocaHandout vocaHandout) {
        em.persist(vocaHandout);
    }
}
