package hr.fer.zemris.java.hw16.jvdraw.geometricobjects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.geoeditors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectVisitor;

/**
 * Models an abstract geometrical object in 2D space. 
 * Each geometrical objects has its listeners that it needs to modify
 * each time its values are changed.
 * 
 * @author 0036502252
 *
 */
public abstract class GeometricalObject {
	/**
	 * The object's list of listeners.
	 */
	protected List<GeometricalObjectListener> listeners;

	/**
	 * 
	 */
	public GeometricalObject() {
		listeners = new ArrayList<>();
	}

	/**
	 * This method allows the object to accept any of its visitors. 
	 * 
	 * @param visitor the visitor which visits this object
	 */
	public abstract void accept(GeometricalObjectVisitor visitor);

	/**
	 * @return the object's own editor, which allows for checking and then 
	 * editing the object's values remotely using a {@link JPanel}.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Notifies all of the object's listeners about its state change.
	 */
	public void fireObjectChanged() {
		listeners.forEach(a -> a.geometricalObjectChanged(this));
	}

	/**
	 * Attaches a listener to the object by adding the listener to its list. 
	 * 
	 * @param l the listener to be added/attached to the object
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Detaches a listener from the object by removing the listener from this
	 * list.
	 * 
	 * @param l the listener to be detached from the object
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
}