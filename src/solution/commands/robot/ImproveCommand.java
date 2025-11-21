package commands.robot;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.terraBot.TerraBot;
import simulation.terraBot.improvements.ImprovementRecipe;

@NoArgsConstructor
public class ImproveCommand extends RobotCommand {

    private ImprovementRecipe recipe;

    public ImproveCommand(final CommandInput commandInput) {
        super(commandInput);
        recipe = new ImprovementRecipe(commandInput.getImprovementType(), commandInput.getName());
    }

    @Override
    public final void doExecute(final Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        try {
            message = terraBot.improveEnvironment(recipe);
        } catch (RuntimeException e) {
            message = e.getMessage();
        }

    }
}
