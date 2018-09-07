package hr.fer.zemris.java.hw16.jvdraw.geoeditors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Circle;

/**
 * A {@link JPanel} which allows for editing of {@link Circle} objects.
 * It is directly connected to its corresponding circle and can edit its 
 * properties.
 * 
 * @author 0036502252
 *
 */
public class CircleEditor extends GeometricalObjectEditor {
	/** Default UID */
	private static final long serialVersionUID = 1L;

	/**
	 * The editor's corresponding filled circle.
	 */
	private Circle circle;

	/**
	 * The circle's center X coordinate.
	 */
	private JTextField centerX;
	/**
	 * The circle's center Y coordinate.
	 */
	private JTextField centerY;
	
	/**
	 * The circle's radius.
	 */
	private JTextField radius;
	
	/**
	 * The circle's outline color red component.
	 */
	private JTextField red;
	/**
	 * The circle's outline color green component.
	 */
	private JTextField green;
	/**
	 * The circle's outline color blue component.
	 */
	private JTextField blue;
		
	/**
	 * Constrcuts a new {@link CircleEditor}.
	 * 
	 * @param circle the editor's corresponding circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		initGUI();
	}
	
	
	/**
	 * Initializes the editor and its GUI components.
	 */
	private void initGUI() {
		setLayout(new GridLayout(3,1));
		
		initFields();
		addCenterPanel();
		addRadiusPanel();
		addColorPanel();
	}


	/**
	 * Initializes the input fields.
	 */
	private void initFields() {
		centerX = new JTextField(String.valueOf(circle.getCenter().x));
		centerY = new JTextField(String.valueOf(circle.getCenter().y));
		
		radius = new JTextField(String.valueOf(circle.getRadius()));
		
		red = new JTextField(String.valueOf(circle.getOutlineColor().getRed()));
		green = new JTextField(String.valueOf(circle.getOutlineColor().getGreen()));
		blue = new JTextField(String.valueOf(circle.getOutlineColor().getBlue()));
		
	}


	/**
	 * Initializes the center point input segment.
	 */
	private void addCenterPanel() {
		JPanel centerPanel = new JPanel(new GridLayout(1, 3));
		
		centerPanel.add(new JLabel("Center point"));
		centerPanel.add(centerX);
		centerPanel.add(centerY);
		
		add(centerPanel);
	}


	/**
	 * Initializes the circle radius input segment.
	 */
	private void addRadiusPanel() {
		JPanel radiusPanel = new JPanel(new GridLayout(1, 2));
		
		radiusPanel.add(new JLabel("Radius"));
		radiusPanel.add(radius);

		add(radiusPanel);
	}

	/**
	 * Initializes the outline color input segment.
	 */
	private void addColorPanel() {
		JPanel colorPanel = new JPanel(new GridLayout(1, 4));
		
		colorPanel.add(new JLabel("Outline color (RGB)"));
		colorPanel.add(red);
		colorPanel.add(green);
		colorPanel.add(blue);
		
		add(colorPanel);
	}


	@Override
	public void checkEditing() throws EditException {
		if(!isValidInput(centerX.getText(), POINT_VALUE)) {
			throw new EditException("Starting X coordinate must be a positive integer!");
		}
		
		if(!isValidInput(centerY.getText(), POINT_VALUE)) {
			throw new EditException("Starting Y coordinate must be a positive integer!");
		}
		
		if(!isValidInput(radius.getText(), POINT_VALUE)) {
			throw new EditException("Radius must be a positive integer!");
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
		circle.setOutlineColor(new Color(
				Integer.valueOf(red.getText()),
				Integer.valueOf(green.getText()),
				Integer.valueOf(blue.getText())
		));
		
		circle.setCenter(new Point(
				Integer.valueOf(centerX.getText()),
				Integer.valueOf(centerY.getText())
		));
		
		circle.setRadius(Integer.valueOf(radius.getText()));
		
	}
}
