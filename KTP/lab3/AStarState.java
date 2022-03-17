import java.util.*;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    private HashMap<Location, Waypoint> closeWaypoint;
    private HashMap<Location, Waypoint> openWaypoints;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
        this.closeWaypoint = new HashMap<Location, Waypoint>();
        this.openWaypoints = new HashMap<Location, Waypoint>();


    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    //проверяет все вершины в наборе открытых ввершин, и возвращает ссылку на вершину с минимальной стоимостью
    public Waypoint getMinOpenWaypoint()
    {
        Waypoint minPoint = null;
        if (!openWaypoints.isEmpty()) {
            double minCost = Double.MAX_VALUE;
            for (Waypoint CPoint : openWaypoints.values())
                if (CPoint.getTotalCost() < minCost) {
                    minCost = CPoint.getTotalCost();
                    minPoint = CPoint;
                }
        }
        return minPoint;
    }

    //добавляет вершину, если вершина хуже новой
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        if (openWaypoints.containsKey(newWP.getLocation())) {
            if (newWP.getPreviousCost() < openWaypoints.get(newWP.getLocation()).getPreviousCost()) {
                openWaypoints.put(newWP.getLocation(), openWaypoints.get(newWP.getLocation()));
                return true;
            }
            return false;
        }
        else {
            openWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
    }



    //возвращает кол-во точек в наборе
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }


    //перемещение открытых вершин в закрытые
    public void closeWaypoint(Location loc)
    {
        closeWaypoint.put(loc,openWaypoints.get(loc));
        openWaypoints.remove(loc);
    }

    //возвращает тру\фолс на запрос встречи закрытых вершин
    public boolean isLocationClosed(Location loc)
    {
        return closeWaypoint.keySet().contains(loc);
    }
}