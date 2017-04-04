package com.cs3733.teamd.Database;

import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import com.cs3733.teamd.Model.Title;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cs3733.teamd.Model.Title.*;

/**
 * Created by kicknickr on 3/31/2017.
 */
// TODO: Make singleton ??
public class DBHandler {


    private Connection connection;
    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Tag> tags = new ArrayList<>();
    public ArrayList<Professional> professionals = new ArrayList<>();

    /**
     *
     * @param c: A valid connection to a database
     */
    public DBHandler(Connection c) {
        this.connection = c;
    }

    /**
     *
     * @throws Exception when the class cannot be constructed.
     */
    public DBHandler() throws Exception {
        if(!environmentTester()) {throw new Exception();}

        try {
            // substitute your database name for myDB
            connection = DriverManager.getConnection("jdbc:derby:db;create=true");
        } catch (SQLException e) {
            System.err.println("Connection failed. Check output connectionsole.");
            e.printStackTrace();
            throw new Exception();
        }
        System.out.println("Java DB connectionnection established!");

    }


    /**
     * Tests whether the environment is set up with Java
     * DB libraries
     * @return whether or not the environment passes inspection
     */
    private boolean environmentTester() {
        System.out.println("-------Embedded Java DB Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.\n" +
                    "For IntelliJ do the following:\n" +
                    "\tFile | Project Structure, Modules, Dependency tab\n" +
                    "\tAdd by clicking on the green plus iconnection on the right of the window\n" +
                    "\tSelect JARs or directories. Go to the folder where the Java JDK is installed\n" +
                    "\tSelect the folder java/jdk1.8.xxx/db/lib where xxx is the version.\n" +
                    "\tClick OK, compile the code and run it.");
            e.printStackTrace();
            return false;
        }

        System.out.println("Java DB driver registered!");
        return true;
    }

    /**
     * Load data from database
     */
    public void load() throws SQLException {
        //TODO loop through all entity tables creating hashmap with key of PK
        //TODO loop through all assoc tables using keys to fill out assoc attribs

        Statement s = connection.createStatement();

        Map<Integer, Node> nodeMap = new HashMap<>();
        Map<String, Tag> tagMap = new HashMap<>();
        Map<Integer, Professional> professionalMap = new HashMap<>();


        //STAGE 1: Creating Entities adding associations when possible

        //LOAD NODES
        ResultSet nodeTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_NODE);
        while (nodeTupleRslt.next()) {
            int ID = nodeTupleRslt.getInt("ID");
            //create new node
            Node newNode = new Node(nodeTupleRslt.getInt("X"), nodeTupleRslt.getInt("Y"),ID);
            nodeMap.put(ID, newNode);
        }
        nodeTupleRslt.close();

        //LOAD TAGS
        ResultSet RoomTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_TAG);
        while (RoomTupleRslt.next()) {
            String newName = RoomTupleRslt.getString("tagName");
            Tag newTag = new Tag(newName);
            tagMap.put(newName, newTag);
            //newTag.addNode(nodeMap.get(ID)); //Association
        }
        RoomTupleRslt.close();

        //LOAD PROFESSIONALS
        ResultSet HCPTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_HCP);
        while (HCPTupleRslt.next()) {
            int ID = HCPTupleRslt.getInt("ID");
            ArrayList<Title> titles  = new ArrayList<Title>();
            titles.add(Title.MD);
            Professional newPro = new Professional(HCPTupleRslt.getString("First_name") + " " + HCPTupleRslt.getString("Last_name"),titles,ID);

            professionalMap.put(ID, newPro);
        }
        HCPTupleRslt.close();

