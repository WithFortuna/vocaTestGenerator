package vocaGroup.vocaGenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vocaGroup.vocaGenerator.domain.*;
import vocaGroup.vocaGenerator.login.CustomUserDetails;
import vocaGroup.vocaGenerator.login.utility.SecurityUtil;
import vocaGroup.vocaGenerator.repository.TeamRepository;
import vocaGroup.vocaGenerator.service.HandoutService;
import vocaGroup.vocaGenerator.service.StudentService;
import vocaGroup.vocaGenerator.service.VocaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HandoutController {
    private final HandoutService handoutService;
    private final VocaService vocaService;
    private final StudentService studentService;
    private final TeamRepository teamRepository;

    //===================================================================handout만들기
    //사용자에게 객체 여러개를 선택받고 싶을 때(by id)
    //List형 데이터를(id) 유저에게서 입력받는다.
    @GetMapping("/handouts/new")
    public String createHandout(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();

        List<Voca> vocabs = vocaService.findAll(userId);
        model.addAttribute("vocabs", vocabs);
        return "handouts/createHandoutForm";
    }

    @PostMapping("/handouts/new")
    public String create(@RequestParam("selectedVocas") List<Long> selectedVocas,
                         @RequestParam("week") String week) { //selectedVocas: 유저가 선택한 VOCA_ID들, week:유저가 입력한 주차
        User user = SecurityUtil.getCurrentUser();
        Handout handout = new Handout(week, user);
        handoutService.join(handout);

        //1. VocaHandout 만들기
        List<VocaHandout> vocaHandoutList = vocaService.createVocaHandout(selectedVocas, handout);
        //2. Handout 만들기
        handout.addVocaList(vocaHandoutList);

        return "redirect:/";
    }

    //==========================================================================handout 보여주기
    @GetMapping("/handouts")
    public String showList(Model model) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        List<Handout> handouts = handoutService.findAll(userId);
        model.addAttribute("handouts", handouts);
        return "/handouts/handoutList";
    }

    @GetMapping("/handouts/{id}")
    public String showHandoutDetail(@PathVariable Long id,
                                    Model model) {
        Handout findHandout = handoutService.findById(id);
        boolean isOwner = SecurityUtil.getCurrentUser().getId().equals(findHandout.getUser().getId());

        if (!isOwner) {

            model.addAttribute("errors", "권한 없음");
            return "/handouts/handoutDetail";
        }
        List<VocaHandout> vocabs = findHandout.getVocabs();
        model.addAttribute("handout", findHandout);
        model.addAttribute("vocabs", vocabs);

        return "/handouts/handoutDetail";

    }
    //===============================================================handout배포하기

    @GetMapping("/handouts/distribute")
    public String distributeHandout(Model model) {
/* ver1
        List<Handout> handouts = handoutService.findAll();
        List<Team> teams = Arrays.asList(Team.values()); //enum타입의 값들을 List로.

        model.addAttribute("handouts", handouts);
        model.addAttribute("teams", teams); //enum값도 다른 객체와 전혀 다르지 않게 view와 상호작용한다.
*/
//ver2
        Long userId = SecurityUtil.getCurrentUser().getId();

        List<Handout> handouts = handoutService.findAll(userId);
        List<Team> teams = teamRepository.findAll(userId);

        model.addAttribute("handouts", handouts);
        model.addAttribute("teams", teams); //enum값도 다른 객체와 전혀 다르지 않게 view와 상호작용한다.

        return "/handouts/distributeHandout";
    }

    @PostMapping("/handouts/distribute")
    public String distribute(@RequestParam List<Long> handoutSelections, @RequestParam Long teamSelectionId, Model model) {
        //해당 팀에게 handout 할당
        List<Student> students = studentService.findByTeam(teamSelectionId);
        for (Long i : handoutSelections) {
            for (Student s : students) {
                handoutService.passHandout(i,s);
            }
        }

        //팀별 handout 조회
        Long userId = SecurityUtil.getCurrentUser().getId();
        List<Team> teams = teamRepository.findAll(userId);
        Map<String, Object> handoutsByTeam = new HashMap<>(); //팀별 handout 저장
//        model.addAttribute("teams", teams);

        for (Team team : teams) {
            //Team에 속한 학생이 없어서 handout이 안보이는 경우
            boolean isTeamNoStudents = studentService.findByTeam(team.getId()).isEmpty();
            if (isTeamNoStudents) {
                handoutsByTeam.put(team.getTeamName(), "No Students");      //문자열 삽입
            } else {
                //Team에 속한 학생이 존재하는 경우
                List<Handout> handoutTeam = handoutService.findHandoutByTeam(team.getId());
                handoutsByTeam.put(team.getTeamName(), handoutTeam);        //List 삽입
            }

        }


        model.addAttribute("handoutsByTeam", handoutsByTeam);
        return "/handouts/distributeResult";
    }
}
