package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketBean {

	private ObservableList<CommentBean> comments = FXCollections.observableArrayList();
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
        parent.addTicket(this);
    }
    
    public void deleteTicket(ProjectBean parent) {
    	this.deleteComments();
    	parent.removeTicket(this);
    	ticketDAO.deleteTicket(getProjectName(), getTitle());
    }
    
    public void deleteComments() {
    	this.comments.clear();
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
    	return comments;
    }
    
    public void addComment(CommentBean c) {
    	this.comments.add(c);
    }

//TICKET STATIC METHODS BELOW
    
   /** Calls ProjectBean getAllTickets so it can go through all the projects and all those tickets
    * 
    * @return returns a list of ticket beans
    */
    public static ObservableList<TicketBean> getTicketBeanList() {
    	return ProjectBean.getAllTickets();
    }
    
    /** When reading all ticket beans from database it calls insertTicket from ProjectBean class
     * that method in projectBean class allocated the given ticket the right projectParent based on its name
     */
	public static void readAllTicketsInDatabase() {
		ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
		ticketBeans = ticketDAO.readAllTickets();
		for (TicketBean t: ticketBeans) {
			ProjectBean.insertTicket(t);
		}
	}
	
	public static void insertComment(CommentBean c) {
		ObservableList<TicketBean> ticketBeans = ProjectBean.getAllTickets();
		
		for (TicketBean t: ticketBeans) {
			if (t.getTitle().equals(c.getTicketParent()) && t.getProjectName().equals(c.getProjectAncestor())) {
				t.addComment(c);
				break;
			}
		}
	}
}
