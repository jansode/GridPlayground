package grid_playground;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class GridFrame extends JFrame {

	private GridPanel gridPanel;
	private SidePanel sidePanel;


	public GridFrame() {
		this.setTitle("Grid playground");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		
		gridPanel = new GridPanel();
		this.add(gridPanel);
		
		sidePanel = new SidePanel(gridPanel);
		sidePanel.setPreferredSize(new Dimension(200, 200));
		this.add(sidePanel, BorderLayout.WEST);

		this.setVisible(true);

	}
}
