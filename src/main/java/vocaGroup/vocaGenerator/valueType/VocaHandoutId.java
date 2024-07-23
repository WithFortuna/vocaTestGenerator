package vocaGroup.vocaGenerator.valueType;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VocaHandoutId implements Serializable {
    private Long handoutId;
    private Long vocaId;

    public VocaHandoutId() {

    }
    public VocaHandoutId(Long handoutId, Long vocaId) {
        this.handoutId = handoutId;
        this.vocaId = vocaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocaHandoutId that = (VocaHandoutId) o;
        return Objects.equals(handoutId, that.handoutId) && Objects.equals(vocaId, that.vocaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handoutId, vocaId);
    }
}
