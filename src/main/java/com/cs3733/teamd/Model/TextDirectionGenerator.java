package com.cs3733.teamd.Model;

import javax.sound.midi.SysexMessage;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Stephen on 4/13/2017.
 *
 * Generates Text Directions Given a Path
 */
public class TextDirectionGenerator {

    private static double SLIGHT_THRESHOLD = 20.0;
    private static double FULL_THRESHOLD = 45.0;

    private boolean endAtElevator;

    private List<Point> points;
    public TextDirectionGenerator(List<Point> points, boolean endAtElevator) {
        this.points = points;
        this.endAtElevator = endAtElevator;
    }
    // This method will generate text directions from the points


    public List<String> generateTextDirections() {
        System.out.println("Here");
        Collections.reverse(this.points);
        List<Direction> directions = reduceDirections(
                generateDirections()
        );
        for(Direction d: directions) {
            System.out.println(d);
        }
        Collections.reverse(this.points);
        return null;
    }

    /**
     * Reduce the directions so that they are simplified
     * @param directions - directions that need to be reduced
     * @return - Reduced Direction Set
     */
    private List<Direction> reduceDirections(List<Direction> directions) {
        List<Direction> reducedDirections = new ArrayList<Direction>();
        boolean lastGoStraight = false;
        for(Direction d: directions) {
            if(lastGoStraight) {
                if(d == Direction.GO_STRAIGHT) {
                    continue;
                } else {
                    lastGoStraight = false;
                }
            }
            if(d == Direction.GO_STRAIGHT) {
                lastGoStraight = true;
            }
            reducedDirections.add(d);
        }
        return reducedDirections;
    }

    public static Direction getDirectionFromDeltas(
            double prevToCurrentX,
            double prevToCurrentY,
            double currentToNextX,
            double currentToNextY
    ) {
        // What is the angle between the two lines?
        double previousToCurrentAngle = Math.toDegrees(Math.atan2(prevToCurrentY, prevToCurrentX));
        double currentToNewAngle = Math.toDegrees(Math.atan2(currentToNextY, currentToNextX));

        double angle = currentToNewAngle - previousToCurrentAngle;

        //System.out.println(previousToCurrentAngle+" "+currentToNewAngle+" "+angle);
        if(Math.abs(angle) <= SLIGHT_THRESHOLD) {
            return Direction.GO_STRAIGHT;
        } else if(Math.abs(angle) > 180.0){
            return Direction.GO_STRAIGHT;
        }else if(Math.abs(angle) < FULL_THRESHOLD) {
            if(angle > 0) {
                return Direction.SLIGHT_RIGHT;
            } else {
                return  Direction.SLIGHT_LEFT;
            }
        } else {
            if(angle > 0) {
                return Direction.TURN_RIGHT;
            } else {
                return Direction.TURN_LEFT;
            }
        }

    }

    private List<Direction> generateDirections() {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        System.out.println("Generating Directions..");
        Point currentPoint, previousPoint, nextPoint;

        for(int i = 0; i < points.size(); i++) {
            currentPoint = points.get(i);
            // These next two if clauses make sure we have a next point and a previous point
            if(i!=0) {
                previousPoint = points.get(i - 1);
            } else {
                previousPoint = null;
            }

            if(i < (points.size() - 1)) {
                nextPoint = points.get(i + 1);
            } else {
                nextPoint = null;
            }

            if(nextPoint == null) {
                if(!endAtElevator) {
                    directions.add(Direction.ARRIVED);
                } else {
                    directions.add(Direction.PROCEED_TO_ELEVATOR);
                }
                break;
            }
            if(previousPoint == null) {
                directions.add(Direction.GO_STRAIGHT);
                // Continue to next loop
                continue;
            }

            // nextPoint and previousPoint should not be null now
            double deltaPrevToCurrentX = currentPoint.getX() - previousPoint.getX();
            double deltaPrevToCurrentY = currentPoint.getY() - previousPoint.getY();

            double deltaCurrentToNextX = nextPoint.getX() - currentPoint.getX();
            double deltaCurrentToNextY = nextPoint.getY() - currentPoint.getY();

            Direction d = getDirectionFromDeltas(
                    deltaPrevToCurrentX, deltaPrevToCurrentY, deltaCurrentToNextX, deltaCurrentToNextY
            );
            directions.add(d);
        }

        return directions;
    }

    public enum Direction {
        GO_STRAIGHT,
        TURN_LEFT,
        SLIGHT_LEFT,
        TURN_RIGHT,
        SLIGHT_RIGHT,
        PROCEED_TO_ELEVATOR,
        LEAVE_ELEVATOR,
        ARRIVED
    }
}
