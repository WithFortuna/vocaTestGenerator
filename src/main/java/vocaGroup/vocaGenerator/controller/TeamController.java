package vocaGroup.vocaGenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.Team;
import vocaGroup.vocaGenerator.domain.User;
import vocaGroup.vocaGenerator.login.utility.SecurityUtil;
import vocaGroup.vocaGenerator.repository.TeamRepository;
import vocaGroup.vocaGenerator.service.StudentService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TeamController {
    private final TeamRepository teamRepository;
    private final StudentService studentService;

    /*팀 조회*/
    @GetMapping("/teams")
    public String showTeams(Model model) {
        Long userId = SecurityUtil.getCurrentUser().getId();

        List<Team> teams = teamRepository.findAll(userId);
        model.addAttribute("teams", teams);

        return "/teams/showTeams";
    }

    @GetMapping("/teams/delete/{id}")
    public String deleteTeam(@PathVariable Long id) {
        teamRepository.delete(id);
        return "redirect:/teams";
    }

    /*팀 생성*/
    @GetMapping("/teams/new")
    public String createForm() {
        return "teams/createTeam";
    }

    @PostMapping("/teams/new")
    public String createTeam(@RequestParam String teamName) {
        try {
            User currentUser = SecurityUtil.getCurrentUser();
            teamRepository.save(new Team(teamName, currentUser));
            return "redirect:/teams";
        } catch (Exception e) {
            return "teams/createTeam";
        }
    }

    //해당 팀의 학생 보여주기
    @PostMapping("/teams/{id}")
    public String showTeamMember(@PathVariable Long id, @RequestParam String teamName,
                                     Model model) {
        List<Student> students = studentService.findByTeam(id);
        model.addAttribute("students", students);
        model.addAttribute("teamName", teamName);
        return "teams/showTeamMember";
    }

}
