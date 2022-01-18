package grid_playground;

import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class RulesPanel extends JPanel {

	protected GridPanel gridPanel;

	public RulesPanel(GridPanel gridPanel) {
		this.gridPanel = gridPanel;
		this.setPreferredSize(new Dimension(180, 200));
	}
	
	public abstract void applyRules();
}
