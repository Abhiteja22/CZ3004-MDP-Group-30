//package mdp_git_latest;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import java.awt.Font;

public class MainView extends JPanel {

	public final static int PANEL_WIDTH = 600;
	public final static int PANEL_HEIGHT = 600;	
	private List<GridCell> gridCells = new ArrayList<>();
	private List<Location> obstacleLocations = new ArrayList<>();
	private int numberOfCols=20;
	private int numberOfRows=20;
	private int gridWidth;
	private int gridHeight;
	private Robot robot = new Robot();
	List<ArrayList<Character>> path = new ArrayList<ArrayList<Character>>();
	
	private Timer t;
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MainView() {
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		calculateDimensions();
		//generateRandomObstacels(5);
		setObstacles();
//		robot.moveForward();
//		robot.moveForward();
//		robot.moveForward();
//		robot.turnRight();
		populateGridCells();
		System.out.println("Finished Populating Grid Cells.");
		Explore explore = new Explore(obstacleLocations);
		
		for (int i=0; i<obstacleLocations.size(); i++) {
			Location nextLocation = explore.nearestNeighbour();

			//path.add('f');
			//System.out.println(nextLocation);
			path.add(explore.printPath(nextLocation));
			
			
			//pathAnimator(path);
		}
//		for (List<Character> i : path) {
//			for (char j : i) {
//				System.out.print(j);
//			}
//			System.out.println();
//		}
		t = new Timer(500, new MoveListener());
	}
	
	private class MoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (path.isEmpty()) {
				t.stop();
				return;
			}
			if (path.get(0).isEmpty()) {
				path.remove(0);
			}
			char i = path.get(0).get(0);
			path.get(0).remove(0);
			System.out.println(i);
			System.out.println("Test");
			if (i == 'f') {				
				robot.moveForward();
			}
			else if (i == 'r') {
				robot.turnRight();
			}
			else if (i == 'l') {
				robot.turnLeft();
			} 
			
			
			repaint();
		}
	}
	
	private void pathAnimator(List<Character> path) {
		
		for (char i : path) {
			//System.out.print(i);
			if (i == 'f') {
				//System.out.println("Robot Coordinates: (" + robot.getCurrentGridCell().getLocation().getX() + ","+ robot.getCurrentGridCell().getLocation().getY()+ ")");
				robot.moveForward();
				//System.out.println("Robot Coordinates: (" + robot.getCurrentGridCell().getLocation().getX() + ","+ robot.getCurrentGridCell().getLocation().getY()+ ")");
			}
			else if (i == 'r') {
				robot.turnRight();
			}
			else if (i == 'l') {
				robot.turnLeft();
			}
			else {
				
			}

			repaint();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
	
	private void generateRandomObstacels(int numberOfObstacles) {
		Random random = new Random();
		for (int i=0;i<numberOfObstacles;i++) {
			int x = random.nextInt(numberOfCols);
			int y = random.nextInt(numberOfRows);
			Location obstacleLocation = new Location(x,y);
			obstacleLocations.add(obstacleLocation);
		}
	}
	
	private void setObstacles() {
//		Location obstacleLocation = new Location(x,y);
//		obstacleLocations.add(obstacleLocation);
		obstacleLocations.add(new Location(5,10,'S'));
		obstacleLocations.add(new Location(10,10,'E'));
		obstacleLocations.add(new Location(7,1,'S'));
		obstacleLocations.add(new Location(12,15,'E'));
		obstacleLocations.add(new Location(5,8,'W'));
		obstacleLocations.add(new Location(15,7,'N'));
	}
	
	private void calculateDimensions() {
		gridWidth = PANEL_WIDTH/numberOfCols;
		gridHeight = PANEL_HEIGHT/numberOfRows;
	}
	
	private void populateGridCells() {
		for (int y=0; y<numberOfRows; y++) {
			for (int x=0; x<numberOfCols; x++) {
				Location location = new Location(x,y);
				boolean isObstacle = locationIsObstacle(location);
				GridCell gridCell = new GridCell(location, isObstacle);
				gridCells.add(gridCell);
			}
		}
	}
	
	private boolean locationIsObstacle(Location location) {
		for (Location obstacleLocation:obstacleLocations) {
			if (obstacleLocation.isSameLocation(location)) {
				return true;
			}
		}
		return false;
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		paintGridCells(g);
		paintRobot(g);
		t.start();
	}
	
	private void paintRobot(Graphics g) {
		g.setColor(Color.blue);
		Location robotExactLocation = robot.getExactLocation();
		if (robotExactLocation == null) {
			Location robotGridLocation = robot.getCurrentGridCell().getLocation();
			int x = robotGridLocation.getX() * gridWidth;
			int y = robotGridLocation.getY() * gridHeight;
			robotExactLocation = new Location(x,y);
		}
		int robotWidth = gridWidth*3;
		int robotHeight = gridHeight * 3;
		g.fillOval(robotExactLocation.getX(), robotExactLocation.getY(), robotWidth, robotHeight);
		g.setColor(Color.red);
		if (robot.getOrientation() == 'N') {
			g.fillRect(robotExactLocation.getX()+gridWidth, robotExactLocation.getY(), gridWidth, gridHeight);
		} else if (robot.getOrientation() == 'E') {
			g.fillRect(robotExactLocation.getX()+(gridWidth*2), robotExactLocation.getY()+gridHeight, gridWidth, gridHeight);
		} else if (robot.getOrientation() == 'W') {
			g.fillRect(robotExactLocation.getX()+gridWidth, robotExactLocation.getY()+(gridHeight*2), gridWidth, gridHeight);
		} else {
			g.fillRect(robotExactLocation.getX(), robotExactLocation.getY()+gridHeight, gridWidth, gridHeight);
		}
//		g.fillOval(0, 0, robotWidth, robotHeight);
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
		for (Location obstacleLocation : obstacleLocations) {
			g.setColor(Color.red);
			int direction = obstacleLocation.getDirection();
			int x = obstacleLocation.getX() * gridWidth;
			int y = obstacleLocation.getY() * gridHeight;
			
			if (direction == 'N') { //North
				g.fillRect(x,y-5,gridWidth,5);
			} else if (direction == 'E') { // East
				g.fillRect(x+gridWidth,y,5,gridHeight);
			} else if (direction == 'S') { // South
				g.fillRect(x,y+gridHeight,gridWidth,5);
			} else { // West
				g.fillRect(x-5,y,5,gridHeight);
			}
		}
	}

}
