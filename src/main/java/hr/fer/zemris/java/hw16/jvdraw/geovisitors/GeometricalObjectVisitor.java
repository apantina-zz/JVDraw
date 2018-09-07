package hr.fer.zemris.java.hw16.jvdraw.geovisitors;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Line;

/**
 * Models a basic visitor which can visit all defined types of 
 * {@link GeometricalObject}s and perform some operation on them. 
 * Using a visitor, all of the geometric objects can be visited using
 * a single loop, but each specific object can be treated differently.
 * 
 * @author 0036502252
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Visits a {@link Line}.
	 * @param line the object to be visited
	 */
	void visit(Line line);
	/**
	 * Visits a {@link Circle}.
	 * @param circle the object to be visited
	 */
	void visit(Circle circle);
	/**
	 * Visits a {@link FilledCircle}.
	 * @param filledCircle the object to be visited
	 */
	void visit(FilledCircle filledCircle);
}
