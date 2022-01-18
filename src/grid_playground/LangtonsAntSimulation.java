package grid_playground;

import java.awt.Point;

public class LangtonsAntSimulation extends Simulation {

	enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	// private ArrayList<Point> antLocations;

	Point antLocation;
	Direction currentDirection;

	boolean firstIteration = true;

	public LangtonsAntSimulation(Grid grid) {
		super(grid);

		grid.setEmptyValue(0);
		grid.setOccupiedValue(1);
		// grid.setSelectionValue(2);

		// grid.bindValueToColor(2, new Color(255, 0, 0));
		grid.clear();

		currentDirection = Direction.UP;
	}

	@Override
	public void NextGeneration() {

		if (firstIteration) {
			antLocation = grid.getRightSelectedCell();
			firstIteration = false;
		}

		updateAnt(antLocation);
	}

	private void updateAnt(Point ant) {
		int value = grid.getCell(ant.x, ant.y);
		if (value == 0) {
			grid.setCell(ant.x, ant.y, 1);
		} else {
			grid.setCell(ant.x, ant.y, 0);
		}

		switch (currentDirection) {
		case UP:
			if (value == 0) {
				currentDirection = Direction.LEFT;
				ant.x--;
				if (ant.x < 0)
					ant.x = grid.getNumCellsX() - 1;
			} else {
				currentDirection = Direction.RIGHT;
				ant.x = (ant.x + 1) % grid.getNumCellsX();
			}
			break;
		case RIGHT:
			if (value == 0) {
				currentDirection = Direction.UP;
				ant.y--;
				if (ant.y < 0)
					ant.y = grid.getNumCellsY() - 1;
			} else {
				currentDirection = Direction.DOWN;
				ant.y = (ant.y + 1) % grid.getNumCellsY();
			}
			break;
		case DOWN:
			if (value == 0) {
				currentDirection = Direction.RIGHT;
				ant.x = (ant.x + 1) % grid.getNumCellsX();
			} else {
				currentDirection = Direction.LEFT;
				ant.x--;
				if (ant.x < 0)
					ant.x = grid.getNumCellsX() - 1;
			}
			break;
		case LEFT:
			if (value == 0) {
				currentDirection = Direction.DOWN;
				ant.y = (ant.y + 1) % grid.getNumCellsY();
			} else {
				currentDirection = Direction.UP;
				ant.y--;
				if (ant.y < 0)
					ant.y = grid.getNumCellsY() - 1;
			}
			break;
		}

	}

	@Override
	public void onClear() {
		firstIteration = true;
	}

}
