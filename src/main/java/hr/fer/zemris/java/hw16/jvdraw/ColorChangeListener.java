package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * A listener which is updated every time an {@link IColorProvider} changes
 * its color.
 * 
 * @author 0036502252
 *
 */
public interface ColorChangeListener {
	/**
	 * Called when the {@link IColorProvider} changes its color.
	 * 
	 * @param source the IColorProvider
	 * @param oldColor the old color
	 * @param newColor the new color
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
