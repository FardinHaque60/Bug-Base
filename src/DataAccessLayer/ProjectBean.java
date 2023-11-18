package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectBean {

    // List of project used as results when searching
    private static ObservableList<ProjectBean> projectBeans = FXCollections.observableArrayList();
    
    private ObservableList<TicketBean> tickets = FXCollections.observableArrayList();

    // Reference to ProjectDAO
    private static ProjectDAO projectDAO = ProjectDAO.getProjectConnection();
    
    private SimpleStringProperty name, date, description;

    public ProjectBean(String name, String date, String description) {
    	this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
    }

    public void writeProjectBean() {
        // This will insert the project into the database
        projectDAO.writeProject(this);

        // Add the project to the list of all projects
        projectBeans.add(this);
    }
    
    public void updateProject(String newName, String newDate, String newDescription) {
    	String oldName = this.name.get();
    	this.setName(newName);
    	this.setDate(newDate);
    	this.setDescription(newDescription);
    	
    	for (TicketBean t: tickets) {
    		t.setProjectName(newName);
    	}
    	
    	projectDAO.updateProject(this, oldName);
    }
    
    // deletes this project and all its predecessors 
    public void deleteProject() {
    	//clears all tickets under this project
    	//when deleting tickets it handles deleting its comments
    	//deleting a ticket will also remove it from its parents list
    	while (tickets.size() > 0) {
    		tickets.get(0).deleteTicket(this);
    	}
    	projectBeans.remove(this); //removes project from list of projects
    	projectDAO.deleteProject(this); //deletes it from db 
    }
    
    public void removeTicket(TicketBean t) {
    	this.tickets.remove(t);
    }
    
    //adds a ticket to a given parents list of tickets
    public void addTicket(TicketBean t) {
    	this.tickets.add(t);
    }
    
    public String getName() {
        return this.name.get();
    }
    
    public void setName(String name) {
    	this.name.set(name);
    }

    public String getDate() {
        return this.date.get();
    }
    
    public void setDate(String date) {
    	this.date.set(date);
    }

    public String getDescription() {
        return this.description.get();
    }
    
    public void setDescription(String description) {
    	this.description.set(description);
    }

    public ObservableList<TicketBean> getTicketInfo() {
        return tickets;
    }
    
//STATIC METHODS    
    
    //will insert a ticket into the correct project
    public static void insertTicket(TicketBean t) {
    	for (ProjectBean p: projectBeans) {
    		if (t.getProjectName().equals(p.getName())) {
    			p.tickets.add(t);
    			break;
    		}
    	}
    }
    
    //returns all the tickets inside application
    static ObservableList<TicketBean> getAllTickets() {
    	ObservableList<TicketBean> allTicketBeans = FXCollections.observableArrayList();
    	for (ProjectBean p: projectBeans) {
    		allTicketBeans.addAll(p.tickets);
    	}
    	return allTicketBeans;
    }
    
    //returns list of all projects in application
    public static ObservableList<ProjectBean> getProjectBeanList() {
    	return projectBeans;
    }

    //reads all the projects in DB and stores them in local list
    //ran once in initialization
    public static void readAllProjectsInDatabase() {
        projectBeans = ProjectDAO.readAllProjects();
    }
}
