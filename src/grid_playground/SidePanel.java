package grid_playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SidePanel extends JPanel implements ActionListener, ChangeListener {

	private GridPanel gridPanel;

	private JComboBox gridType;
	private JComboBox simulationType;

	private final String[] gridTypes = { "Square", "Hexagonal" };
	private final String[] simulationTypes = { "Game of Life", "Langton's ant", "Arrow game" };

	private RulesPanel rulesPanel;
	private JSlider simulationSpeed;
	private JCheckBox wrapAroundCheckbox;
	private JButton startButton;
	private JButton stopButton;
	private JButton clearButton;
	private JButton randomButton;

	private JColorChooser colorChooser;

	public SidePanel(GridPanel gridPanel) {

		this.gridPanel = gridPanel;

		this.setLayout(new FlowLayout());

		JLabel gridTypeLabel = new JLabel("Grid type:");
		gridTypeLabel.setPreferredSize(new Dimension(180, 25));
		this.add(gridTypeLabel);

		gridType = new JComboBox(gridTypes);
		gridType.addActionListener(this);
		gridType.setPreferredSize(new Dimension(180, 25));
		this.add(gridType);

		JLabel simulationTypeLabel = new JLabel("Simulation type:");
		simulationTypeLabel.setPreferredSize(new Dimension(180, 25));
		this.add(simulationTypeLabel);

		simulationType = new JComboBox(simulationTypes);
		simulationType.setPreferredSize(new Dimension(180, 25));
		simulationType.addActionListener(this);
		this.add(simulationType);

		JLabel simulationSpeedLabel = new JLabel("Simulation speed:");
		simulationTypeLabel.setPreferredSize(new Dimension(180, 25));
		this.add(simulationSpeedLabel);

		simulationSpeed = new JSlider(JSlider.HORIZONTAL, 1, 1000, 1000);
		simulationSpeed.setPreferredSize(new Dimension(180, 25));
		simulationSpeed.addChangeListener(this);
		simulationSpeed.setInverted(true);
		this.add(simulationSpeed);

		wrapAroundCheckbox = new JCheckBox("Wrap around");
		wrapAroundCheckbox.setPreferredSize(new Dimension(130, 25));
		wrapAroundCheckbox.addActionListener(this);
		this.add(wrapAroundCheckbox);

		startButton = new JButton();
		startButton.setText("Start");
		startButton.addActionListener(this);

		stopButton = new JButton();
		stopButton.setText("Stop");
		stopButton.addActionListener(this);

		clearButton = new JButton();
		clearButton.setText("Clear");
		clearButton.addActionListener(this);

		randomButton = new JButton();
		randomButton.setText("Initialize random");
		randomButton.addActionListener(this);

		colorChooser = new JColorChooser();

		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(startButton);
		this.add(stopButton);
		this.add(randomButton);
		this.add(clearButton);

		JLabel rulesLabel = new JLabel("Rules:");
		rulesLabel.setPreferredSize(new Dimension(180, 25));
		this.add(rulesLabel);

		rulesPanel = new GameOfLifeRulesPanel(gridPanel);
		this.add(rulesPanel);

		JLabel cellColorLabel = new JLabel("Cell color:");
		cellColorLabel.setPreferredSize(new Dimension(200, 25));
		JLabel borderColorLabel = new JLabel("Border color:");
		borderColorLabel.setPreferredSize(new Dimension(200, 25));
		JLabel filledColorLabel = new JLabel("Filled color:");
		filledColorLabel.setPreferredSize(new Dimension(200, 25));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			rulesPanel.applyRules();
			gridPanel.startSimulation();
		} else if (e.getSource() == stopButton) {
			gridPanel.stopSimulation();
		} else if (e.getSource() == randomButton) {
			gridPanel.initRandom();
		} else if (e.getSource() == clearButton) {
			gridPanel.stopSimulation();
			gridPanel.clear();
		} else if (e.getSource() == gridType) {
			String selection = gridType.getSelectedItem().toString();
			gridPanel.setGridType(selection);
		} else if (e.getSource() == simulationType) {
			String selection = simulationType.getSelectedItem().toString();
			gridPanel.setSimulation(selection);

			this.remove(rulesPanel);
			if (selection == "Game of Life")
				rulesPanel = new GameOfLifeRulesPanel(gridPanel);
			else if(selection == "Langton's ant")
				rulesPanel = new LangtonsAntRulesPanel(gridPanel);
			else if(selection == "Arrow game")
				rulesPanel = new ArrowGamesRulesPanel(gridPanel);
			
			this.add(rulesPanel);
			rulesPanel.revalidate();
			rulesPanel.repaint();

		} else if (e.getSource() == wrapAroundCheckbox) {
			gridPanel.setWrapAround(wrapAroundCheckbox.isSelected());
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (source == simulationSpeed) {
			if (!source.getValueIsAdjusting()) {
				int updateSpeed = (int) source.getValue();
				gridPanel.setUpdateSpeed(updateSpeed);
			}
		}
	}

}
