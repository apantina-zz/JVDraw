package hr.fer.zemris.java.hw16.jvdraw.geometricobjects;

/**
 * Models a listener which is notified every time a {@link GeometricalObject}'s 
 * property is changed. Geometrical objects will notify each of their 
 * listener when their own property changes. 
 * 
 * @author 0036502252
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Called each time the object being listened has its property changed
	 * (and subsequently notifies its listeners).
	 * 
	 * @param o
	 */
	void geometricalObjectChanged(GeometricalObject o);
}
