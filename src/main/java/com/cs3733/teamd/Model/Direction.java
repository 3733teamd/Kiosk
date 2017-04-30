package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Entities.Tag;

import java.util.LinkedList;
import java.util.Queue;

import static com.cs3733.teamd.Model.DirectionType.*;

/**
 * Created by Me on 4/25/2017.
 */
public class Direction {

    private static double SLIGHT_THRESHOLD = 20.0;
    private static double FULL_THRESHOLD = 45.0;
//    private static double SHARP_THRESHOLD = 120.0;

    public DirectionType directionType;
    public Tag nearbyTag;


    public Direction(DirectionType directionType, Tag nearbyTag) {
        this.directionType = directionType;
        this.nearbyTag = nearbyTag;
    }

    public Direction(Node previousNode, Node currentNode, Node nextNode) {
        this.directionType = deriveDirectionType(previousNode, currentNode, nextNode);
        this.nearbyTag = deriveLandmarkTag(currentNode);
    }

    private static double deriveAngle(Node previousNode, Node currentNode, Node nextNode) {
        double deltaPrevToCurrentX = currentNode.getX() - previousNode.getX();
        double deltaPrevToCurrentY = currentNode.getY() - previousNode.getY();

        double deltaCurrentToNextX = nextNode.getX() - currentNode.getX();
        double deltaCurrentToNextY = nextNode.getY() - currentNode.getY();


        // What is the angle between the two lines?
        double previousToCurrentAngle = Math.toDegrees(Math.atan2(deltaPrevToCurrentY, deltaPrevToCurrentX));
        double currentToNewAngle = Math.toDegrees(Math.atan2(deltaCurrentToNextY, deltaCurrentToNextX));

        double angle = currentToNewAngle - previousToCurrentAngle;
        if (Math.abs(angle) > 180) {
            if (angle > 0) angle-=360;
            else if (angle < 0) angle+=360;
        }
        return angle;
    }

    public static DirectionType deriveDirectionType(Node previousNode, Node currentNode, Node nextNode) {

        double angle = deriveAngle(previousNode, currentNode, nextNode);

        if(Math.abs(angle) < SLIGHT_THRESHOLD) {
            return GO_STRAIGHT;
        }
        else if(Math.abs(angle) < FULL_THRESHOLD) {
            return (angle < 0 ? SLIGHT_RIGHT : SLIGHT_LEFT);
        }
//        else if (Math.abs(angle) < SHARP_THRESHOLD) {
//            return (angle < 0 ? TURN_RIGHT : TURN_LEFT);
//        }
//        else {
//            return (angle < 0 ? SHARP_RIGHT : SHARP_LEFT);
//        }
        else {
            return (angle < 0 ? TURN_RIGHT : TURN_LEFT);
        }
    }

    public static Tag deriveLandmarkTag(Node baseNode) {
        //Get the landmark tag (tag in close proximity)
        Queue<Node> seen = new LinkedList<Node>();
        LinkedList<Node> openSet = new LinkedList<Node>();
        Node current;
        int nodeVisitedCount = 0;

        openSet.add(baseNode);
        seen.add(baseNode);

        Tag bestTag = null;
        double bestDistanceToTag = -1;
        while (nodeVisitedCount++ < 10 || bestTag == null) {
            current = seen.remove();
            //Put its neighboors in seen
            for (Node n : current.getNodes()) {
                if (!openSet.contains(n)) {
                    seen.add(n);
                }
            }
            //Candidate for nearbyTag (the one of 'current' node)
            Tag checkTag = current.getMostSpecificTag();
            if (checkTag == null) continue;
            double newDist = baseNode.getDist(current);
            if (newDist <= bestDistanceToTag || bestDistanceToTag == -1) {
                bestDistanceToTag = newDist;
                bestTag = checkTag;
            }

        }

        return bestTag;
    }

}
