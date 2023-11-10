package DataAccessLayer;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CommentBean {

    // Reference to CommentDAO
    private static CommentDAO commentDAO = new CommentDAO();

    SimpleIntegerProperty ticketParent;

	private SimpleStringProperty description, date;

    public CommentBean(int ticketParent, String date, String description) {
        this.ticketParent = new SimpleIntegerProperty(ticketParent);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description.replace("\n", " "));
    }

    /**
     * Adds comment into the database and in the list of comments into its ticketParent.
     */
    public void writeCommentBean(TicketBean ticket) {
        commentDAO.writeComment(this);

    }

    public int getTicketParent() {
        return this.ticketParent.get();
    }

    public String getDate() {
        return this.date.get();
    }

    public String getDescription() {
        return this.description.get();
    }
    public static void readAllCommentsInDatabase() {
       
        CommentDAO.readAllComments();
    }

    
}
