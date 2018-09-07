package hr.fer.zemris.java.hw16.jvdraw.geoeditors;

import java.util.function.Predicate;

import javax.swing.JPanel;

/**
 * A {@link JPanel} which allows for editing geometrical objects. 
 * Each GeometricalObjectEditor is directly connected to their 
 * {@link GeometricalObject} and can edit its properties.
 * 
 * @author 0036502252
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/** Default UID */
	private static final long serialVersionUID = 1L;
	/**
	 * Used by editors to check if RGB values are valid.
	 */
	protected final Predicate<Integer> RGB_VALUE = val -> val >= 0 && val <= 255;
	/**
	 * Used by editors to check if point coordinates are valid.
	 */
	protected final Predicate<Integer> POINT_VALUE = val -> val >= 0;

	/**
	 * Checks the editing panels for valid input values.
	 * 
	 * @throws EditException if any of the values are invalid
	 */
	public abstract void checkEditing() throws EditException;
	/**
	 * Applies the user input values after validating them.
	 */
	public abstract void acceptEditing();
	
	/**
	 * Checks if the user input is valid using the given predicate.
	 * 
	 * @param input the user input
	 * @param predicate the {@link Predicate} used to check the input
	 * @return true if the input is valid, false otherwise
	 */
	protected boolean isValidInput(String input, Predicate<Integer> predicate) {
		int val;
		try {
			val = Integer.parseInt(input);
		} catch(NumberFormatException ex) {
			return false;
		}
		return predicate.test(val);
	}
}
