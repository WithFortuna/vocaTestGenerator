package vocaGroup.vocaGenerator.domain;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
@Entity
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;
    public Team(String teamName, User user) {
        this.teamName = teamName;
        this.user = user;
    }

}
