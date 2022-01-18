package grid_playground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class SquareGrid extends Grid {

	public SquareGrid(int width, int height, int num_cells_x, int num_cells_y) {
		super(width, height, num_cells_x, num_cells_y);
	}
	
	private Point getCellCoordinates(int x, int y)
	{
		int cell_size = width / numCellsX;
		x /= cell_size;
		y /= cell_size;
		return new Point(x,y);
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY) {

		Point cell = getCellCoordinates(mouseX,mouseY);
		setCell(cell.x, cell.y, (getCell(cell.x, cell.y) == selectionValue) ? emptyValue : selectionValue);
		
		if(singleSelectionMode)
		{
			Point newPoint = new Point(cell.x,cell.y);
			if(selectedCell.x != -1 &&  selectedCell != newPoint)
			{
				setCell(selectedCell.x, selectedCell.y, emptyValue);
			}
			selectedCell = newPoint;
		}
	}

	@Override
	public void onMouseRightClick(int mouseX, int mouseY) {
		rightSelectedCell = getCellCoordinates(mouseX,mouseY);
	}

	@Override
	public int numNeighbours(int x, int y) {
		int neighbours = 0;

		int left = x - 1;
		int right = x + 1;
		int up = y - 1;
		int down = y + 1;

		if (wrapAround) {

			left = (x > 0) ? x - 1 : numCellsX - 1;
			right = (x < numCellsX - 1) ? x + 1 : 0;
			up = (y > 0) ? y - 1 : numCellsY - 1;
			down = (y < numCellsY - 1) ? y + 1 : 0;

			neighbours += getCell(left, y);
			neighbours += getCell(right, y);
			neighbours += getCell(x, up);
			neighbours += getCell(x, down);
			neighbours += getCell(left, up);
			neighbours += getCell(right, up);
			neighbours += getCell(right, down);
			neighbours += getCell(left, down);

		} else {
			if (left >= 0)
				neighbours += getCell(left, y);
			if (right < numCellsX)
				neighbours += getCell(right, y);
			if (up >= 0)
				neighbours += getCell(x, up);
			if (down < numCellsY)
				neighbours += getCell(x, down);
			if (left >= 0 && up >= 0)
				neighbours += getCell(left, up);
			if (right < numCellsX && up >= 0)
				neighbours += getCell(right, up);
			if (right < numCellsX && down < numCellsY)
				neighbours += getCell(right, down);
			if (left >= 0 && down < numCellsY)
				neighbours += getCell(left, down);
		}

		return neighbours;
	}

	@Override
	public void render(Graphics2D g2d) {
		int size = width / numCellsX;
		for (int y = 0; y < numCellsY; ++y) {
			for (int x = 0; x < numCellsX; ++x) {
				int curr_x = x * size;
				int curr_y = y * size;

				Color fill_color = value_to_color.get(getCell(x, y));
				g2d.setColor(fill_color);
				g2d.fillRect(curr_x, curr_y, size, size);
				g2d.setColor(kDefaultCellOutline);
				g2d.drawRect(curr_x, curr_y, size, size);
			}
		}
		
		for(Point p : highlightedCells)
		{
			int curr_x = p.x * size;
			int curr_y = p.y * size;

			Color fill_color = new Color(255,255,255);
			g2d.setColor(fill_color);
			g2d.fillRect(curr_x, curr_y, size, size);
		}
	}

	@Override
	public void highlightCells(ArrayList<Point> cells) {
		highlightedCells = cells;
	}
}
