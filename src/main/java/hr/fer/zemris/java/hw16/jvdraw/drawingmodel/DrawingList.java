package hr.fer.zemris.java.hw16.jvdraw.drawingmodel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.geoeditors.EditException;
import hr.fer.zemris.java.hw16.jvdraw.geoeditors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometricobjects.GeometricalObject;

/**
 * {@link JList} implementation which stores {@link GeometricalObject}s and 
 * allows for external object editing and list manipulation. Uses a 
 * {@link DrawingModel} as its internal model.
 * 
 * @author 0036502252
 *
 */
public class DrawingList extends JList<GeometricalObject> {
	/**Default UID*/
	private static final long serialVersionUID = 1L;

	/**
	 * The JList implementation's model.
	 */
	DrawingModel model;
	
	/**
	 * Constructs a new {@link DrawingList}.
	 * 
	 * @param listModel the list's model.
	 */
	public DrawingList(DrawingObjectListModel listModel) {
		this.model = listModel.getModel();
		
		setModel(listModel);
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
	}

	/**
	 * A key listener which listens for delete, + and - keys and allows for 
	 * deleting items, and shifting them up and down in the list, 
	 * respectively.
	 */
	private final KeyListener keyListener = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			GeometricalObject selected = getSelectedValue();
			if (e.getKeyCode() == KeyEvent.VK_DELETE && selected != null) {
				model.remove(getSelectedValue());
			} else if((e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_ADD) && selected != null) {
				model.changeOrder(getSelectedValue(), 1);
				setSelectedIndex(getSelectedIndex() + 1);
			} else if((e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT) && selected != null) {
				model.changeOrder(getSelectedValue(), -1);	
				setSelectedIndex(getSelectedIndex() - 1);
			}
		}
	};
	
	/**
	 * A mouse listener which listens for double clicks on the list's elements
	 * and opens a {@link GeometricalObjectEditor} for the specified 
	 * selected geometric object.
	 */
	private MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				GeometricalObject selected = DrawingList.this.getSelectedValue();
				if(selected == null) return;
				
				GeometricalObjectEditor editor = selected
						.createGeometricalObjectEditor();
				
				int clicked = JOptionPane.showConfirmDialog(
						DrawingList.this,
						editor, 
						selected.toString(),
						JOptionPane.OK_CANCEL_OPTION
				);
				
				if(clicked == JOptionPane.OK_OPTION) {
					try {
						editor.checkEditing();
						editor.acceptEditing();
					} catch(EditException ex) {
						JOptionPane.showMessageDialog(DrawingList.this,
								ex.getMessage(),
								"Error",
								JOptionPane.ERROR_MESSAGE
						);
					}
				}
			}
		}
	};
}
