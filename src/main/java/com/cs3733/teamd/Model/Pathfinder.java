package com.cs3733.teamd.Model;

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

    public static double pathLength(LinkedList<Node> path){

        double length = 0;
        for(int i=0; i < path.size() - 1; i++){
            length += path.get(i).getDist(path.get(i+1));
        }

        return length;
    }

    public boolean hasPath() {
        LinkedList<Node> nodes = shortestPath();
        boolean hasStart = false;
        boolean hasEnd = false;
        for(Node n: nodes) {
            if(start.getID() == n.getID()) {
                hasStart = true;
            }
            if(end.getID() == n.getID()) {
                hasEnd = true;
            }
        }
        return hasStart && hasEnd;
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

    public LinkedList<Node> BFSPath() throws Exception {
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

        throw new Exception("Path does not exist to destination.");
    }

    public LinkedList<Node> DFSPath() throws Exception {
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
            throw new Exception("No path to destination found.");
        }



        throw new Exception("Path does not exist to destination.");
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
        //return null; // or maybe new LinkedList<Node>()

        
        /////////////////////// TEMPORARY FIX /////////////////////// TODO TODO TODO
        return new LinkedList<Node>(Arrays.asList(new Node[]{start, start}));
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
