package vocaGroup.vocaGenerator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.Team;

import java.util.List;

@Repository
public class StudentRepository {
    @PersistenceContext
    private EntityManager em;

    //등록 삭제 조회
    public void save(Student student) {
        em.persist(student);
    }

    public void delete(Long studentId) {
        Student student = em.find(Student.class, studentId);
        em.remove(student);
    }

    public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    public List<Student> findAll() {
        List<Student> findStudents = em.createQuery("select s from Student s ", Student.class)
                .getResultList();
        return findStudents;
    }

    public List<Student> findByTeam(Long teamId) {

        List<Student> studentList = em.createQuery("select s from Student s where s.team.id = :teamId", Student.class)
                .setParameter("teamId",teamId)
                .getResultList();
        return studentList;
    }
}
