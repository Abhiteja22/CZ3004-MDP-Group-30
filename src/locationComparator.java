//package mdp_git;
import java.util.Comparator;


public class locationComparator implements Comparator<Location> {

    @Override
    public int compare(Location o1, Location o2) {
        double c1 = o1.getCost();
        double c2 = o2.getCost();
        if (c1 < c2) return -1;
        if (c1 > c2) return 1;
        return 0;
    }
}
