package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingList;
import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModelImplementation;
import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.drawingmodel.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geovisitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;


/**
 * A Swing drawing application which suppports various geometric models 
 * and color changing. The user can see drawing feedback in real time. On the 
 * left side of the program there is a list which displays all of the drawn 
 * objects, with relevant information about their properties. The pictures 
 * created in the program can be loaded from and saved to .jvd files, and
 * also exported to image files.
 * 
 * @author 0036502252
 *
 */
public class JVDraw extends JFrame {
	/** Default UID */
	private static final long serialVersionUID = 1L;
	/**
	 * Default width of the window.
	 */
	private static final int DEFAULT_WIDTH = 800;
	/**
	 * Default height of the window.
	 */
	private static final int DEFAULT_HEIGHT = 600;
	/**
	 * App window title.
	 */
	private static final String TITLE = "JVDraw";
	/**
	 * Default location of the window on the screen.
	 */
	private static final Point DEFAULT_LOCATION = new Point(50, 50);
	/**
	 * Initial foreground color.
	 */
	private static final Color INITIAL_FGCOLOR = Color.BLACK;
	/**
	 * Initial background color.
	 */
	private static final Color INITIAL_BGCOLOR = Color.LIGHT_GRAY;
	/**
	 * Supported image file extensions.
	 */
	private static final List<String> IMG_EXTS = Arrays.asList("gif","png","jpg");
	
	/**
	 * The application toolbar.
	 */
	private JToolBar toolBar;
	/**
	 * The app's current tool.
	 */
	private Tool tool;
	/**
	 * The app's background color chooser.
	 */
	private JColorArea bgColorArea;
	/**
	 * The app's foreground color chooser.
	 */
	private JColorArea fgColorArea;
	/**
	 * The app's canvas.
	 */
	private JDrawingCanvas canvas;
	/**
	 * The tool used for drawing filled circles.
	 */
	private FilledCircleTool filledCircleTool;
	/**
	 * The line tool.
	 */
	private LineTool lineTool;
	/**
	 * The circle tool.
	 */
	private CircleTool circleTool;
	/**
	 * The drawing model.
	 */
	private DrawingModel model;
	/**
	 * The JList used for displaying objects.
	 */
	private JList<GeometricalObject> list;
	/**
	 * Path of the file with which the program is currently working.
	 */
	private Path currentPath;
	
	/**
	 * Utility flag which indicates whether the operated file/picture
	 * has changed.
	 */
	private boolean hasChanged;
	
	/**
	 * Constructs a new {@link JVDraw}.
	 */
	public JVDraw() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle(TITLE);
		setLocation(DEFAULT_LOCATION);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.model = new DrawingModelImplementation();
		this.list = new DrawingList(new DrawingObjectListModel(model));
		
