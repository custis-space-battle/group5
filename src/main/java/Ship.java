import java.util.HashSet;
import java.util.Set;

/**
 * Created by Участник on 20.05.2017.
 */
public class Ship {
    boolean isDead = false;

    private Set<Point> shipPointSet = new HashSet();
    public Ship(){
    }

    public Set<Point> getShipPointSet() {
        return shipPointSet;
    }
}
