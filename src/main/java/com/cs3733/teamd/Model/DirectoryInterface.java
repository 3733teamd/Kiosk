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
    Tag saveTag(String name);

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
    boolean deleteTag(Tag t);

    /**
     * Create a professional and persist it to database with only the name parameter
     * @param name - Name of the prof
     * @return - the Porf object
     */
    Professional saveProfessional(String name);
    /**
     * Create a professional and persist it to database with a full list of params
     * @param name - Name of the prof
     * @param titles - the titles used by the prof
     * @param tags - the tags the prof is in
     * @return - the Porf object
     */
    Professional saveProfessional(String name, List<Title> titles, List<Tag> tags);
    /**
     * Update a Professional object and persist it to database
     * @param p - prof to update
     * @return - true if succesful, false if failed
     */
    boolean updateProfessional(Professional p);
    /**
     * Remove a Professional object and persist it to database
     * @param p - prof to remove
     * @return - true if succesful, false if failed
     */
    boolean removeProfessional(Professional p);
    /**
     * Adds a tag to a prof, and vice-versa
     * @param p - prof to link
     * @param t - tag to link
     * @return - true if succesful, false if failed
     */
    boolean addTagToProfessional(Professional p, Tag t);
    /**
     * remove a tag from a prof, and vice-versa
     * @param p - prof to unlink
     * @param t - tag to unlink
     * @return - true if succesful, false if failed
     */
    boolean removeTagFromProfessional(Professional p, Tag t);
    /**
     * Adds a title to a prof
     * @param p - prof to add the title to
     * @param t - title to be added
     * @return - true if succesful, false if failed
     */
    boolean addTitleToProfessional(Professional p, ProTitle t);
    /**
     * removes a title from a prof
     * @param p - the prof to lose his title
     * @param t - the title to be lost
     * @return - true if succesful, false if failed
     */
    boolean removeTitleFromProfessional(Professional p, ProTitle t);

    /**
     * gets rid of node-tag pairing
     * @param n the node to be removed
     * @param t the tag to be removed
     * @return - true if succesful, false if failed
     */
    boolean removeNodeTag(Node n, Tag t);
    /**
     * addsd node-tag pairing
     * @param n the node to be added
     * @param t the tag to be added
     * @return - true if succesful, false if failed
     */
    boolean addNodeTag(Node n, Tag t);

    /**
     * gets all nodes
     * @return - the list of all current nodes
     */
    List<Node> getNodes();
    /**
     * gets all tags
     * @return - the list of all current tags
     */
    List<Tag> getTags();
    /**
     * gets all profs
     * @return - the list of all current profs
     */
    List<Professional> getProfessionals();

    User loginUser(String username, String password);
    User getCurrentUser();
    void logoutUser();

    boolean createUser(String username, String password);

    /**
     *
     * @return
     */
    List<ProTitle> getTitles();

    /**
     *
     * @param acronym
     * @param title
     * @return
     */
    ProTitle addTitle(String acronym, String title);
}
