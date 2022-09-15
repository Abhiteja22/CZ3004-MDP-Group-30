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
	private int direction;
	public double f = 0;
    public double g = 0;
    // Hardcoded heuristic
    public double h;
	public Location parent = null;
	public double weight; 

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getDirection() {
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
	
	public boolean isSameLocation(Location otherLocation) {
		return otherLocation.getX() == x && otherLocation.getY() == y;
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

	public List<Location> getNeighbors(Location n) {
		// TODO Auto-generated method stub
		//Find north location
		Location frontLocation = new Location(n.getX(),n.getY()-1);
		Location backLocation = new Location(n.getX(),n.getY()+1);
		Location leftLocation = new Location(n.getX()-1,n.getY());
		Location rightLocation = new Location(n.getX()+1,n.getY());
		ArrayList<Location> locationList = new ArrayList<>();
		locationList.add(frontLocation);
//		locationList.add(backLocation);
//		locationList.add(leftLocation);
		locationList.add(rightLocation);
		return locationList;
	}
}
