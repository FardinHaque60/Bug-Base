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
	
	
	//TODO: fix bug where writeProject is only writing once to DB
	//may be becasue pw is closed and never reopened 
	
	/**
	 * Adds project into the project database.
	 * @param bean	The project as a ProjectBean
	 */
	public void writeProject(ProjectBean bean) { 	
		pw.println(bean.getName());
		pw.println("\t" + bean.getDate());
		pw.println("\t" + bean.getDescription());	
		// testing line
		System.out.println(bean.getDate() + " " + bean.getName() + " " + bean.getDescription());
		pw.flush();
	}
	
	//TODO: Look into read from database
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
}
