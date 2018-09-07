package hr.fer.zemris.java.hw16.jvdraw.drawingmodel;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObjectListener;

/**
 * A concrete implementation of the {@link DrawingModel}. Listens 
 * for changes in {@link GeometricalObject}s and stores them in its list.
 * 
 * @author 0036502252
 *
 */
public class DrawingModelImplementation implements DrawingModel, GeometricalObjectListener{
	/**
	 * The model's list of {@link GeometricalObject}s.
	 */
	private List<GeometricalObject> objects;
	/**
	 * The list of this model's listeners.
	 */
	private List<DrawingModelListener> listeners;
	
	/**
	 * 	Constructs a new {@link DrawingModelImplementation}.
	 */
	public DrawingModelImplementation() {
		this.objects = new ArrayList<>();
		this.listeners = new ArrayList<>();
	}
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		object.addGeometricalObjectListener(this);
		objects.add(object);
		int index = getSize() - 1;
		
		listeners.forEach(a->a.objectsAdded(this, index, index));

	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o);
		listeners.forEach(a->a.objectsChanged(this, index, index));
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		if(index != -1) {
			objects.remove(object);
			listeners.forEach(a->a.objectsRemoved(this, index, index));
		}
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		swap(index, index + offset);
		listeners.forEach(a->a.objectsChanged(this, index, index));
	}
	
	/**
	 * Utility method used when changing the order of list elements.
	 * Swaps two elements using their corresponding indices.
	 * 
	 * @param index1 the index of the first element being swapped
	 * @param index2 the index of the second element being swapped
	 */
	private void swap(int index1, int index2) {
		try {
			GeometricalObject obj1 = getObject(index1);
			GeometricalObject obj2 = getObject(index2);
			
			objects.set(index2, obj1);
			objects.set(index1, obj2);
		} catch(IndexOutOfBoundsException ignorable) {
		}
	}
}
