package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class TicketBean {

	private static ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
    // Reference to TicketDAO for database operations
    private static TicketDAO ticketDAO = TicketDAO.getTicketConnection();
    //projectName => Project this ticket belongs to
    private SimpleStringProperty projectName, title, description;
    private ObservableList<CommentBean> comments;

    
    public TicketBean(String projectName, String title, String description) {
        this.projectName = new SimpleStringProperty(projectName);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.comments = FXCollections.observableArrayList();
    }

    /**
     * Adds ticket into the database.
     * Adds ticket to list of all tickets (used in displaying to search)
     */
    public void writeTicketBean() {
        ticketDAO.writeTicket(this);
        ticketBeans.add(this);
    }

    public void addComment(CommentBean comment) {
        this.comments.add(comment);
    }

    public String getProjectName() {
        return this.projectName.get();
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

    public static ObservableList<TicketBean> getTicketBeanList() {
    	return ticketBeans;
    }
    
	public static void readAllTicketsInDatabase() {
		ticketBeans = ticketDAO.readAllTickets();
	}

	public int getIdbyName() {
		String stringValue = this.title.get();
		return ticketDAO.getTicketIDByTitle(stringValue);
	}
	
	//TODO: look to move
	public void loadCommentsForTicket() {
	    this.comments = ticketDAO.readAllCommentsByID(getIdbyName());
	}
}
