package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class TicketBean {

    // Reference to TicketDAO for database operations
    private static TicketDAO ticketDAO = new TicketDAO();
    private SimpleStringProperty projectName, title, description;
    private ObservableList<CommentBean> comments;

    
    public TicketBean(String projectName, String title, String description) {
        this.projectName = new SimpleStringProperty(projectName);
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

	public static void readAllTicketsInDatabase() {
		// TODO Auto-generated method stub
		ticketDAO.readAllTickets();
	}

	public int getIdbyName() {
		// TODO Auto-generated method stub
		String stringValue = this.title.get();
		return ticketDAO.getTicketIDByTitle(stringValue);
	}
	public void loadCommentsForTicket() {
	   
	    this.comments = ticketDAO.readAllCommentsByID(getIdbyName());
	   
	}
	

  
}
