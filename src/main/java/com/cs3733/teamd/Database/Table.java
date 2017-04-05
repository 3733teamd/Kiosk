package com.cs3733.teamd.Database;

/**
 * Created by tom on 4/4/17.
 */
public enum Table {
    Nodes ("Node", "(\n" +
            " ID INTEGER PRIMARY KEY,\n" +
            " X INTEGER NOT NULL,\n" +
            " Y INTEGER NOT NULL\n" +
            ")"),

    AdjacentNodes ("AdjacentNode", "(\n" +
            "  N1 INTEGER NOT NULL,\n" +
            "  N2 INTEGER NOT NULL\n" +
            ")"),

    Tags ("Tag", "(\n" +

            "  Name VARCHAR(100) PRIMARY KEY\n" +

            ")"),

    NodeTags ("NodeTag", "(\n" +
            "  nodeID INTEGER,\n" +
            "  tagName VARCHAR(100) NOT NULL\n" +
            ")"),

    HCPs ("HCP", "(\n" +
            "  ID INTEGER PRIMARY KEY,\n" +
            "  Last_name VARCHAR(25) NOT NULL\n" +
            ")"),

    HCPTags ("HCPTag", "(\n" +
            "  tagName VARCHAR(100) NOT NULL,\n" +
            "  hcpID INTEGER NOT NULL\n" +
            ")"),

    ProfessionalTitles ("ProTitle",  "(\n" +
            "  Acronym VARCHAR(5) PRIMARY KEY,\n" +
            "  Full_title VARCHAR(50) NOT NULL\n" +
            ")"),

    HCPTitles ("HCPTitle", "(\n" +
            "  HCP_ID INTEGER,\n" +
            "  Title_Acronym VARCHAR(5)\n" +
   ")" );

    String name;
    String schema;
    Table(String name, String schema){
        this.name = name;
        this.schema = schema;
    }

    public String createStatement(){
        return "CREATE TABLE " + this.name + " " + this.schema;
    }

    public String selectAllStatement(){
        return "SELECT * FROM " + this.name;
    }

    public String emptyStatement(){
        return "DELETE FROM " + this.name;
    }

    public String dropStatement(){
        return "DROP TABLE " + this.name;
    }
}
