
package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Circle;

/**
 * This tool is used to draw a {@link Circle} geometric object by
 * tracking mouse movements and clicks.
 * 
 * @author 0036502252
 *
 */
public class CircleTool extends ToolAdapter {
	/**
	 * The foreground color provider.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * The center point of the circle.
	 */
	private Point center;
	/**
	 * The radius of the circle.
	 */
	private int radius;
	/**
	 * Utility flag - true if the user hasn't performed the first click yet; 
	 * the first click sets center point in 2D space, where 
	 * the second indicates the radius depending on how far away it is 
	 * from the center, fixating the circle.
	 */
	private boolean firstClick;
	/**
	 * The drawing model used for storing and displaying 
	 * geometric objects as graphical elements.
	 */
	private DrawingModel model;
	/**
	 * The canvas used for drawing on the {@link JVDraw} application.
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * Constructs a new {@link CircleTool}.
	 * @param fgColorProvider the foreground color provider.
	 * @param model the model used
	 * @param canvas the canvas used for drawing
	 */
	public CircleTool(IColorProvider fgColorProvider, DrawingModel model, JDrawingCanvas canvas) {
		this.fgColorProvider = fgColorProvider;
		this.model = model;
		firstClick = true;
		this.canvas = canvas;

		center = new Point();
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
		if(firstClick) {
			firstClick = false;
			center.setLocation(e.getX(), e.getY());
		} else {
			firstClick = true;
			double distance = Point.distance(center.x, center.y, e.getX(), e.getY());
			radius = (int) Math.round(distance);
			model.add(new Circle(fgColorProvider.getCurrentColor(), radius, center));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(firstClick) return;
		radius = Double.valueOf(Point.distance(
						center.getX(),
						center.getY(),
						e.getX(),
						e.getY())
				).intValue();
		canvas.repaint();
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(!firstClick) {			
			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.drawOval(center.x - radius, center.y - radius , 2*radius, 2*radius);
		}
	}

}
