package grid_playground;

import java.util.ArrayList;

public class GameOfLifeSimulation extends Simulation {

	public GameOfLifeSimulation(Grid grid) {
		super(grid);
		
		grid.setEmptyValue(0);
		grid.setOccupiedValue(1);
		grid.setSelectionValue(1);
		
		grid.setSingleSelectionMode(false);
		grid.clear();
	}

	@Override
	public void NextGeneration() {
		ArrayList<ArrayList<Integer>> new_grid = new ArrayList<ArrayList<Integer>>();
		for (int y = 0; y < grid.getNumCellsY(); ++y) {
			new_grid.add(new ArrayList<Integer>());
			for (int x = 0; x < grid.getNumCellsX(); ++x) {
				int cell_value = grid.getCell(x, y);
				int neighbours = grid.numNeighbours(x, y);

				new_grid.get(y).add(0);

				if (cell_value == 1) {
					if (neighbours <= rules.underpopulation) {
						new_grid.get(y).set(x, 0);
					} else if (neighbours >= rules.overpopulation) {
						new_grid.get(y).set(x, 0);
					} else {
						new_grid.get(y).set(x, 1);
					}
				} else if (cell_value == 0) {
					if (neighbours == rules.reproduction) {
						new_grid.get(y).set(x, 1);
					}
				}
			}
		}

		for (int y = 0; y < grid.getNumCellsY(); ++y) {
			for (int x = 0; x < grid.getNumCellsX(); ++x) {
				grid.setCell(x, y, new_grid.get(y).get(x));
			}
		}

	}

	@Override
	public void onClear() {
	}
}
