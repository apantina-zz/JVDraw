package hr.fer.zemris.java.hw16.jvdraw.geoeditors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.FilledCircle;

/**
 * A {@link JPanel} which allows for editing of {@link FilledCircle} objects.
 * It is directly connected to its corresponding filled circle and can edit its 
 * properties.
 * 
 * @author 0036502252
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {
	/** Default UID */
	private static final long serialVersionUID = 1L;

	/**
	 * The editor's corresponding filled circle.
	 */
	private FilledCircle circle;
	
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
	private JTextField redOutline;
	/**
	 * The circle's outline color green component.
	 */
	private JTextField greenOutline;
	/**
	 * The circle's outline color blue component.
	 */
	private JTextField blueOutline;
		
	/**
	 * The circle's fill color red component.
	 */
	private JTextField redFill;
	/**
	 * The circle's fill color green component.
	 */
	private JTextField greenFill;
	/**
	 * The circle's fill color blue component.
	 */
	private JTextField blueFill;

	/**
	 * Constrcuts a new {@link FilledCircleEditor}.
	 * 
	 * @param circle the editor's corresponding circle
	 */
	public FilledCircleEditor(FilledCircle circle) {
		this.circle = circle;
		initGUI();
	}
	
	
	/**
	 * Initializes the editor and its GUI components.
	 */
	private void initGUI() {
		setLayout(new GridLayout(4,1));
		
		initFields();
		
		addCenterPanel();
		addRadiusPanel();
		addColorOutlinePanel();
		addColorFillPanel();
	}


	/**
	 * Initializes the input fields.
	 */
	private void initFields() {
		centerX = new JTextField(String.valueOf(circle.getCenter().x));
		centerY = new JTextField(String.valueOf(circle.getCenter().y));
		
		radius = new JTextField(String.valueOf(circle.getRadius()));
		
		redOutline = new JTextField(String.valueOf(circle.getOutlineColor().getRed()));
		greenOutline = new JTextField(String.valueOf(circle.getOutlineColor().getGreen()));
		blueOutline = new JTextField(String.valueOf(circle.getOutlineColor().getBlue()));
		
		redFill= new JTextField(String.valueOf(circle.getFillColor().getRed()));
		greenFill = new JTextField(String.valueOf(circle.getFillColor().getGreen()));
		blueFill = new JTextField(String.valueOf(circle.getFillColor().getBlue()));
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
	private void addColorOutlinePanel() {
		JPanel colorPanel = new JPanel(new GridLayout(1, 4));
		
		colorPanel.add(new JLabel("Outline color (RGB)"));
		colorPanel.add(redOutline);
		colorPanel.add(greenOutline);
		colorPanel.add(blueOutline);
		
		add(colorPanel);
	}
	
	/**
	 * Initializes the fill color input segment.
	 */
	private void addColorFillPanel() {
		JPanel colorPanel = new JPanel(new GridLayout(1, 4));
		
		colorPanel.add(new JLabel("Fill color (RGB)"));
		colorPanel.add(redFill);
		colorPanel.add(greenFill);
		colorPanel.add(blueFill);
		
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
		
		if(!isValidInput(redOutline.getText(), RGB_VALUE)) {
			throw new EditException("R value must be between 0 and 255!");
		}
		
		if(!isValidInput(greenOutline.getText(), RGB_VALUE)) {
			throw new EditException("G value must be between 0 and 255!");
		}
		
		if(!isValidInput(blueOutline.getText(), RGB_VALUE)) {
			throw new EditException("B value must be between 0 and 255!");
		}
		
		if(!isValidInput(redFill.getText(), RGB_VALUE)) {
			throw new EditException("R value must be between 0 and 255!");
		}
		
		if(!isValidInput(greenFill.getText(), RGB_VALUE)) {
			throw new EditException("G value must be between 0 and 255!");
		}
		
		if(!isValidInput(blueFill.getText(), RGB_VALUE)) {
			throw new EditException("B value must be between 0 and 255!");
		}
		
	}
	
	
	@Override
	public void acceptEditing() {
		circle.setOutlineColor(new Color(
				Integer.valueOf(redOutline.getText()),
				Integer.valueOf(greenOutline.getText()),
				Integer.valueOf(blueOutline.getText())
		));
		
		circle.setFillColor(new Color(
				Integer.valueOf(redFill.getText()),
				Integer.valueOf(greenFill.getText()),
				Integer.valueOf(blueFill.getText())
		));
		
		circle.setCenter(new Point(
				Integer.valueOf(centerX.getText()),
				Integer.valueOf(centerY.getText())
		));
		
		circle.setRadius(Integer.valueOf(radius.getText()));
	}
	
}
