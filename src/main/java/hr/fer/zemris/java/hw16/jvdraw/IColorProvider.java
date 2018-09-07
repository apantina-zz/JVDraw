package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Represents a color provider used by the {@link JVDraw}. Each color provider
 * holds its current color, and allows for listener registration and 
 * deregistration.
 * 
 * @author 0036502252
 *
 */
public interface IColorProvider {
	/**
	 * @return the provider's current color
	 */
	Color getCurrentColor();
	/**
	 * Adds a {@link ColorChangeListener} to the provider.
	 * 
	 * @param l the listener to be added.
	 */
	void addColorChangeListener(ColorChangeListener l);
	/**
	 * Removes a {@link ColorChangeListener} from the provider.
	 * 
	 * @param l the listener to be removed.
	 */
	void removeColorChangeListener(ColorChangeListener l);
}
