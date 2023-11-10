package DataAccessLayer;

import javafx.beans.property.SimpleIntegerProperty;
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

    // Properties including ID
    private SimpleIntegerProperty id;
    private SimpleStringProperty name, date, description;

    // Constructor now includes ID
    public ProjectBean(String name, String date, String description) {
        // ID will be null or a default value initially
        this.id = new SimpleIntegerProperty(-1); // or null if you change the type to IntegerProperty
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
    }

    public void writeProjectBean() {
        // This will insert the project into the database and return the generated ID
        int generatedId = projectDAO.writeProject(this);

        // Set the generated ID back to the bean
        this.id.set(generatedId);

        // Add the project with the new ID to the list
        projectBeans.add(this);
    }
    public int getProjectId() {
        return this.id.get();
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

    public static ObservableList<ProjectBean> getAllProjectInfo() {
        return ProjectDAO.readAllProjects();
    }
    public void loadTicketsForProject() {
        // You would need to implement this method in the ProjectDAO or similar
        this.tickets = projectDAO.readAllTicketsByName(this.getName());
    }
   
    


}
