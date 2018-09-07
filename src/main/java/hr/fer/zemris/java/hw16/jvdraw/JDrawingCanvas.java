package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * Represents the {@link JVDraw}'s drawing area. Uses a {@link Tool} to listen 
 * for mouse actions depending on the chosen geometrical object, and 
 * is connected to its {@link DrawingModel} to draw all of its elements.
 * 
 * @author 0036502252
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener{
	/** Default UID */
	private static final long serialVersionUID = 1L;
	/**
	 * The tool used by the canvas for tracking mouse clicks and movements.
	 */
	private Tool tool;
	/**
	 * The object model which holds all of the components that the 
	 * canvas will then draw.
	 */
	private DrawingModel source;
	/**
	 * The visitor which paints all of the model's objects on this canvas.
	 */
	private GeometricalObjectPainter painter;
	/**
	 * Constructs a new {@link JDrawingCanvas}.
	 * 
	 * @param tool the tool used for painting
	 * @param source the source of {@link GeometricalObject}s
	 */
	public JDrawingCanvas(Tool tool, DrawingModel source) {
		this.tool = tool;
		this.source = source;
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	/**
	 * Sets the tool which will be used by this canvas.
	 * 
	 * @param tool the tool to set
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.source = source;
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.source = source;
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.source = source;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics arg0) {
		Graphics2D g2 = (Graphics2D) arg0;
		g2.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON
		);
		g2.setStroke(new BasicStroke(2));
		
		painter = new GeometricalObjectPainter(g2);
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i = 0, n = source.getSize(); i < n; i++) {
			source.getObject(i).accept(painter);
		}
		
		if(source != null) {
			for(int i = 0; i < source.getSize(); i++) {
				source.getObject(i).accept(new GeometricalObjectPainter(g2));
			}
		}
		
		tool.paint(g2); 
	}
}
