package grid_playground;

public abstract class Simulation {

	protected Grid grid;
	protected SimulationRules rules;

	public Simulation(Grid grid) {
		this.grid = grid;
		this.grid.setSingleSelectionMode(false);
		
		rules = new SimulationRules();
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void setRules(SimulationRules rules) {
		this.rules = rules;
	}
	
	public SimulationRules getRules()
	{
		return rules;
	}

	public abstract void NextGeneration();
	public abstract void onClear();
}
