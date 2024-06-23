import rendering.ConsoleRenderer;
import rendering.Renderer;
import world_map.WorldMap;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("========= WELCOME TO SIMULATION =========");
        System.out.println("========= PLEASE INPUT WIDTH AND HEIGHT OF THE MAP IN FORMAT: WIDTH HEIGHT =========");
        System.out.println("========= AFTER YOUR INPUT SIMULATION WILL START =========");
        System.out.println("========= SIMULATION CAN BE PAUSED WITH PRESSING p or P BUTTON=========");
        Scanner scanner = new Scanner(System.in);

        int mapWidth = scanner.nextInt();
        int mapHeight = scanner.nextInt();

        WorldMap worldMap = new WorldMap(mapWidth, mapHeight);
        Renderer renderer = new ConsoleRenderer();

        Simulation simulation = new Simulation(renderer, worldMap);
        simulation.start();
    }
}