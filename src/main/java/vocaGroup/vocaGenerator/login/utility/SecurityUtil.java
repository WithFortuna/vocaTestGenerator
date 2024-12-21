package vocaGroup.vocaGenerator.login.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vocaGroup.vocaGenerator.domain.User;
import vocaGroup.vocaGenerator.login.CustomUserDetails;

public class SecurityUtil {
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUser();
        }
        throw new IllegalStateException("User is not authenticated 위치: SecurityUtil"); //인증 안된 경우
    }
}
