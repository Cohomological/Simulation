import actions.*;
import actions.generation.*;
import entities.static_objects.Grass;
import rendering.Renderer;
import world_map.WorldMap;

import java.util.ArrayList;

public class Simulation {
    public int turnCounter = 0;

    private WorldMap worldMap;
    private Renderer renderer;
    ArrayList<Action> initActions = new ArrayList<>();
    ArrayList<Action> turnActions = new ArrayList<>();

    public Simulation(Renderer renderer, WorldMap worldMap) {
        this.renderer = renderer;
        this.worldMap = worldMap;

        fillInitActions();
        fillTurnActions();
    }

    private void fillTurnActions() {
        turnActions.add(new HerbivoreGeneration(0.1));
        turnActions.add(new GrassGeneration(0.2));
        turnActions.add(new MakeMoves());
    }

    private void fillInitActions() {
        initActions.add(new GrassGeneration(0.2));
        initActions.add(new HerbivoreGeneration(0.1));
        initActions.add(new PredatorGeneration(0.1));
        initActions.add(new RockGeneration(0.05));
        initActions.add(new TreeGeneration(0.05));
    }

    public void startSimulation() {
        performActions(initActions);

        while (true) {
            turnCounter++;

            performActions(turnActions);

            System.out.println("=======TURN NUMBER: " + turnCounter + "=======");
            renderer.render(worldMap);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void performActions(ArrayList<Action> actions) {
        for (Action action : actions) {
            action.perform(worldMap);
        }
    }
}
