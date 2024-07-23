package vocaGroup.vocaGenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.Team;
import vocaGroup.vocaGenerator.repository.StudentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    // 등록 삭제 조회 수정

    @Transactional
    public void join(Student student) {
        studentRepository.save(student);
    }

    @Transactional
    public void quit(Long studentId) {
        studentRepository.delete(studentId);
    }

    public Student findById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }


    public List<Student> findByTeam(Team team) {
        List<Student> studentList = studentRepository.findByTeam(team);
        return studentList;
    }
}
