package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.Node;
import javafx.scene.image.Image;

import java.util.*;

import static com.cs3733.teamd.Model.DirectionType.*;

/**
 * Created by Stephen on 4/13/2017.
 *
 * Generates Text Directions Given a Path
 */
public class TextDirectionGenerator {

    private boolean endAtElevator;

    private List<Node> pathPoints;

    private int onFloor;

    private static final Map<DirectionType, String[]> translations;  //Eng, Span, Fran, Port, Chine
    static {
        /**
         *
         */
        Map<DirectionType, String[]> translationsTemp = new HashMap<DirectionType, String[]>();
        String[] proceedFrom = {"proceed from ",
                "precdeder desde ",
                "\u0050\u0072\u006f\u0063\u00e8\u0064\u0065 \u0064\u0065 ",
                "Proceder de ",
                "\u7ee7\u7eed "};
        translationsTemp.put(PROCEED_FROM_TAG, proceedFrom);

        String[] goStraight = {"proceed straight", "proceder recto",
                "\u0050\u0072\u006f\u0063\u00e8\u0064\u0065 tout droit",
                "Proceder de",
                "\u76f4\u884c"};
        translationsTemp.put(GO_STRAIGHT, goStraight);

        String[] turnLeft = {"turn left", "girar a la izquierda",
                "\u0074\u006f\u0075\u0072\u006e\u0065\u007a \u00e0 \u0067\u0061\u0075\u0063\u0068\u0065",
                "\u0076\u0069\u0072\u0065 \u00e0 \u0065\u0073\u0071\u0075\u0065\u0072\u0064\u0061",
                "\u8f6c\u5de6"};
        translationsTemp.put(TURN_LEFT, turnLeft);

        String[] slightLeft = {"make a slight left", "hacer un poco a la izquierda",
                "\u0046\u0061\u007a\u0065\u0072 \u0075\u006d\u0061 \u006c\u0069\u0067\u0065\u0069\u0072\u0061 \u0065\u0073\u0071\u0075\u0065\u0072\u0064\u0061",
                "Fazer uma ligeira esquerda",
                "\u7a0d\u5fae\u7559\u4e0b"};
        translationsTemp.put(SLIGHT_LEFT, slightLeft);

        String[] turnRight = {"turn right", "dobla a la derecha",
                "\u0074\u006f\u0075\u0072\u006e\u0065\u007a \u00e0 \u0064\u0072\u006f\u0069\u0074\u0065",
                "Proceder de",
                "\u53f3\u8f6c"};
        translationsTemp.put(TURN_RIGHT, turnRight);

        String[] slightRight = {"make a slight right", "hacer un ligero derecho",
                "\u0050\u0072\u006f\u0063\u00e8\u0064\u0065 \u0064\u0065",
                "\u0076\u0069\u0072\u0065 \u00e0 \u0064\u0069\u0072\u0065\u0069\u0074\u0061",
                "\u7ee7\u7eed"};
        translationsTemp.put(SLIGHT_RIGHT, slightRight);

        String[] arrived = {"you have arrived at your destination", "has llegado a tu destino",
                "\u0056\u006f\u0075\u0073 \u00ea\u0074\u0065\u0073 \u0061\u0072\u0072\u0069\u0076\u00e9 \u00e0 \u0076\u006f\u0074\u0072\u0065 \u0064\u0065\u0073\u0074\u0069\u006e\u0061\u0074\u0069\u006f\u006e",
                "\u0056\u006f\u0063\u00ea \u0063\u0068\u0065\u0067\u006f\u0075 \u0061\u006f \u0073\u0065\u0075 \u0064\u0065\u0073\u0074\u0069\u006e\u006f",
                "\u4f60\u5df2\u5230\u8fbe\u4f60\u7684\u76ee\u7684\u5730"};
        translationsTemp.put(ARRIVED, arrived);

        String[] proceedToElevator = {
                "take the elevator to your destination floor",
                "tomar el ascensor hasta el piso de destino",
                "Prenez l'ascenseur \u00e0 votre \u00e9\u0074\u0061\u0067\u0065 de destination",
                "Pegue o elevador \u0061\u0074\u00e9 o andar de destino",
                "\u628a\u7535\u68af\u5e26\u5230\u4f60\u7684\u76ee\u7684\u5730\u697c\u5c42"
        };
        translationsTemp.put(PROCEED_TO_ELEVATOR, proceedToElevator);

        translations = Collections.unmodifiableMap(translationsTemp);
    }

