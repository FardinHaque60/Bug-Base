package DataAccessLayer;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommentBean {

	// List of comments used for ticket info
    private static ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
    
    
    // Reference to CommentDAO
    private static CommentDAO commentDAO = CommentDAO.getCommentConnection();

    private SimpleIntegerProperty ticketParent;
	private SimpleStringProperty description, date;

    public CommentBean(int ticketParent, String date, String description) {
        this.ticketParent = new SimpleIntegerProperty(ticketParent);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Adds comment into the database and in the list of comments into its ticketParent.
     */
    public void writeCommentBean() {
        commentDAO.writeComment(this);
    }

    public int getTicketParent() {
        return this.ticketParent.get();
    }
    
    public static ObservableList<CommentBean> getCommentBeans() {
    	return commentBeans;
    }

    public String getDate() {
        return this.date.get();
    }

    public String getDescription() {
        return this.description.get();
    }
    
    public static void readAllCommentsInDatabase() {
		commentBeans = CommentDAO.readAllComments();
	}
}
