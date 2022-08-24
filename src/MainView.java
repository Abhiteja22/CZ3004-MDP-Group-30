package mdp_test_own;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;

public class MainView extends JPanel {

	public final static int PANEL_WIDTH = 600;
	public final static int PANEL_HEIGHT = 600;	
	private List<GridCell> gridCells = new ArrayList<>();
	private List<Location> obstacleLocations = new ArrayList<>();
	private int numberOfCols=20;
	private int numberOfRows=20;
	private int gridWidth;
	private int gridHeight;
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MainView() {
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		calculateDimensions();
		populateGridCells();
		System.out.println("Finished Populating Grid Cells.");
	}
	
	private void calculateDimensions() {
		gridWidth = PANEL_WIDTH/numberOfCols;
		gridHeight = PANEL_HEIGHT/numberOfRows;
	}
	
	private void populateGridCells() {
		for (int y=0; y<numberOfRows; y++) {
			for (int x=0; x<numberOfCols; x++) {
				Location location = new Location(x,y);
				boolean isObstacle = false;
				GridCell gridCell = new GridCell(location, isObstacle);
				gridCells.add(gridCell);
			}
		}
	}
	
	public void paint(Graphics g) {
		paintGridCells(g);
	}
	
	private void paintGridCells(Graphics g) {
		for (GridCell gridCell : gridCells) {
			int x = gridCell.getLocation().getX() * gridWidth;
			int y = gridCell.getLocation().getY() * gridHeight;
			if (gridCell.isObstacle()) {
				g.fillRect(x,y,gridWidth,gridHeight);
			} else {
				g.drawRect(x,y,gridWidth,gridHeight);
			}
		}
	}

}
