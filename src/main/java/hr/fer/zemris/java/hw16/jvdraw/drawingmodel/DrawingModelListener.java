package hr.fer.zemris.java.hw16.jvdraw.drawingmodel;

/**
 * A listener which tracks the changes in a {@link DrawingModel}. 
 * Each time an object in the model is added, removed, or changed,
 * the listener will be notified.
 * 
 * @author 0036502252
 *
 */
public interface DrawingModelListener {
	/**
     * Sent after the indices in the index0,index1
     * interval have been inserted in the drawing model.
     * The new interval includes both index0 and index1.
	 * 
	 * @param source the drawing model being tracked
	 * @param index0 the lower bound of the interval
	 * @param index1 the upper bound of the interval
	 */
	void objectsAdded(DrawingModel source, int index0, int index1);
	/**
     * Sent after the indices in the index0,index1
     * interval have been removed from the drawing model.
     * The new interval includes both index0 and index1.
	 * 
	 * @param source the drawing model being tracked
	 * @param index0 the lower bound of the interval
	 * @param index1 the upper bound of the interval
	 */
	void objectsRemoved(DrawingModel source, int index0, int index1);
    /**
     * Sent when the contents of the list has changed in a way
     * that's too complex to characterize with the previous
     * methods. For example, this is sent when an item has been
     * replaced. Index0 and index1 bracket the change.
     *
	 * @param source the drawing model being tracked
	 * @param index0 the lower bound of the interval
	 * @param index1 the upper bound of the interval
     */
	void objectsChanged(DrawingModel source, int index0, int index1);
}
