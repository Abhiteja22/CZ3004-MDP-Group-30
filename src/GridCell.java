package mdp_test_own;

/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor*/
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
