package vocaGroup.vocaGenerator.domain.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter //Role.getKey()를 위해서는 필요. 왜냐하면 SpringSecurity는 prefix로 "ROLE_" 필요
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER","사용자"), ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
