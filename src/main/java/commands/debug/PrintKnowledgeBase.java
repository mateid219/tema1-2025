package commands.debug;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class PrintKnowledgeBase extends DebugCommand {
    public PrintKnowledgeBase(CommandInput commandInput) {
        super(commandInput);
    }

    @Override
    public void doExecute(Simulation simulation) {
        commandArrayOutput = simulation.getTerraBot().printKnowledgeBase();
    }
}
