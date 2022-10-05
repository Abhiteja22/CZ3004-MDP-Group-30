//package mdp_git;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Explore {
	private List<Location> robotGoalLocations = new ArrayList<Location>();
	private ArrayList<Location> Visited = new ArrayList<Location>(5);
	private Location startingLocation;
	private Robot r = new Robot();
	private PriorityQueue<Location> openList;
	private List<Location> blockedLocations = new ArrayList<Location>(); // For Obstacles
    private ArrayList<Location> closedList = new ArrayList<>();
		private boolean invalidExplore;

    public boolean getInvalidExplore() {
    	return this.invalidExplore;
    }
    Map<String, String> obstacleDict = new HashMap<String, String>();
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
	            //System.out.println("Checking: (" + n.getX() + ", " + n.getY() + ")");

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

			System.out.println("Checking Goal Location: (" + obstacleLocation.getX() + ", " + obstacleLocation.getY() + ")");
			Location target = this.aStar(startingLocation,  obstacleLocation);

			double cost = this.getPathCost(target);
			System.out.println("Cost: " + cost);
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


	public Explore(List<Location> obstacleLocations2, int val) {
		this.openList = new PriorityQueue<Location>(new locationComparator());
		for (Location obstacleLocation : obstacleLocations2) {
			int x = obstacleLocation.getX();
			int y = obstacleLocation.getY();

			// For Virtual boundary blocked Locations
			blockedLocations.add(new Location(x,y));
			blockedLocations.add(new Location(x-1,y-1));
			blockedLocations.add(new Location(x,y-1));
			blockedLocations.add(new Location(x+1,y-1));
			blockedLocations.add(new Location(x-1,y));

			blockedLocations.add(new Location(x+1,y));
			blockedLocations.add(new Location(x-1,y+1));
			blockedLocations.add(new Location(x,y+1));
			blockedLocations.add(new Location(x+1,y+1));
		}
		for (Location obstacleLocation : obstacleLocations2) {
			int direction = obstacleLocation.getDirection();
			int x = obstacleLocation.getX();
			int y = obstacleLocation.getY();

			int furthestGrid = val; // Furthest grid cell the robot stops before image

			if (direction == 'N') { //North
				//robotGoalLocations.add(new Location(x+1,y-2, 'S')); // 10 cm away
				//robotGoalLocations.add(new Location(x+1,y-5, 'S')); // 50 cm away
				//robotGoalLocations.add(new Location(x+1,y-furthestGrid, 'S'));
				for (int i=furthestGrid; i>=2; i--) { /// For distance
					if (y-i <= 0) {
						continue;
					}
					boolean locationAvail = false;
					for (int j=0; j<=1; j++) { /// For width
						int x_robot = x+j;
						int y_robot = y-i;
						Location new_location = new Location(x_robot,y_robot, 'S');

						for (int k=-1;k<=0;k++) { // For robot width
							boolean currLocationUnavail = false;
							for (int l = y_robot-5; l <= y-2; l++) { // for robot HEIGHT
								Location check_location = new Location(x_robot+k,l);
								if (!check_location.checkLocation(blockedLocations)) {
									currLocationUnavail = true;
									break;
								}
							}
							if (currLocationUnavail) {
								break;
							} else if (k == 0) {
								locationAvail = true;
								break;
							}
						}
						if (locationAvail) {
							robotGoalLocations.add(new_location);
							obstacleDict.put(locationToString(new_location), locationToString(obstacleLocation));
							break;
						}
					}
					if (locationAvail) {
						break;
					}
				}
			} else if (direction == 'E') { // East
				//robotGoalLocations.add(new Location(x+2,y+1, 'W')); // 10 cm away
				//robotGoalLocations.add(new Location(x+5,y+1, 'W')); // 50 cm away
				//robotGoalLocations.add(new Location(x+furthestGrid,y+1, 'W')); // 50 cm away
				for (int i=furthestGrid; i>=2; i--) {
					if (x+i >= 19) {
						continue;
					}
					boolean locationAvail = false;
					for (int l=0; l<=1; l++) {
						int x_robot = x+i;
						int y_robot = y+l;
						Location new_location = new Location(x_robot,y_robot, 'W');


						for (int j=0;j<=1;j++) {
							boolean currLocationUnavail = false;
							for (int k = x_robot+5; k >= x+2; k--) {
								Location check_location = new Location(k,y_robot+j);
								if (!check_location.checkLocation(blockedLocations)) {
									currLocationUnavail = true;
									break;
								}
							}
							if (currLocationUnavail) {
								break;
							} else if (j == 1) {
								locationAvail = true;
								break;
							}
						}
						if (locationAvail) {
							robotGoalLocations.add(new_location);
							obstacleDict.put(locationToString(new_location), locationToString(obstacleLocation));
							break;
						}
					}
					if (locationAvail) {
						break;
					}
				}
			} else if (direction == 'S') { // South
//				robotGoalLocations.add(new Location(x-1,y+furthestGrid, 'N')); // 10 cm away
//				robotGoalLocations.add(new Location(x-1,y+5, 'N')); // 50 cm away
				for (int i=furthestGrid; i>=2; i--) {
					if (y+i >= 19) {
						continue;
					}
					boolean locationAvail = false;
					for (int l=-1; l<=0;l++) {
						int x_robot = x+l;
						int y_robot = y+i;
						Location new_location = new Location(x_robot,y_robot, 'N');

						for (int j=0;j<=1;j++) {
							boolean currLocationUnavail = false;
							for (int k = y_robot+5; k >= y+2; k--) {
								Location check_location = new Location(x_robot+j,k);
								if (!check_location.checkLocation(blockedLocations)) {
									currLocationUnavail = true;
									break;
								}
							}
							if (currLocationUnavail) {
								break;
							} else if (j == 1) {
								locationAvail = true;
								break;
							}
						}
						if (locationAvail) {
							robotGoalLocations.add(new_location);
							obstacleDict.put(locationToString(new_location), locationToString(obstacleLocation));
							break;
						}
					}
					if (locationAvail) {
						break;
					}
				}
			} else { // West
				//robotGoalLocations.add(new Location(x-furthestGrid,y-1, 'E')); // 10 cm away
				//robotGoalLocations.add(new Location(x-5,y-1, 'E')); // 50 cm away
				for (int i=furthestGrid; i>=2; i--) {
					if (x-i <= 0) {
						continue;
					}
					boolean locationAvail = false;
					for (int l=-1; l<=0; l++) {
						int x_robot = x-i;
						int y_robot = y+l;
						Location new_location = new Location(x_robot,y_robot, 'E');


						for (int j=0;j<=1;j++) {
							boolean currLocationUnavail = false;
							for (int k = x_robot-5; k <= x-2; k++) {
								Location check_location = new Location(k,y_robot+j);
								if (!check_location.checkLocation(blockedLocations)) {
									currLocationUnavail = true;
									break;
								}
							}
							if (currLocationUnavail) {
								break;
							} else if (j == 1) {
								locationAvail = true;
								break;
							}
						}
						if (locationAvail) {
							robotGoalLocations.add(new_location);
							obstacleDict.put(locationToString(new_location), locationToString(obstacleLocation));
							break;
						}
					}
					if (locationAvail) {
						break;
					}
				}
			}
		}
		if (robotGoalLocations.size() < obstacleLocations2.size()) {
			invalidExplore = true;
		} else {
			invalidExplore = false;
		}
		startingLocation = new Location(0,17, 'N');

	}

	public String locationToString(Location target) {
		String res = "";
		res += Integer.toString(target.getX());
		res += ",";
		res += Integer.toString(target.getY());

		return res;
	}


}
