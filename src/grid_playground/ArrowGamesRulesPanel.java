package grid_playground;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

import grid_playground.SimulationRules.Direction;

public class ArrowGamesRulesPanel extends RulesPanel {

	private JTextField northField;
	private JTextField eastField;
	private JTextField southField;
	private JTextField westField;

	private HashMap<Character, Direction> char_to_dir;
	private HashMap<Direction, Character> dir_to_char;

	public ArrowGamesRulesPanel(GridPanel gridPanel) {
		super(gridPanel);

		char_to_dir = new HashMap<Character, Direction>();
		char_to_dir.put('N', Direction.NORTH);
		char_to_dir.put('E', Direction.EAST);
		char_to_dir.put('S', Direction.SOUTH);
		char_to_dir.put('W', Direction.WEST);

		dir_to_char = new HashMap<Direction, Character>();
		dir_to_char.put(Direction.NORTH, 'N');
		dir_to_char.put(Direction.EAST, 'E');
		dir_to_char.put(Direction.SOUTH, 'S');
		dir_to_char.put(Direction.WEST, 'W');

		JLabel northLabel = new JLabel("North changes to: ");
		northLabel.setPreferredSize(new Dimension(125, 25));
		this.add(northLabel);

		northField = new JTextField(3);
		northField.setText(String.valueOf(dir_to_char.get(gridPanel.getRules().northGoes)));
		this.add(northField);

		JLabel eastLabel = new JLabel("East changes to: ");
		eastLabel.setPreferredSize(new Dimension(125, 25));
		this.add(eastLabel);

		eastField = new JTextField(3);
		eastField.setText(String.valueOf(dir_to_char.get(gridPanel.getRules().eastGoes)));
		this.add(eastField);

		JLabel southLabel = new JLabel("South changes to: ");
		southLabel.setPreferredSize(new Dimension(125, 25));
		this.add(southLabel);

		southField = new JTextField(3);
		southField.setText(String.valueOf(dir_to_char.get(gridPanel.getRules().southGoes)));
		this.add(southField);

		JLabel westLabel = new JLabel("West changes to: ");
		westLabel.setPreferredSize(new Dimension(125, 25));
		this.add(westLabel);

		westField = new JTextField(3);
		westField.setText(String.valueOf(dir_to_char.get(gridPanel.getRules().westGoes)));
		this.add(westField);
	}

	@Override
	public void applyRules() {
		SimulationRules rules = new SimulationRules();
		rules.northGoes = char_to_dir.get(northField.getText().toUpperCase().charAt(0));
		rules.eastGoes = char_to_dir.get(eastField.getText().toUpperCase().charAt(0));
		rules.southGoes = char_to_dir.get(southField.getText().toUpperCase().charAt(0));
		rules.westGoes = char_to_dir.get(westField.getText().toUpperCase().charAt(0));
		gridPanel.setRules(rules);
	}
}
