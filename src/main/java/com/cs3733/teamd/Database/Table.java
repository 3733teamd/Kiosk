package com.cs3733.teamd.Database;

/**
 * Created by tom on 4/4/17.
 */
public enum Table {
    Nodes ("Node", "(\n" +
            " ID INTEGER PRIMARY KEY,\n" +
            "  X INTEGER NOT NULL,\n" +
            "  Y INTEGER NOT NULL\n" +
            ")"),

    AdjacentNodes ("AdjacentNode", "(\n" +
            "  N1 INTEGER NOT NULL,\n" +
            "  N2 INTEGER NOT NULL,\n" +
            "  CONSTRAINT pk_AdjNd_n1n2 PRIMARY KEY (N1,N2),\n" +
            "  CONSTRAINT fk_AdjNd_n1 FOREIGN KEY (N1) REFERENCES Node(ID) initially deferred,\n" +
            "  CONSTRAINT fk_AdjNd_n2 FOREIGN KEY (N2) REFERENCES Node(ID) initially deferred\n" +
            ")"),

    Tags ("Tag", "(\n" +
            "  nodeID INTEGER,\n" +
            "  tagName VARCHAR(100) NOT NULL,\n" +
            "  CONSTRAINT pk_NdTg_nidtid PRIMARY KEY(nodeID, tagName),\n" +
            "  CONSTRAINT fk_node_id FOREIGN KEY (nodeID) REFERENCES Node(ID) initially deferred,\n" +
            "  CONSTRAINT fk_tag_id FOREIGN KEY (tagName) REFERENCES Tag(Name) initially deferred\n" +
            ")"),

    NodeTags ("NodeTag", "(\n" +
            "  nodeID INTEGER,\n" +
            "  tagName VARCHAR(100) NOT NULL,\n" +
            "  CONSTRAINT pk_NdTg_nidtid PRIMARY KEY(nodeID, tagName),\n" +
            "  CONSTRAINT fk_node_id FOREIGN KEY (nodeID) REFERENCES Node(ID) initially deferred,\n" +
            "  CONSTRAINT fk_tag_id FOREIGN KEY (tagName) REFERENCES Tag(Name) initially deferred\n" +
            ")"),

    HCPs ("HCP", "(\n" +
            "  ID INTEGER PRIMARY KEY,\n" +
            "  First_name VARCHAR(25) NOT NULL,\n" +
            "  Last_name VARCHAR(25) NOT NULL\n" +
            ")"),

    HCPTags ("HCPTag", "(\n" +
            "  tagName VARCHAR(100) NOT NULL,\n" +
            "  hcpID INTEGER NOT NULL,\n" +
            "  CONSTRAINT pk_hcptg PRIMARY KEY(tagName, hcpID),\n" +
            "  CONSTRAINT fk_node_id FOREIGN KEY (tagName) REFERENCES Tag(Name) initially deferred,\n" +
            "  CONSTRAINT fk_hcp_id FOREIGN KEY (hcpID) REFERENCES HCP(ID) initially deferred\n" +
            ")"),

    ProfessionalTitles ("ProTitle",  "(\n" +
            "  Acronym VARCHAR(5) PRIMARY KEY,\n" +
            "  Full_title VARCHAR(50) NOT NULL\n" +
            ")"),

    HCPTitles ("HCPTitle", "(\n" +
            "  HCP_ID INTEGER,\n" +
            "  Title_Acronym VARCHAR(5),\n" +
            "  CONSTRAINT pk_HcpTit_HcpAcy PRIMARY KEY (HCP_ID, Title_Acronym),\n" +
            "  CONSTRAINT fk_HcpTit_hcp FOREIGN KEY (HCP_ID) REFERENCES HCP (ID) initially deferred,\n" +
            "  CONSTRAINT fk_HcpTit_acy FOREIGN KEY (Title_Acronym) REFERENCES ProTitle (Acronym) initially deferred\n" +
            ")");

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
