package DataAccessLayer;

import DataAccessLayer.Connection.ConnectionType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommentBean {
	
	//does not need to hold list of comments since we never need to display/search for all comments
	private final static Connection commentConnection = Connection.getCommentConnection();
	
	private SimpleStringProperty ticketParent, description, date;
	
	/**
	 * Creates a ProjectBean with the given name, date, and description.
	 * @param projectParent	name of the project it belongs to
	 * @param title			title of the ticket
	 * @param description	description of the ticket
	 */
	public CommentBean(String ticketParent, String date, String description) {
		
		// adds name and date
		this.ticketParent = new SimpleStringProperty(ticketParent);
		this.date = new SimpleStringProperty(date);
		
		//adds description as one line if user has new lines for description
		String[] tmp = description.split("\n");
		this.description = new SimpleStringProperty("");
		for (String s: tmp) {
			this.description.bind(Bindings.concat(this.description.get() + s + " "));
		}
	}
	
	/**
	 * Adds comment into the database and in the list of comments into its ticketParent.
	 */
	public void writeCommentBean() {
		commentConnection.writeComment(this);
		//adds this ticket to respective project
		TicketBean.insertComment(this);
	}

	/**
	 * Fills tickets with any existing comments from comment data in the database. Should only run once.
	 */
	public static void readAllCommentsInDatabase() {
		commentConnection.readAllComments(); //method in connection takes care of allocating comments into respective tickets
	}
	
	public String getTicketParent() {
		return ticketParent.get();
	}

	public String getDate() {
		return date.get();
	}

	public String getDescription() {
		return description.get();
	}
	
}