//        ResultSet SMPTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_SMP);
//        while (HCPTupleRslt.next()) {
//            Professional newSMP = new Professional(
//                    SMPTupleRslt.getString("Name"));
//
//        }
//        HCPTupleRslt.close();


        //STAGE 2: Add remaining associations

        //Put adjacent nodes in nodes
        ResultSet AdjacentNodeTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_ADJACENTNODE);
        while (AdjacentNodeTupleRslt.next()) {
            Node N1 = nodeMap.get(AdjacentNodeTupleRslt.getInt("N1"));
            Node N2 = nodeMap.get(AdjacentNodeTupleRslt.getInt("N2"));
            N1.addNode(N2); //This handles both directions
        }
        AdjacentNodeTupleRslt.close();


        // Associate tags and nodes
        ResultSet NodeTapTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_NODETAG);
        while (NodeTapTupleRslt.next()) {
            Node n = nodeMap.get(NodeTapTupleRslt.getInt("nodeID"));
            Tag tag = tagMap.get(NodeTapTupleRslt.getInt("tagName"));
            n.addTag(tag);
        }
        HCPTupleRslt.close();

        // Associate professionals and tags
        ResultSet HCPRoomTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_HCPTAG);
        while (HCPRoomTupleRslt.next()) {
            Professional pro = professionalMap.get(HCPRoomTupleRslt.getInt("hcpID"));
            Tag tag = tagMap.get(HCPRoomTupleRslt.getInt("tagName"));
            pro.addTag(tag);
        }
        HCPTupleRslt.close();

        ResultSet HCPTitleTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_HCPTITLE);
        while (HCPTitleTupleRslt.next()) {
            Professional pro = professionalMap.get(HCPTitleTupleRslt.getInt("HCP_ID"));
            switch(HCPTitleTupleRslt.getString("Title_Acronym")) {
                case "RN":
                    pro.addTitle(RN);
                    break;
                case "CPNP":
                    pro.addTitle(CPNP);
                    break;
                case "MS":
                    pro.addTitle(MS);
                    break;
                case "MD":
                    pro.addTitle(MD);
                    break;
                case "DNP":
                    pro.addTitle(DNP);
                    break;
                case "DO":
                    pro.addTitle(DO);
                    break;
                case "WHNP":
                    pro.addTitle(WHNP);
                    break;
                case "PA-C":
                    pro.addTitle(PAC);
                    break;
                case "Au.D":
                    pro.addTitle(AuD);
                    break;
                case "DPM":
                    pro.addTitle(DPM);
                    break;
                case "CCC-A":
                    pro.addTitle(CCCA);
                    break;
                case "LDN":
                    pro.addTitle(LDN);
                    break;
                case "PhD":
                    pro.addTitle(PhD);
                    break;
                case "LICSW":
                    pro.addTitle(LICSW);
                    break;
                case "RD":
                    pro.addTitle(RD);
                    break;
                case "NP":
                    pro.addTitle(NP);
                    break;
                case "PsyD":
                    pro.addTitle(PsyD);
                    break;
                case "ABPP":
                    pro.addTitle(ABPP);
                    break;
                default:
                    System.err.println("Unrecognized label");
            }

        }
        HCPTitleTupleRslt.close();

        nodes = new ArrayList<>(nodeMap.values());
        tags = new ArrayList<>(tagMap.values());
        professionals = new ArrayList<>(professionalMap.values());
    }

    /**
     * Setup all tables and connectionstraints
     */
    public void setup() throws SQLException, IOException {

        Statement s = connection.createStatement();

        String[] createTables =
                {
                        DBStatements.CREATE_TABLE_NODE,
                        DBStatements.CREATE_TABLE_TAG,
                        DBStatements.CREATE_TABLE_HCP,
                        DBStatements.CREATE_TABLE_ADJACENTNODE,
                        DBStatements.CREATE_TABLE_HCPTAG,
                        DBStatements.CREATE_TABLE_PROTITLE,
                        DBStatements.CREATE_TABLE_NODETAG,
                        DBStatements.CREATE_TABLE_HCPTITLE

                };

        //Create all tables
        for (String statement : createTables) {
            System.out.println(statement);
            try {
                s.execute(statement);
            } catch (SQLException se){
                if(!se.getSQLState().equals("X0Y32")){
                    se.printStackTrace();
                }
            }
        }

        //About to do mass insert
        //Disable auto commit so deferred constraints do not activate
        connection.setAutoCommit(false);

        //Mass insert from file for initial data
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/DatabaseImports/DBInitialImports.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                s.execute(line);
            }
        }

        //Inserts done, enable connectionstraints (will check them aswell)
        connection.setAutoCommit(true);

        s.close();

    }

    /**
     * Empty all tables
     * @throws SQLException
     */
    public void empty() throws SQLException {

        Statement s = connection.createStatement();
        connection.setAutoCommit(false);

        for (String name: DBStatements.DATABASE_NAMES){
            s.execute("DELETE FROM " + name);
        }

        connection.setAutoCommit(true);
        s.close();
    }

    /**
     * Drops all tables
     * @throws SQLException
     */
    public void drop() throws SQLException {
        Statement s = connection.createStatement();
        connection.setAutoCommit(false);

        for (String name: DBStatements.DATABASE_NAMES){
            s.execute("DROP TABLE " + name);
        }

        connection.setAutoCommit(true);
        s.close();
    }

    /**
     * Closes database connection
     * @throws SQLException
     */
    public void close() throws SQLException {
        connection.close();
    }
}
