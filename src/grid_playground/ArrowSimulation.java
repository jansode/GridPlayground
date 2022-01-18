package grid_playground;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Timer;

import grid_playground.SimulationRules.Direction;

public class ArrowSimulation extends Simulation implements ActionListener {

	private class Particle {
		public Point currentCell;
		public int lastValue;

		public Direction northGoes = Direction.EAST;
		public Direction eastGoes = Direction.SOUTH;
		public Direction southGoes = Direction.WEST;
		public Direction westGoes = Direction.NORTH;

		public Direction emptyNorth = Direction.WEST;
		public Direction emptyEast = Direction.NORTH;
		public Direction emptySouth = Direction.SOUTH;
		public Direction emptyWest = Direction.EAST;

		public long iterations = 0;
	}

	ArrayList<Particle> particles;

	private Timer timer;

	private boolean firstIteration = true;
	private Point currentCell;

	private HashMap<Direction, Integer> dir_to_int = new HashMap<Direction, Integer>();
	private HashMap<Integer, Direction> int_to_dir = new HashMap<Integer, Direction>();

	private final int kEmpty = 0;
	private final int kUp = 1;
	private final int kRight = 2;
	private final int kDown = 3;
	private final int kLeft = 4;

	private final long kIterationsThreshold = 1000;

	private int lastValue;

	public ArrowSimulation(Grid grid) {
		super(grid);

		particles = new ArrayList<Particle>();

		timer = new Timer(1500, this);

		dir_to_int.put(Direction.NORTH, 1);
		dir_to_int.put(Direction.EAST, 2);
		dir_to_int.put(Direction.SOUTH, 3);
		dir_to_int.put(Direction.WEST, 4);

		int_to_dir.put(1, Direction.NORTH);
		int_to_dir.put(2, Direction.EAST);
		int_to_dir.put(3, Direction.SOUTH);
		int_to_dir.put(4, Direction.WEST);

		currentCell = new Point(-1, -1);

		grid.bindValueToColor(0, new Color(5, 25, 35));
		grid.bindValueToColor(4, new Color(0, 53, 84));
		grid.bindValueToColor(3, new Color(0, 100, 148));
		grid.bindValueToColor(2, new Color(5, 130, 202));
		grid.bindValueToColor(1, new Color(0, 166, 251));

		grid.setSingleSelectionMode(true);

		grid.setSelectionValue(1);
	}

	private void splitParticle(Particle p, ArrayList<Particle> addThese) {
		Particle newParticle = new Particle();

		int new_x = Utils.getRandomBetween(p.currentCell.x - 1, p.currentCell.x + 1);
		if (new_x < 0)
			new_x = grid.getNumCellsX() - 1;
		new_x %= grid.getNumCellsX();

		int new_y = Utils.getRandomBetween(p.currentCell.y - 1, p.currentCell.y + 1);
		if (new_y < 0)
			new_y = grid.getNumCellsY() - 1;
		new_y %= grid.getNumCellsY();

		newParticle.currentCell = new Point(new_x,new_y);// p.currentCell;
		newParticle.northGoes = p.northGoes;
		newParticle.eastGoes = p.eastGoes;
		newParticle.southGoes = p.southGoes;
		newParticle.westGoes = p.westGoes;

		newParticle.emptyNorth = p.emptyNorth;
		newParticle.emptyEast = p.emptyEast;
		newParticle.emptySouth = p.emptySouth;
		newParticle.emptyWest = p.emptyWest;

		newParticle.iterations = 0;

		new_x = Utils.getRandomBetween(p.currentCell.x - 1, p.currentCell.x + 1);
		if (new_x < 0)
			new_x = grid.getNumCellsX() - 1;
		new_x %= grid.getNumCellsX();

		new_y = Utils.getRandomBetween(p.currentCell.y - 1, p.currentCell.y + 1);
		if (new_y < 0)
			new_y = grid.getNumCellsY() - 1;
		new_y %= grid.getNumCellsY();

		Particle newParticle2 = new Particle();
		newParticle2.currentCell = new Point(new_x,new_y);// p.currentCell;
		newParticle2.northGoes = p.northGoes;
		newParticle2.eastGoes = p.eastGoes;
		newParticle2.southGoes = p.southGoes;
		newParticle2.westGoes = p.westGoes;

		newParticle2.emptyNorth = p.emptyNorth;
		newParticle2.emptyEast = p.emptyEast;
		newParticle2.emptySouth = p.emptySouth;
		newParticle2.emptyWest = p.emptyWest;

		newParticle2.iterations = 0;

		addThese.add(newParticle);
		addThese.add(newParticle2);

		System.out.println("SPLIT!");
	}

