package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectLoader;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectPainter;

/**
 * Contains various utility methods used by the {@link JVDraw} application.
 * 
 * @author 0036502252
 *
 */
public class Util {

	/**
	 * Indicates a {@link Line} in the canvas' .jvd file representation.
	 */
	private static final String LINE = "LINE";
	/**
	 * Indicates a {@link Circle} in the canvas' .jvd file representation.
	 */	
	private static final Object CIRCLE = "CIRCLE";
	/**
	 * Indicates a {@link FilledCircle} in the canvas' .jvd file representation.
	 */
	private static final Object FCIRCLE = "FCIRCLE";
	
	/**
	 * Number of parameters in a {@link Line}'s .jvd representation.
	 */
	private static final int LINE_PARAM_COUNT = 8;
	/**
	 * Number of parameters in a {@link Circle}'s .jvd representation.
	 */
	private static final int CIRCLE_PARAM_COUNT = 7;
	/**
	 * Number of parameters in a {@link FilledCircle}'s .jvd representation.
	 */
	private static final int FCIRCLE_PARAM_COUNT = 10;
	/**
	 * Utility list used between methods when loading objects from 
	 * a .jvd file.
	 */
	private static List<GeometricalObject> objects;
	
	/**
	 * Loads geometric objects from a .jvd file.
	 * 
	 * @param path the path from which the objects will be loaded
	 * @return the list of loaded geometric objects 
	 */
	public static List<GeometricalObject> loadFromFile(Path path) {
		List<String> lines;
		try {
			lines = Files.readAllLines(path);
		} catch(IOException ex) {
			return null;
		}
		
		objects = new ArrayList<>();
		
		for(String line : lines) {
			String[] params = line.split("\\s+");
			if(params[0].equals(LINE)) {
				parseLine(params);
			} else if(params[0].equals(CIRCLE)) {
				parseCircle(params);
			} else if(params[0].equals(FCIRCLE)) {
				parseFilledCircle(params);
			}
		}
		
		return objects;
	}

	/**
	 * Parses a circle .jvd representation and creates the corresponding 
	 * {@link Circle} object.
	 * 
	 * @param strParams the parameters from the loaded .jvd file
	 */
	private static void parseCircle(String[] strParams) {
		if(strParams.length != CIRCLE_PARAM_COUNT) return;

		int[] params = new int[CIRCLE_PARAM_COUNT - 1];
		//center
		params[0] = parseInt(strParams[1]);
		params[1] = parseInt(strParams[2]);
		//radius
		params[2] = parseInt(strParams[3]);
		//outline
		params[3] = parseRGB(strParams[4]);
		params[4] = parseRGB(strParams[5]);
		params[5] = parseRGB(strParams[6]);
		if(validParams(params)) {
			objects.add(new Circle(
					new Color(params[3], params[4], params[5]),
					params[2],
					new Point(params[0], params[1])
			));
		}		
	}
	
	/**
	 * Parses a circle .jvd representation and creates the corresponding 
	 * {@link Line} object.
	 * 
	 * @param strParams the parameters from the loaded .jvd file
	 */
	private static void parseLine(String[] strParams) {
		if(strParams.length != LINE_PARAM_COUNT) return;
		int[] params = new int[LINE_PARAM_COUNT - 1];
		params[0] = parseInt(strParams[1]);
		params[1] = parseInt(strParams[2]);
		params[2] = parseInt(strParams[3]);
		params[3] = parseInt(strParams[4]);
		
		params[4] = parseRGB(strParams[5]);
		params[5] = parseRGB(strParams[6]);
		params[6] = parseRGB(strParams[7]);
		
		if(validParams(params)) {
			objects.add(new Line(
					new Point(params[0], params[1]),
					new Point(params[2], params[3]),
					new Color(params[4], params[5], params[6])
			));
		}
	}

	/**
	 * Parses a circle .jvd representation and creates the corresponding 
	 * {@link FilledCircle} object.
	 * 
	 * @param strParams the parameters from the loaded .jvd file
	 */
	private static void parseFilledCircle(String[] strParams) {
		if(strParams.length != FCIRCLE_PARAM_COUNT) return;

		int[] params = new int[FCIRCLE_PARAM_COUNT - 1];
		//center
		params[0] = parseInt(strParams[1]);
		params[1] = parseInt(strParams[2]);
		//radius
		params[2] = parseInt(strParams[3]);
		//outline
		params[3] = parseRGB(strParams[4]);
		params[4] = parseRGB(strParams[5]);
		params[5] = parseRGB(strParams[6]);
		//fill
		params[6] = parseRGB(strParams[7]);
		params[7] = parseRGB(strParams[8]);
		params[8] = parseRGB(strParams[9]);

		if(validParams(params)) {
			objects.add(new FilledCircle(
					new Color(params[6], params[7], params[8]),
					new Color(params[3], params[4], params[5]),
					params[2],
					new Point(params[0], params[1])
			));
		}
	}
	
