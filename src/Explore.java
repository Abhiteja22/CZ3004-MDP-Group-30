package mdp_test_own;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;

public class Explore {
	private List<Location> robotGoalLocations = new ArrayList<Location>();
	private ArrayList<Location> Visited = new ArrayList<Location>(5);
	private Location startingLocation;
	
	public Location nearestNeighbour() {
		int startingx = startingLocation.getX();
		int startingy = startingLocation.getY();
		ArrayList<Integer> distances = new ArrayList<Integer>(5);
		for (int j=0;j<5;j++) {
			int x = robotGoalLocations.get(j).getX();
			int y = robotGoalLocations.get(j).getY();
			int distance = Math.abs((x-startingx)) + Math.abs((y-startingy));
			distances.add(distance);
		}
		System.out.println(distances);
		Location next_location = new Location();
		int min_distance = 9999;
		for (Location obstacleLocation : robotGoalLocations) {
			if (Visited.contains(obstacleLocation)) {
				continue;
			}
			int x = obstacleLocation.getX();
			int y = obstacleLocation.getY();
			
			int distance = Math.abs((x-startingx)) + Math.abs((y-startingy));
			if (distance < min_distance) {
				System.out.println("Distance: "+ distance);
				min_distance = distance;
				next_location = obstacleLocation;
			}
		}
		this.startingLocation = next_location;
		this.Visited.add(next_location);
		return next_location;
	}
	public Explore() {
	}
	public Explore(List<Location> obstacleLocations2) {
		for (Location obstacleLocation : obstacleLocations2) {
			int direction = obstacleLocation.getDirection();
			int x = obstacleLocation.getX();
			int y = obstacleLocation.getY();
			
			if (direction == 0) { //North
				robotGoalLocations.add(new Location(x,y-2));
			} else if (direction == 1) { // East
				robotGoalLocations.add(new Location(x+2,y));
			} else if (direction == 2) { // South
				robotGoalLocations.add(new Location(x,y+2));
			} else { // West
				robotGoalLocations.add(new Location(x-2,y));
			}
		}
		startingLocation = new Location(0,17);
	}
}
