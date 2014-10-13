import java.awt.Color;
import java.awt.Graphics2D;


public abstract class SimulationObject {
	public int x;
	public int y;
	
	public void paint(Graphics2D g2) {
		if(SimulationPanel.SHOW_COORDS) {
			g2.setColor(Color.BLUE);
			g2.drawString("(" + x + ", " + y + ")", x, y - 10);
		}
	}
	
	public void act() {
		
	}
}
