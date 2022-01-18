package grid_playground;

public class SimulationRules {

	public enum Direction {
		NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
	};

	// Game of life rules
	public int underpopulation = 1;
	public int reproduction = 3;
	public int overpopulation = 4;

	// Arrow simulation rules
	public Direction northGoes = Direction.EAST;
	public Direction eastGoes = Direction.SOUTH;
	public Direction southGoes = Direction.WEST;
	public Direction westGoes = Direction.NORTH;

	public Direction emptyNorth = Direction.WEST;
	public Direction emptyEast = Direction.NORTH;
	public Direction emptySouth = Direction.SOUTH;
	public Direction emptyWest = Direction.EAST;

	public SimulationRules() {

	}
}
