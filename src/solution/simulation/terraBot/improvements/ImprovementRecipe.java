package simulation.terraBot.improvements;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ImprovementRecipe {
    @Getter private String improvementType;
    @Getter private String name;
    public ImprovementRecipe(final String improvementType, final String name) {
        this.improvementType = improvementType;
        this.name = name;
    }
}
