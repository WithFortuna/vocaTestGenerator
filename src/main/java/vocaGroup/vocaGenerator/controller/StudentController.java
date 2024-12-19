package vocaGroup.vocaGenerator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.DTO.StudentForm;
import vocaGroup.vocaGenerator.domain.Team;
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
        List<Team> teams = teamRepository.findAll();
        model.addAttribute("teams",teams);
        model.addAttribute("studentForm", new StudentForm());
        return "students/createStudentForm";
    }

    @PostMapping("/students/register")
    public String create(@Valid StudentForm studentForm, @RequestParam Long teamId, BindingResult result) {
        if (result.hasErrors()) {
            return "studetns/createStudentForm";
        }
        Team findTeam = teamRepository.findById(teamId);
        Student student = new Student(studentForm.getName(), studentForm.getAge(), findTeam);
        studentService.join(student);


        return "redirect:/"; //url 을 redirect
    }

    @GetMapping("/students")
    public String showlist(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "/students/studentList";
    }
}
