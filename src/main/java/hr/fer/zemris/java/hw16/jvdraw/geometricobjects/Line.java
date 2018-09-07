package hr.fer.zemris.java.hw16.jvdraw.geometricobjects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.geoeditors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geoeditors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectVisitor;

/**
 * Models a line in 2D space. Apart from having its start point and end 
 * coordinates, each line also has the color with which it is painted.
 * 
 * @author 0036502252
 *
 */
public class Line extends GeometricalObject {
	/**
	 * The starting point of the line.
	 */
	private Point startPoint;
	/**
	 * The ending point of the line.
	 */
	private Point endPoint;
	/**
	 * The color with which the line is painted.
	 */
	private Color color;
	
	/**
	 * Constructs a new {@link Line}.
	 * 
	 * @param startPoint the start point 
	 * @param endPoint the ending point
	 * @param color the color of the line
	 */
	public Line(Point startPoint, Point endPoint, Color color) {
		super();
		this.startPoint = new Point(startPoint);
		this.endPoint = new Point(endPoint);
		this.color = color;
		
	}

	/**
	 * @param startPoint the startPoint to set
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
		fireObjectChanged();
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		fireObjectChanged();
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
		fireObjectChanged();
	}

	@Override
	public void accept(GeometricalObjectVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the start point
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * @return the end point
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Line (%d,%d)-(%d,%d)", 
				getStartPoint().x,
				getStartPoint().y,
				getEndPoint().x,
				getEndPoint().y
		);
	}
}