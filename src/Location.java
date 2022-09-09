package mdp_test_own;

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
	public Location(int a, int b, int c) {
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
}
