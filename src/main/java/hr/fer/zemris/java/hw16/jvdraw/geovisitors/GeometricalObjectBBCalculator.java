package hr.fer.zemris.java.hw16.jvdraw.geovisitors;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Line;

/**
 * An implementation of the {@link GeometricalObjectVisitor} which 
 * calculates the bounding box for all of the geometrical objects it visits.
 * 
 * @author 0036502252
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/**
	 * The current maximum x-axis value.
	 */
	private int xMax;
	/**
	 * The current maximum y-axis value.
	 */
	private int yMax;
	/**
	 * The current minimum x-axis value.
	 */
	private int xMin;
	/**
	 * The current minimum y-axis value.
	 */
	private int yMin;
	/**
	 * Utility flag which is used for determining the maximum and minimum
	 * 2D space values for each object. Also used for error checking.
	 */
	private boolean firstVisit = true;
	/**
	 * Dummy object used to minimize code duplication between 
	 * visiting {@link Circle} and {@link FilledCircle} objects.
	 */
	private Circle dummy;
	
	/**
	 * Constructs a new {@link GeometricalObjectBBCalculator}.
	 */
	public GeometricalObjectBBCalculator() {
		dummy = new Circle(Color.BLACK, 1, new Point());
	}
	
	@Override
	public void visit(Line line) {
		int xMax = Math.max(line.getStartPoint().x, line.getEndPoint().x);
		int yMax = Math.max(line.getStartPoint().y, line.getEndPoint().y);
		int xMin = Math.min(line.getStartPoint().x, line.getEndPoint().x);
		int yMin = Math.min(line.getStartPoint().y, line.getEndPoint().y);
		
		check(xMax, yMax, xMin, yMin);
	}

	@Override
	public void visit(Circle circle) {
		int xMax = circle.getCenter().x + circle.getRadius();
		int yMax = circle.getCenter().y + circle.getRadius();
		int xMin = circle.getCenter().x - circle.getRadius();
		int yMin = circle.getCenter().y - circle.getRadius();

		check(xMax, yMax, xMin, yMin);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		/*
		 * Since a filled circle and regular circle's bounding box are 
		 * calculated in the exact same way, I use a dummy object, set its
		 * values to the filled circle's values, and just dispatch 
		 * it to the Circle's visit method.
		 */
		dummy.setCenter(filledCircle.getCenter());
		dummy.setRadius(filledCircle.getRadius());
		
		visit(dummy);
	}
	
	/**
	 * Checks if the given values exceed the bounding box's values. 
	 * 
	 * @param xMax the x-axis value to check if it is the maximum one
	 * @param yMax the y-axis value to check if it is the maximum one
	 * @param xMin the x-axis value to check if it is the minimum one
	 * @param yMin the y-axis value to check if it is the minimum one
	 */
	private void check(int xMax, int yMax, int xMin, int yMin) {
		if(firstVisit) {
			firstVisit = false;
			this.xMax = xMax;
			this.yMax = yMax;
			this.xMin = xMin;
			this.yMin = yMin;
		}
		
		if(xMax > this.xMax) this.xMax = xMax;
		if(yMax > this.yMax) this.yMax = yMax;
		if(xMin < this.xMin) this.xMin = xMin;
		if(yMin < this.yMin) this.yMin = yMin;
	
	}
		
	/**
	 * @return the bounding box, represented as a {@link Rectangle} object
	 * @throws IllegalStateException if the no objects were yet visited,
	 * 		or if no models exist
	 */
	public Rectangle getBoundingBox() {
		if(firstVisit) {
			throw new IllegalStateException("Can't calculate bounding box since"
					+ " no graphics objects were visited yet.");
		}
		
		return new Rectangle(xMin, yMin, (xMax - xMin), (yMax - yMin));
	}
}