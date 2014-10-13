import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Simulation {
	public static final int POPULATION_SIZE = 50;
	public static final int MAX_FOOD = 100;
	public static final int TICKS_PER_EPOCH = 100;
	public static final int ELITISM_OFFSET = 3;

	public static ArrayList<Creature> population = new ArrayList<Creature>();
	public static ArrayList<Food> food = new ArrayList<Food>();

	public static Random random = new Random();

	public static int epoch = 0;
	public static int currentTick = 0;

	public static void setup() {
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new Creature(random.nextDouble()
					* SimulationPanel.WIDTH, random.nextDouble()
					* SimulationPanel.HEIGHT));
		}
	}

	public static void update() {
		currentTick++;

		if (currentTick < TICKS_PER_EPOCH) {
			insertFood();
			for (Food f : food) {
				f.act();
			}

			for (Creature c : population) {
				c.act();
			}
			return;
		}

		// If the current epoch is complete:
		epoch++;
		currentTick = 0;

		Collections.sort(population, new FitnessComparator());

		ArrayList<Creature> newPopulation = new ArrayList<Creature>();

		for (int x = 0; x < ELITISM_OFFSET; x++) {
			newPopulation.add(population.get(x));
		}

		for (int x = ELITISM_OFFSET; x < POPULATION_SIZE; x++) {
			Creature mother = GeneticAlgorithm
					.rouletteSelectCreature(population);
			Creature father = GeneticAlgorithm
					.rouletteSelectCreature(population);

			while (mother == father) {
				mother = GeneticAlgorithm.rouletteSelectCreature(population);
				father = GeneticAlgorithm.rouletteSelectCreature(population);
			}
			
			if(mother == null || father == null) System.out.println("NULL!");
		}

		population = newPopulation;

	}

	private static void insertFood() {
		if (food.size() < MAX_FOOD) {
			Food f = new Food(Simulation.random.nextDouble()
					* SimulationPanel.WIDTH, Simulation.random.nextDouble()
					* SimulationPanel.HEIGHT);
			food.add(f);
		}
	}

	public static double sigmoid(double x) {
		return 1 / (1 + Math.pow(Math.E, -x));
	}
}