    private static final Map<DirectionType, String> iconFilePaths;  //Eng, Span, Fran, Port, Chine
    static {
        Map<DirectionType, String> tempIcons = new HashMap<DirectionType, String>();
        tempIcons.put(PROCEED_FROM_TAG,"dir_icons/procceed.png");
        tempIcons.put(GO_STRAIGHT,"dir_icons/procceed.png");
        tempIcons.put(TURN_LEFT,"dir_icons/left.png");
        tempIcons.put(TURN_RIGHT,"dir_icons/right.png");
        tempIcons.put(SLIGHT_LEFT,"dir_icons/slight left.png");
        tempIcons.put(SLIGHT_RIGHT,"dir_icons/slight right.png");
        tempIcons.put(ARRIVED,"dir_icons/arrive.png");
        tempIcons.put(PROCEED_TO_ELEVATOR,"dir_icons/elevator.png");
        iconFilePaths = Collections.unmodifiableMap(tempIcons);
    }



    public List<Image> finalImages;
    public List<String> finalDirectionTexts;

    int langIndex;


    /**
     * Creates a Text Direction Generator
     *
     * @param pathPoints - Points to Create Text Directions from
     */
    public TextDirectionGenerator(List<Node> pathPoints, int onFloor) {
        this.pathPoints = pathPoints;
        this.onFloor = onFloor;
        this.finalImages = new ArrayList<>();
        this.finalDirectionTexts = new ArrayList<>();

        switch (ApplicationConfiguration.getInstance().getCurrentLanguage()) {
            case ENGLISH:
                this.langIndex = 0;
                break;
            case SPANISH:
                this.langIndex = 1;
                break;
            case FRENCH:
                this.langIndex = 2;
                break;
            case PORTUGUESE:
                this.langIndex = 3;
                break;
            case CHINESE:
                this.langIndex = 4;
                break;
            default:
                this.langIndex = 0;
        }
    }

    public static String getTranslation(Direction d, int languageIndex) {
//        DirectionType dirType = d.directionType;
//        switch(dirType) {
//            case PROCEED_FROM_TAG:
//            case GO_STRAIGHT:
//            case TURN_LEFT:
//            case SHARP_LEFT:
//            case SLIGHT_LEFT:
//            case TURN_RIGHT:
//            case SLIGHT_RIGHT:
//            case SHARP_RIGHT:
//            case ARRIVED:
//                //For most all cases, use the landmark version
//                String formatStr = translations.get(dirType.toString())[languageIndex];
//                return String.format(formatStr,d.nearbyTag.getTagName());
//            case PROCEED_TO_ELEVATOR:
//                //For elevator do not referance landmarks
//                return translations.get(dirType.toString())[languageIndex];
//            default:
//                System.err.println("UNKNOWN DIRECTION TYPE PROVIDED.");
//        }
//        return null;
        String result = translations.get(d.directionType)[languageIndex];
        if (result == null) System.err.println("UNKNOWN DIRECTION TYPE");
        return result;
    }

    // This method will generate text directions from the pathPoints

