package vocaGroup.vocaGenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.Handout;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.StudentHandout;
import vocaGroup.vocaGenerator.domain.Team;
import vocaGroup.vocaGenerator.repository.HandoutRepository;
import vocaGroup.vocaGenerator.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //repository 주입
public class HandoutService {
    private final HandoutRepository handoutRepository;
    private final StudentRepository studentRepository;
    //등록 삭제 조회
    //전제: Hanodut객체는 생성완료.

    //등록
    @Transactional
    public void join(Handout handout) {
        handoutRepository.save(handout);
    }

    //삭제
    @Transactional
    public void delete(Long id) {
        handoutRepository.delete(id);
    }

    //조회
    public Handout findById(Long id) {
        return handoutRepository.findById(id);
    }
    public List<Handout> findAll() {
        return handoutRepository.findAll();
    }

    public List<Handout> findHandoutByTeam(Long id) {
        List<Handout> handoutByTeam = handoutRepository.findStudentHandoutByTeam(id);//각 팀별로, Handout기준으로 중복없이 Handout을 가져옴
        return handoutByTeam;
    }

    //배포
    @Transactional
    public void passHandout(Long handoutId, Student findStudent) {
        Handout findHandout = handoutRepository.findById(handoutId);

        StudentHandout studentHandout = new StudentHandout(findStudent,findHandout);
        handoutRepository.saveStudentHandout(studentHandout);
    }

}

/*기능
*handout을 생성한다.
* 학생에게 배부한다.
* */
