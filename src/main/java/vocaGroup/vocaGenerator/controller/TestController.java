package vocaGroup.vocaGenerator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vocaGroup.vocaGenerator.domain.*;
import vocaGroup.vocaGenerator.domain.DTO.TestForm;
import vocaGroup.vocaGenerator.login.utility.SecurityUtil;
import vocaGroup.vocaGenerator.repository.TeamRepository;
import vocaGroup.vocaGenerator.service.HandoutService;
import vocaGroup.vocaGenerator.service.StudentService;
import vocaGroup.vocaGenerator.service.TestService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final HandoutService handoutService;
    private final TestService testService;
    private final StudentService studentService;
    private final ObjectMapper objectMapper;
    private final TeamRepository teamRepository;

    //==============================================================test생성
    @GetMapping("tests/new")
    public String createTest1(Model model) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        List<Handout> handouts = handoutService.findAll(userId);
        model.addAttribute("handouts", handouts);
        return "/handouts/handoutList2";
    }

    @PostMapping("tests/new") //input: 선택된 HandoutID, output:해당 handout의 vocaHandout들
    public String createTestForm(@RequestParam Long selectedHandoutId, Model model) {
        Handout findHandout = handoutService.findById(selectedHandoutId);
        List<VocaHandout> vocabs = findHandout.getVocabs();
        model.addAttribute("handout", findHandout);
        model.addAttribute("vocabs", vocabs);

        return "/tests/createTestForm";
    }

    @PostMapping("tests/new/create") //input:선택된 (단어,출제유형) 들..
    public String create(@RequestParam String week,
                         @RequestParam Long handoutId, @RequestParam String testSelections, Model model) {
        if (testSelections == null || testSelections.isEmpty() || "[]".equals(testSelections)) {
            model.addAttribute("error", "적어도 하나의 단어를 선택해주세요.");
            return "/tests/createTestForm";
        } else {
            System.out.println("==========================================================");
            System.out.println("Received testSelections: " + testSelections); //json 배열 출력
            System.out.println("==========================================================");
        }
        User currentUser = SecurityUtil.getCurrentUser();
        Handout findHandout = handoutService.findById(handoutId);
        Test test = new Test(week, findHandout, currentUser);
        testService.save(test);
        try {
            List<TestForm> testForms = objectMapper.readValue(testSelections, new TypeReference<List<TestForm>>() {
            }); //문자열 파싱: testSelections 변수 -> TestForm DTO로 변환
            if (testForms.isEmpty()) {
                model.addAttribute("error", "적어도 하나의 단어를 선택해주세요.");
                return "/tests/createTestForm";
            }
            // testForms 리스트를 사용하여 테스트를 생성
            testService.createVocaTest(testForms, test.getId());

            return "redirect:/"; // 홈 페이지로 리다이렉트
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to process test selections.");
            model.addAttribute("handout", findHandout);
            model.addAttribute("vocabs", findHandout.getVocabs());

            return "/tests/createTestForm"; // 에러 발생 시 폼 페이지로 돌아감
        }
    }

    //==================================================================test조회
    @GetMapping("/tests")
    public String showList(Model model) {
        Long userId = SecurityUtil.getCurrentUser().getId();

        List<Test> tests = testService.findAll(userId);
        model.addAttribute("tests", tests);
        return "/tests/testList";
    }

    @GetMapping("/tests/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Test findTest = testService.findById(id);
        boolean isOwner = SecurityUtil.getCurrentUser().getId().equals(findTest.getUser().getId());

        if (!isOwner) {

            model.addAttribute("errors", "권한 없음");
            return "/tests/testDetail";
        }

        List<VocaBlock> vocaBlocks = findTest.getTestVocabs();
        model.addAttribute("test", findTest);
        model.addAttribute("vocaBlocks", vocaBlocks);

        return "/tests/testDetail";
    }

    //======================================test 배포

    @GetMapping("tests/distribute")
    public String distributeTest(Model model) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        List<Test> tests = testService.findAll(userId);
        model.addAttribute("tests", tests);


        List<Team> teams = teamRepository.findAll(userId);
        model.addAttribute("teams", teams);

        return "/tests/distributeTest";
    }

    @PostMapping("tests/distribute")
    public String distribute(@RequestParam List<Long> testSelections, @RequestParam Long teamSelectionId, Model model) {
        //Test를 선택된 Team에게 배포
        System.out.println("=======================================================");
        List<Student> students = studentService.findByTeam(teamSelectionId);
        System.out.println("=======================================================");
        for (Long id : testSelections) {
            for (Student s : students) {
                testService.passTest(id, s);
            }

        }

        //Team별 Test조회
        Long userId = SecurityUtil.getCurrentUser().getId();
        List<Team> teams = teamRepository.findAll(userId);
        Map<String, Object> testsByTeam = new HashMap<>();
        for (Team team : teams) {
            //Team에 속한 학생이 없어서 test가 안보이는 경우
            boolean isTeamNoStudents = studentService.findByTeam(team.getId()).isEmpty();
            if (isTeamNoStudents) {
                testsByTeam.put(team.getTeamName(), "No Students");      //문자열 삽입
            } else {
                //Team에 속한 학생이 존재하는 경우
                List<Test> tests = testService.findTestByTeam(team.getId());
                testsByTeam.put(team.getTeamName(), tests);
            }

        }

        model.addAttribute("teams", teams);
        model.addAttribute("testsByTeam", testsByTeam);

        return "/tests/distributeResult";
    }
}
