package vocaGroup.vocaGenerator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vocaGroup.vocaGenerator.domain.Student;
import vocaGroup.vocaGenerator.domain.DTO.StudentForm;
import vocaGroup.vocaGenerator.domain.Team;
import vocaGroup.vocaGenerator.service.StudentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/students/register")
    public String createForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "students/createStudentForm";
    }

    @PostMapping("/students/register")
    public String create(@Valid StudentForm studentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "studetns/createStudentForm";
        }
        String str = studentForm.getTeam();
        if(str.equals("A")){
            Student student = new Student(studentForm.getName(), studentForm.getAge(), Team.A);
            studentService.join(student);

        }
        else {
            Student student = new Student(studentForm.getName(), studentForm.getAge(), Team.B);
            studentService.join(student);
        }

        return "redirect:/"; //url 을 redirect
    }

    @GetMapping("/students")
    public String showlist(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "/students/studentList";
    }
}
