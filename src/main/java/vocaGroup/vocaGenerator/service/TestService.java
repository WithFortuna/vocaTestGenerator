package vocaGroup.vocaGenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.*;
import vocaGroup.vocaGenerator.domain.DTO.TestForm;
import vocaGroup.vocaGenerator.login.utility.SecurityUtil;
import vocaGroup.vocaGenerator.repository.StudentRepository;
import vocaGroup.vocaGenerator.repository.TestRepository;
import vocaGroup.vocaGenerator.repository.VocaRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final StudentRepository studentRepository;
    private final VocaRepository vocaRepository;


    //Test의 시험지 만들기 by system
    @Transactional
    public Test createVocaTestBySystem(Long id) {
        Test findTest = testRepository.findById(id);
        findTest.createVocaList1();
        findTest.createVocaList2();
        return findTest;
    }
    //Test 시험지 만들기 by view
    @Transactional
    public void createVocaTest(List<TestForm> testForms, Long testId) {
        Test findTest = testRepository.findById(testId);
        List<VocaBlock> vocaBlocks = findTest.getTestVocabs();
        for (TestForm t : testForms) {
            Voca findVoca = vocaRepository.findById(t.getVocaId());
            if (t.getType().equals("english")) { //english => 'english를 숨겨라' 라는 의미
                VocaBlock vocaBlock = new VocaBlock();
                vocaBlock.setKorean(findVoca.getKorean());
                vocaBlocks.add(vocaBlock);
            } else if (t.getType().equals("korean")) {
                VocaBlock vocaBlock = new VocaBlock();
                vocaBlock.setEnglish(findVoca.getEnglish());
                vocaBlocks.add(vocaBlock);

            }
        }
    }


    @Transactional
    public void save(Test test) {
        testRepository.save(test);
    }

    //조회
    public Test findById(Long id) {
        return testRepository.findById(id);
    }

    public List<Test> findAll(Long userId) {
        return testRepository.findAll(userId);
    }

    //배포
    @Transactional
    public void passTest(Long testId, Student student) {
        Test findTest = testRepository.findById(testId);
        User currentUser = SecurityUtil.getCurrentUser();
        StudentTest studentTest = new StudentTest(findTest, currentUser);
        studentTest.setStudent(student);
        testRepository.saveStudentTest(studentTest);
    }

    public List<Test> findTestByTeam(Long id) {
        List<Test> tests = testRepository.findTestByTeam(id);
        return tests;
    }
}

/*기능
* handout의 모든 단어는 Test에 출제되며 한글/영어 중 하나의 뜻이 가려져서 나온다.
* */
