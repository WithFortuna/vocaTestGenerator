package vocaGroup.vocaGenerator.domain.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class VocaForm {
    @NotEmpty(message = "필수값")
    private String english;
    @NotEmpty(message = "필수값")
    private String korean;

}
