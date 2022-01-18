package grid_playground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;

public class HexagonalGrid extends Grid {

	public HexagonalGrid(int width, int height, int num_cells_x, int num_cells_y) {
		super(width, height, num_cells_x, num_cells_y);
	}
	
	private Point getCellCoordinates(int x, int y)
	{
		int cell_size = width / numCellsX;
		int w = 2 * cell_size;
		int h = (int) (Math.sqrt(3) * cell_size);

		float min = Float.POSITIVE_INFINITY;
		Point closest = new Point(-1, -1);

		// TODO: You don't have to check all hexagons.
		for (int i = 0; i < numCellsY; ++i) {
			for (int j = 0; j < numCellsX; ++j) {
				int curr_x = leftPadding + j * (w * 3 / 4);
				int curr_y = topPadding + i * h;
				if ((j + 1) % 2 == 0) {
					curr_y += h / 2;
				}

				float dx = curr_x - j;
				float dy = curr_y - i;

				float distance = (float) Math.sqrt(dx * dx + dy * dy);

				if (distance < min) {
					min = distance;
					closest.x = j;
					closest.y = i;
				}
			}
		}
		
		return closest;
	}

	public void onMouseClick(int mouseX, int mouseY) {
		
		Point cell = getCellCoordinates(mouseX,mouseY);
		setCell(cell.x, cell.y, (getCell(cell.x, cell.y) == selectionValue) ? emptyValue : selectionValue);
		if(singleSelectionMode)
		{
			if(selectedCell.x != -1 &&  selectedCell != cell)
			{
				setCell(selectedCell.x, selectedCell.y, emptyValue);
			}
			selectedCell = cell;
		}
		
	}

	@Override
	public void onMouseRightClick(int mouseX, int mouseY) {
		rightSelectedCell = getCellCoordinates(mouseX,mouseY);
	}

	// Returns the number of neighbours for a given cell.
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

			if ((x + 1) % 2 == 0) {
				neighbours += getCell(right, down);
				neighbours += getCell(left, down);
			} else {
				neighbours += getCell(left, up);
				neighbours += getCell(right, up);
			}

		} else {
			if (left >= 0)
				neighbours += getCell(left, y);
			if (right < numCellsX)
				neighbours += getCell(right, y);
			if (up >= 0)
				neighbours += getCell(x, up);
			if (down < numCellsY)
				neighbours += getCell(x, down);

			if ((x + 1) % 2 == 0) {
				if (right < numCellsX && down < numCellsY)
					neighbours += getCell(right, down);
				if (left >= 0 && down < numCellsY)
					neighbours += getCell(left, down);
			} else {
				if (left >= 0 && up >= 0)
					neighbours += getCell(left, up);
				if (right < numCellsX && up >= 0)
					neighbours += getCell(right, up);
			}
		}

		return neighbours;
	}

	private void drawHexagon(Graphics2D g2d, Point center, int size, Color fill_color, Color outline_color) {
		int[] x_points = new int[6];
		int[] y_points = new int[6];

		for (int i = 0; i < 6; ++i) {
			int angle_deg = 60 * i;
			float angle_rad = (float) (Math.PI / 180 * angle_deg);
			x_points[i] = (int) (center.x + size * Math.cos(angle_rad));
			y_points[i] = (int) (center.y + size * Math.sin(angle_rad));
		}

		Polygon polygon = new Polygon(x_points, y_points, 6);

		g2d.setColor(fill_color);
		g2d.fillPolygon(polygon);
		g2d.setColor(outline_color);
		g2d.drawPolygon(polygon);
	}

	@Override
	public void render(Graphics2D g2d) {
		int cell_size = width / numCellsX;
		int w = 2 * cell_size;
		int h = (int) (Math.sqrt(3) * cell_size);

		for (int y = 0; y < numCellsY; ++y) {
			for (int x = 0; x < numCellsX; ++x) {
				int curr_x = leftPadding + x * (w * 3 / 4);
				int curr_y = topPadding + y * h;
				if ((x + 1) % 2 == 0) {
					curr_y += h / 2;
				}

				Color fill_color = value_to_color.get(getCell(x, y));
				drawHexagon(g2d, new Point(curr_x, curr_y), cell_size, fill_color, kDefaultCellOutline);
			}
		}
	}

	@Override
	public void highlightCells(ArrayList<Point> cells) {
		// TODO Auto-generated method stub
		
	}
}
