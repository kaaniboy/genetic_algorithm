import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;

public class Creature extends SimulationObject {
	private static final int RADIUS = 8;
	private static final int RAY_LENGTH = 12;
	private static final int MOVEMENT_FACTOR = 5;
	private static final int ROTATION_FACTOR = 15;

	private static final int INPUT_SIZE = 3;
	private static final int HIDDEN_SIZE = 10;
	private static final int OUTPUT_SIZE = 2;
	private static final int WEIGHT_FACTOR = 2;

	private double[][] weights1 = new double[INPUT_SIZE][HIDDEN_SIZE];
	private double[][] weights2 = new double[HIDDEN_SIZE][OUTPUT_SIZE];
	
	private int rotation = 0;
	public int fitness = 0;
	
	private ArrayList<Food> eaten = new ArrayList<Food>();

	public Creature(int x, int y) {
		this.x = x;
		this.y = y;

		randomizeWeights();
	}

	public Creature(double x, double y) {
		this((int)x, (int)y);
	}

	private void randomizeWeights() {
		for (int x = 0; x < INPUT_SIZE; x++) {
			for (int y = 0; y < HIDDEN_SIZE; y++) {
				weights1[x][y] = Simulation.random.nextDouble() * WEIGHT_FACTOR
						- (WEIGHT_FACTOR / 2.0);
			}
		}

		for (int x = 0; x < HIDDEN_SIZE; x++) {
			for (int y = 0; y < OUTPUT_SIZE; y++) {
				weights2[x][y] = Simulation.random.nextDouble() * WEIGHT_FACTOR
						- (WEIGHT_FACTOR / 2.0);
			}
		}
	}

	public double[] runNetwork(double[] inputs) {
		double[] hiddenActivations = new double[HIDDEN_SIZE];
		double[] outputActivations = new double[OUTPUT_SIZE];

		for (int x = 0; x < HIDDEN_SIZE; x++) {
			double activation = 0;
			for (int y = 0; y < INPUT_SIZE; y++) {
				activation += inputs[y] * weights1[y][x];
			}
			hiddenActivations[x] = Simulation.sigmoid(activation);
		}

		for (int x = 0; x < OUTPUT_SIZE; x++) {
			double activation = 0;
			for (int y = 0; y < HIDDEN_SIZE; y++) {
				activation += hiddenActivations[y] * weights2[y][x];
			}
			outputActivations[x] = Simulation.sigmoid(activation);
		}

		return outputActivations;
	}

	public void act() {
		double[] outputs = runNetwork(new double[] { 0, 0, 1 });

		// The first output of the network represents movement, whereas the
		// second represents rotation.

		rotation = (rotation + (int) (ROTATION_FACTOR * (outputs[1] - .5))) % 360;

		x += (int) (outputs[0] * MOVEMENT_FACTOR * Math.cos(Math
				.toRadians(rotation)));
		y -= (int) (outputs[0] * MOVEMENT_FACTOR * Math.sin(Math
				.toRadians(rotation)));
		
		tryEating();
	}

	public void tryEating() {
		for(Food f: Simulation.food) {
			if(Math.sqrt(Math.pow(x - f.x, 2) + Math.pow(y - f.y, 2)) - 1 <= RADIUS) {
				fitness++;
				eaten.add(f);
				System.out.println("REMOVE!");
			}
		}
		
		for(Food f: eaten) {
			Simulation.food.remove(f);
		}
		
		eaten.clear();
	}
	
	public void paint(Graphics2D g2) {
		g2.setColor(Color.ORANGE);
		g2.fillOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);

		g2.setStroke(new BasicStroke(1F));
		g2.setColor(Color.BLUE);
		if (rotation == 0) {
			g2.drawLine(x, y, x + RAY_LENGTH, y);
		} else if (rotation == 90) {
			g2.drawLine(x, y, x, y - RAY_LENGTH);
		} else if (rotation == 180) {
			g2.drawLine(x, y, x - RAY_LENGTH, y);
		} else if (rotation == 270) {
			g2.drawLine(x, y, x, y + RAY_LENGTH);
		} else {
			g2.drawLine(
					x,
					y,
					x + (int) (RAY_LENGTH * Math.cos(Math.toRadians(rotation))),
					y - (int) (RAY_LENGTH * Math.sin(Math.toRadians(rotation))));
		}
		
		g2.setColor(Color.BLUE);
		g2.drawString("F: " + fitness, x, y - 20);
		super.paint(g2);
	}

	public Creature cloneAndReset() {
		Creature c = new Creature(Simulation.random.nextDouble()
					* SimulationPanel.WIDTH, Simulation.random.nextDouble()
					* SimulationPanel.HEIGHT);
		c.weights1 = Arrays.copyOf(weights1, weights1.length);
		c.weights2 = Arrays.copyOf(weights2, weights2.length);
		
		return c;
	}
}
