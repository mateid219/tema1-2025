package simulation.terrabot.improvements;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ImprovementRecipe {
    @Getter private String improvementType;
    @Getter private String name;
    public ImprovementRecipe(String improvementType, String name) {
        this.improvementType = improvementType;
        this.name = name;
    }
}
