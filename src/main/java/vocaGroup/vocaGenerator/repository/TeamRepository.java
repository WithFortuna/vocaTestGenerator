package vocaGroup.vocaGenerator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.Team;

import java.util.List;

@Transactional
@Repository
public class TeamRepository {
    @PersistenceContext
    private EntityManager em;

    //Create
    public void save(Team team) {
        em.persist(team);
    }

    //Research
    public Team findById(Long id) {
        return em.find(Team.class, id);
    }

    public List<Team> findAll() {
        List<Team> teams = em.createQuery("select t from Team t", Team.class)
                .getResultList();
        return teams;
    }

    //Update

    //Delete
    public void delete(Long id) {
        Team findTeam = em.find(Team.class, id);
        em.remove(findTeam);
    }
}
