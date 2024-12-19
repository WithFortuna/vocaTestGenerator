package vocaGroup.vocaGenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vocaGroup.vocaGenerator.domain.Team;
import vocaGroup.vocaGenerator.repository.TeamRepository;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TeamController {
    private final TeamRepository teamRepository;

    @GetMapping("/teams")
    public String showTeams(Model model) {
        List<Team> teams = teamRepository.findAll();
        model.addAttribute("teams", teams);

        return "/teams/showTeams";
    }

    @GetMapping("/teams/delete/{id}")
    public String deleteTeam(@PathVariable Long id) {
        teamRepository.delete(id);
        return "redirect:/teams";
    }

    @GetMapping("/teams/new")
    public String createForm() {
        return "teams/createTeam";
    }

    @PostMapping("/teams/new")
    public String createTeam(@RequestParam String teamName) {
        teamRepository.save(new Team(teamName));

        return "redirect:/teams";
    }
}
