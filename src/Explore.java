//package mdp_test_own;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Explore {
	private List<Location> robotGoalLocations = new ArrayList<Location>();
	private ArrayList<Location> Visited = new ArrayList<Location>(5);
	private Location startingLocation;	
	private Robot r = new Robot();
	
	public Location aStar(Location start, Location target){
	    ArrayList<Location> closedList = new ArrayList<>();
	    ArrayList<Location> openList = new ArrayList<>();

	    start.f = start.g + start.calculateHeuristic(start, target);
	    openList.add(start);
	    System.out.println(target.getX());
	    System.out.println(target.getY());
	    while(!openList.isEmpty()){
	    	System.out.println(openList);
	        Location n = openList.remove(0);
	        if(n.isSameLocation(target)){
	            return n;
	        }

	        for(Location  edge : n.getNeighbors(n)){
	            Location m = edge;
	            double totalWeight = n.g + 1;

	            if(!openList.contains(m) && !closedList.contains(m)){
	                m.parent = n;
	                m.g = totalWeight;
	                m.f = m.g + m.calculateHeuristic(m, target);
	                openList.add(m);
	            } else {
	                if(totalWeight < m.g){
	                    m.parent = n;
	                    m.g = totalWeight;
	                    m.f = m.g + m.calculateHeuristic(m, target);

	                    if(closedList.contains(m)){
	                        closedList.remove(m);
	                        openList.add(m);
	                    }
	                }
	            }
	        }

	        openList.remove(n);
	        closedList.add(n);
	    }
	    return null;
	}

	public void printPath(Location target){
	    Location n = target;

	    if(n==null)
	        return;

	    List<Location> ids = new ArrayList<>();
	    ids.add(n);
	    while(n.parent != null){
	        ids.add(n.parent);
	        n = n.parent;
	    }
	    //ids.add(n);
	    Collections.reverse(ids);

	    for(Location id : ids){
	        System.out.print(id.getX() + " " + id.getY() + " ");
	    }
	    Robot r = new Robot();
	    // Convert location to direction
	    for (int i=0; i<ids.size()-1; i++) {
	    	int x1 = ids.get(i).getX();
	    	int y1 = ids.get(i).getY();
	    	int x2 = ids.get(i+1).getX();
	    	int y2 = ids.get(i+1).getY();
	    	if (r.getOrientation() == 0) {
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Forward");
		    	}
		    	else if (x1+1 == x2 && y1 == y2) {
		    		System.out.print("Turn right");
//		    		r.setOrientation();
		    	}
		    	else if (x1-1 == x2 && y1 == y2) {
		    		System.out.print("Turn left");
//		    		r.setOrientation(r.orientation);
		    	}
	    	}
	    	else if (r.getOrientation() == 1){ // if robot is facing right
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Turn left");
		    		r.setOrientation(r.orientation);
		    	}
		    	else if (x1+1 == x2 && y1 == y2) {
		    		System.out.print("Forward");
		    		
		    	}
		    	else if (x1 == x2 && y1+1 == y2) {
		    		System.out.print("Turn right");
		    	}
	    	}
	    	else if (r.getOrientation() == 1){ // if robot is facing right
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Turn left");
		    		r.setOrientation(r.orientation);
		    	}
		    	else if (x1+1 == x2 && y1 == y2) {
		    		System.out.print("Forward");
		    		
		    	}
		    	else if (x1 == x2 && y1+1 == y2) {
		    		System.out.print("Turn right");
		    	}
	    	}
	    	else if (r.getOrientation() == 2){ // if robot is facing left
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Turn right");
		    		r.setOrientation(r.orientation);
		    	}
		    	else if (x1-1 == x2 && y1 == y2) {
		    		System.out.print("Forward");
		    		
		    	}
		    	else if (x1 == x2 && y1+1 == y2) {
		    		System.out.print("Turn left");
		    	}
	    	}
		    	
	    }
	    System.out.println("");
	}
	
	public int getPathCost(Location target) {
		Location n = target;
		int cost = 0;
	 
		List<Location> ids = new ArrayList<>();
	    ids.add(n);
	    while(n.parent != null){
	        ids.add(n.parent);
	        n = n.parent;
	    }
	    Collections.reverse(ids);
	    for (int i=0; i<ids.size()-1; i++) {
	    	int x1 = ids.get(i).getX();
	    	int y1 = ids.get(i).getY();
	    	int x2 = ids.get(i+1).getX();
	    	int y2 = ids.get(i+1).getY();
	    	if (r.getOrientation() == 0) {
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Forward");
		    		cost += 1;
		    	}
		    	else if (x1+1 == x2 && y1 == y2) {
		    		System.out.print("Turn right");
		    		cost += 1;
		    		r.setOrientation(r.orientation.EAST);
		    	}
		    	else if (x1-1 == x2 && y1 == y2) {
		    		System.out.print("Turn left");
		    		r.setOrientation(r.orientation.WEST);
		    	}
	    	}
	    	else if (r.getOrientation() == 1){ // if robot is facing right
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Turn left");
		    		r.setOrientation(r.orientation.WEST);
		    	}
		    	else if (x1+1 == x2 && y1 == y2) {
		    		System.out.print("Forward");
		    		
		    	}
		    	else if (x1 == x2 && y1+1 == y2) {
		    		System.out.print("Turn right");
		    	}
	    	}
	    	else if (r.getOrientation() == 1){ // if robot is facing right
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Turn left");
		    		r.setOrientation(r.orientation);
		    	}
		    	else if (x1+1 == x2 && y1 == y2) {
		    		System.out.print("Forward");
		    		
		    	}
		    	else if (x1 == x2 && y1+1 == y2) {
		    		System.out.print("Turn right");
		    	}
	    	}
	    	else if (r.getOrientation() == 2){ // if robot is facing left
	    		if (x1 == x2 && y1-1 == y2) {
		    		System.out.print("Turn right");
		    		r.setOrientation(r.orientation.EAST);
		    	}
		    	else if (x1-1 == x2 && y1 == y2) {
		    		System.out.print("Forward");
		    		
		    	}
		    	else if (x1 == x2 && y1+1 == y2) {
		    		System.out.print("Turn left");
		    	}
	    	}		
	    }
	    
	    // If the robot orientation is not correct, need to rotate direction
		return cost;
	}
	// Find nearest neighbour at each step
	public Location nearestNeighbour() {
//		int startingx = startingLocation.getX();
//		int startingy = startingLocation.getY();
//		ArrayList<Integer> distances = new ArrayList<Integer>(5);
//		for (int j=0;j<1;j++) {   //For each Location
//			int x = robotGoalLocations.get(j).getX();
//			int y = robotGoalLocations.get(j).getY();
//			int distance = Math.abs((x-startingx)) + Math.abs((y-startingy));
//			distances.add(distance);
//		}
		//System.out.println(distances);
		Location next_location = new Location();
		int min_cost = 9999;
		for (Location obstacleLocation : robotGoalLocations) {
			if (Visited.contains(obstacleLocation)) {
				continue;
			}
			System.out.println("Test");
			System.out.println(startingLocation.getX() + startingLocation.getY() );
			Location target = this.aStar(startingLocation,  obstacleLocation);
//			System.out.println(target);
			this.printPath(target);
			int cost = this.getPathCost(target);
			if (cost < min_cost) {
				min_cost = cost;
				next_location = obstacleLocation;
			}
			
//			int x = obstacleLocation.getX();
//			int y = obstacleLocation.getY();
//			
//			int distance = Math.abs((x-startingx)) + Math.abs((y-startingy));
//			if (distance < min_distance) {
//				System.out.println("Distance: "+ distance);
//				min_distance = distance;
//				next_location = obstacleLocation;
//			}
		}
		this.startingLocation = next_location;
		this.Visited.add(next_location);
		return next_location;
	}
	
	
	public Explore() {
	}
	public Explore(List<Location> obstacleLocations2) {
		for (Location obstacleLocation : obstacleLocations2) {
			int direction = obstacleLocation.getDirection();
			int x = obstacleLocation.getX();
			int y = obstacleLocation.getY();
			
			if (direction == 0) { //North
				robotGoalLocations.add(new Location(x,y-2, 'S'));
			} else if (direction == 1) { // East
				robotGoalLocations.add(new Location(x+2,y, 'W'));
			} else if (direction == 2) { // South
				robotGoalLocations.add(new Location(x-1,y+2, 'N'));
			} else { // West
				robotGoalLocations.add(new Location(x-2,y, 'E'));
			}
		}
		startingLocation = new Location(0,17);
	}
	

}
