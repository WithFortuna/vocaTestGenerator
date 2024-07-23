package vocaGroup.vocaGenerator.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class HandoutForm {
    private String week;
    //View에게서 변수 하나가 아니라 List같은 배열형태로 받아야 할 때
    private List<VocaForm> vocaForms;
}
