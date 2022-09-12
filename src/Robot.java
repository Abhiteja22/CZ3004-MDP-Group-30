package mdp_test_own;

/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor*/
public class Robot {
	enum Orientation {
		  NORTH,
		  EAST,
		  SOUTH,
		  WEST
	}
	
	private GridCell currentGridCell = new GridCell(new Location(0,17),false);
	private Location exactLocation;
	private Orientation orientation = Orientation.NORTH;
	
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
		if (this.orientation == Orientation.NORTH) {
			return 0;
		} else if (this.orientation == Orientation.EAST) {
			return 1;
		} else if (this.orientation == Orientation.WEST) {
			return 2;
		} else {
			return 3;
		}
	}
	
	private void setOrientation(Orientation a) {
		this.orientation = a;
	}
	
	public Robot() {
	}
	public Robot(GridCell a, Location b, Orientation c) {
		this.currentGridCell = a;
		this.exactLocation = b;
		this.orientation = c;
	}
	
	public void moveForward() { //Adjust it such that it cannot move forward when facing edges
		Location currentLocation = this.getCurrentGridCell().getLocation();
		
		Location newLocation = new Location();
		if (this.orientation == Orientation.NORTH) {
			newLocation = new Location(currentLocation.getX(), currentLocation.getY()-1);
		} else if (this.orientation == Orientation.EAST) {
			newLocation = new Location(currentLocation.getX()+1, currentLocation.getY());
		} else if (this.orientation == Orientation.WEST) {
			newLocation = new Location(currentLocation.getX()-1, currentLocation.getY());
		} else {
			newLocation = new Location(currentLocation.getX(), currentLocation.getY()+1);
		}
		
		this.setCurrentGridCell(newLocation, false);
	}
	
	public void turnRight() {
		if (this.orientation == Orientation.NORTH) {
			this.setOrientation(Orientation.EAST);
		} else if (this.orientation == Orientation.EAST) {
			this.setOrientation(Orientation.SOUTH);
		} else if (this.orientation == Orientation.WEST) {
			this.setOrientation(Orientation.NORTH);
		} else {
			this.setOrientation(Orientation.WEST);
		}
	}
	
	public void turnLeft() {
		if (this.orientation == Orientation.NORTH) {
			this.setOrientation(Orientation.WEST);
		} else if (this.orientation == Orientation.EAST) {
			this.setOrientation(Orientation.NORTH);
		} else if (this.orientation == Orientation.WEST) {
			this.setOrientation(Orientation.SOUTH);
		} else {
			this.setOrientation(Orientation.EAST);
		}
	}
}
