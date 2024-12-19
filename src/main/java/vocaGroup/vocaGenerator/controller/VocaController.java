package vocaGroup.vocaGenerator.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import vocaGroup.vocaGenerator.domain.DTO.VocaForm;
import vocaGroup.vocaGenerator.domain.Voca;
import vocaGroup.vocaGenerator.service.VocaService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VocaController {
    private final VocaService vocaService;

    @GetMapping("/vocabs/register")
    public String createForm(Model model) {
        model.addAttribute("vocaForm", new VocaForm());//<--유저에게 입력받을 값
        return "vocabs/createVocaForm";
    }

    @PostMapping("/vocabs/register")
    public String create(@Valid VocaForm vocaForm, BindingResult result) {
        if (result.hasErrors()) {
            return "studetns/createStudentForm";
        }
        Voca voca = new Voca(vocaForm.getEnglish(), vocaForm.getKorean());
        vocaService.join(voca);

        return "redirect:/vocabs/register";
    }

    @GetMapping("/vocabs")
    public String showList(Model model) {
        List<Voca> vocabs = vocaService.findAll();
        model.addAttribute("vocabs", vocabs);
        return "/vocabs/vocaList";
    }
}
