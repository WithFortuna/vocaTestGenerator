package vocaGroup.vocaGenerator.controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.cfg.defs.Mod10CheckDef;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("authentication", authentication);
        log.info("home controller");
        return "home";
    }
}
