package mdp_test_own;

/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor*/
public class Robot {
	private GridCell currentGridCell = new GridCell(new Location(0,17),false);
	private Location exactLocation;
	
	public GridCell getGridCell() {
		return this.currentGridCell;
	}
	
	public Location getExactLocation() {
		return this.exactLocation;
	}
	
	public Robot() {
	}
	public Robot(GridCell a, Location b) {
		this.currentGridCell = a;
		this.exactLocation = b;
	}
}
