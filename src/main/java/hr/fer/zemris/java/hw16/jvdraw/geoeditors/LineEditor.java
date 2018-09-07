package hr.fer.zemris.java.hw16.jvdraw.geoeditors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Line;

/**
 * A {@link JPanel} which allows for editing of {@link Line} objects. 
 * It is directly connected to its corresponding line and can edit its 
 * properties.
 * 
 * @author 0036502252
 *
 */
public class LineEditor extends GeometricalObjectEditor {
	/** Default UID */
	private static final long serialVersionUID = 1L;

	/**
	 * The editor's corresponding line.
	 */
	private Line line;
	
	/**
	 * The starting point's X coordinate.
	 */
	private JTextField startX;
	/**
	 * The ending point's X coordinate.
	 */
	private JTextField endX;
	/**
	 * The starting point's Y coordinate.
	 */
	private JTextField startY;
	/**
	 * The ending point's Y coordinate
	 */
	private JTextField endY;
	
	/**
	 * The red component of the line's color.
	 */
	private JTextField red;
	/**
	 * The green component of the line's color.
	 */
	private JTextField green;
	/**
	 * The blue component of the line's color.
	 */
	private JTextField blue;
		
	/**
	 * Constructs a new {@link LineEditor}.
	 * 
	 * @param line the editor's corresponding line
	 */
	public LineEditor(Line line) {
		this.line = line;
		initGUI();
	}
	
	
	/**
	 * Initializes the editor and its GUI components.
	 */
	private void initGUI() {
		setLayout(new GridLayout(3,1));
		
		initFields();
		addStartPanel();
		addEndPanel();
		addColorPanel();
	}


	/**
	 * Initializes the input fields.
	 */
	private void initFields() {
		startX = new JTextField(String.valueOf(line.getStartPoint().x));
		endX = new JTextField(String.valueOf(line.getEndPoint().x));
		
		startY = new JTextField(String.valueOf(line.getStartPoint().y));
		endY = new JTextField(String.valueOf(line.getEndPoint().y));
		
		red = new JTextField(String.valueOf(line.getColor().getRed()));
		green = new JTextField(String.valueOf(line.getColor().getGreen()));
		blue = new JTextField(String.valueOf(line.getColor().getBlue()));
	}


	/**
	 * Initializes the starting point input segment.
	 */
	private void addStartPanel() {
		JPanel startPanel = new JPanel(new GridLayout(1, 3));
		
		startPanel.add(new JLabel("Start point"));
		startPanel.add(startX);
		startPanel.add(startY);
		
		add(startPanel);
	}


	/**
	 * Initializes the ending point input segment.
	 */
	private void addEndPanel() {
		JPanel endPanel = new JPanel(new GridLayout(1, 3));
		
		endPanel.add(new JLabel("End point"));
		endPanel.add(endX);
		endPanel.add(endY);

		add(endPanel);
	}


	/**
	 * Initializes the line color input segment.
	 */
	private void addColorPanel() {
		JPanel colorPanel = new JPanel(new GridLayout(1, 4));
		
		colorPanel.add(new JLabel("Line color (RGB)"));
		colorPanel.add(red);
		colorPanel.add(green);
		colorPanel.add(blue);
		
		add(colorPanel);
	}


	@Override
	public void checkEditing() throws EditException {
		if(!isValidInput(startX.getText(), POINT_VALUE)) {
			throw new EditException("Starting X coordinate must be a positive integer!");
		}
		
		if(!isValidInput(startY.getText(), POINT_VALUE)) {
			throw new EditException("Starting Y coordinate must be a positive integer!");
		}
		
		if(!isValidInput(endX.getText(), POINT_VALUE)) {
			throw new EditException("Ending X coordinate must be a positive integer!");
		}
		
		if(!isValidInput(endY.getText(), POINT_VALUE)) {
			throw new EditException("Ending Y coordinate must be a positive integer!");
		}
		
		if(!isValidInput(red.getText(), RGB_VALUE)) {
			throw new EditException("R value must be between 0 and 255!");
		}
		if(!isValidInput(green.getText(), RGB_VALUE)) {
			throw new EditException("G value must be between 0 and 255!");
		}
		if(!isValidInput(blue.getText(), RGB_VALUE)) {
			throw new EditException("B value must be between 0 and 255!");
		}
	}
	
	@Override
	public void acceptEditing() {
		line.setColor(new Color(
				Integer.valueOf(red.getText()),
				Integer.valueOf(green.getText()),
				Integer.valueOf(blue.getText())
		));
		
		line.setStartPoint(new Point(
				Integer.valueOf(startX.getText()),
				Integer.valueOf(startY.getText())
		));
		
		line.setEndPoint(new Point(
				Integer.valueOf(endX.getText()),
				Integer.valueOf(endY.getText())
		));
	}
}
