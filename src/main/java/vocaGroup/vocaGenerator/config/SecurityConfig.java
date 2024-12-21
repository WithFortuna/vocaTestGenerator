package vocaGroup.vocaGenerator.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vocaGroup.vocaGenerator.domain.enumType.Role;

@Configuration
public class SecurityConfig {

    //PasswordEncoder 조립
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Security filter chain 조립
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserDetailsService userDetailsService) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/register","/login").permitAll()
//                        .requestMatchers("/??").hasRole(Role.ADMIN.getKey())
                        .anyRequest().authenticated()
                )
                .formLogin(login->login
//                        .loginPage("/login")    //해당 명령어를 끄면, spring security가 login 페이지 렌더링
                                .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout->logout
                                .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );


        //AuthenticationManger 설정
        // => UserDetailServiceImpl & PasswordEncoder 조립
        httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return httpSecurity.build();


    }

}
