//package mdp_git_latest;

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
	public char distance = 0;
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
//		Location frontLocation = new Location();
//		Location leftLocation = new Location();
//		Location rightLocation = new Location();
//		Location backLocation = new Location();
		if (n.direction == 'N') {
			Location frontLocation = new Location(n.getX(),n.getY()-1, 'N', 'f');
			Location frontLocation1 = new Location(n.getX()+1,n.getY()-1, 'N', 'f');
			Location frontLocation2 = new Location(n.getX()+2,n.getY()-1, 'N', 'f');
			Location backLocation = new Location(n.getX(),n.getY()+1, 'N', 'b');
			Location leftLocation = new Location(n.getX(),n.getY()+2, 'W', 'l');			
			Location rightLocation = new Location(n.getX()+2,n.getY(), 'E', 'r');
			leftLocation.g = 5;
			locationList.add(leftLocation);
			rightLocation.g = 5;
			locationList.add(rightLocation);
			//For Invalid Locations
			if (n.getY() != 0) { // At top, cannot go forward
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc) && frontLocation2.checkLocation(loc)) {
					frontLocation.distance = 1;
					locationList.add(frontLocation);
				}
			}
			if (!(n.getY() >= 17)) { // At bottom, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					backLocation.distance = 1;
					locationList.add(backLocation);
				}
			}	
	
		}
		else if (n.direction == 'E') {
			Location frontLocation = new Location(n.getX()+1,n.getY(), 'E', 'f');
			Location frontLocation1 = new Location(n.getX()+1,n.getY()+1, 'N', 'f');
			Location frontLocation2 = new Location(n.getX()+1,n.getY()+2, 'N', 'f');
			Location backLocation = new Location(n.getX()-1,n.getY(), 'E', 'b');
			Location leftLocation = new Location(n.getX()-2,n.getY(), 'N', 'l');
			Location rightLocation = new Location(n.getX(),n.getY()+2, 'S', 'r');	
//			locationList.add(frontLocation);
//			locationList.add(backLocation);
//			backLocation.g = 5;
//			leftLocation.g = 5;
//			locationList.add(leftLocation);
//			rightLocation.g = 5;
//			locationList.add(rightLocation);
//			frontLocation.distance = 1;
//			backLocation.distance = 1;
			// For Invalid Locations
			if (n.getX() != 19) { // At right side, cannot go forward
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc) && frontLocation2.checkLocation(loc)) {
					frontLocation.distance = 1;
					locationList.add(frontLocation);
				}
			}
			if (!(n.getX() <= 2)) { // At Left side, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					backLocation.distance = 1;
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
			Location frontLocation = new Location(n.getX() - 1,n.getY(), 'W', 'f');
			Location frontLocation1 = new Location(n.getX()-1,n.getY()-1, 'N', 'f');
			Location frontLocation2 = new Location(n.getX()-1,n.getY()-2, 'N', 'f');
			Location backLocation = new Location(n.getX()+1,n.getY(), 'W', 'b');
			Location leftLocation = new Location(n.getX()+2,n.getY(), 'S', 'l');
			Location rightLocation = new Location(n.getX(),n.getY()-2, 'N', 'r');
//			locationList.add(frontLocation);
//			locationList.add(backLocation);
//			backLocation.g = 5;
//			leftLocation.g = 5;
//			locationList.add(leftLocation);
//			rightLocation.g = 5;
//			locationList.add(rightLocation);
//			frontLocation.distance = 1;
//			backLocation.distance = 1;		
			// For Invalid Locations
			if (n.getX() != 0) { // At Left side, cannot go forward
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc) && frontLocation2.checkLocation(loc)) {
					frontLocation.distance = 1;					
					locationList.add(frontLocation);					
				}
			}
			if (!(n.getX() >= 17)) { // At Right side, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					backLocation.distance = 1;
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
			Location frontLocation = new Location(n.getX(),n.getY()+1, 'S', 'f');
			Location frontLocation1 = new Location(n.getX()-1,n.getY()+1, 'N', 'f');
			Location frontLocation2 = new Location(n.getX()-2,n.getY()+1, 'N', 'f');
			Location backLocation = new Location(n.getX(),n.getY()-1, 'S', 'b');
			Location leftLocation = new Location(n.getX(),n.getY()-2, 'E', 'l');
			Location rightLocation = new Location(n.getX()-2,n.getY(), 'W', 'r');
//			locationList.add(frontLocation);
//			locationList.add(backLocation);
//			backLocation.g = 10;
//			leftLocation.g = 5;
//			locationList.add(leftLocation);
//			rightLocation.g = 5;
//			locationList.add(rightLocation);
//			frontLocation.distance = 1;
//			backLocation.distance = 1;
			// For Invalid Locations
			// At bottom, cannot go forward
			if (n.getY() != 19) { 
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc) && frontLocation2.checkLocation(loc)) {
					frontLocation.distance = 1;
					locationList.add(frontLocation);
				}
			}
			if (!(n.getY() <= 2)) { // At top, cannot go backward
				if (backLocation.checkLocation(loc)) {
					backLocation.g = 10;
					backLocation.distance = 1;
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
