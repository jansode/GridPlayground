package grid_playground;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GridPanel extends JPanel implements ActionListener, MouseListener {

	private Grid grid;
	private Timer timer;
	private int updateSpeed;
	private Simulation simulation;

	private final int kDefaultUpdateSpeed = 1000;
	private final int kNumSquares = 60;

	public GridPanel() {
		updateSpeed = kDefaultUpdateSpeed;
		timer = new Timer(kDefaultUpdateSpeed, this);
		grid = new SquareGrid(800, 800, kNumSquares, kNumSquares);
		simulation = new GameOfLifeSimulation(grid);
		addMouseListener(this);
	}

	public void setWrapAround(boolean wrapAround) {
		grid.setWrapAround(wrapAround);
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		if (grid != null) {
			grid.render(g2d);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	public void actionPerformed(ActionEvent e) {
		simulation.NextGeneration();
		repaint();
	}

	public void setUpdateSpeed(int speed) {
		updateSpeed = speed;
		timer.setDelay(updateSpeed);
	}

	public void startSimulation() {
		timer.start();
	}

	public void stopSimulation() {
		timer.stop();
	}

	public void initRandom() {
		grid.initRandom();
		repaint();
	}

	public void clear() {
		grid.clear();
		simulation.onClear();
		repaint();
	}

	public void setSimulation(String simulation) {
		if (simulation == "Game of Life")
			this.simulation = new GameOfLifeSimulation(grid);
		else if (simulation == "Langton's ant")
			this.simulation = new LangtonsAntSimulation(grid);
		else if (simulation == "Arrow game")
			this.simulation = new ArrowSimulation(grid);

		repaint();
	}

	public void setGridType(String gridType) {
		if (gridType == "Square") {
			grid = new SquareGrid(800, 800, kNumSquares, kNumSquares);
			simulation.setGrid(grid);
		} else if (gridType == "Hexagonal") {
			grid = new HexagonalGrid(800, 800, kNumSquares, kNumSquares);
			simulation.setGrid(grid);
		}

		repaint();
	}

	public void setRules(SimulationRules rules) {
		simulation.setRules(rules);
	}

	public SimulationRules getRules() {
		return simulation.getRules();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			grid.onMouseClick(e.getX(), e.getY());
		else
			grid.onMouseRightClick(e.getX(), e.getY());

		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