    private List<Direction> generateDirections() {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        System.out.println("Generating Directions..");
        Node currentNode, previousNode, nextNode;

        for (int i = 0; i < pathPoints.size(); i++) {
            currentNode = pathPoints.get(i);
            //Do not get directions if node is node on current floor being displayed
            if (currentNode.getFloor() != onFloor) {
                continue;
            }
            // These next two if clauses make sure we have a next point and a previous point
            if (i != 0) {
                previousNode = pathPoints.get(i - 1);
            } else {
                previousNode = null;
            }

            if (i < (pathPoints.size() - 1)) {
                nextNode = pathPoints.get(i + 1);
            } else {
                nextNode = null;
            }

            //If no next node, then we have arrived at destination
            if (nextNode == null) {
                directions.add(new Direction(ARRIVED, null));
                break;
            }
            //If next node has changed floor, we must be entering an elevator
            if (nextNode.getFloor() != onFloor) {
                directions.add(new Direction(PROCEED_TO_ELEVATOR, null));
                break;
            }
            //If there is no previous node or we've just changed floor, proceed from current location
            if (
                    (previousNode == null) ||
                            (previousNode.getFloor() != currentNode.getFloor())
                    ) {
                directions.add(new Direction(PROCEED_FROM_TAG, currentNode.getMostSpecificTag()));
                // Continue to next loop
                continue;
            }

            //If no special case, then generate a direction based on path angles
            Direction d = new Direction(previousNode, currentNode, nextNode);
//            if(d != GO_STRAIGHT) {
//                System.out.println(currentNode.getTags().size());
//            }
            directions.add(d);
        }

        return directions;
    }

    /**
     * Reduce the directions so that they are simplified
     *
     * @param directions - directions that need to be reduced
     * @return - Reduced DirectionType Set
     */
    private List<Direction> reduceDirections(List<Direction> directions) {
        List<Direction> reducedDirections = new ArrayList<Direction>();
        Direction lastDirection = null;
        for (Direction currentDirection : directions) {
            if(lastDirection != null) {
                if (!(lastDirection.directionType == GO_STRAIGHT && currentDirection.directionType == GO_STRAIGHT &&
                        lastDirection.nearbyTag == currentDirection.nearbyTag)) {
                    reducedDirections.add(currentDirection);
                }
            }
            else {
                reducedDirections.add(currentDirection);
            }
            lastDirection = currentDirection;
        }
        return reducedDirections;
    }

    private void populateDirections(List<Direction> directions) {
        //Clear saved directions
        finalDirectionTexts.clear();
        finalImages.clear();


        boolean isFirstElement = true;
        for (Direction d : directions) {
            String directionStepText = getTranslation(d, langIndex);

            String andText = "";
            String nearbyLocText = "";
            switch (langIndex) {
                case 0:
                    andText = "and then";
                    nearbyLocText = "Nearby location";
                    break;
                case 1:
                    andText = "y";
                    nearbyLocText = "Ubicaci\u00F3n cercana";
                    break;
                case 2:
                    andText = "et alors";
                    nearbyLocText = "Emplacement proche";
                    break;
                case 3:
                    andText = "e depois";
                    nearbyLocText = "Localiza\u00E7\u00E3o pr\u00F3xima";
                    break;
                case 4:
                    andText = "\u63a5\u7740";
                    nearbyLocText = "\u5173\u95ED\u4F4D\u7F6E";
                    break;
                default:
                    System.err.println("UNKNOWN LANGINDEX");
            }

            if (isFirstElement && (directionStepText != null)) {
                isFirstElement = false;
            }
            else {
                directionStepText = andText +" " + directionStepText;
            }
            directionStepText = directionStepText.substring(0, 1).toUpperCase() + directionStepText.substring(1);
            if (d.directionType != PROCEED_FROM_TAG) {
                directionStepText+=".";
            }

            if (d.nearbyTag != null) {
                if (d.directionType == PROCEED_FROM_TAG) {
                    directionStepText += d.nearbyTag.getTagName() + ".";
                }
                else {
                    directionStepText += " " + nearbyLocText + ": " + d.nearbyTag.getTagName() + ".";
                }
            }

            finalDirectionTexts.add(directionStepText);
            finalImages.add(new Image(getClass().getClassLoader().getResourceAsStream(iconFilePaths.get(d.directionType))));
        }
    }

    // This method will generate text directions from the pathPoints
    public List<String> generateTextDirections() {
        Collections.reverse(this.pathPoints);
        List<Direction> directions = reduceDirections(generateDirections());
        for(Direction d: directions) {
            System.out.println("DirectionType:"+d.directionType+" NearbyTag:"+d.nearbyTag);
        }
        Collections.reverse(this.pathPoints);
        populateDirections(directions);
        return this.finalDirectionTexts;
    }

}