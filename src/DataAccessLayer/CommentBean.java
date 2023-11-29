package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommentBean {
    
    // Reference to CommentDAO
    private static CommentDAO commentDAO = CommentDAO.getCommentConnection();

	private SimpleStringProperty projectAncestor, ticketParent, description, date;

    public CommentBean(String parentAncestor, String ticketParent, String date, String description) {
    	this.projectAncestor = new SimpleStringProperty(parentAncestor);
        this.ticketParent = new SimpleStringProperty(ticketParent);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Adds comment into the database and in the list of comments into its ticketParent.
     * 
     * @param TicketBean t to be written and added to list
     */
    public void writeCommentBean(TicketBean t) {
        commentDAO.writeComment(this);
        t.addComment(this);
    }
    
    public void deleteComment(TicketBean ticketBean) {
    	ticketBean.removeComment(this);
    	commentDAO.deleteComment(this);
    }
    
    //called when we are looking to update this comments description and timestamp
    public void updateComment(String newDescription, String newTimestamp) {
    	String oldDescription = this.getDescription();
    	
    	this.description.set(newDescription);
    	this.date.set(newTimestamp);
    	
    	CommentDAO.updateComment(this, oldDescription);
    }

    public String getProjectAncestor() {
    	return this.projectAncestor.get();
    }
    
    public void updateTicketParent(String newTicketParent) {
    	String oldTicketParent = ticketParent.get();
    	
    	ticketParent.set(newTicketParent); //changes local commentBean object to reflect new ticket parent name
    	
    	commentDAO.updateTicketName(this, oldTicketParent); //updates DB with new information
    }
    
    public void updateProjectName(String newProjAncestor) {
    	String oldProjectAncestor = projectAncestor.get();
    	
    	projectAncestor.set(newProjAncestor);
    	
    	commentDAO.updateProjectName(this, oldProjectAncestor);
    }
    
    public String getTicketParent() {
        return this.ticketParent.get();
    }

    public String getDate() {
        return this.date.get();
    }

    public String getDescription() {
        return this.description.get();
    }
    
    /** Reads all comments from database and calls insertComment from TicketBean class on each one
     * that method in TicketBean handles putting the comments to their respective based on
     * projectName and ticketTitle matches
     */
    public static void readAllCommentsInDatabase() {
    	ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
		commentBeans = CommentDAO.readAllComments();
		for (CommentBean c: commentBeans) {
			TicketBean.insertComment(c);
		}
	}
}
