package vocaGroup.vocaGenerator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.DTO.StudentForm;
import vocaGroup.vocaGenerator.domain.Team;
import vocaGroup.vocaGenerator.login.CustomUserDetails;
import vocaGroup.vocaGenerator.login.utility.SecurityUtil;
import vocaGroup.vocaGenerator.repository.TeamRepository;
import vocaGroup.vocaGenerator.service.StudentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final TeamRepository teamRepository;
    @GetMapping("/students/register")
    public String createForm(Model model) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        List<Team> teams = teamRepository.findAll(userId);
        model.addAttribute("teams",teams);
        model.addAttribute("studentForm", new StudentForm());
        return "students/createStudentForm";
    }

    @PostMapping("/students/register")
    public String create(@Valid StudentForm studentForm, @RequestParam Long teamId,
                         BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "studetns/createStudentForm";
        }
        Team findTeam = teamRepository.findById(teamId);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Student student = new Student(studentForm.getName(), studentForm.getAge(), findTeam,userDetails.getUser());
        studentService.join(student);


        return "redirect:/"; //url 을 redirect
    }

    @GetMapping("/students")
    public String showList(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();

        List<Student> students = studentService.findAll(userId);
        model.addAttribute("students", students);
        return "/students/studentList";
    }
}