	/**
	 * Checks if the parameters are valid.
	 * 
	 * @param params the provided parameters
	 * @return true if the parameters are valid
	 */
	private static boolean validParams(int[] params) {
		for(int i = 0; i < params.length; i++) {
			if(params[i] == -1) return false;
		}
		
		return true;
	}

	/**
	 * Parses a string representing an integer, which 
	 * models a point coordinate or radius in a .jvd file. Therefore
	 * the parameter is considered valid only if it represents a 
	 * positive integer.
	 * 
	 * @param str the input to be parsed
	 * @return the parsed value, or -1 if the value is invalid
	 */
	private static int parseInt(String str) {
		int value;
		try {
			value = Integer.valueOf(str);
		} catch(NumberFormatException ex) {
			return -1;
		}
		return value >= 0 ? value : -1;
	}

	/**
	 * Parses a string representing an integer, which 
	 * models an RGB component in a .jvd file. Therefore
	 * the parameter is considered valid only if it represents a 
	 * positive integer between 0 and 255.
	 * 
	 * @param str the input to be parsed
	 * @return the parsed value, or -1 if the value is invalid
	 */
	private static int parseRGB(String str) {
		int value;
		try {
			value = Integer.valueOf(str);
		} catch(NumberFormatException ex) {
			return -1;
		}
		return value >= 0 && value <= 255 ? value : -1;
	}

	/**
	 * Sends an error to the user in form of a {@link JOptionPane}.
	 * 
	 * @param message the error message
	 */
	public static void sendError(String message) {
		JOptionPane.showMessageDialog(
				null, 
				message, 
				"Error", 
				JOptionPane.ERROR_MESSAGE
		);
	}
	
	/**
	 * Sends an informational message to the user in form of a {@link JOptionPane}.
	 * 
	 * @param message the error message
	 */
	public static void sendInfo(String message) {
		JOptionPane.showMessageDialog(
				null, 
				message, 
				"Info", 
				JOptionPane.INFORMATION_MESSAGE
		);
	}

	
	/**
	 * Clears the {@link DrawingModel} of all its elements.
	 * 
	 * @param model the model to be cleared
	 */
	public static void clearModel(DrawingModel model) {
		while(model.getSize() != 0) {
			model.remove(model.getObject(0));
		}
	}

	/**
	 * Saves a drawing model's contents to a .jvd file.
	 * 
	 * @param Path the path of the resulting .jvd file.
	 * @param model the model to be saved
	 */
	public static void saveToFile(Path path, DrawingModel model) {
		List<String> text = new GeometricalObjectLoader(model).getStringRepresentation();
		try {
			Files.write(path, text);	
			sendInfo("Saved file " + path.getFileName().toString());
		} catch(IOException ex) {
			Util.sendError("Could not save file.");
		}
	}

	/**
	 * Gets the extension from a file.
	 * 
	 * @param file the file 
	 * @return the file's extension
	 */
	public static String getExtension(File file) {
		String extension = "";
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
	
		if (i > p) {
			extension = fileName.substring(i+1);
		}
		return extension;
	}
	
	/**
	 * Gets the extension from a file.
	 * 
	 * @param path the file 
	 * @return the file's extension
	 */
	public static String getExtension(Path path) {
		return getExtension(path.toFile());
	}

	/**
	 * Creates a {@link BufferedImage} using the drawing model and bounding 
	 * box.
	 * 
	 * @param model the drawing model
	 * @param bBox the bounding box
	 * @return the created buffered image
	 */
	public static BufferedImage createImage(DrawingModel model,
			Rectangle bBox) {
		BufferedImage img = new BufferedImage(
				bBox.width, 
				bBox.height, 
				BufferedImage.TYPE_3BYTE_BGR
		);
		
		Graphics2D g2 = img.createGraphics();
		g2.translate(-bBox.x, -bBox.y);
		g2.fillRect(bBox.x, bBox.y, bBox.width, bBox.height);
		g2.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON
		);
		g2.setStroke(new BasicStroke(2));

		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2);
		for(int i = 0, n = model.getSize(); i < n; i++) {
			model.getObject(i).accept(painter);
		}
		g2.dispose();
		
		return img;
	}
}

