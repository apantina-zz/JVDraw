package hr.fer.zemris.java.hw16.jvdraw.geometricobjects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.geoeditors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.geoeditors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectVisitor;

/**
 * Models a circle in 2D space. Each circle has its center point, radius 
 * and outline color. 
 * 
 * @author 0036502252
 *
 */
public class Circle extends GeometricalObject {
	/**
	 * The outline color of the circle.
	 */
	private Color outlineColor;
	/**
	 * The circle's radius.
	 */
	private int radius;
	/**
	 * The center point of the circle.
	 */
	private Point center;
	
	/**
	 * Constructs a new {@link Circle}.
	 * 
	 * @param outlineColor the outline color of the circle
	 * @param radius the radius of the circle
	 * @param center the center point of the circle
	 */
	public Circle(Color outlineColor, int radius, Point center) {
		super();
		this.outlineColor = outlineColor;
		this.radius = radius;
		this.center = new Point(center);
	}

	@Override
	public void accept(GeometricalObjectVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @param outlineColor the outline color to set
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
		fireObjectChanged();
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		fireObjectChanged();
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(Point center) {
		this.center = center;
		fireObjectChanged();
	}

	/**
	 * @return the color
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}
	
	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Circle (%d, %d), %d", 
				getCenter().x,
				getCenter().y,
				getRadius()
		);
	}
}
