package vocaGroup.vocaGenerator.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vocaGroup.vocaGenerator.domain.User;
import vocaGroup.vocaGenerator.domain.enumType.Role;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  //BCryptPasswordEncoder가 주입될 것

/* "/login" URL POST요청은 스프링 시큐리티가 처리하므로 별도 작성 x*/

    @GetMapping("/register")
    public String showRegisterForm() {
        return "/users/registerForm";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
                             Model model) {
        User findUser = userRepository.findByUsername(username);
        if (findUser != null) {
            model.addAttribute("error", "이미 존재하는 아이디입니다. 다른 아이디를 설정해주세요");
            return "/users/registerForm";
        }
        else{
            String encodedPassword = passwordEncoder.encode(password);
            User newUser = new User(username, encodedPassword, Role.USER);
            userRepository.save(newUser);

            return "redirect:/";
        }


    }
}

