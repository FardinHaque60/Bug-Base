package DataAccessLayer;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class TicketBean {

    // Reference to TicketDAO for database operations
    private static TicketDAO ticketDAO = new TicketDAO();
    private SimpleIntegerProperty projectId; 
    private SimpleStringProperty title, description;
    private ObservableList<CommentBean> comments;

    public TicketBean(int projectId, String title, String description) {
        this.projectId = new SimpleIntegerProperty(projectId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description.replace("\n", " "));
        this.comments = FXCollections.observableArrayList();
    }

    /**
     * Adds ticket into the database.
     */
    public void writeTicketBean() {
        ticketDAO.writeTicket(this);
    }

    /**
     * Reads all tickets from the database and initializes the tickets collection.
     */
    public static void initializeTicketsFromDatabase() {
        ObservableList<TicketBean> tickets = ticketDAO.readAllTickets();
        // Assuming there's a way to set all tickets, or the list is static and we can clear and add all
        // ... your logic to initialize tickets ...
    }

    public void addComment(CommentBean comment) {
        this.comments.add(comment);
    }

    // ... Other methods ...

    public int getProjectId() {
        return this.projectId.get();
    }
    public String getTitle() {
        return this.title.get();
    }

    public String getDescription() {
        return this.description.get();
    }
    
    public ObservableList<CommentBean> getCommentInfo() {
        return this.comments;
    }
 

	public static void readAllTicketsInDatabase() {
		// TODO Auto-generated method stub
		ticketDAO.readAllTickets();
	}

  
}
