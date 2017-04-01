package com.cs3733.teamd.Model;

import sun.awt.image.ImageWatched;

import java.util.*;

/**
 * Created by tom on 3/31/17.
 */
public class Pathfinder {

    private Node start;
    private Node end;

    /**
     * Creates new Pathfinder object
     * @param start Location of the kiosk
     * @param end Location of destination
     */
    public Pathfinder(Node start, Node end){
        this.start = start;
        this.end = end;
    }

    /**
     * Gives shortest path
     * @return a linked list of nodes from start to end
     */
    public LinkedList<Node> shortestPath(){

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
            // TODO: get node with lowest heuristic value
            Node current = openSet.get(0);

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
        return null;
    }

    /**
     * Helper function to determine distance between two nodes
     * @param a First Node
     * @param b Second Node
     * @return distance between a and b
     */
    public double distanceBetween(Node a, Node b){
        return a.getDist(b);
    }

    /**
     * Helper function to determine heuristic between two nodes
     * @param a First Node
     * @param b Second Node
     * @return calculated cost
     */
    private double heuristic(Node a, Node b){
        // returns 0 for now
        // TODO: add an actual heuristic function
        return 0.0;
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
