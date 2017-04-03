package com.cs3733.teamd.Database;

import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;

import java.io.BufferedReader;
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


    private Connection con;
    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Tag> tags = new ArrayList<>();
    public ArrayList<Professional> professionals = new ArrayList<>();

    /**
     *
     * @param c: A valid connection to a database
     */
    public DBHandler(Connection c) {
        this.con = c;
    }

    /**
     *
     * @throws Exception when the class cannot be constructed.
     */
    public DBHandler() throws Exception {
        if(!EnvironmentTester()) {throw new Exception();}
        Connection connection = null;

        try {
            // substitute your database name for myDB
            con = DriverManager.getConnection("jdbc:derby:db;create=true");
        } catch (SQLException e) {
            System.err.println("Connection failed. Check output console.");
            e.printStackTrace();
            throw new Exception();
        }
        System.out.println("Java DB connection established!");

    }


    /**
     * Tests whether the environment is set up with Java
     * DB libraries
     * @return whether or not the environment passes inspection
     */
    private boolean EnvironmentTester() {
        System.out.println("-------Embedded Java DB Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.\n" +
                    "For IntelliJ do the following:\n" +
                    "\tFile | Project Structure, Modules, Dependency tab\n" +
                    "\tAdd by clicking on the green plus icon on the right of the window\n" +
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
    public void Load() throws SQLException {
        //TODO loop through all entity tables creating hashmap with key of PK
        //TODO loop through all assoc tables using keys to fill out assoc attribs

        Statement s = con.createStatement();

        Map<Integer, Node> nodeMap = new HashMap<>();
        Map<Integer, Tag> tagMap = new HashMap<>();
        Map<Integer, Professional> professionalMap = new HashMap<>();


        //STAGE 1: Creating Entities adding associations when possible

        ResultSet nodeTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_NODE);
        while (nodeTupleRslt.next()) {
            Node newNode = new Node(nodeTupleRslt.getInt("X"), nodeTupleRslt.getInt("Y"));
            int ID = nodeTupleRslt.getInt("ID");
            nodeMap.put(ID, newNode);
        }
        nodeTupleRslt.close();

        ResultSet RoomTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_ROOMNODE);
        while (RoomTupleRslt.next()) {
            Tag roomTag = new Tag(RoomTupleRslt.getString("Title"));
            int ID = RoomTupleRslt.getInt("ID");
            tagMap.put(ID, roomTag);
            roomTag.addNode(nodeMap.get(ID)); //Association
        }
        RoomTupleRslt.close();

        ResultSet HCPTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_HCP);
        while (HCPTupleRslt.next()) {
            Professional newPro = new Professional(
                    HCPTupleRslt.getString("First_name") + " " + HCPTupleRslt.getString("Last_name"));
            int ID = HCPTupleRslt.getInt("ID");
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

        // Associate professionals and tags
        ResultSet HCPRoomTupleRslt = s.executeQuery(DBStatements.SELECT_ALL_HCPROOM);
        while (HCPRoomTupleRslt.next()) {
            Professional pro = professionalMap.get(HCPRoomTupleRslt.getInt("HCP_ID"));
            Tag tag = tagMap.get(HCPRoomTupleRslt.getInt("Room_ID"));
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
     * Setup all tables and constraints
     */
    public void Setup() throws Exception {

        Statement s = con.createStatement();

        String[] createTables =
                {
                        DBStatements.CREATE_TABLE_NODE,
                        DBStatements.CREATE_TABLE_ADJACENTNODE,
                        DBStatements.CREATE_TABLE_ROOMNODE,
                        DBStatements.CREATE_TABLE_PROTITLE,
                        DBStatements.CREATE_TABLE_HCP,
                        DBStatements.CREATE_TABLE_HCPROOM,
                        DBStatements.CREATE_TABLE_HCPTITLE,
                        DBStatements.CREATE_TABLE_SMP,
                        DBStatements.CREATE_TABLE_SMPROOM
                };

        //Create all tables
        for (String statement : createTables) {
                s.execute(statement);
        }

        //About to do mass insert
        //Disable auto commit so deferred constraints do not activate
        con.setAutoCommit(false);

        //Mass insert from file for initial data
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/DatabaseImports/DBInitialImports.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                s.execute(line);
            }
        }

        //Inserts done, enable constraints (will check them aswell)
        con.setAutoCommit(true);

        s.close();

    }

    /**
     * Empty all tables
     */
    public void Empty() {
        try {
            Statement s;
            s = con.createStatement();

            con.setAutoCommit(false);

            String[] emptyTables =
                    {
                            DBStatements.EMPTY_TABLE_ROOMNODE,
                            DBStatements.EMPTY_TABLE_ADJACENTNODE,
                            DBStatements.EMPTY_TABLE_HCP,
                            DBStatements.EMPTY_TABLE_HCPROOM,
                            DBStatements.EMPTY_TABLE_HCPTITLE,
                            DBStatements.EMPTY_TABLE_NODE,
                            DBStatements.EMPTY_TABLE_PROTITLE,
                            DBStatements.EMPTY_TABLE_SMP,
                            DBStatements.EMPTY_TABLE_SMPROOM
                    };

            for (String statement : emptyTables) { s.execute(statement); }

            con.setAutoCommit(true);

            s.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Close() throws SQLException {
        con.close();
    }
}
