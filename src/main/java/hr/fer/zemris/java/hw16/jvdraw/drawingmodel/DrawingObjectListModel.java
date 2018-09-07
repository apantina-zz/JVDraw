package hr.fer.zemris.java.hw16.jvdraw.drawingmodel;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;

/**
 * An object adapter for the {@link DrawingModel} list model used by 
 * the {@link JVDraw} application for storing geometric objects.
 * 
 * @author 0036502252
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {
	/**Default UID*/
	private static final long serialVersionUID = 1L;
	/**
	 * The drawing model being adapted.
	 */
	private DrawingModel model;
	
	/**
	 * Constructs a new {@link DrawingObjectListModel}.
	 * 
	 * @param model the model being adapted
	 */
	public DrawingObjectListModel(DrawingModel model) {
		model.addDrawingModelListener(this);
		this.model = model;
	}
	
	@Override
	public GeometricalObject getElementAt(int arg0) {
		return model.getObject(arg0);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	} 
	
	/**
	 * @return this adapter's <i>adaptee</i>, or model
	 */
	public DrawingModel getModel() {
		return model;
	}
}
