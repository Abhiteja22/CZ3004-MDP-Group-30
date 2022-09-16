package mdp_git_latest;

import java.util.ArrayList;
import java.util.List;

//package mdp_test_own;

/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor*/
public class Location {
	private int x;
	private int y;
	private char direction;
	private char movement;
	public double f = 0;
    public double g = 0;
    // Hardcoded heuristic
    public double h;
	public Location parent = null;
	public double weight;
	public double extraCost; 
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public char getDirection() {
		return this.direction;
	}
	
	public void print() {
		System.out.println("X: " + this.getX() + ", Y = " + this.getY());
	}
	public Location() {
		x=0;
		y=0;
	}
	public Location(int a, int b) {
		this.x = a;
		this.y = b;
	}
	public Location(int a, int b, char c) {
		this.x = a;
		this.y = b;
		this.direction = c;
	}
	
	public Location(int a, int b, char c, char d) {
		this.x = a;
		this.y = b;
		this.direction = c;
		this.movement = d;
		// TODO Auto-generated constructor stub
	}

	public boolean isSameLocation(Location otherLocation) {
		return otherLocation.getX() == x && otherLocation.getY() == y;
	}
	
	public boolean checkLocation(List<Location> loc) { // returns false if location is inside loc
		for (Location a : loc) {
			if (this.isSameLocation(a)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isSameGoalLocation(Location otherLocation) {
		return otherLocation.getX() == x && otherLocation.getY() == y && otherLocation.getDirection() == direction;
	}
	public boolean isInStartPoint() {
		return false;
	}
	
	public boolean isInEndPoint() {
		return false;
	}
	
	public int calculateHeuristic(Location start, Location target) {
		int x_start = start.getX();
		int y_start = start.getY();
		int x_end = target.getX();
		int y_end = target.getY();
		int distance = Math.abs((x_end-x_start)) + Math.abs((y_end - y_start));
		return distance;
	}

	public Location[] getNeighbors() {
		// TODO Auto-generated method stub
		
		return null;
	}

	public List<Location> getNeighbors(Location n, List<Location> loc) {
		// TODO Auto-generated method stub
		//Find north location
		ArrayList<Location> locationList = new ArrayList<>();
		Location frontLocation = new Location();
//		Location leftLocation = new Location();
//		Location rightLocation = new Location();
//		Location backLocation = new Location();
		if (n.direction == 'N') {
			frontLocation = new Location(n.getX(),n.getY()-1, 'N', 'f');
			Location backLocation = new Location(n.getX(),n.getY()+1, 'N', 'b');
			Location leftLocation = new Location(n.getX(),n.getY()+2, 'W', 'l');
			Location rightLocation = new Location(n.getX()+2,n.getY(), 'E', 'r');
			
			// For Invalid Locations
			if (n.getY() != 0) { // At top, cannot go forward
				if (frontLocation.checkLocation(loc)) {
					locationList.add(frontLocation);
				}
			}
			if (!(n.getY() >= 17)) { // At bottom, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					locationList.add(backLocation);
				}
			}
			
			if (leftLocation.checkLocation(loc)) {
				leftLocation.g = 5;
				locationList.add(leftLocation);
			}
			if (rightLocation.checkLocation(loc)) {
				rightLocation.g = 5;
				locationList.add(rightLocation);
			}
		}
		else if (n.direction == 'E') {
			frontLocation = new Location(n.getX()+1,n.getY(), 'E', 'f');
			Location backLocation = new Location(n.getX()-1,n.getY()+1, 'E', 'b');
			Location leftLocation = new Location(n.getX()-2,n.getY(), 'N', 'l');
			Location rightLocation = new Location(n.getX(),n.getY()+2, 'S', 'r');
			
			// For Invalid Locations
			if (n.getX() != 19) { // At right side, cannot go forward
				if (frontLocation.checkLocation(loc)) {
					locationList.add(frontLocation);
				}
			}
			if (!(n.getX() <= 2)) { // At Left side, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					locationList.add(backLocation);
				}
			}
			
			if (leftLocation.checkLocation(loc)) {
				leftLocation.g = 5;
				locationList.add(leftLocation);
			}
			if (rightLocation.checkLocation(loc)) {
				rightLocation.g = 5;
				locationList.add(rightLocation);
			}
		}
		else if (n.direction == 'W') {
			frontLocation = new Location(n.getX() - 1,n.getY()-1, 'W', 'f');
			Location backLocation = new Location(n.getX()+1,n.getY()+1, 'W', 'b');
			Location leftLocation = new Location(n.getX()+2,n.getY(), 'S', 'l');
			Location rightLocation = new Location(n.getX(),n.getY()-2, 'N', 'r');
			
			// For Invalid Locations
			if (n.getX() != 0) { // At Left side, cannot go forward
				if (frontLocation.checkLocation(loc)) {
					locationList.add(frontLocation);
				}
			}
			if (!(n.getX() >= 17)) { // At Right side, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					locationList.add(backLocation);
				}
			}
			
			if (leftLocation.checkLocation(loc)) {
				leftLocation.g = 5;
				locationList.add(leftLocation);
			}
			if (rightLocation.checkLocation(loc)) {
				rightLocation.g = 5;
				locationList.add(rightLocation);
			}
		}
		else {
			frontLocation = new Location(n.getX(),n.getY()+1, 'S', 'f');
			Location backLocation = new Location(n.getX(),n.getY()-1, 'S', 'b');
			Location leftLocation = new Location(n.getX(),n.getY()-2, 'E', 'l');
			Location rightLocation = new Location(n.getX()-2,n.getY(), 'W', 'r');
			
			// For Invalid Locations
			if (n.getY() != 19) { // At bottom, cannot go forward
				if (frontLocation.checkLocation(loc)) {
					locationList.add(frontLocation);
				}
			}
			if (!(n.getY() <= 2)) { // At top, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					locationList.add(backLocation);
				}
			}		

			if (leftLocation.checkLocation(loc)) {
				leftLocation.g = 5;
				locationList.add(leftLocation);
			}
			if (rightLocation.checkLocation(loc)) {
				rightLocation.g = 5;
				locationList.add(rightLocation);
			}
		}
		
		 //To check if any invalid locations were being added
//		for (Location l : locationList) {
//			if (l.getX()>19 || l.getX() < 0) {
//				System.out.println("n: Direction-" + n.direction + " Coordinates: " + n.getX() + "," + n.getY());
//				System.out.println(l.getX());
//			}
//		}
		for (Location l : locationList) {
			if (l.getX()==7 && l.getX() == 1) {
				System.out.println("n: Direction-" + n.direction + " Coordinates: " + n.getX() + "," + n.getY());
				System.out.println(l.getX());
			}
		}
		
		
		return locationList;
	}

	

	public double getCost() {
		// TODO Auto-generated method stub
		return f;
	}

	public char getMovement() {
		return this.movement;
		// TODO Auto-generated method stub
	}
}
