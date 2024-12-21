package vocaGroup.vocaGenerator.login;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.User;

import java.util.Optional;

@Transactional
@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public User findByUsername(String username) {
        Optional<User> optionalUser = em.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
                .getResultStream().findFirst();
        return optionalUser.orElse(null);
    }

    public void save(User user) {
        em.persist(user);
    }
}