	private void createNewParticle(int x, int y) {
		Particle newParticle = new Particle();
		newParticle.currentCell = new Point(x, y);
		newParticle.lastValue = 1;

		newParticle.northGoes = int_to_dir.get(Utils.getRandomBetween(1, 4, 1));
		newParticle.eastGoes = int_to_dir.get(Utils.getRandomBetween(1, 4, 2));
		newParticle.southGoes = int_to_dir.get(Utils.getRandomBetween(1, 4, 3));
		newParticle.westGoes = int_to_dir.get(Utils.getRandomBetween(1, 4, 4));

		newParticle.emptyNorth = int_to_dir.get(Utils.getRandomBetween(1, 4, 1));
		newParticle.emptyEast = int_to_dir.get(Utils.getRandomBetween(1, 4, 2));
		newParticle.emptySouth = int_to_dir.get(Utils.getRandomBetween(1, 4, 3));
		newParticle.emptyWest = int_to_dir.get(Utils.getRandomBetween(1, 4, 4));

		particles.add(newParticle);
		System.out.println("particles size: " + particles.size());
	}

	@Override
	public void NextGeneration() {
		if (firstIteration) {
			Point newParticle = grid.getSelectedCell();
			createNewParticle(newParticle.x, newParticle.y);
			timer.start();
			firstIteration = false;
		}

		ArrayList<Particle> toRemove = new ArrayList<Particle>();
		for (Particle p : particles) {
			for (Particle p2 : particles) {
				if (p == p2)
					continue;

				if (p.currentCell.x == p2.currentCell.x && p.currentCell.y == p2.currentCell.y) {
					System.out.println("Removed!");
					toRemove.add(p);
					toRemove.add(p2);
				}
			}
		}

		for (Particle p : toRemove) {
			particles.remove(p);
		}

		ArrayList<Particle> addThese = new ArrayList<Particle>();
		ArrayList<Particle> removeThese = new ArrayList<Particle>();
		for (int i = 0; i < particles.size(); ++i) {

			Particle p = particles.get(i);
			if (p.iterations++ == kIterationsThreshold) {
				splitParticle(p, addThese);
				removeThese.add(p);
			}

			switch (grid.getCell(p.currentCell.x, p.currentCell.y)) {
			case 0:
				p.lastValue += 1;
				p.lastValue %= 5;
				grid.setCell(p.currentCell.x, p.currentCell.y, p.lastValue);

				if (p.lastValue == kUp) {
					p.currentCell.y--;
					if (p.currentCell.y == -1)
						p.currentCell.y = grid.getNumCellsY() - 1;
				} else if (p.lastValue == kRight) {
					p.currentCell.x++;
					if (p.currentCell.x == grid.getNumCellsX())
						p.currentCell.x = 0;
				} else if (lastValue == kDown) {
					p.currentCell.y++;
					if (p.currentCell.y == grid.getNumCellsY())
						p.currentCell.y = 0;
				} else if (lastValue == kLeft) {
					p.currentCell.x--;
					if (p.currentCell.x == -1)
						p.currentCell.x = grid.getNumCellsX() - 1;
				}

				/*
				 * lastValue--; if(lastValue == -1) lastValue = 4;
				 */

				break;
			case 1:
				grid.setCell(p.currentCell.x, p.currentCell.y, dir_to_int.get(p.northGoes));
				p.currentCell.y--;
				if (p.currentCell.y == -1)
					p.currentCell.y = grid.getNumCellsY() - 1;

				p.lastValue = kUp;
				break;
			case 2:
				grid.setCell(p.currentCell.x, p.currentCell.y, dir_to_int.get(p.eastGoes));
				p.currentCell.x++;
				if (p.currentCell.x == grid.getNumCellsX())
					p.currentCell.x = 0;

				p.lastValue = kRight;
				break;
			case 3:
				grid.setCell(p.currentCell.x, p.currentCell.y, dir_to_int.get(p.southGoes));

				p.currentCell.y++;
				if (p.currentCell.y == grid.getNumCellsY())
					p.currentCell.y = 0;

				p.lastValue = kDown;
				break;
			case 4:
				grid.setCell(p.currentCell.x, p.currentCell.y, dir_to_int.get(p.westGoes));
				p.currentCell.x--;
				if (p.currentCell.x == -1)
					p.currentCell.x = grid.getNumCellsX() - 1;

				p.lastValue = kLeft;
				break;
			}
		}

		for (Particle p : removeThese)
			particles.remove(p);

		for (Particle p : addThese)
			particles.add(p);

		ArrayList<Point> points = new ArrayList<Point>();
		for (Particle p : particles)
			points.add(p.currentCell);

		grid.highlightCells(points);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (particles.size() == 1200) {
			timer.stop();
			return;
		}
		int new_x = ThreadLocalRandom.current().nextInt(0, grid.getNumCellsX());
		int new_y = ThreadLocalRandom.current().nextInt(0, grid.getNumCellsY());

		/*
		 * ArrayList<Particle> addThese = new ArrayList<Particle>(); for(int i = 0; i <
		 * particles.size(); ++i) { splitParticle(particles.get(i),i,addThese); }
		 * 
		 * particles.clear();
		 * 
		 * for(int i = 0; i < addThese.size(); ++i) { particles.add(addThese.get(i)); }
		 */
		createNewParticle(new_x, new_y);
	}

	@Override
	public void onClear() {
		timer.stop();
		particles.clear();
		firstIteration = true;
	}

}
