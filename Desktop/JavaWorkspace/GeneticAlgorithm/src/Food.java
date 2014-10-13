import java.awt.Color;
import java.awt.Graphics2D;


public class Food extends SimulationObject {
	private static final int SIDE_LENGTH = 6;
	
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Food(double x, double y) {
		this((int)x, (int)y);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		g2.fillRect(x, y, SIDE_LENGTH, SIDE_LENGTH);
		
		super.paint(g2);
	}
}
