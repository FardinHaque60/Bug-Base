package DataAccessLayer;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketBean {

	//list of all tickets, used to show up in search results
	private static ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
	private final static Connection ticketConnection = Connection.getTicketConnection();
	
	private SimpleStringProperty projectParent, title, description;
	
	/**
	 * Creates a ProjectBean with the given name, date, and description.
	 * @param projectParent	name of the project it belongs to
	 * @param title			title of the ticket
	 * @param description	description of the ticket
	 */
	public TicketBean(String projectParent, String title, String description) {
		
		// adds name and date
		this.projectParent = new SimpleStringProperty(projectParent);
		this.title = new SimpleStringProperty(title);
		
		//adds description as one line if user has new lines for description
		String[] tmp = description.split("\n");
		this.description = new SimpleStringProperty("");
		for (String s: tmp) {
			this.description.bind(Bindings.concat(this.description.get() + s + " "));
		}
	}
	
	/**
	 * Adds project into the database and in the list.
	 */
	public void writeTicketBean() {
		ticketConnection.writeTicket(this);
		ticketBeans.add(this);
		//adds this ticket to respective project
		ProjectBean.insertTicket(this);
	}
	
	/**
	 * Gets the project bean observable list.
	 * @return all the project beans as an observable list
	 */
	public static ObservableList<TicketBean> getAllTicketInfo() {
		return ticketBeans;
	}

	/**
	 * Fills projectBeans list will all the project data in the database. Should only run once.
	 */
	public static void readAllTicketsInDatabase() {
		ticketBeans.clear(); // don't really need this line, but added just in case it runs more than once.
		ticketBeans.addAll(ticketConnection.readAllTickets());
	}
	
	public String getProjectParent() {
		return projectParent.get();
	}

	public String getTitle() {
		return title.get();
	}

	public String getDescription() {
		return description.get();
	}
	
	
}
