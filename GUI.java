import javax.swing.JFrame;


public class GUI extends JFrame {
	public GUI() {
		setSize(600, 600);
		setTitle("Genetic Algorithm Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		
		SimulationPanel simulationPanel = new SimulationPanel();
		add(simulationPanel);
	}
}
