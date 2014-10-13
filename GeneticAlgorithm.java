import java.util.ArrayList;


public class GeneticAlgorithm {
	public static Creature rouletteSelectCreature(ArrayList<Creature> population) {
		int totalFitness = 0;
		for(Creature c: population) {
			totalFitness += c.fitness;
		}
		
		double rand = Simulation.random.nextDouble();
		
		int creatureIndex = 0;
		double currentFitnessSum = 0;
		
		Creature creature = null;
		while(creatureIndex < population.size()) {
			currentFitnessSum += population.get(creatureIndex).fitness / (totalFitness * 1.00);
			if(rand <= currentFitnessSum) {
				creature = population.get(creatureIndex);
				break;
			}
			
			creatureIndex++;
		}
		
		return creature;
	}
	
	public static Creature crossover(Creature mother, Creature father) {
		return null;
	}
}
