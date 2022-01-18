package grid_playground;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class GameOfLifeRulesPanel extends RulesPanel implements ActionListener {

	private JTextField overpopulationField;
	private JTextField underpopulationField;
	private JTextField reproduceField;

	public GameOfLifeRulesPanel(GridPanel gridPanel) {
		super(gridPanel);

		JLabel underpopulationLabel = new JLabel("Underpopulation (<=):");
		underpopulationLabel.setPreferredSize(new Dimension(125, 25));
		this.add(underpopulationLabel);

		underpopulationField = new JTextField(3);
		underpopulationField.setText(String.valueOf(gridPanel.getRules().underpopulation));
		this.add(underpopulationField);

		JLabel reproduceLabel = new JLabel("Reproduction (==):");
		reproduceLabel.setPreferredSize(new Dimension(125, 25));
		this.add(reproduceLabel);

		reproduceField = new JTextField(3);
		reproduceField.setText(String.valueOf(gridPanel.getRules().reproduction));
		this.add(reproduceField);

		JLabel overpopulationLabel = new JLabel("Overpopulation (>=):");
		overpopulationLabel.setPreferredSize(new Dimension(125, 25));
		this.add(overpopulationLabel);

		overpopulationField = new JTextField(3);
		overpopulationField.setText(String.valueOf(gridPanel.getRules().overpopulation));
		this.add(overpopulationField);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void applyRules() {

		SimulationRules rules = new SimulationRules();
		rules.overpopulation = Integer.parseInt(overpopulationField.getText());
		rules.underpopulation = Integer.parseInt(underpopulationField.getText());
		rules.reproduction = Integer.parseInt(reproduceField.getText());

		gridPanel.setRules(rules);
	}
}
