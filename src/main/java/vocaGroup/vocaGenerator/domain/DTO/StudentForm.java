package vocaGroup.vocaGenerator.domain.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class StudentForm {
    @NotEmpty(message = "필수값")
    String name;
//    @NotEmpty(message = "필수값") int값에는 이 에노테이션 불가
    int age;
}
