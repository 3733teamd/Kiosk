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
                    "\tLocationTypeID VARCHAR(4) NOT NULL ,\n" +
                    "\tX INTEGER NOT NULL,\n" +
                    "\tY INTEGER NOT NULL,\n" +
                    "\tBuilding VARCHAR(10) NOT NULL,\n" +
                    "\tFloor INTEGER NOT NULL,\n" +
                    "\tCONSTRAINT uq_Nd_IDType UNIQUE (ID,LocationTypeID)\n" +
                    ")";
    public static final String CREATE_TABLE_ADJACENTNODE =
            "CREATE TABLE AdjacentNode\n" +
                    "(\n" +
                    "  N1 INTEGER NOT NULL,\n" +
                    "  N2 INTEGER NOT NULL,\n" +
                    "  CONSTRAINT pk_AdjNd_n1n2 PRIMARY KEY (N1,N2),\n" +
                    "  CONSTRAINT fk_AdjNd_n1 FOREIGN KEY (N1) REFERENCES Node(ID) initially deferred,\n" +
                    "  CONSTRAINT fk_AdjNd_n2 FOREIGN KEY (N2) REFERENCES Node(ID) initially deferred\n" +
                    ")";

    public static final String CREATE_TABLE_ROOMNODE =
            "CREATE TABLE RoomNode\n" +
                    "(\n" +
                    "  ID INTEGER PRIMARY KEY,\n" +
                    "  LocationTypeID VARCHAR(4) NOT NULL,\n" +
                    "  Title VARCHAR(10) NOT NULL,\n" +
                    "  CONSTRAINT fk_RmNd_IsA_IDLocType FOREIGN KEY (ID, LocationTypeID) REFERENCES Node(ID, LocationTypeID) initially deferred,\n" +
                    "  CONSTRAINT ck_RmNd_is_rm CHECK (LocationTypeID IN ('ROOM'))\n" +
                    ")";

    public static final String CREATE_TABLE_SMP =
            "CREATE TABLE SMP\n" +
                    "(\n" +
                    "  Name VARCHAR(100) PRIMARY KEY\n" +
                    ")";

    public static final String CREATE_TABLE_HCP =
            "CREATE TABLE HCP\n" +
                    "(\n" +
                    "  ID INTEGER PRIMARY KEY,\n" +
                    "  First_name VARCHAR(25) NOT NULL,\n" +
                    "  Last_name VARCHAR(25) NOT NULL\n" +
                    ")";

    public static final String CREATE_TABLE_SMPROOM =
            "CREATE TABLE SMPRoom\n" +
                    "(\n" +
                    "  SMP_name VARCHAR(100),\n" +
                    "  Room_ID INTEGER,\n" +
                    "  CONSTRAINT pk_SmpRm_SmpRm PRIMARY KEY (SMP_name, Room_ID),\n" +
                    "  CONSTRAINT fk_SmpRm_smp FOREIGN KEY (SMP_name) REFERENCES  SMP (Name) initially deferred,\n" +
                    "  CONSTRAINT fk_SmpRm_rm FOREIGN KEY (Room_ID) REFERENCES  RoomNode (ID) initially deferred\n" +
                    ")";

    public static final String CREATE_TABLE_HCPROOM =
            "CREATE TABLE HCPRoom\n" +
                    "(\n" +
                    "  HCP_ID INTEGER,\n" +
                    "  Room_ID INTEGER,\n" +
                    "  CONSTRAINT pk_HcpRm_SmpRm PRIMARY KEY (HCP_ID, Room_ID),\n" +
                    "  CONSTRAINT fk_HcpRm_hcp FOREIGN KEY (HCP_ID) REFERENCES  HCP (ID) initially deferred,\n" +
                    "  CONSTRAINT fk_HmpRm_rm FOREIGN KEY (Room_ID) REFERENCES  RoomNode (ID) initially deferred\n" +
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
                    "  HCP_ID INTEGER,\n" +
                    "  Title_Acronym VARCHAR(5),\n" +
                    "  CONSTRAINT pk_HcpTit_HcpAcy PRIMARY KEY (HCP_ID, Title_Acronym),\n" +
                    "  CONSTRAINT fk_HcpTit_hcp FOREIGN KEY (HCP_ID) REFERENCES HCP (ID) initially deferred,\n" +
                    "  CONSTRAINT fk_HcpTit_acy FOREIGN KEY (Title_Acronym) REFERENCES ProTitle (Acronym) initially deferred\n" +
                    ")";

    public static final String DROP_TABLE_NODE = "DROP TABLE Node";
    public static final String DROP_TABLE_ADJACENTNODE = "DROP TABLE AdjacentNode";
    public static final String DROP_TABLE_ROOMNODE = "DROP TABLE RoomNode";
    public static final String DROP_TABLE_SMP = "DROP TABLE SMP";
    public static final String DROP_TABLE_HCP = "DROP TABLE HCP";
    public static final String DROP_TABLE_SMPROOM = "DROP TABLE SMPRoom";
    public static final String DROP_TABLE_HCPROOM = "DROP TABLE HCPRoom";
    public static final String DROP_TABLE_PROTITLE = "DROP TABLE ProTitle";
    public static final String DROP_TABLE_HCPTITLE = "DROP TABLE HCPTitle";

    public static final String EMPTY_TABLE_NODE = "DELETE FROM Node";
    public static final String EMPTY_TABLE_ADJACENTNODE = "DELETE FROM AdjacentNode";
    public static final String EMPTY_TABLE_ROOMNODE = "DELETE FROM RoomNode";
    public static final String EMPTY_TABLE_SMP = "DELETE FROM SMP";
    public static final String EMPTY_TABLE_HCP = "DELETE FROM HCP";
    public static final String EMPTY_TABLE_SMPROOM = "DELETE FROM SMPRoom";
    public static final String EMPTY_TABLE_HCPROOM = "DELETE FROM HCPRoom";
    public static final String EMPTY_TABLE_PROTITLE = "DELETE FROM ProTitle";
    public static final String EMPTY_TABLE_HCPTITLE = "DELETE FROM HCPTitle";

    public static final String SELECT_ALL_NODE = "SELECT * FROM Node";
    public static final String SELECT_ALL_ADJACENTNODE = "SELECT * FROM AdjacentNode";
    public static final String SELECT_ALL_ROOMNODE = "SELECT * FROM RoomNode";
    public static final String SELECT_ALL_SMP = "SELECT * FROM SMP";
    public static final String SELECT_ALL_HCP = "SELECT * FROM HCP";
    public static final String SELECT_ALL_SMPROOM = "SELECT * FROM SMPRoom";
    public static final String SELECT_ALL_HCPROOM = "SELECT * FROM HCPRoom";
    public static final String SELECT_ALL_PROTITLE = "SELECT * FROM ProTitle";
    public static final String SELECT_ALL_HCPTITLE = "SELECT * FROM HCPTitle";


    /**
     * Private constructor. Constants can be accessed because object is static.
     */
    private DBStatements() {}
}
