package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.Node;

import java.util.*;

/**
 * Created by Stephen on 4/13/2017.
 *
 * Generates Text Directions Given a Path
 */
public class TextDirectionGenerator {


    private static double SLIGHT_THRESHOLD = 20.0;
    private static double FULL_THRESHOLD = 45.0;

    private boolean endAtElevator;

    private List<Node> points;

    private int onFloor;

    private List<String> pointsOfInterestNames;

    /**
     * Creates a Text DirectionType Generator
     * @param points - Points to Create Text Directions from
     */
    public TextDirectionGenerator(List<Node> points, int onFloor) {
        this.points = points;
        this.onFloor = onFloor;
        this.pointsOfInterestNames = new ArrayList<String>();
    }

    private static Map<DirectionType, String[]> getTranslations() {
        Map<DirectionType, String[]> translations = new HashMap<DirectionType, String[]>();
        String[] proceedFrom = { "proceed from ", "precdeder desde " };
        translations.put(DirectionType.PROCEED_FROM_TAG, proceedFrom);

        String[] goStraight = { "proceed straight", "proceder recto"};
        translations.put(DirectionType.GO_STRAIGHT, goStraight);

        String[] turnLeft = {"turn left", "girar a la izquierda"};
        translations.put(DirectionType.TURN_LEFT, turnLeft);

        String[] slightLeft = {"make a slight left", "hacer un poco a la izquierda"};
        translations.put(DirectionType.SLIGHT_LEFT, slightLeft);

        String[] turnRight = {"turn right", "dobla a la derecha"};
        translations.put(DirectionType.TURN_RIGHT, turnRight);

        String[] slightRight = {"make a slight right", "hacer un ligero derecho"};
        translations.put(DirectionType.SLIGHT_RIGHT, slightRight);

        String[] arrived = {"you have arrived at your destination", "has llegado a tu destino"};
        translations.put(DirectionType.ARRIVED, arrived);

        String[] proceedToElevator = {
                "take the elevator to your destination floor",
                "tomar el ascensor hasta el piso de destino"
        };
        translations.put(DirectionType.PROCEED_TO_ELEVATOR, proceedToElevator);
        return translations;

    }
    // This method will generate text directions from the points


    public List<String> generateTextDirections() {
        Collections.reverse(this.points);
        List<DirectionType> directions = reduceDirections(
                generateDirections()
        );
        for(DirectionType d: directions) {
            System.out.println(d);
        }
        Collections.reverse(this.points);
        return getDirectionsInLanguage(directions, pointsOfInterestNames);
    }

    public static List<String> getDirectionsInLanguage(
            List<DirectionType> directions,
            List<String> pointsOfInterestNames) {
        int textIndex = (ApplicationConfiguration.getInstance().getCurrentLanguage()
                == ApplicationConfiguration.Language.ENGLISH) ? 0 : 1;
        List<String> directionList = new ArrayList<String>();
        String tagName = (pointsOfInterestNames.size() > 0) ? pointsOfInterestNames.get(0) : "starting point.";
        boolean isFirstElement = true;
        for(DirectionType d: directions) {
            String addition = "";
            switch(d) {
                case PROCEED_FROM_TAG:
                    addition = getTranslations().get(d)[textIndex] + tagName;
                    break;
                case GO_STRAIGHT:
                case TURN_LEFT:
                case SLIGHT_LEFT:
                case TURN_RIGHT:
                case SLIGHT_RIGHT:
                case ARRIVED:
                case PROCEED_TO_ELEVATOR:
                    addition = getTranslations().get(d)[textIndex];
                    break;
                default:

            }

            if(isFirstElement && (addition.length() > 0)) {
                addition = addition.replaceFirst(addition.substring(0,1),addition.substring(0,1).toUpperCase());
                isFirstElement = false;
            } else {
                if(textIndex == 0) {
                    addition = "and then "+addition;
                } else {
                    addition = "y "+addition;
                }

            }
            directionList.add(addition);
        }
        return directionList;
    }

    /**
     * Reduce the directions so that they are simplified
     * @param directions - directions that need to be reduced
     * @return - Reduced DirectionType Set
     */
    private List<DirectionType> reduceDirections(List<DirectionType> directions) {
        List<DirectionType> reducedDirections = new ArrayList<DirectionType>();
        boolean lastGoStraight = false;
        for(DirectionType d: directions) {
            if(lastGoStraight) {
                if(d == DirectionType.GO_STRAIGHT) {
                    continue;
                } else {
                    lastGoStraight = false;
                }
            }
            if(d == DirectionType.GO_STRAIGHT) {
                lastGoStraight = true;
            }
            reducedDirections.add(d);
        }
        return reducedDirections;
    }

    /**
     * Get a direction from a previous (x,y) and current (x,y) and a next (x,y)
     * @param prevToCurrentX - delta from previous x to current x
     * @param prevToCurrentY - delta from previous y to current y
     * @param currentToNextX - delta from current x to next x
     * @param currentToNextY - delta from current y to next y
     * @return - DirectionType of travel about the current point
     */
    public static DirectionType getDirectionFromDeltas(
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
            return DirectionType.GO_STRAIGHT;
        } else if(Math.abs(angle) > 180.0){
            return DirectionType.GO_STRAIGHT;
        }else if(Math.abs(angle) < FULL_THRESHOLD) {
            if(angle > 0) {
                return DirectionType.SLIGHT_RIGHT;
            } else {
                return  DirectionType.SLIGHT_LEFT;
            }
        } else {
            if(angle > 0) {
                return DirectionType.TURN_RIGHT;
            } else {
                return DirectionType.TURN_LEFT;
            }
        }

    }

    private List<DirectionType> generateDirections() {
        ArrayList<DirectionType> directions = new ArrayList<DirectionType>();
        System.out.println("Generating Directions..");
        Node currentPoint, previousPoint, nextPoint;

        for(int i = 0; i < points.size(); i++) {
            currentPoint = points.get(i);
            if(currentPoint.getFloor() != onFloor) {
                continue;
            }
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
                directions.add(DirectionType.ARRIVED);
                break;
            } else if(nextPoint.getFloor() != onFloor) {
                directions.add(DirectionType.PROCEED_TO_ELEVATOR);
                break;
            }
            if(
                    (previousPoint == null) ||
                    (previousPoint.getFloor() != currentPoint.getFloor())
                    ) {
                directions.add(DirectionType.PROCEED_FROM_TAG);
                if(currentPoint.getTags().size() > 0) {
                    pointsOfInterestNames.add(currentPoint.getTags().getFirst().getTagName());
                }
                // Continue to next loop
                continue;
            }

            // nextPoint and previousPoint should not be null now
            double deltaPrevToCurrentX = currentPoint.getX() - previousPoint.getX();
            double deltaPrevToCurrentY = currentPoint.getY() - previousPoint.getY();

            double deltaCurrentToNextX = nextPoint.getX() - currentPoint.getX();
            double deltaCurrentToNextY = nextPoint.getY() - currentPoint.getY();

            DirectionType d = getDirectionFromDeltas(
                    deltaPrevToCurrentX, deltaPrevToCurrentY, deltaCurrentToNextX, deltaCurrentToNextY
            );
            if(d != DirectionType.GO_STRAIGHT) {
                System.out.println(currentPoint.getTags().size());
            }
            directions.add(d);
        }

        return directions;
    }


}
