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
        parent.tickets.add(this); //adds it into the list of beans within its parent
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
    	//iterate through all projects
    	//add all tickets in project p to list
    	//return final list
    	return ticketBeans;
    }
    
	public static void readAllTicketsInDatabase() {
		ticketBeans = ticketDAO.readAllTickets(); //static list of all ticket beans gets set based on db persisted entries
		loadTicketsIntoProjects(); //assigns tickets to respective parent 
		loadCommentsForTicket();
	}
	
	private static void loadTicketsIntoProjects() {
		ObservableList<ProjectBean> p = ProjectBean.getProjectBeanList();
		String parent;
		for (TicketBean t: ticketBeans) {
			parent = t.getProjectName();
			for (ProjectBean proj: p) {
				if (parent.equals(proj.getName())) {
					proj.tickets.add(t);
					break;
				}
			}
		}
	}
	
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
