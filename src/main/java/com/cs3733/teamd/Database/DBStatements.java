package com.cs3733.teamd.Database;

/**
 * Created by kicknickr on 3/31/2017.
 */

/**
 * Class for the string constants of database operations.
 */
public final class DBStatements {
    public static final String CREATE_TABLE_NODE =
            "CREATE TABLE Node\n" +
                    "(\n" +
                    "\tID INTEGER PRIMARY KEY,\n" +
                    "\tX INTEGER NOT NULL,\n" +
                    "\tY INTEGER NOT NULL,\n" +
                    ")";
    public static final String CREATE_TABLE_ADJACENTNODE =
            "CREATE TABLE AdjacentNode\n" +
                    "(\n" +
                    "  N1 INTEGER ON DELETE CASCADE,\n" +
                    "  N2 INTEGER ON DELETE CASCADE,\n" +
                    "  CONSTRAINT pk_AdjNd_n1n2 PRIMARY KEY (N1,N2),\n" +
                    "  CONSTRAINT fk_AdjNd_n1 FOREIGN KEY (N1) REFERENCES Node(ID) initially deferred,\n" +
                    "  CONSTRAINT fk_AdjNd_n2 FOREIGN KEY (N2) REFERENCES Node(ID) initially deferred\n" +
                    ")";

    public static final String CREATE_TABLE_TAG =
            "CREATE TABLE Tag\n" +
                    "(\n" +
                    "  Name VARCHAR(100) PRIMARY KEY\n" +
                    ")";

    public static final String CREATE_TABLE_NODETAG =
            "CREATE TABLE NodeTag\n" +
                    "(\n" +
                    "  nodeID INTEGER ON DELETE CASCADE,\n" +
                    "  tagName VARCHAR(100) ON DELETE CASCADE,\n" +
                    "  CONSTRAINT pk_NdTg_nidtid PRIMARY KEY(nodeID, tagName),\n" +
                    "  CONSTRAINT fk_node_id FOREIGN KEY (nodeID) REFERENCES Node(ID) initially deferred,\n" +
                    "  CONSTRAINT fk_tag_id FOREIGN KEY (tagName) REFERENCES Tag(Name) initially deferred\n" +
                    ")";



    public static final String CREATE_TABLE_HCP =
            "CREATE TABLE HCP\n" +
                    "(\n" +
                    "  ID INTEGER PRIMARY KEY,\n" +
                    "  First_name VARCHAR(25) NOT NULL,\n" +
                    "  Last_name VARCHAR(25) NOT NULL\n" +
                    ")";

    public static final String CREATE_TABLE_HCPTAG =
            "CREATE TABLE HCPTag\n" +
                    "(\n" +
                    "  tagName VARCHAR(100) ON DELETE CASCADE,\n" +
                    "  hcpID INTEGER ON DELETE CASCADE,\n" +
                    "  CONSTRAINT pk_hcptg PRIMARY KEY(tagName, hcpID),\n" +
                    "  CONSTRAINT fk_node_id FOREIGN KEY (tagName) REFERENCES Tag(Name) initially deferred,\n" +
                    "  CONSTRAINT fk_hcp_id FOREIGN KEY (hcpID) REFERENCES HCP(ID) initially deferred\n" +
                    ")";

    public static final String CREATE_TABLE_PROTITLE =
            "CREATE TABLE ProTitle\n" +
                    "(\n" +
                    "  Acronym VARCHAR(5) PRIMARY KEY,\n" +
                    "  Full_title VARCHAR(50) NOT NULL\n" +
                    ")";

    public static final String CREATE_TABLE_HCPTITLE =
            "CREATE TABLE HCPTitle\n" +
                    "(\n" +
                    "  HCP_ID INTEGER ON DELETE CASCADE,\n" +
                    "  Title_Acronym VARCHAR(5) ON DELETE CASCADE,\n" +
                    "  CONSTRAINT pk_HcpTit_HcpAcy PRIMARY KEY (HCP_ID, Title_Acronym),\n" +
                    "  CONSTRAINT fk_HcpTit_hcp FOREIGN KEY (HCP_ID) REFERENCES HCP (ID) initially deferred,\n" +
                    "  CONSTRAINT fk_HcpTit_acy FOREIGN KEY (Title_Acronym) REFERENCES ProTitle (Acronym) initially deferred\n" +
                    ")";

    public static final String[] DATABASE_NAMES = {"Node", "AdjacentNode", "RoomNode", "SMP", "HCP", "HCPTitle", "ProTitle", "Tag"};


    public static final String SELECT_ALL_NODE = "SELECT * FROM Node";
    public static final String SELECT_ALL_ADJACENTNODE = "SELECT * FROM AdjacentNode";
    public static final String SELECT_ALL_NODETAG = "SELECT * FROM NodeTag";
    public static final String SELECT_ALL_TAG = "SELECT * FROM Tag";
    public static final String SELECT_ALL_HCP = "SELECT * FROM HCP";
    public static final String SELECT_ALL_HCPTAG = "SELECT * FROM HCPTag";
    public static final String SELECT_ALL_PROTITLE = "SELECT * FROM ProTitle";
    public static final String SELECT_ALL_HCPTITLE = "SELECT * FROM HCPTitle";

    public static final String MAX_ID_NODE = "SELECT MAX(ID) FROM NODE";
    public static final String MAX_ID_HCP = "SELECT MAX(ID) FROM HCP";

    public static final String INSERT_INTO_NODE = "INSERT INTO Node VALUES(?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_ADJACENTNODE = "INSERT INTO AdjacentNode VALUES(?, ?)";
    public static final String INSERT_INTO_NODETAG = "INSERT INTO NodeTag VALUES(?, ?)";
    public static final String INSERT_INTO_TAG = "INSERT INTO Tag VALUES(?)";
    public static final String INSERT_INTO_HCP = "INSERT INTO HCP VALUES(?, ?, ?)";
    public static final String INSERT_INTO_HCPTAG = "INSERT INTO HCPTag VALUES(?, ?)";
    public static final String INSERT_INTO_PROTITLE = "INSERT INTO ProTitle VALUES(?, ?)";
    public static final String INSERT_INTO_HCPTITLE = "INSERT INTO HCPTitle VALUES(?, ?)";

    public static final String DELETE_FROM_NODE = "DELETE FROM Node WHERE ID = ?";
    public static final String DELETE_FROM_HCP = "DELETE FROM HCP WHERE ID = ?";
    public static final String DELETE_FROM_TAG = "DELETE FROM Tag WHERE Name = ?";



    /**
     * Private constructor. Constants can be accessed because object is static.
     */
    private DBStatements() {}
}