		initGUI();
		setVisible(true);
	}

	/**
	 * Initialzes the app GUI.
	 */
	private void initGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit.actionPerformed(null); 
			}
		});
		
		createToolbars();
		addButtons();
		addMenuBar();
		addCanvas();
		initTools();
		initList();
	}

	/**
	 * Checks if the document has been saved once the program starts closing.
	 * 
	 * @return true if the user has canceled closing
	 */
	private boolean checkForUnsavedDocument() {
		boolean closingAborted = false;
		
		if(hasChanged) {
			int choice = JOptionPane.showConfirmDialog(
					JVDraw.this,
					"There are unsaved changes. Do you want to save this file? ",
					"Save",
					JOptionPane.YES_NO_CANCEL_OPTION
			);	
			switch(choice) {
			case JOptionPane.YES_OPTION:
				save.actionPerformed(null);
				break;
			case JOptionPane.NO_OPTION:
				break;
			case JOptionPane.CANCEL_OPTION:
				closingAborted = true;
				break;
			}	
		}
		
		return closingAborted;
	}

	/**
	 * Initializes the {@link JList}.
	 */
	private void initList() {
		JScrollPane jsp = new JScrollPane(list);
		jsp.setSize(jsp.getPreferredSize());
		jsp.setPreferredSize(jsp.getPreferredSize());
		getContentPane().add(canvas, BorderLayout.CENTER);
		getContentPane().add(jsp, BorderLayout.LINE_END);
	}


	/**
	 * Initializes the canvas and adds it to the app frame.
	 */
	private void addCanvas() {
		canvas = new JDrawingCanvas(tool, model);
		MouseListener l = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}
		};
		canvas.addMouseListener(l);
		
		canvas.addMouseMotionListener((MouseMotionListener) l);
		model.addDrawingModelListener(canvas);
		model.addDrawingModelListener(changeListener);
	}


	/**
	 * Initializes the menu bar with actions and adds it to the app frame.
	 */
	private void addMenuBar() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.addSeparator();
		fileMenu.add(export);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		menubar.add(fileMenu);
		this.setJMenuBar(menubar);
	}


	/**
	 * Adds buttons to the app's toolbar.
	 */
	private void addButtons() {
		ButtonGroup drawingGroup = new ButtonGroup();
		
		JToggleButton lineButton = new JToggleButton(line);
		JToggleButton circleButton = new JToggleButton(circle);
		JToggleButton filledCircleButton = new JToggleButton(filledCircle);

		drawingGroup.add(lineButton);
		drawingGroup.add(circleButton);
		drawingGroup.add(filledCircleButton);
		
		toolBar.add(lineButton);
		toolBar.add(circleButton);
		toolBar.add(filledCircleButton);
		lineButton.setSelected(true);
		toolBar.addSeparator();		
	}
	
	/**
	 * Creates the toolbar and info label.
	 */
	private void createToolbars() {
		getContentPane().setLayout(new BorderLayout());
		
		toolBar = new JToolBar();
		toolBar.setFloatable(true);
		toolBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel bottomPanel = new JPanel();
		getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		
		fgColorArea = new JColorArea(INITIAL_FGCOLOR);
		bgColorArea = new JColorArea(INITIAL_BGCOLOR);
		ColorInfoLabel infoLabel = new ColorInfoLabel(bgColorArea, fgColorArea);
		
		toolBar.addSeparator();
		toolBar.add(fgColorArea);
		toolBar.addSeparator();
		toolBar.add(bgColorArea);
		toolBar.addSeparator();
		
		bottomPanel.add(infoLabel);
	}
	
	/**
	 * Initializes the app's drawing tools.
	 */
	private void initTools() {
		filledCircleTool = new FilledCircleTool(bgColorArea, fgColorArea, model, canvas);
		lineTool = new LineTool(fgColorArea, model, canvas);
		circleTool = new CircleTool(fgColorArea, model, canvas);
		
		tool = lineTool;
		canvas.setTool(lineTool);
	}

	/**
	 * Sets the app's current tool to {@link LineTool}.
	 */
	private final Action line = new AbstractAction("Line") {

		/** Default UID*/
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JVDraw.this.tool = lineTool;
			canvas.setTool(lineTool);
		}
	};
	
	/**
	 * Sets the app's current tool to {@link CircleTool}.
	 */
	private final Action circle = new AbstractAction("Circle") {
		/** Default UID*/
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JVDraw.this.tool = circleTool;
			canvas.setTool(circleTool);
		}
	};
	
	/**
	 * Sets the app's current tool to {@link FilledCircleTool}.
	 */
	private final Action filledCircle = new AbstractAction("Filled circle") {
		/** Default UID*/
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JVDraw.this.tool = filledCircleTool;
			canvas.setTool(filledCircleTool);
		}
	};
	
	/**
	 * Opens an existing .jvd file.
	 */
	private final Action open = new AbstractAction("Open") {
		/** Default UID*/
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			
			if(jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path path = jfc.getSelectedFile().toPath();
			if(!path.getFileName().toString().endsWith("jvd")) {
				Util.sendError("Only .jvd files are supported");
				return;
			}
			
			List<GeometricalObject> fromFile = Util.loadFromFile(path);
			if(fromFile == null) {
				Util.sendError("Could not open file.");
				return;
			}
			
			Util.clearModel(model);
			fromFile.forEach(obj->model.add(obj));
			currentPath = path;
			hasChanged = false;
		}
	};
	
	/**
	 * Saves the drawing model content to file.
	 */	
	private final Action save = new AbstractAction("Save") {
		/** Default UID*/
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Path newPath = null;
			
			if(currentPath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save file");

				if(jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				newPath = jfc.getSelectedFile().toPath();
				String extension = Util.getExtension(newPath);
				
				if(extension.isEmpty()) {
					newPath = Paths.get(jfc.getSelectedFile().toString() + ".jvd");
				} else if(!extension.equals("jvd")) {
					Util.sendError("Only .jvd files are supported");
					return;
				}
			}
			
			newPath = newPath == null ? currentPath : newPath;
			currentPath = newPath;
			Util.saveToFile(newPath, model);
			hasChanged = false;
		}
	};
	

	/**
	 * Saves the drawing model content to file, to the user-specified path.
	 */
	private final Action saveAs = new AbstractAction("Save as") {
		/** Default UID*/
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save as");

			if(jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			currentPath = jfc.getSelectedFile().toPath();

			String extension = Util.getExtension(currentPath);
			
			if(extension.isEmpty()) {
				currentPath = Paths.get(jfc.getSelectedFile().toString() + ".jvd");
			} else if(!extension.equals("jvd")) {
				Util.sendError("Only .jvd files are supported");
				return;
			}

			
			Util.saveToFile(currentPath, model);
			hasChanged = false;
		}
	};
	
	/**
	 * Exports the drawing model to an image. 
	 */
	private final Action export = new AbstractAction("Export") {
		/** Default UID*/
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			GeometricalObjectBBCalculator calc = new GeometricalObjectBBCalculator();
			for(int i = 0, n = model.getSize(); i < n; i++) {
				model.getObject(i).accept(calc);
			}
			
			Rectangle bBox;
			try {
				bBox = calc.getBoundingBox();				
			} catch(IllegalStateException ex) {
				Util.sendError("Can't export empty image.");
				return;
			}
			
			BufferedImage img = Util.createImage(model, bBox);
						
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Export");
			if(jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = jfc.getSelectedFile();
			String extension = Util.getExtension(file);
			//if no extension is provided, assume .png
			if(extension.isEmpty()) {
				file = new File(jfc.getSelectedFile().toString() + ".png");
			} else if(!IMG_EXTS.contains(extension)) {
				Util.sendError("Invalid extension! Valid extensions are: " + IMG_EXTS);
				return;
			}
			
			try {
				ImageIO.write(img, Util.getExtension(file), file);
			} catch (IOException e) {
				Util.sendError("Error exporting file.");
				return;
			}
			Util.sendInfo("Exported file " + file.getName());
		}
	};
	
	/**
	 * Listens to the drawing model in order to check if there are 
	 * any modified files which should be saved before exiting the app.
	 */
	private DrawingModelListener changeListener = new DrawingModelListener() {		

		@Override
		public void objectsRemoved(DrawingModel source, int index0, int index1) {
			hasChanged = true;
		}
		
		@Override
		public void objectsChanged(DrawingModel source, int index0, int index1) {
			hasChanged = true;
		}
		
		@Override
		public void objectsAdded(DrawingModel source, int index0, int index1) {
			hasChanged = true;
		}
	};
	
	/**
	 * Exits the application after checking for unsaved changes.
	 */
	private final Action exit = new AbstractAction("Exit") {
		/** Default UID*/
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean closingAborted = checkForUnsavedDocument();
			if(!closingAborted) {
				dispose();
				System.exit(0);
			}
		}
	};
	
	/**
	 * Main method which starts the EDT and the app itself.
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw());
	}
}
