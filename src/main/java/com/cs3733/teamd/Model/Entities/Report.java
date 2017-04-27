package com.cs3733.teamd.Model.Entities;

/**
 * Created by benjamin on 4/25/2017.
 */
public class Report {
    public String tagText;
    public String commentText;
    public String status;
    public int ID;

    private DirectoryInterface dir;

    public Report (){}

    public Report (String tag, String comment, String statusIn, int ID){
        // tag, comment, status
        this.tagText=tag;
        this.commentText=comment;
        this.status=statusIn;
        this.ID=ID;
    }

    public Report (String tag, String comment, String statusIn){
        // tag, comment, status
        this.tagText=tag;
        this.commentText=comment;
        this.status=statusIn;
    }

    public Report (String tag, String comment){
        // tag, comment, status
        this.tagText=tag;
        this.commentText=comment;
    }

    public String getTagText(){return tagText;}
    public String getCommentText(){return commentText;}
    public String getStatus(){return status;}

    public int getID(){return ID;}

    public void setStatus(String newStatus){
        status=newStatus;
    }



}
