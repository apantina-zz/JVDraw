package hr.fer.zemris.java.hw16.jvdraw.geovisitors;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Line;

/**
 * A {@link GeometricalObjectVisitor} implementation which generates a string 
 * representation of all objects by visiting each geometric object in 
 * a drawing model which stores the elements.
 * 
 * @author 0036502252
 *
 */
public class GeometricalObjectLoader implements GeometricalObjectVisitor {
	/**
	 * The generated text is stored in this list.
	 */
	private List<String> text;
	/**
	 * The model which stores all of the elements.
	 */
	private DrawingModel model;
	
	/**
	 * Constructs a new {@link GeometricalObjectLoader}.
	 * 
	 * @param model the model used for storage
	 */
	public GeometricalObjectLoader(DrawingModel model) {
		this.model = model;
		text = new ArrayList<>();
	}
	
	@Override
	public void visit(Line line) {
		text.add(
				"LINE " + line.getStartPoint().x 
				+ " " + line.getStartPoint().y
 				+ " " + line.getEndPoint().x 
 				+ " " + line.getEndPoint().y 
 				+ " " + line.getColor().getRed() 
 				+ " " + line.getColor().getGreen()
 				+ " " + line.getColor().getBlue()
		);
	}

	@Override
	public void visit(Circle circle) {
		text.add(
				 "CIRCLE " + circle.getCenter().x 
				 + " " + circle.getCenter().y 
				 + " " + circle.getRadius() + " "
				 + " " + circle.getOutlineColor().getRed()
				 + " " + circle.getOutlineColor().getGreen()
				 + " " + circle.getOutlineColor().getBlue()
		);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		text.add(
				 "FCIRCLE " + filledCircle.getCenter().x 
				 + " "  + filledCircle.getCenter().y  
				 + " " + filledCircle.getRadius()
				 + " " + filledCircle.getOutlineColor().getRed()
				 + " " + filledCircle.getOutlineColor().getGreen()
				 + " " + filledCircle.getOutlineColor().getBlue()
				 + " " + filledCircle.getFillColor().getRed()
				 + " " + filledCircle.getFillColor().getGreen()
				 + " " + filledCircle.getFillColor().getBlue()
		);
	}
	
	/**
	 * @return the string representation of all the objects in the model
	 */
	public List<String> getStringRepresentation() {
		for(int i = 0, n = model.getSize(); i < n; i++) {
			model.getObject(i).accept(this);
		}
		
		return text;
	}
}