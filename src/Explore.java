//package mdp_git_latest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Explore {
	private List<Location> robotGoalLocations = new ArrayList<Location>();
	private ArrayList<Location> Visited = new ArrayList<Location>(5);
	private Location startingLocation;	
	private Robot r = new Robot();
	private PriorityQueue<Location> openList;
	private List<Location> blockedLocations = new ArrayList<Location>(); // For Obstacles
    private ArrayList<Location> closedList = new ArrayList<>();

	public Location aStar(Location start, Location target){
	    //ArrayList<Location> openList = new ArrayList<>();
	    
	    start.f = start.g + start.calculateHeuristic(start, target);
	    this.openList.add(start);
	    while(!openList.isEmpty()){
	    	
	        Location n = openList.remove();
	        //System.out.println(n.getX());
	        if(n.isSameGoalLocation(target)){
	            return n;
	        }

	        for(Location  edge : n.getNeighbors(n, blockedLocations)){
	            Location m = edge;
	            double totalWeight = n.g + m.g;

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

	public ArrayList<Character> printPath(Location target){
	    Location n = target;

//	    if(n==null)
//	        return;
	    ArrayList<Character> robotPath = new ArrayList<Character>();
	    List<Location> ids = new ArrayList<>();
	    ids.add(n);
	    while(n.parent != null){
	        ids.add(n.parent);
	        n = n.parent;
	    }
	    //ids.add(n);
	    Collections.reverse(ids);
	    
//	    for(Location id : ids){
//	        System.out.print(id.getMovement()+ " ");
//
//	        robotPath.add(id.getMovement());
//	        //System.out.println(id.getX() + " " + id.getY() + " " + id.getDirection()+ " ");
//	    }
	    for (int i = 1; i < ids.size(); i++) {
	    	//System.out.print(ids.get(i).getMovement()+ " ");

	        robotPath.add(ids.get(i).getMovement());
	    	
	    }
	    robotPath.add('s');
	    robotPath.add('c');
	    System.out.println("");
	    System.out.println(robotPath);
	    return robotPath;
	}
	
	public double getPathCost(Location target) {
		Location n = target;
		//double cost = n.f;
		double cost = 0;
		List<Location> ids = new ArrayList<>();
	    ids.add(n);
	    while(n.parent != null){
	        ids.add(n.parent);	        
	        n = n.parent;
	        //cost += n.h;
	        cost += n.distance;
	    }
	    Collections.reverse(ids);
	    
	    // If the robot orientation is not correct, need to rotate direction
		return cost;
	}
	// Find nearest neighbour at each step
	public Location nearestNeighbour() {
		//System.out.println(distances);
		Location next_location = new Location();
		Location returned_location = new Location();
		double min_cost = 999999;
		
		for (Location obstacleLocation : robotGoalLocations) {
			if (Visited.contains(obstacleLocation)) {
				continue;
			}

			Location target = this.aStar(startingLocation,  obstacleLocation);

			double cost = this.getPathCost(target);
			//System.out.println(cost);
			if (cost < min_cost) {
				min_cost = cost;
				next_location = obstacleLocation;
				returned_location = target;
			}
		}
		//this.printPath(returned_location);
		//System.out.println(returned_location);
		System.out.println(min_cost);
		this.startingLocation = next_location;
		this.Visited.add(next_location);
		this.openList.clear();
		this.closedList.clear();
		returned_location.print();
		return returned_location;
	}
	
	
	public Explore() {
	}
	public Explore(List<Location> obstacleLocations2) {
		this.openList = new PriorityQueue<Location>(new locationComparator());
		for (Location obstacleLocation : obstacleLocations2) {
			int direction = obstacleLocation.getDirection();
			int x = obstacleLocation.getX();
			int y = obstacleLocation.getY();
			
			if (direction == 'N') { //North
				robotGoalLocations.add(new Location(x+1,y-2, 'S'));
			} else if (direction == 'E') { // East
				robotGoalLocations.add(new Location(x+2,y+1, 'W'));
			} else if (direction == 'S') { // South
				robotGoalLocations.add(new Location(x-1,y+2, 'N'));
			} else { // West
				robotGoalLocations.add(new Location(x-2,y-1, 'E'));
			}
			
			// For Blocked Locations
			blockedLocations.add(new Location(x-1,y-1));
			blockedLocations.add(new Location(x,y-1));
			blockedLocations.add(new Location(x+1,y-1));
			blockedLocations.add(new Location(x-1,y));
			blockedLocations.add(new Location(x,y));
			blockedLocations.add(new Location(x+1,y));
			blockedLocations.add(new Location(x-1,y+1));
			blockedLocations.add(new Location(x,y+1));
			blockedLocations.add(new Location(x+1,y+1));
		}
		startingLocation = new Location(0,17, 'N');
		
	}
	

}
