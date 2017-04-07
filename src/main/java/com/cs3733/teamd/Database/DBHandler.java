package com.cs3733.teamd.Database;

import com.cs3733.teamd.Model.*;

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


    public Connection getConnection() {
        return connection;
    }

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

        open();

    }

    public void open() throws Exception {
        try {
            // substitute your database name for myDB
            connection = DriverManager.getConnection("jdbc:derby:db;create=true");
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
     * Load data from the Database into the classes Java objects
     * @throws SQLException
     */
    public void load() throws SQLException {
        //TODO loop through all entity tables creating hashmap with key of PK
        //TODO loop through all assoc tables using keys to fill out assoc attribs

        Statement s = connection.createStatement();

        Map<Integer, Node> nodeMap = new HashMap<>();
        Map<Integer, Tag> tagMap = new HashMap<>();
        Map<Integer, Professional> professionalMap = new HashMap<>();
        Map<Integer, Title> professionalTitleMap = new HashMap<>();


        //STAGE 1: Creating Entities adding associations when possible

        //LOAD NODES
        ResultSet nodeTupleRslt = s.executeQuery(Table.Nodes.selectAllStatement());
        while (nodeTupleRslt.next()) {
            int ID = nodeTupleRslt.getInt("id");
            //create new node
            Node newNode = new Node(
                    nodeTupleRslt.getInt("x"),
                    nodeTupleRslt.getInt("y"),
                    nodeTupleRslt.getInt("floor"),
                    ID);
            nodeMap.put(ID, newNode);
        }
        nodeTupleRslt.close();

        //LOAD TAGS
        ResultSet RoomTupleRslt = s.executeQuery(Table.Tags.selectAllStatement());
        while (RoomTupleRslt.next()) {
            String newName = RoomTupleRslt.getString("name");
            int id = RoomTupleRslt.getInt("id");
            Tag newTag = new Tag(newName, id);
            tagMap.put(id, newTag);
            //newTag.addNode(nodeMap.get(ID)); //Association
        }
        RoomTupleRslt.close();

        //LOAD PROFESSIONALS
        ResultSet HCPTupleRslt = s.executeQuery(Table.HCPs.selectAllStatement());
        while (HCPTupleRslt.next()) {
            int ID = HCPTupleRslt.getInt("id");
            Professional newPro = new Professional(
                    HCPTupleRslt.getString("lastName"),
                    ID);
            professionalMap.put(ID, newPro);
        }
        HCPTupleRslt.close();

        //STAGE 2: Add remaining associations

        //Put adjacent nodes in nodes
        ResultSet AdjacentNodeTupleRslt = s.executeQuery(Table.AdjacentNodes.selectAllStatement());
        while (AdjacentNodeTupleRslt.next()) {
            Node N1 = nodeMap.get(AdjacentNodeTupleRslt.getInt("n1"));
            Node N2 = nodeMap.get(AdjacentNodeTupleRslt.getInt("n2"));
            N1.addNode(N2); //This handles both directions
        }
        AdjacentNodeTupleRslt.close();


        // Associate tags and nodes
        ResultSet NodeTapTupleRslt = s.executeQuery(Table.NodeTags.selectAllStatement());
        while (NodeTapTupleRslt.next()) {
            int nodeId = NodeTapTupleRslt.getInt("nodeId");
            Node n = nodeMap.get(nodeId);
            int tagId = NodeTapTupleRslt.getInt("tagId");
            Tag tag = tagMap.get(tagId);
            if(n == null) {
                System.out.println("Node ID("+nodeId+") does not exist!");
            } else if (tag == null) {
                System.out.println("Tag ID("+tagId+") does not exist!");
            } else {
                n.addTag(tag);
            }
        }
        HCPTupleRslt.close();

        // Associate professionals and tags
        ResultSet HCPRoomTupleRslt = s.executeQuery(Table.HCPTags.selectAllStatement());
        while (HCPRoomTupleRslt.next()) {
            Professional pro = professionalMap.get(HCPRoomTupleRslt.getInt("hcpId"));
            Tag tag = tagMap.get(HCPRoomTupleRslt.getInt("tagId"));
            pro.addTag(tag);
        }
        HCPTupleRslt.close();

        // Load the professional titles
        ResultSet professtionalTitles = s.executeQuery(Table.ProfessionalTitles.selectAllStatement());
        while(professtionalTitles.next()) {
            int id = professtionalTitles.getInt("id");
            String acronym = professtionalTitles.getString("acronym");
            String fullTitle = professtionalTitles.getString("fullTitle");
            Title t;
            switch(acronym) {
                case "PA-C":
                    t = PAC;
                    break;
                case "Au.D":
                    t = AuD;
                    break;
                case "CCC-A":
                    t = CCCA;
                    break;
                default:
                    t = Title.valueOf(acronym);
                    break;
            }
            professionalTitleMap.put(id, t);
        }
        professtionalTitles.close();
        // Add titles to HCP
        ResultSet HCPTitleTupleRslt = s.executeQuery(Table.HCPTitles.selectAllStatement());
        while (HCPTitleTupleRslt.next()) {
            Professional pro = professionalMap.get(HCPTitleTupleRslt.getInt("hcpId"));
            String title = professionalTitleMap.get(HCPTitleTupleRslt.getInt("titleId")).toString();
            switch(title) {
                case "PA-C":
                    pro.addTitle(PAC);
                    break;
                case "Au.D":
                    pro.addTitle(AuD);
                    break;
                case "CCC-A":
                    pro.addTitle(CCCA);
                    break;
                default:
                    pro.addTitle(Title.valueOf(title));
                    break;
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
        // DOES THE TABLE EXIST???
        boolean empty = tablesExists(connection);
        //if no tables
        if(empty) {
            System.out.println("No database found, creating tables");
            Statement s = connection.createStatement();
            // Setup the tables
            try {
                setupTables(s);
            } catch (SQLException se) {
                if (!se.getSQLState().equals("X0Y32")) {
                    se.printStackTrace();
                }
            }


            //About to do mass insert
            //Disable auto commit so deferred constraints do not activate
            connection.setAutoCommit(false);

            //Mass insert from file for initial data
            loadDbEntriesFromFileIntoDb(s, "/DatabaseImports/DBInitialImports.txt");

            //Inserts done, enable connectionstraints (will check them aswell)
            connection.setAutoCommit(true);

            s.close();
        }
    }

    private boolean tablesExists(Connection c) throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        ResultSet res = meta.getTables(null, null, "%", null);

        boolean empty = true;

        while (res.next()){ // Look for tables that are not SYSTEM tables
            if(!res.getString("TABLE_NAME").contains("SYS")){
                empty = false;
                System.out.println("Found existing database");
                break;
            }
        }

        return empty;
    }

    /**
     * Create the tables in the Database
     * @param s - SQL Statement Object
     * @throws SQLException
     */
    public void setupTables(Statement s) throws SQLException {
        for (Table table : Table.values()) {
            executeStatement(s, table.createStatement());
        }
    }

    /**
     * Log's all SQL events
     * @param s
     * @param text
     */
    private void executeStatement(Statement s, String text) throws SQLException {
        // What log level are we at?
        if(ApplicationConfiguration.getInstance().getSqlLoggingLevel()
                == ApplicationConfiguration.SQL_LOG_LEVEL.FULL) {
            System.out.println(text);
        }
        s.execute(text);
    }

    /**
     * Load's a file of SQL statements and executes them
     * @param s - SQL Statement object
     * @param filename - What is the name of the file that contains the DB statements
     * @throws IOException
     * @throws SQLException
     */
    public void loadDbEntriesFromFileIntoDb(Statement s, String filename) throws IOException, SQLException {
        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.length() > 0) {
                    executeStatement(s, line);
                }
            }
    }

    /**
     * Empty all tables
     * @throws SQLException
     */
    public void empty() throws SQLException {

        Statement s = connection.createStatement();
        connection.setAutoCommit(false);

        for (Table table: Table.values()){
            executeStatement(s, table.emptyStatement());
        }

        connection.setAutoCommit(true);
        s.close();
    }

    public void emptyExceptTitles() throws SQLException {
        Statement s = connection.createStatement();
        connection.setAutoCommit(false);

        for (Table table : Table.values()){
            if(table != Table.ProfessionalTitles){
                s.execute(table.emptyStatement());

            }
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

        for (Table table: Table.values()){
            s.execute(table.dropStatement());
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

    //saves dir into the database
    public void save() throws SQLException{

        Directory dir = Directory.getInstance();
        Statement s = connection.createStatement();
        //wipe everything
        emptyExceptTitles();
        //for each node
        for(Node n: dir.getNodes()){
            //add to Node
            s.execute("INSERT INTO Node VALUES "+n.toSql());
        }
        //for each prof.
        for(Professional p : dir.getProfessionals()){
            System.out.println(p.toSql());
            s.execute("INSERT INTO HCP VALUES "+p.toSql());
        }
        //for each tag
        for(Tag t : dir.getTags()){
            String sql = "INSERT INTO Tag VALUES ('"
                    + t.getTagName() + "')";
            System.out.println(sql);
            s.execute(sql);
        }

        //fill in adjacentNode
        for(Node n : dir.getNodes()){
            for(Node edge : n.getNodes()){
                //create adjacent node
                s.execute("INSERT INTO AdjacentNode VALUES ("+n.getID()+","+edge.getID()+")");
            }
            //fills in NODETAG
            for(Tag t : n.getTags()){
                //add a nodetag
                s.execute("INSERT INTO NodeTag VALUES ("
                    + n.getID() + ", '"
                    + t.getTagName() + "')");
            }
        }
        //fill HCPTag
        for(Tag t : dir.getTags()){
            for(Professional p : t.getProfs()){
                s.execute("INSERT INTO HCPTag VALUES ('"
                    +t.getTagName()
                    +"',"
                    +p.getID() + ")");
            }
        }
    }

    /**
     * Save's a Node into the database and return the associated id.
     * @param x - x coordinate of the node
     * @param y - y coordinate of the node
     * @param floor - floor of the node
     * @return - ID of the new node
     */
    public int saveNode(int x, int y, int floor) {
        String sqlInsert = "INSERT INTO Node (x, y, floor) VALUES (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,x);
            statement.setInt(2,y);
            statement.setInt(3,floor);
            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0){
                return -1;
            } else {
                // Get autogenerated key
                ResultSet rs = statement.getGeneratedKeys();
                if(rs.next()) {
                    return rs.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean deleteNode(int id) {
        String sqlDelete = "DELETE FROM Node WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    public boolean updateNode(int x, int y, int id) {
        String sqlUpdate = "UPDATE Node SET x=?, y=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, id);
            statement.executeUpdate();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }


    /**
     * Save's a Tag into the database and return the associated id.
     * @param s - string of the
     * @return - ID of the new tag
     */
    public int saveTag(String s) {
        String sqlInsert = "INSERT INTO Tag (name) VALUES (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,s);
            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0){
                return -1;
            } else {
                // Get autogenerated key
                ResultSet rs = statement.getGeneratedKeys();
                if(rs.next()) {
                    return rs.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
