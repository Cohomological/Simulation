import rendering.ConsoleRenderer;
import rendering.Renderer;
import world_map.WorldMap;


public class Main {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(10, 10);
        Renderer renderer = new ConsoleRenderer();

        Simulation simulation = new Simulation(renderer, worldMap);
        simulation.startSimulation();

//        System.out.println("🟩");
    }
}