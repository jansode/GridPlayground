package grid_playground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Grid {

	protected ArrayList<ArrayList<Integer>> grid_array;
	protected HashMap<Integer, Color> value_to_color;

	protected final int kDefaultValue = 0;
	protected final Color kDefaultFilledColor = new Color(255, 255, 255);
	protected final Color kDefaultCellColor = new Color(25, 25, 25);
	protected final Color kDefaultCellOutline = new Color(5, 25, 35);
	
	protected ArrayList<Point> highlightedCells = new ArrayList<Point>();

	protected int width;
	protected int height;
	protected int numCellsX;
	protected int numCellsY;

	protected int leftPadding;
	protected int topPadding;

	// Controls whether neighbouring cells at the edge
	// are wrapped around to the opposing side of the grid.
	protected boolean wrapAround = false;

	// In this mode only one coordinate can be selected at
	// a time.
	protected boolean singleSelectionMode = false;
	protected Point selectedCell;
	
	// Cell selected with mouse right click.
	protected Point rightSelectedCell;
	
	protected int emptyValue = 0;
	protected int occupiedValue = 1;
	protected int selectionValue = 1;

	public Grid(int width, int height, int num_cells_x, int num_cells_y) {
		this.width = width;
		this.height = height;
		this.numCellsX = num_cells_x;
		this.numCellsY = num_cells_y;

		grid_array = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < num_cells_y; ++i) {
			grid_array.add(new ArrayList<Integer>());
			for (int j = 0; j < num_cells_x; ++j)
				grid_array.get(i).add(kDefaultValue);
		}

		value_to_color = new HashMap<Integer, Color>();

		bindValueToColor(0, kDefaultCellColor);
		bindValueToColor(1, kDefaultFilledColor);

		selectedCell = new Point(-1, -1);
		rightSelectedCell = new Point(-1, -1);
	}
	
	public Point getSelectedCell()
	{
		return selectedCell;
	}
	
	public Point getRightSelectedCell()
	{
		return rightSelectedCell;
	}

	public abstract void onMouseClick(int mouseX, int mouseY);
	public abstract void onMouseRightClick(int mouseX, int mouseY);
	
	public abstract void highlightCells(ArrayList<Point> cells);

	public abstract int numNeighbours(int x, int y);

	public abstract void render(Graphics2D g2d);

	public void initRandom() {
		for (int y = 0; y < numCellsY; ++y) {
			for (int x = 0; x < numCellsX; ++x) {
				if (Math.random() < 0.5) {
					setCell(x, y, emptyValue);
				} else {
					setCell(x, y, occupiedValue);
				}
			}
		}
	}

	public void clear() {
		highlightedCells.clear();
		for (int y = 0; y < numCellsY; ++y) {
			for (int x = 0; x < numCellsX; ++x) {
				setCell(x, y, emptyValue);
			}
		}
	}

	public int getNumCellsX() {
		return numCellsX;
	}

	public int getNumCellsY() {
		return numCellsY;
	}

	public void setWrapAround(boolean wrapAround) {
		this.wrapAround = wrapAround;
	}

	public void bindValueToColor(int value, Color color) {
		value_to_color.put(value, color);
	}

	public void setCell(int x, int y, int value) {
		grid_array.get(y).set(x, value);
	}

	public int getCell(int x, int y) {
		return grid_array.get(y).get(x);
	}

	public void setLeftPadding(int padding) {
		this.leftPadding = padding;
	}

	public void setTopPadding(int padding) {
		this.topPadding = padding;
	}

	public void setSingleSelectionMode(boolean value) {
		this.singleSelectionMode = value;
	}
	
	public void setSelectionValue(int value)
	{
		this.selectionValue = value;
	}

	public void setOccupiedValue(int value)
	{
		this.occupiedValue = value;
	}

	public void setEmptyValue(int value)
	{
		this.emptyValue = value;
	}
}
