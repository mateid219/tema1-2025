package commands.robot;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.terrabot.TerraBot;
import simulation.terrabot.improvements.ImprovementRecipe;

@NoArgsConstructor
public class ImproveCommand extends RobotCommand {
    private static final String PLANT  = "plant";
    private static final String FERTILIZE  = "fertilize";
    private static final String INCREASE_AIR_HUMIDITY = "increase humidity";
    private static final String INCREASE_SOIL_MOISTURE = "oisture";
    private static final String PLANT_IMPROVEMENT_FORMAT = "The %s was planted successfully.";
    private static final String IMPROVEMENT_FORMAT = "The %s was successfully %s using %s";
    private static final String FERTILIZED = "fertilized";
    private static final String INCREASED = "increased";
    private static final String MOISTURE = "moisture";
    private static final String HUMIDITY = "humidity";
    private static final String SOIL = "soil";

    private static final String ERROR_SUBJECT_NOT_SAVED =
            "ERROR: Subject not yet saved. Cannot perform action";
    private static final String ERROR_FACT_NOT_SAVED =
            "ERROR: Fact not yet saved. Cannot perform action";



    private static final String PLANT_VEGETATION = "plantVegetation";
    private static final String FERTILIZE_SOIL = "fertilizeSoil";
    private static final String INCREASE_HUMIDITY = "increaseHumidity";
    private static final String INCREASE_MOISTURE = "increaseMoisture";

    private static final int IMPROVE_ENVIRONMENT_COST = 10;

    private static final double OXYGEN_INCREASE = 0.3;
    private static final double ORGANIC_MATTER_INCREASE = 0.3;
    private static final double HUMIDITY_INCREASE = 0.2;
    private static final double WATER_RETENTION_INCREASE = 0.2;


    ImprovementRecipe recipe;

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
