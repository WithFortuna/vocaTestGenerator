package vocaGroup.vocaGenerator.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vocaGroup.vocaGenerator.domain.User;

//기능: spring security에서 로그인 관리를 위해서, User엔티티 -> UserDetails 객체로 변환함
// User객체에 저장된 password와 http request의 password를 비교하기 위해 UserDetails를 제공하기 위함
@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findByUsername(username);
        if (findUser == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(findUser.getUsername())
                .password(findUser.getPassword())
                .roles(findUser.getRole().name())   //"ROLE_" 접두사 자동 추가 cf) .authorities(new SimpleGrantedAuthority(findUser.getRole.getKey()))
                .build();

    }
}
