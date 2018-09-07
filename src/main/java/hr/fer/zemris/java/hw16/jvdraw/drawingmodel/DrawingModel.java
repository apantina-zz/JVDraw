package hr.fer.zemris.java.hw16.jvdraw.drawingmodel;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;

/**
 * A {@link GeometricalObject} storage model. Graphical objects have their
 * defined position and it is expected that image rendering will be created
 * by drawing objects from the starting to the ending index in the 
 * model's underlying data structure.
 * 
 * @author 0036502252
 *
 */
public interface DrawingModel {
	/**
	 * @return the number of {@link GeometricalObject}s in this model
	 */
	int getSize();
	/**
	 * Gets the object from the model at the specified index.
	 * 
	 * @param index the specified index
	 * @return the geometric object for the given index
	 */
	GeometricalObject getObject(int index);
	/**
	 * Adds a geometric object after the last index of the drawing model. 
	 * 
	 * @param object the object to be added to the model
	 */
	void add(GeometricalObject object);
	/**
	 * Adds/attaches a listener to this model.
	 * 
	 * @param l the listener to be attached
	 */
	void addDrawingModelListener(DrawingModelListener l);
	/**
	 * Removes/detaches a listener from this model.
	 * 
	 * @param l the listener to be detached
	 */
	void removeDrawingModelListener(DrawingModelListener l);
	/**
	 * Removes an object from the data model, if it exists.
	 * 
	 * @param object
	 */
	void remove(GeometricalObject object);
	/**
	 * Changes the order of the elements in the list by shifting the 
	 * give object for <code>offset</code> spaces in the model.
	 * 
	 * @param object the object to be moved
	 * @param offset the number of spaces the object will be shifted
	 */
	void changeOrder(GeometricalObject object, int offset);
}
