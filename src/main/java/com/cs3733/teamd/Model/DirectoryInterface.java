package com.cs3733.teamd.Model;

import java.util.List;

/**
 * This class is used for database persistance
 *
 * Created by sdmichelini on 4/6/17.
 */
public interface DirectoryInterface {
    /**
     * Create's a new node and persists it to the database
     * @param x - x location of the Node
     * @param y - y location of the node
     * @param floor - which floor is the node on?
     * @return - the new node
     */
    Node saveNode(int x, int y, int floor);

    /**
     * Deletes a Node from the Database
     * @param n - Node to delete
     * @return - true if deleted, false if Node has edges or DB fails
     */
    boolean deleteNode(Node n);

    /**
     * Updates a Node in the database(most likely location)
     * @param n - Node to update
     * @return - true if succeeds, false if failure
     */
    boolean updateNode(Node n);

    /**
     * Creates a new Edge Between Two Nodes
     * @param n1 - Node 1
     * @param n2 - Node 2
     * @return true if succesful, false if there is already an edge
     */
    boolean saveEdge(Node n1, Node n2);

    /**
     * Delete's an edge between two nodes
     * @param n1 - Node 1
     * @param n2 - Node 2
     * @return true if successful, false on database error
     */
    boolean deleteEdge(Node n1, Node n2);

    /**
     * Create's a tag and persists it to the databse
     * @param name - Name of the tag
     * @return - The tag object
     */
    Tag addTag(String name);

    /**
     * Update a tag object and persist it to database
     * @param t - tag to update
     * @return - true if succesful, false if failed
     */
    boolean updateTag(Tag t);

    /**
     * Remove a tag object from the database
     * @param t - Tag to update
     * @return - true if successful, false if failure
     */
    boolean removeTag(Tag t);

    Professional saveProfessional(String name);
    Professional saveProfessional(String name, List<Title> titles, List<Tag> tags);
    boolean updateProfessional(Professional p);
    boolean removeProfessional(Professional p);

    boolean addTagToProfessional(Professional p, Tag t);
    boolean removeTagFromProfessional(Professional p, Tag t);

    boolean addTitleToProfessional(Professional p, Title t);
    boolean removeTitleFromProfessional(Professional p, Title t);

    List<Node> getNodes();
    List<Tag> getTags();
    List<Professional> getProfessionals();
}
