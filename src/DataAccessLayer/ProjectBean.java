package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectBean {

    // List of project used as results when searching
    private static ObservableList<ProjectBean> projectBeans = FXCollections.observableArrayList();

    // List of tickets in this project
    public ObservableList<TicketBean> tickets = FXCollections.observableArrayList();

    // Reference to ProjectDAO
    private static ProjectDAO projectDAO = ProjectDAO.getProjectConnection();

    private SimpleStringProperty name, date, description;

    
    // Constructor now includes ID
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

    public String getName() {
        return this.name.get();
    }

    public String getDate() {
        return this.date.get();
    }

    public String getDescription() {
        return this.description.get();
    }

    public ObservableList<TicketBean> getTicketInfo() {
        return tickets;
    }
    
    public static ObservableList<ProjectBean> getProjectBeanList() {
    	return projectBeans;
    }

//METHODS THAT ARE RAN ONCE IN INITIALIZATION
    
    public static void getAllProjectInfo() {
        projectBeans = ProjectDAO.readAllProjects();
    }
}
