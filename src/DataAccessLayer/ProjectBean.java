package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectBean {

    // List of project used as results when searching
    private static ObservableList<ProjectBean> projectBeans = FXCollections.observableArrayList();

    // List of tickets in this project
    private ObservableList<TicketBean> tickets = FXCollections.observableArrayList();

    // Reference to ProjectDAO
    private static ProjectDAO projectDAO = new ProjectDAO();

    private SimpleStringProperty name, date, description;

    public ProjectBean(String name, String date, String description) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
    }

    public void writeProjectBean() {
        projectDAO.writeProject(this);
        projectBeans.add(this);
    }

    // This method will read from the DAO and update the local list
    public static void readAllProjectsInDatabase() {
        projectBeans.clear();
        projectBeans.addAll(projectDAO.readAllProjects());
    }

    // ... other methods ...

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
        // this method should return an observable list of TicketBeans related to the project
        return tickets; // Assuming 'tickets' is the ObservableList holding TicketBeans related to the project
    }

    public static ObservableList<ProjectBean> getAllProjectInfo() {
        // Assuming ProjectDAO.readAllProjects() fetches the projects from the database
        return ProjectDAO.readAllProjects();
    }
}
