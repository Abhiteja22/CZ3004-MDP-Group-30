package mdp_git;

import java.util.ArrayList;
import java.util.List;

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
		ArrayList<Location> locationList = new ArrayList<>();
		boolean addRight = true;
		boolean addLeft = true;
		boolean addBackRight = true;
		boolean addBackLeft = true;
		Location frontLocation = new Location();
		Location frontLocation1 = new Location();

		Location backLocation = new Location();
		Location backLocation1 = new Location();
		Location backLocation2 = new Location();
		Location backLocation3 = new Location();
		Location backLocation4 = new Location();

		Location leftLocation = new Location();
		Location rightLocation = new Location();
		Location backLeftLocation = new Location();
		Location backRightLocation = new Location();
		int x = n.getX();
		int y = n.getY();

		if (n.direction == 'N') {
			frontLocation = new Location(n.getX(),n.getY()-1, 'N', 'f');
			frontLocation1 = new Location(n.getX()+1,n.getY()-1);

			backLocation = new Location(n.getX(),n.getY()+1, 'N', 'b');
			backLocation1 = new Location(n.getX(),n.getY()+2, 'N', 'b');
			backLocation2 = new Location(n.getX()+1,n.getY()+2, 'N', 'b');
			backLocation3 = new Location(n.getX()+2,n.getY()+2, 'N', 'b');
			backLocation4 = new Location(n.getX()-1,n.getY()+2, 'N', 'b');

			backLeftLocation = new Location(n.getX()-2,n.getY()+4, 'E', 'a');
			backRightLocation = new Location(n.getX()+3,n.getY()+5, 'W', 'd');
			leftLocation = new Location(n.getX()-4,n.getY()-2, 'W', 'l');
			rightLocation = new Location(n.getX()+5,n.getY()-3, 'E', 'r');

			//For Invalid Locations
			if (!(n.getY() <= 0)) { // At top, cannot go forward
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc)) { //&& frontLocation2.checkLocation(loc)) {
					frontLocation.distance = 1;
					locationList.add(frontLocation);
				}

				for (int i=1; i<=3; i++) {
					for (int j=-4;j<=1;j++) {
						if (j==-4 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x+j,y-i);
						if (!check_right_location.checkLocation(loc)) {
							addLeft = false;
						}
					}
				}

				for (int i=1; i<=3; i++) {
					for (int j=0;j<=5;j++) {
						if (j==5 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x+j,y-i);
						if (!check_right_location.checkLocation(loc)) {
							addRight = false;
						}
					}
				}
			}
			if (!(n.getY() >= 20)) { // At bottom, cannot go backward
				if (backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc)) {
					backLocation.g = 5;
					backLocation.distance = 1;
					locationList.add(backLocation);
				}

				if (!(backLocation4.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackLeft = false;
				}
				if (!(backLocation3.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackRight = false;
				}

				for (int i=3; i<=5; i++) {
					for (int j=-3;j<=1;j++) {
						Location check_right_location = new Location(x+j,y+i);
						if (!check_right_location.checkLocation(loc)) {
							addBackLeft = false;
						}
					}
				}

				for (int i=3; i<=5; i++) {
					for (int j=0;j<=4;j++) {
						Location check_right_location = new Location(x+j,y+i);
						if (!check_right_location.checkLocation(loc)) {
							addBackRight = false;
						}
					}
				}
			}
		}
		else if (n.direction == 'E') {
			frontLocation = new Location(n.getX()+1,n.getY(), 'E', 'f');
			frontLocation1 = new Location(n.getX()+1,n.getY()+1, 'N', 'f');

			backLocation = new Location(n.getX()-1,n.getY(), 'E', 'b');
			backLocation1 = new Location(n.getX()-2,n.getY(), 'N', 'b');
			backLocation2 = new Location(n.getX()-2,n.getY()+1, 'N', 'b');
			backLocation3 = new Location(n.getX()-2,n.getY()+2, 'N', 'b');
			backLocation4 = new Location(n.getX()-2,n.getY()-1, 'N', 'b');

			backLeftLocation = new Location(n.getX()-4,n.getY()-2, 'S', 'a');
			backRightLocation = new Location(n.getX()-5,n.getY()+3, 'N', 'd');
			leftLocation = new Location(n.getX()+2,n.getY()-4, 'N', 'l');
			rightLocation = new Location(n.getX()+3,n.getY()+5, 'S', 'r');

			// For Invalid Locations
			if (!(n.getX() >= 19)) { // At right side, cannot go forward
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc)) {
					frontLocation.distance = 1;
					locationList.add(frontLocation);
				}

				for (int i=1; i<=3; i++) {
					for (int j=-4;j<=1;j++) {
						if (j==-4 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x+i,y+j);
						if (!check_right_location.checkLocation(loc)) {
							addLeft = false;
						}
					}
				}

				for (int i=1; i<=3; i++) {
					for (int j=0;j<=5;j++) {
						if (j==5 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x+i,y+j);
						if (!check_right_location.checkLocation(loc)) {
							addRight = false;
						}
					}
				}
			}
			if (!(n.getX() <= -1)) { // At Left side, cannot go backward
				if (backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc)) {
					backLocation.g = 5;
					backLocation.distance = 1;
					locationList.add(backLocation);
				}

				if (!(backLocation4.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackLeft = false;
				}
				if (!(backLocation3.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackRight = false;
				}

				for (int i=3; i<=5; i++) {
					for (int j=-3;j<=1;j++) {
						Location check_right_location = new Location(x-i,y+j);
						if (!check_right_location.checkLocation(loc)) {
							addBackLeft = false;
						}
					}
				}

				for (int i=3; i<=5; i++) {
					for (int j=0;j<=4;j++) {
						Location check_right_location = new Location(x-i,y+j);
						if (!check_right_location.checkLocation(loc)) {
							addBackRight = false;
						}
					}
				}
			}
		}
		else if (n.direction == 'W') {
			frontLocation = new Location(n.getX() - 1,n.getY(), 'W', 'f');
			frontLocation1 = new Location(n.getX()-1,n.getY()-1, 'N', 'f');

			backLocation = new Location(n.getX()+1,n.getY(), 'W', 'b');
			backLocation1 = new Location(n.getX()+2,n.getY(), 'N', 'b');
			backLocation2 = new Location(n.getX()+2,n.getY()-1, 'N', 'b');
			backLocation3 = new Location(n.getX()+2,n.getY()-2, 'N', 'b');
			backLocation4 = new Location(n.getX()+2,n.getY()+1, 'N', 'b');

			backLeftLocation = new Location(n.getX()+4,n.getY()+2, 'N', 'a');
			backRightLocation = new Location(n.getX()+5,n.getY()-3, 'S', 'd');
			leftLocation = new Location(n.getX()-2,n.getY()+4, 'S', 'l');
			rightLocation = new Location(n.getX()-3,n.getY()-5, 'N', 'r');

			// For Invalid Locations
			if (!(n.getX() <= 0)) { // At Left side, cannot go forward
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc)) {
					frontLocation.distance = 1;
					locationList.add(frontLocation);
				}

				for (int i=1; i<=3; i++) {
					for (int j=-1;j<=4;j++) {
						if (j==4 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x-i,y+j);
						if (!check_right_location.checkLocation(loc)) {
							addLeft = false;
						}
					}
				}

				for (int i=1; i<=3; i++) {
					for (int j=0;j<=5;j++) {
						if (j==5 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x-i,y-j);
						if (!check_right_location.checkLocation(loc)) {
							addRight = false;
						}
					}
				}
			}
			if (!(n.getX() >= 20)) { // At Right side, cannot go backward
				if (backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc)) {
					backLocation.g = 5;
					backLocation.distance = 1;
					locationList.add(backLocation);
				}

				if (!(backLocation4.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackLeft = false;
				}
				if (!(backLocation3.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackRight = false;
				}

				for (int i=3; i<=5; i++) {
					for (int j=-1;j<=3;j++) {
						Location check_right_location = new Location(x+i,y+j);
						if (!check_right_location.checkLocation(loc)) {
							addBackLeft = false;
						}
					}
				}

				for (int i=3; i<=5; i++) {
					for (int j=0;j<=4;j++) {
						Location check_right_location = new Location(x+i,y-j);
						if (!check_right_location.checkLocation(loc)) {
							addBackRight = false;
						}
					}
				}
			}
		}
		else {
			frontLocation = new Location(n.getX(),n.getY()+1, 'S', 'f');
			frontLocation1 = new Location(n.getX()-1,n.getY()+1, 'N', 'f');

			backLocation = new Location(n.getX(),n.getY()-1, 'S', 'b');
			backLocation1 = new Location(n.getX(),n.getY()-2, 'N', 'b');
			backLocation2 = new Location(n.getX()-1,n.getY()-2, 'N', 'b');
			backLocation3 = new Location(n.getX()-2,n.getY()-2, 'N', 'b');
			backLocation4 = new Location(n.getX()+1,n.getY()-2, 'N', 'b');

			backLeftLocation = new Location(n.getX()+2,n.getY()-4, 'W', 'a');
			backRightLocation = new Location(n.getX()-3,n.getY()-5, 'E', 'd');
			leftLocation = new Location(n.getX()+4,n.getY()+2, 'E', 'l');
			rightLocation = new Location(n.getX()-5,n.getY()+3, 'W', 'r');

			// For Invalid Locations
			// At bottom, cannot go forward
			if (!(n.getY() >= 19)) {
				if (frontLocation.checkLocation(loc) && frontLocation1.checkLocation(loc)) {
					frontLocation.distance = 1;
					locationList.add(frontLocation);
				}

				for (int i=1; i<=3; i++) {
					for (int j=-1;j<=4;j++) {
						if (j==4 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x+j,y+i);
						if (!check_right_location.checkLocation(loc)) {
							addLeft = false;
						}
					}
				}

				for (int i=1; i<=3; i++) {
					for (int j=0;j<=5;j++) {
						if (j==5 && i==1) {
							continue;
						}
						Location check_right_location = new Location(x-j,y+i);
						if (!check_right_location.checkLocation(loc)) {
							addRight = false;
						}
					}
				}
			}
			if (!(n.getY() <= -1)) { // At top, cannot go backward
				if (backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc)) {
					backLocation.g = 5;
					backLocation.distance = 1;
					locationList.add(backLocation);
				}

				if (!(backLocation4.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackLeft = false;
				}
				if (!(backLocation3.checkLocation(loc) && backLocation1.checkLocation(loc) && backLocation2.checkLocation(loc))) {
					addBackRight = false;
				}

				for (int i=3; i<=5; i++) {
					for (int j=-1;j<=3;j++) {
						Location check_right_location = new Location(x+j,y-i);
						if (!check_right_location.checkLocation(loc)) {
							addBackLeft = false;
						}
					}
				}

				for (int i=3; i<=5; i++) {
					for (int j=0;j<=4;j++) {
						Location check_right_location = new Location(x-j,y-i);
						if (!check_right_location.checkLocation(loc)) {
							addBackRight = false;
						}
					}
				}
			}
		}

		if (addLeft) {
			leftLocation.g = 5;
			leftLocation.distance = 3;
			locationList.add(leftLocation);
		}
		if (addRight) {
			rightLocation.g = 5;
			rightLocation.distance = 3;
			locationList.add(rightLocation);
		}

		if (addBackLeft) {
			backLeftLocation.g = 5;
			backLeftLocation.distance = 3;
			locationList.add(backLeftLocation);
		}
		if (addBackRight) {
			backRightLocation.g = 5;
			backRightLocation.distance = 3;
			locationList.add(backRightLocation);
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
