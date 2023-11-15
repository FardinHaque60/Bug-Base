package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketBean {

	private static ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
    // Reference to TicketDAO for database operations
    private static TicketDAO ticketDAO = TicketDAO.getTicketConnection();
    
    //projectName => Project this ticket belongs to
    private SimpleStringProperty projectName, title, description;

    
    public TicketBean(String projectName, String title, String description) {
        this.projectName = new SimpleStringProperty(projectName);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Adds ticket into the database.
     * Adds ticket to list of all tickets (used in displaying to search)
     */
    public void writeTicketBean(ProjectBean parent) {
        ticketDAO.writeTicket(this); //writes it to db
        ticketBeans.add(this); //adds it to the static list of ALL ticket beans
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
    	// clones comment bean list
    	ObservableList<CommentBean> commentBeanList = FXCollections.observableArrayList(CommentBean.getCommentBeans());
    	// remove all the comment beans that are not related to the ticket
    	commentBeanList.removeIf(commentBean -> (commentBean.getTicketParent() != this.getIdbyName()));
        return commentBeanList;
    }

    public static ObservableList<TicketBean> getTicketBeanList() {
    	return ticketBeans;
    }
    
	public static void readAllTicketsInDatabase() {
		ticketBeans = ticketDAO.readAllTickets();
	}
	
	
	//gets an id for a ticket based on its name
	public int getIdbyName() {
		String stringValue = this.title.get();
		return ticketDAO.getTicketIDByTitle(stringValue);
	}
}
