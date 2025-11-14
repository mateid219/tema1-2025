package simulation.terrabot;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Fact {
    @Getter private String subject;
    @Getter private String components;
    public Fact(String subject, String components) {
        this.subject = subject;
        this.components = components;
    }
}
