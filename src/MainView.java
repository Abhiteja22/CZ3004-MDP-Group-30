//package mdp_git;

import java.util.ArrayList;
import java.util.Arrays;
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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import java.awt.Font;
import java.nio.charset.StandardCharsets;

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

		//////////////////// ORIGINAL /////////////////////////////////////
		//Explore explore = new Explore(obstacleLocations);
//		long startTime = System.nanoTime();
//		for (int i=0; i<obstacleLocations.size(); i++) {
//			Location nextLocation = explore.nearestNeighbour();
//			path.add(explore.printPath(nextLocation));
//		}
//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime)/1000000;
//		System.out.println("Time Taken (ms): " + duration);
//		String outputPath = convert(path);
//		System.out.println(outputPath);
//		distance = getDistance(path);
//		System.out.println("Length of Path: " + outputPath.length());
//		System.out.println("Distance of Path: " + distance);
//		System.out.println();
		/////////////////////////////////////////////////////////////////////

		////////////////////////// ADDED FOR LOOPS /////////////////////////////////////
		// Comment when doing socket code ////////////
		// setObstacles();
		// populateGridCells();
		// int distance;
		// int min = 999999;
		// String outputPathFinal = "";
		// for (int j = 5; j >= 2; j--) {
		// 	List<ArrayList<Character>> testing_path = new ArrayList<ArrayList<Character>>();
		// 	Explore explore = new Explore(obstacleLocations, j);
		// 	if (explore.getInvalidExplore()) {
		// 		System.out.println("Unable to generate path for j = " + j);
		// 		continue;
		// 	}
		//
		// 	long startTime = System.nanoTime();
		// 	for (int i=0; i<obstacleLocations.size(); i++) {
		// 		Location nextLocation = explore.nearestNeighbour();
		// 		testing_path.add(explore.printPath(nextLocation));
		// 	}
		// 	long endTime = System.nanoTime();
		// 	long duration = (endTime - startTime)/1000000;
		// 	System.out.println("Time Taken (ms) for j = " + j + ": " + duration);
		// 	String outputPath = convert(testing_path);
		// 	System.out.println(outputPath);
		// 	distance = getDistance(testing_path);
		// 	System.out.println("Length of Path: " + outputPath.length());
		// 	System.out.println("Distance of Path: " + distance);
		// 	if (distance < min) {
		// 		min = distance;
		// 		outputPathFinal = outputPath;
		// 		path = testing_path;
		// 	}
		// 	System.out.println();
		// }
		// System.out.println("Output Path: " + outputPathFinal);
		// System.out.println("Distance of Path: " + min);
		///////////////////////////////////////////////////////////////

		////////////////// COnnection ////////////////////////////////////////////
		try{
		      try (ServerSocket serverSocket = new ServerSocket(12345)) {

		        Socket soc = serverSocket.accept();
		          System.out.println("Receive new connection: " + soc.getInetAddress());
		          BufferedOutputStream bout = new BufferedOutputStream(soc.getOutputStream());
		          //DataOutputStream dout=new DataOutputStream(bout);
		          BufferedInputStream bin = new BufferedInputStream(soc.getInputStream());
		          //DataInputStream in = new DataInputStream(bin);
		          byte[] buf = new byte[10000];
		          bin.read(buf);
		          String msg = new String(buf, StandardCharsets.UTF_8);

		          //String msg=(String)in.readUTF();

		          System.out.println("Client message: "+msg);
		          String[] obstacle_split = msg.split(",");
		          System.out.println(obstacle_split[0].split("'")[1]);
	        	  System.out.println(obstacle_split[1].split("'")[1]);
	        	  System.out.println(obstacle_split[2].split("'")[1]);
		          int numberOfObstacle = Integer.parseInt(obstacle_split[0].split("'")[1]);
		          for (int i = 0; i < numberOfObstacle; i++) {
		        	  int x = Integer.parseInt(obstacle_split[3*i+1].split("'")[1]);
		        	  int y = Integer.parseInt(obstacle_split[3*i+2].split("'")[1]);
		        	  char direction = obstacle_split[3*i+3].split("'")[1].charAt(0);
		        	  obstacleLocations.add(new Location(x,y,direction));
		          }
		      	populateGridCells();
				System.out.println("Finished Populating Grid Cells.");

				Explore explore = new Explore();
				// Changeable values
				int numOfGrids = 6; // Max grid distance from which robot can capture image (x-1)*10= val of distance in cm
				int gridsExceedingBoundaries = 2; // Number of grid cells allowed to exceed past boundary
				do {
					Explore explore = new Explore(obstacleLocations, numOfGrids);
					numOfGrids--;
				} while (explore.getInvalidExplore());

				String returned_obstacle = "";
				for (int i=0; i<obstacleLocations.size(); i++) {
					Location nextLocation1 = explore.nearestNeighbour(gridsExceedingBoundaries);

					path.add(explore.printPath(nextLocation1));
					Map<String, String> obstacleLocation = explore.obstacleDict;
					String nextLocation = obstacleLocation.get(explore.locationToString(nextLocation1));
					if (nextLocation == null) {
						nextLocation = "20,20";
					}
					returned_obstacle += nextLocation;
					if (i != obstacleLocations.size()-1) {
						returned_obstacle += ",";
					}
//					if (nextLocation.getDirection() == 'N') {
//						returned_obstacle += String.valueOf(nextLocation.getX() + 1);
//						returned_obstacle += ",";
//						returned_obstacle += String.valueOf(nextLocation.getY() - numOfGrids);
//						if (i != obstacleLocations.size()-1) {
//							returned_obstacle += ",";
//						}
//					} else if (nextLocation.getDirection() == 'W') {
//						returned_obstacle += String.valueOf(nextLocation.getX() - numOfGrids);
//						returned_obstacle += ",";
//						returned_obstacle += String.valueOf(nextLocation.getY() - 1);
//						if (i != obstacleLocations.size()-1) {
//							returned_obstacle += ",";
//						}
//					} else if (nextLocation.getDirection() == 'E') {
//						returned_obstacle += String.valueOf(nextLocation.getX() + numOfGrids);
//						returned_obstacle += ",";
//						returned_obstacle += String.valueOf(nextLocation.getY() + 1);
//						if (i != obstacleLocations.size()-1) {
//							returned_obstacle += ",";
//						}
//					} else {
//						returned_obstacle += String.valueOf(nextLocation.getX() - 1);
//						returned_obstacle += ",";
//						returned_obstacle += String.valueOf(nextLocation.getY() + numOfGrids);
//						if (i != obstacleLocations.size()-1) {
//							returned_obstacle += ",";
//						}
//					}

				}
				String outputPath = convert(path);
		  		String returned_str = "";

		  		returned_str = returned_obstacle + "*" + outputPath;
				//returned_obstacle += ".";
		          //dout.writeUTF(Arrays.toString(path.get(0).toArray())); //insert the directions from algo
		  		System.out.println(returned_str);
		  		byte[] bufout = returned_str.getBytes(StandardCharsets.UTF_8);
		  		bout.write(bufout);
		  		//dout.writeUTF(returned_str);
				//dout.writeUTF(returned_obstacle);
//		  		try {
//					TimeUnit.SECONDS.sleep(15);
//				} catch (InterruptedException e) {
//					System.out.println(e);
//				}
		          //dout.flush();
		          bout.flush();
		          try {
						TimeUnit.SECONDS.sleep(15);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
		          bout.close();
		          //dout.close();
		          soc.close();
		      }
		     }
		catch(Exception e)
		  {
		      e.printStackTrace();
		  }
		///////////////////////////////////////////////////////////////////////////
		t = new Timer(300, new MoveListener());
	}



	String getStringRepresentation(ArrayList<Character> list)
	{
	    StringBuilder builder = new StringBuilder(list.size());
	    for(Character ch: list)
	    {
	        builder.append(ch);
	    }
	    return builder.toString();
	}

	private static String convert(List<ArrayList<Character>> path2) {
		String returnString = "";
		boolean f = false;
		boolean b = false;
		int count = 0;

		for (ArrayList<Character> x : path2) {
			for (char y : x) {
				if (y == 'f') {
					if (b) {
						int countB = count + 5;
						if (countB == 10) {
							countB = 0;
						}
						returnString = returnString + countB + ",";
						count = 0;
						b = false;
						f = true;
					} else if (count == 0) {
						f = true;
					} else if (count == 5) {
						returnString = returnString + "5" + ",";
						count = 0;
					}
					count++;
				} else if (y == 'b') {
					if (f) {
						returnString = returnString + count + ",";
						count = 0;
						f = false;
						b = true;
					} else if (count == 0) {
						b = true;
					} else if (count == 5) {
						returnString = returnString + "0" + ",";
						count = 0;
					}
					count++;
				} else {
					if (f) {
						returnString = returnString + count + ",";
						count = 0;
						f = false;
					} else if (b) {
						int countB = count + 5;
						if (countB == 10) {
							countB = 0;
						}
						returnString = returnString + countB + ",";
						count = 0;
						b = false;
					}
					returnString = returnString + y + ",";
				}
			}
		}
		returnString = returnString + ".";
		return returnString;
	}

	private class MoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (path.get(0).isEmpty()) {
				path.remove(0);
			}

			if (path.isEmpty()) {
				System.out.println("Path exploration Complete");
				t.stop();
				return;
			}

			char i = path.get(0).get(0);
			path.get(0).remove(0);
			System.out.println(i);

			if (i == 'f') {
				robot.moveForward();

			}
			else if (i == 'r') {
				robot.moveForward();
				robot.moveForward();
				robot.moveForward();
				robot.turnRight();
				robot.moveForward();
				robot.moveForward();
				robot.moveForward();
				robot.moveForward();
			}
			else if (i == 'l') {
				robot.moveForward();
				robot.moveForward();
				robot.moveForward();
				robot.turnLeft();
				robot.moveForward();
				robot.moveForward();
				robot.moveForward();
				robot.moveForward();
			}
			else if (i == 'b') {
				robot.moveBackward();
			}
			else if (i == 'a') { // Back Left
				robot.moveBackward();
				robot.moveBackward();
				robot.moveBackward();
				robot.moveBackward();
				robot.turnRight();
				robot.moveBackward();
				robot.moveBackward();
				robot.moveBackward();
			}
			else if (i == 'd') { // Go back
				robot.moveBackward();
				robot.moveBackward();
				robot.moveBackward();
				robot.moveBackward();
				robot.turnLeft();
				robot.moveBackward();
				robot.moveBackward();
				robot.moveBackward();
			}

			robot.getCurrentGridCell().getLocation().print();
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
//		obstacleLocations.add(new Location(7,4,'S'));
//		obstacleLocations.add(new Location(10,10,'E'));

		obstacleLocations.add(new Location(1,1,'S'));
		obstacleLocations.add(new Location(6,7,'N'));
		obstacleLocations.add(new Location(15,3,'W'));
		obstacleLocations.add(new Location(19,10,'W'));
		obstacleLocations.add(new Location(13,17,'E'));
		obstacleLocations.add(new Location(10,12,'E'));
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
		int robotWidth = gridWidth*2;
		int robotHeight = gridHeight * 2;
		g.fillOval(robotExactLocation.getX(), robotExactLocation.getY(), robotWidth, robotHeight);
		g.setColor(Color.red);
		if (robot.getOrientation() == 'N') {
			g.fillRect(robotExactLocation.getX()+gridWidth, robotExactLocation.getY(), gridWidth, gridHeight);
		} else if (robot.getOrientation() == 'E') {
			g.fillRect(robotExactLocation.getX()+gridWidth, robotExactLocation.getY()+gridHeight, gridWidth, gridHeight); // g.fillRect(robotExactLocation.getX()+(gridWidth*2), robotExactLocation.getY()+gridHeight, gridWidth, gridHeight);
		} else if (robot.getOrientation() == 'W') {
			g.fillRect(robotExactLocation.getX(), robotExactLocation.getY(), gridWidth, gridHeight); //g.fillRect(robotExactLocation.getX(), robotExactLocation.getY()+gridHeight, gridWidth, gridHeight);
		} else {
			g.fillRect(robotExactLocation.getX(), robotExactLocation.getY()+gridHeight, gridWidth, gridHeight); //g.fillRect(robotExactLocation.getX()+gridWidth, robotExactLocation.getY()+(gridHeight*2), gridWidth, gridHeight);
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
