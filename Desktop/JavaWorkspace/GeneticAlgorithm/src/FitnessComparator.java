import java.util.Comparator;


public class FitnessComparator implements Comparator<Creature> {

	@Override
	public int compare(Creature arg0, Creature arg1) {
		return -(arg0.fitness - arg1.fitness);
	}

}
