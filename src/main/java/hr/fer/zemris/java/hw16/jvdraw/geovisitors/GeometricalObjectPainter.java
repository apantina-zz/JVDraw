package hr.fer.zemris.java.hw16.jvdraw.geovisitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Line;

/**
 * A {@link GeometricalObjectVisitor} which paints each geometric object 
 * it visits, using the given {@link Graphics2D} strategy.
 * 
 * @author 0036502252
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/**
	 * The given Graphics strategy
	 */
	private Graphics2D g2d;
	
	/**
	 * Constructs a new {@link GeometricalObjectPainter}.
	 * @param g2d the provided Graphics strategy.
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(
				line.getStartPoint().x, 
				line.getStartPoint().y,
				line.getEndPoint().x,
				line.getEndPoint().y	
		);
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getOutlineColor());
		g2d.drawOval(
				circle.getCenter().x - circle.getRadius(),
				circle.getCenter().y - circle.getRadius(),
				2*circle.getRadius(),
				2*circle.getRadius()
		);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getFillColor());
		g2d.fillOval(
				filledCircle.getCenter().x - filledCircle.getRadius(),
				filledCircle.getCenter().y - filledCircle.getRadius(),
				2*filledCircle.getRadius(),
				2*filledCircle.getRadius()
				);

		g2d.setColor(filledCircle.getOutlineColor());
		g2d.drawOval(
				filledCircle.getCenter().x - filledCircle.getRadius(),
				filledCircle.getCenter().y - filledCircle.getRadius(),
				2*filledCircle.getRadius(),
				2*filledCircle.getRadius()
		);
		
	}
}
