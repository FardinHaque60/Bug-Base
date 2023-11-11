package DataAccessLayer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Connection {
	final String PROJECT_DB = "FlatFiles/ProjectDB.txt";
	final String TICKET_DB = "FlatFiles/TicketDB.txt";
	final String COMMENT_DB = "FlatFiles/CommentDB.txt";
	
	File file;
	FileWriter fw;
	PrintWriter pw;
	Scanner scn;
	
	public enum ConnectionType {
		PROJECT, TICKET, COMMENT
	}
	
	//only instantiated once
	private final static Connection ProjectConnection = new Connection(ConnectionType.PROJECT);
	private final static Connection TicketConnection = new Connection(ConnectionType.TICKET);
	private final static Connection CommentConnection = new Connection(ConnectionType.COMMENT);
	
	//creates connections
	private Connection(ConnectionType connectionType) {
		
		// load file based on type
		switch (connectionType) {
		
			case PROJECT:
				file = new File(PROJECT_DB);
				break;
				
			case TICKET:
				file = new File(TICKET_DB);
				break;
				
			case COMMENT:
				file = new File(COMMENT_DB);
				break;
		}
		
		// creates file and print writer with given file
		try {
			fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Singleton getter methods
	public static Connection getTicketConnection() {
		return TicketConnection;
	}
	
	public static Connection getProjectConnection() {
		return ProjectConnection;
	}
	
	public static Connection getCommentConnection() {
		return CommentConnection;
	}
	
//TODO: Daniel look into refactoring lines below 
//PROJECT CONNECTION METHODS
	
	/**
	 * Adds project into the project database.
	 * @param bean	The project as a ProjectBean
	 */
	public void writeProject(ProjectBean bean) { 	
		pw.println(bean.getName());
		pw.println("\t" + bean.getDate());
		pw.println("\t" + bean.getDescription());	
		pw.flush();
	}
	
	/**
	 * Reads the whole project file database and puts them in a observable list.
	 * Will assume that this is called by Connection w/ TYPE: "Project"
	 * @return  an observable list containing ProjectBeans
	 */
	public ObservableList<ProjectBean> readAllProjects() {
		
		// initialize projectBean list
		ObservableList<ProjectBean> projectBeans = FXCollections.observableArrayList();
		
		// initialize scanner to read from project database
		try {
			scn = new Scanner(file);
		}
		catch (IOException e) {
			e.printStackTrace();
			return FXCollections.observableArrayList(); //will return empty list if catches invalid dir error
		}
		
		// fills list from all the projects in the database
		String name, date, description;
		while (scn.hasNextLine()) {
			name = scn.nextLine();
			date = scn.nextLine().replace("\t", "");
			description = scn.nextLine().replace("\t", "");
			projectBeans.add(new ProjectBean(name, date, description));
		}
		
		return projectBeans;
	}
	
//TICKET CONNECTION METHODS
	
	/**
	 * Adds project into the ticket database.
	 * @param bean	The ticket as a TicketBean
	 */
	public void writeTicket(TicketBean bean) { 	
		pw.println(bean.getProjectParent());
		pw.println("\t" + bean.getTitle());
		pw.println("\t" + bean.getDescription());	
		pw.flush();
	}
	
	/**
	 * Reads the whole ticket info from database and puts them in a observable list.
	 * Will assume that this is called by Connection w/ TYPE: "Ticket"
	 * @return  an observable list containing TicketBeans
	 */
	public ObservableList<TicketBean> readAllTickets() {
		
		// initialize projectBean list
		ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
		
		// initialize scanner to read from project database
		try {
			scn = new Scanner(file);
		}
		catch (IOException e) {
			e.printStackTrace();
			return FXCollections.observableArrayList(); //will return empty list if catches invalid dir error
		}
		
		// fills list from all the projects in the database
		String projectParent, title, description;
		while (scn.hasNextLine()) {
			projectParent = scn.nextLine();
			title = scn.nextLine().replace("\t", "");
			description = scn.nextLine().replace("\t", "");
			TicketBean t = new TicketBean(projectParent, title, description);
			ticketBeans.add(t);
			ProjectBean.insertTicket(t); //adds tickets to respective projects
		}
		
		return ticketBeans;
	}
	
//COMMENT CONNECTION METHODS	
	
	/**
	 * Adds project into the comment database.
	 * @param bean	The comment as a CommentBean
	 */
	public void writeComment(CommentBean bean) { 	
		pw.println(bean.getTicketParent());
		pw.println("\t" + bean.getDate());
		pw.println("\t" + bean.getDescription());	
		pw.flush();
	}
	
	/**
	 * Reads the whole Comment info from database and puts them in a observable list.
	 * Will assume that this is called by Connection w/ TYPE: "Comment"
	 */
	public void readAllComments() {
		
		// initialize scanner to read from comment database
		try {
			scn = new Scanner(file);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// fills list from all the projects in the database
		String ticketParent, date, description;
		while (scn.hasNextLine()) {
			ticketParent = scn.nextLine();
			date = scn.nextLine().replace("\t", "");
			description = scn.nextLine().replace("\t", "");
			CommentBean t = new CommentBean(ticketParent, date, description);
			TicketBean.insertComment(t); //adds tickets to respective projects
		}
	}
}
