package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * A color picker used by the {@link JVDraw} application. 
 * Displays a {@link JColorChooser} upon clicking on this component.
 * 
 * @author 0036502252
 *
 */
public class JColorArea extends JComponent implements IColorProvider{
	/** Default UID */
	private static final long serialVersionUID = 1L;
	/**
	 * Default height of this component.
	 */
	private static final int DEFAULT_HEIGHT = 15;
	/**
	 * Default width of this component.
	 */
	private static final int DEFAULT_WIDTH = 15;
	
	/**
	 * The {@link ColorChangeListener}s connected to this component.
	 */
	private List<ColorChangeListener> colorListeners;
	/**
	 * The currently selected color.
	 */
	private Color selectedColor;
	
	/**
	 * A mouse listener which listens for clicks on the component.
	 */
	private MouseListener mListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			Color oldColor = selectedColor;
			Color temp = JColorChooser.showDialog(JColorArea.this, "Choose color", selectedColor);
			selectedColor = temp == null ? selectedColor : temp;
			
			colorListeners.forEach(a->a.newColorSelected(JColorArea.this, oldColor, selectedColor));
			repaint();
		};
	};
		
	/**
	 * Constructs a new {@link JColorArea}.
	 * 
	 * @param selectedColor the initial selected color of the component
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		addMouseListener(mListener);
		colorListeners = new ArrayList<>();
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setMaximumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
		
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		colorListeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		colorListeners.remove(l);
	}
}
