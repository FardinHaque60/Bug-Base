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
    
    public void deleteComment() {
    	commentDAO.deleteComment(this);
    }

    public String getProjectAncestor() {
    	return this.projectAncestor.get();
    }
    
    public void updateProjectName(String newProjAncestor) {
    	projectAncestor.set(newProjAncestor);
    	
    	commentDAO.updateProjectName(this);
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
