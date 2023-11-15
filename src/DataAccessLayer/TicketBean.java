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
    public ObservableList<CommentBean> comments = FXCollections.observableArrayList();

    
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
		loadCommentsForTicket();
	}
	
	/*
	
	//TODO: While I am doing this make sure to assign tickets too
	//loads all the tickets for all the projects, ran once in initializing
	//called by projectBean 
    private static void loadTicketsForProjects() {
        int i;
        String projName;
        ProjectBean currBean;
        for (i = 0; i < projectBeans.size(); i++) {
        	currBean = projectBeans.get(i);
        	projName = currBean.getName();
        	currBean.tickets = projectDAO.readAllTicketsByName(projName);
        }
    }
    
    */
	
	//iterates through all tickets and adds their comments for them
	//using a ticket id, the ticketDAO can get all the comments for it
	private static void loadCommentsForTicket() {
		for (TicketBean t: getTicketBeanList()) {
			t.comments = ticketDAO.readAllCommentsByID(t.getIdbyName());
		}
	}
	
	//gets an id for a ticket based on its name
	public int getIdbyName() {
		String stringValue = this.title.get();
		return ticketDAO.getTicketIDByTitle(stringValue);
	}
}
