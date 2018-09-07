package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Line;

/**
 * This tool is used to draw a {@link Line} geometric object, by 
 * tracking mouse movements and clicks.
 * 
 * @author 0036502252
 *
 */
public class LineTool extends ToolAdapter {
	/**
	 * The foreground color provider.
	 */
	private IColorProvider fgColorProvider;

	/**
	 * The starting point of the line. 
	 */
	private Point start;
	/**
	 * The ending point of the line.
	 */
	private Point end;
	/**
	 * Utility flag - true if the user hasn't performed the first click yet; 
	 * the first click sets starting point in 2D space, where 
	 * the second click sets the ending point, fixating the line.
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
	 * Constructs a new {@link LineTool}. 
	 * @param fgColorProvider the foreground color provider
	 * @param model the model used 
	 * @param canvas the canvas used for drawing
	 */
	public LineTool(IColorProvider fgColorProvider, DrawingModel model, JDrawingCanvas canvas) {
		this.fgColorProvider = fgColorProvider;
		
		this.start = new Point();
		this.end = new Point();
		
		this.model = model;
		this.canvas = canvas;
		
		firstClick = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(firstClick) {
			firstClick = false;
			start.setLocation(e.getX(), e.getY());
		} else {
			firstClick = true;
			end.setLocation(e.getX(), e.getY());
			model.add(new Line(start, end, fgColorProvider.getCurrentColor()));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(firstClick) return;
		end.setLocation(e.getX(), e.getY());
		canvas.repaint();
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		if(!firstClick) {
			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.drawLine(start.x, start.y, end.x, end.y);
		}
	}
}
