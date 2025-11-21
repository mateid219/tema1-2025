package simulation.terraBot;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Fact {
    @Getter private String subject;
    @Getter private String components;
    public Fact(final String subject, final String components) {
        this.subject = subject;
        this.components = components;
    }
}
