package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Entities.Tag;
import com.cs3733.teamd.Model.Exceptions.PathNotFoundException;

import java.util.*;

/**
 * Created by tom on 3/31/17.
 */
public class Pathfinder {

    private static final int FLOOR_COST = 500;

    private Node start;
    private Node end;
    private PathNotFoundException pathNotFound;

    /**
     * Creates new Pathfinder object
     * @param start Location of the kiosk
     * @param end Location of destination
     */
    public Pathfinder(Node start, Node end){
        this.start = start;
        this.end = end;
        this.pathNotFound = new PathNotFoundException("Path not found between " + start + " and " + end);
    }

    /**
     * Creates a new Pathfinder
     * @param start
     * @param ends
     */
    public Pathfinder(Node start, List<Node> ends){
        this.start = start;

        // Made up a formula to measure distance between start and end node simply
        // dist = | difference in floors | * FLOOR_COST + (horizontal distance between nodes)

        // if ends is empty
        if(ends.isEmpty()){
            this.end = start;
            return;
        }

        Node closest = ends.get(0);
        double lowestCost = distanceEstimate(start, closest);

        for(Node n: ends){
            double estimate = distanceEstimate(start, n);
            if(estimate > lowestCost){
                closest = n;
                lowestCost = estimate;
            }
        }

        this.end = closest;

    }

    /**
     * Helper function for finding distance by adding an arbitrary cost for floors and adding distance
     * @param a
     * @param b
     * @return
     */
    private double distanceEstimate(Node a, Node b){
        return Math.abs(a.getFloor() - b.getFloor()) * FLOOR_COST + distanceBetween(a,b);
    }

    public static double pathLength(LinkedList<Node> path){

        double length = 0;
        for(int i=0; i < path.size() - 1; i++){
            length += path.get(i).getDist(path.get(i+1));
        }

        return length;
    }

    /**
     * Mostly for testing purposes. Tests whether there is a path between two nodes
     * @return
     */
    public boolean hasPath() {
        try {
            aStarPath();
        } catch (PathNotFoundException e){
            return false;
        }
        return true;
    }

    /**
     * Finds node in the openSet with lowest heuristic value
     * @param openSet
     * @param costThrough
     * @return
     */
    private Node lowestCostThrough(List<Node> openSet, Map<Node, Double> costThrough){
        Node lowest = openSet.get(0);

        for(Node n: openSet){
            if(costThrough.get(n) < costThrough.get(lowest)){
                lowest = n;
            }
        }
        return lowest;
    }

    private LinkedList<Node> bfsPath() throws PathNotFoundException {
        Queue<Node> seen = new LinkedList<Node>();
        LinkedList<Node> openSet = new LinkedList<Node>();

        Map<Node, Node> cameFrom = new HashMap<>();

        Node current;

        openSet.add(start);
        seen.add(start);

        while (!seen.isEmpty()) {
            current = seen.remove();
            if (current == end) { return reconstructPath(cameFrom, current);}
            for (Node n : current.getNodes()) {
                if (!openSet.contains(n)) {
                    openSet.add(n);
                    cameFrom.put(n, current);
                    seen.add(n);
                }
            }
        }

        throw pathNotFound;
    }

    private LinkedList<Node> dfsPath() throws PathNotFoundException {
        Stack<Node> openSet = new Stack<Node>();
        LinkedList<Node> discovered = new LinkedList<Node>();

        Map<Node, Node> cameFrom = new HashMap<>();

        Node current;

        openSet.push(start);
        while(!openSet.isEmpty()){
            current = openSet.pop();

            if (current == end) {
                return reconstructPath(cameFrom, current);
            }
            if(!discovered.contains(current)){
                discovered.add(current);
                for(Node n : current.getNodes()){
                    cameFrom.put(n,current);
                    openSet.push(n);
                }
            }
            //No path found
            throw new PathNotFoundException("Path not found between " + start + " and " + end);
        }



        throw new PathNotFoundException("Path not found between " + start + " and " + end);
    }

    /**
     * Get's the shortest path using application configuration strategy
     * @return
     * @throws PathNotFoundException
     */
    public LinkedList<Node> shortestPath() throws PathNotFoundException {
        // Which strategy shall we use?
        ApplicationConfiguration config = ApplicationConfiguration.getInstance();
        switch(config.getCurrentSearchAlgorithm()) {
            case A_STAR:
                return aStarPath();
            case BFS:
                return bfsPath();
            case DFS:
                return dfsPath();
            default:
                return null;
        }
    }
    /**
     * Gives shortest path using a start algorithm
     * @return a linked list of nodes from start to end
     */
    private LinkedList<Node> aStarPath() throws PathNotFoundException {

        // Set of nodes already evaluated
        List<Node> closedSet = new ArrayList<Node>();

        // Set of nodes visited, but not evaluated
        List<Node> openSet = new ArrayList<Node>();
        openSet.add(start);


        // Map of node with shortest path leading to it
        Map<Node, Node> cameFrom = new HashMap<>();

        // Map of cost of navigating from start to node
        Map<Node, Double> costFromStart = new HashMap<>();
        costFromStart.put(start, 0.0);

        // Map of cost of navigating path from start to end through node
        Map<Node, Double> costThrough = new HashMap<>();
        costThrough.put(start, heuristic(start, end));

        while (!openSet.isEmpty()){

            Node current = lowestCostThrough(openSet, costThrough);

            if(current.equals(end)){
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for(Node neighbor: current.getNodes()) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeCost = costFromStart.get(current) + distanceBetween(current, neighbor);

                if (!openSet.contains(neighbor)) { // found new neighbor
                    openSet.add(neighbor);
                } else if (tentativeCost >= costFromStart.get(neighbor)) { // not a better path
                    continue;
                }

                cameFrom.put(neighbor, current);
                costFromStart.put(neighbor, tentativeCost);
                costThrough.put(neighbor, tentativeCost + heuristic(neighbor, end));

            }
        }
        // no path
        throw pathNotFound;
    }

    /**
     * Helper function to determine distance between two nodes
     * @param a First Node
     * @param b Second Node
     * @return distance between a and b
     */
    public double distanceBetween(Node a, Node b){
        // used to be its own function, now is implemented in the Node class
        return a.getDist(b);
    }

    /**
     * Helper function to determine heuristic between two nodes
     * @param a First Node
     * @param b Second Node
     * @return calculated cost
     */
    private double heuristic(Node a, Node b){

        // absolute value of the differences in floors
        return Math.abs(a.getFloor() - b.getFloor());
    }

    /**
     * Helper function to construct best path out of all paths found
     * @param cameFrom Map of nodes along shortest path
     * @param current Current node
     * @return list of nodes along the best (shortest) path
     */
    private LinkedList<Node> reconstructPath(Map<Node, Node> cameFrom, Node current){
        LinkedList<Node> bestPath = new LinkedList<>();
        bestPath.add(current);

        while(cameFrom.containsKey(current)){
            current = cameFrom.get(current);
            bestPath.add(current);
        }
        return bestPath;
    }


}
