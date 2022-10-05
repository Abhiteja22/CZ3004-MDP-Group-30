//package mdp_git;

public class Robot {
	public enum Orientation {
		  NORTH,
		  EAST,
		  SOUTH,
		  WEST
	}

	private GridCell currentGridCell = new GridCell(new Location(0,17),false);
	private Location exactLocation;
	private char orientation = 'N';

	public GridCell getCurrentGridCell() {
		return this.currentGridCell;
	}

	public void setCurrentGridCell(Location a, boolean b) {
		this.currentGridCell = new GridCell(a,b);
	}

	public Location getExactLocation() {
		return this.exactLocation;
	}

	public void setExactLocation(Location a) {
		this.exactLocation = a;
	}

	public int getOrientation() {
		return this.orientation;
	}

	public void setOrientation(char a) {
		this.orientation = a;
	}

	public Robot() {
	}
	public Robot(GridCell a, Location b, char c) {
		this.currentGridCell = a;
		this.exactLocation = b;
		this.orientation = c;
	}

	public void moveForward() { //Adjust it such that it cannot move forward when facing edges
		Location currentLocation = this.getCurrentGridCell().getLocation();

		Location newLocation = new Location();
		if (this.orientation == 'N') {
			newLocation = new Location(currentLocation.getX(), currentLocation.getY()-1);
		} else if (this.orientation == 'E') {
			newLocation = new Location(currentLocation.getX()+1, currentLocation.getY());
		} else if (this.orientation == 'W') {
			newLocation = new Location(currentLocation.getX()-1, currentLocation.getY());
		} else {
			newLocation = new Location(currentLocation.getX(), currentLocation.getY()+1);
		}

		this.setCurrentGridCell(newLocation, false);
	}

	public void moveBackward() { //Adjust it such that it cannot move forward when facing edges
		Location currentLocation = this.getCurrentGridCell().getLocation();

		Location newLocation = new Location();
		if (this.orientation == 'N') {
			newLocation = new Location(currentLocation.getX(), currentLocation.getY()+1);
		} else if (this.orientation == 'E') {
			newLocation = new Location(currentLocation.getX()-1, currentLocation.getY());
		} else if (this.orientation == 'W') {
			newLocation = new Location(currentLocation.getX()+1, currentLocation.getY());
		} else {
			newLocation = new Location(currentLocation.getX(), currentLocation.getY()-1);
		}

		this.setCurrentGridCell(newLocation, false);
	}

	public void turnRight() {
		if (this.orientation == 'N') {
			this.setOrientation('E');
		} else if (this.orientation == 'E') {
			this.setOrientation('S');
		} else if (this.orientation == 'W') {
			this.setOrientation('N');
		} else {
			this.setOrientation('W');
		}
	}

	public void turnLeft() {
		if (this.orientation == 'N') {
			this.setOrientation('W');
		} else if (this.orientation == 'E') {
			this.setOrientation('N');
		} else if (this.orientation == 'W') {
			this.setOrientation('S');
		} else {
			this.setOrientation('E');
		}
	}
}
