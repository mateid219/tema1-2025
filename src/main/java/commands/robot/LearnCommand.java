package commands.robot;

import exceptions.BatteryException;
import exceptions.SubjectNotSavedException;
import fileio.CommandInput;
import simulation.terrabot.Fact;
import simulation.Simulation;
import simulation.terrabot.TerraBot;

public final class LearnCommand extends RobotCommand {
    static final String SUCCESS_SAVED = "The fact has been successfully saved in the database.";
    private Fact fact;

    public LearnCommand() { }
    public LearnCommand(final CommandInput commandInput) {
        super(commandInput);
        fact = new Fact(commandInput.getSubject(), commandInput.getComponents());
    }

    @Override
    public void doExecute(final Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        try {
            terraBot.learnFact(fact);
            message = SUCCESS_SAVED;
        } catch (BatteryException | SubjectNotSavedException e) {
            message = e.getMessage();
        }
    }
}
