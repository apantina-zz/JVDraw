package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Used in {@link JVDraw} as part of the State design pattern. 
 * The current state of the drawing program is the current tool used.
 * Each tool responds to mouse movements, and paints something on 
 * a component (in our case on a {@link JDrawingCanvas}). 
 * @author 0036502252
 *
 */
public interface Tool {
	/**
	 * Called when the mouse is pressed.
	 * @param e the mouse event
	 */
	void mousePressed(MouseEvent e);
	/**
	 * Called when the mouse is released after pressing.
	 * @param e the mouse event
	 */
	void mouseReleased(MouseEvent e);
	/**
	 * Called when the mouse is clicked.
	 * @param e the mouse event
	 */
	void mouseClicked(MouseEvent e);
	/**
	 * Called when the mouse is moved.
	 * @param e the mouse event
	 */
	void mouseMoved(MouseEvent e);
	/**
	 * Called when the mouse is dragged.
	 * @param e the mouse event
	 */
	void mouseDragged(MouseEvent e);
	/**
	 * Performs a paint operation using the provided {@link Graphics} strategy.
	 * @param g2d the graphics strategy used for painting
	 * @param e the mouse event
	 */
	void paint(Graphics2D g2d);
}
