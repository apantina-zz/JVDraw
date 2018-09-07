package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * A label used by {@link JVDraw} which displays the currently 
 * selected background and foreground colors.
 * 
 * @author 0036502252
 *
 */
public class ColorInfoLabel extends JLabel {
	/** Default UID */
	private static final long serialVersionUID = 4;
	/**
	 * The foreground color provider.
	 */
	private IColorProvider fgColorArea;
	/**
	 * The background color provider.
	 */
	private IColorProvider bgColorArea;

	/**
	 * Constructs a new {@link ColorInfoLabel}.
	 * 
	 * @param bgColorArea the background color area
	 * @param fgColorArea the foreground color area
	 */
	public ColorInfoLabel(JColorArea bgColorArea, JColorArea fgColorArea) {
		this.fgColorArea = fgColorArea;
		this.bgColorArea = bgColorArea;
		updateLabel();

		this.fgColorArea.addColorChangeListener((src, oldC, newC) -> {
			this.fgColorArea = src;
			updateLabel();
		});
		
		this.bgColorArea.addColorChangeListener((src, oldC, newC) -> {
			this.bgColorArea = src;
			updateLabel();
		});

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setOpaque(true);
		setBackground(Color.white);
	}

	/**
	 * Updates the label with current color information.
	 */
	private void updateLabel() {
		setText("Foreground color: (" + fgColorArea.getCurrentColor().getRed()
				+ ", " + fgColorArea.getCurrentColor().getGreen() + ", "
				+ fgColorArea.getCurrentColor().getBlue() + "), "
				+ "background color: (" + bgColorArea.getCurrentColor().getRed()
				+ ", " + bgColorArea.getCurrentColor().getGreen() + ", "
				+ bgColorArea.getCurrentColor().getBlue() + ")."

		);
	}
}
