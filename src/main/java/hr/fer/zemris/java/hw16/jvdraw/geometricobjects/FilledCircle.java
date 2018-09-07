package hr.fer.zemris.java.hw16.jvdraw.geometricobjects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.geoeditors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.geoeditors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectVisitor;

/**
 * Models a circle in 2D space. Each filled circle has its center point, 
 * radius and outline color, similarly to the {@link Circle}. FilledCircle 
 * models also have a fill, which can be a separate color.
 * 
 * @author 0036502252
 *
 */
public class FilledCircle extends Circle {
	/**
	 * The fill color of the circle. 
	 */
	private Color fillColor;

	/**
	 * Constructs a new {@link FilledCircle}.
	 * 
	 * @param fillColor the fill color of the circle
	 * @param outlineColor the outline color of the circle
	 * @param radius the radius of the circle
	 * @param center the center of the circle
	 */
	public FilledCircle(Color fillColor, Color outlineColor, int radius, Point center) {
		super(outlineColor, radius, center);
		this.fillColor = fillColor;	
	}
	
	@Override
	public void accept(GeometricalObjectVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @param fillColor the fill color to set
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		fireObjectChanged();
	}

	/**
	 * @return the fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
	@Override
	public String toString() {
		return String.format("Filled circle (%d, %d), %d, #%02X%02X%02X", 
				getCenter().x,
				getCenter().y,
				getRadius(),
				getFillColor().getRed(),
				getFillColor().getGreen(),
				getFillColor().getBlue()
		);
	}
}