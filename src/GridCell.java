package mdp_git;


public class GridCell {
	private Location location;
	private boolean isObstacle;
	
	public Location getLocation() {
		return this.location;
	}
	
	public boolean isObstacle() {
		return this.isObstacle;
	}
	
	public GridCell() {
	}
	public GridCell(Location a, boolean b) {
		this.location = a;
		this.isObstacle = b;
	}

	
}
