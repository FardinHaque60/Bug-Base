package DataAccessLayer;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectDAO {
	final static ProjectDAO ProjectConnection = new ProjectDAO();
	
	private ProjectDAO() {
		
	}
	
	public static ProjectDAO getProjectConnection() {
		return ProjectConnection;
	}
	/**
	 * Reads all projects from DB, should be called only once
	 * @return returns list of projectBeans 
	 */
    public static ObservableList<ProjectBean> readAllProjects() {
        ObservableList<ProjectBean> projectBeans = FXCollections.observableArrayList();
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM project");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                String description = resultSet.getString("description");
                projectBeans.add(new ProjectBean(name, date, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectBeans;
    }
    
    /**
     * Writes project bean onto DB
     * @param bean to inserted
     */
    public void writeProject(ProjectBean bean) {
        String sql = "INSERT INTO project (name, date, description) VALUES (?, ?, ?)";

        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getDate());
            preparedStatement.setString(3, bean.getDescription());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /** TODO: Not used yet, needs to be implemented
     * Method used to delete beans
     * @param bean to be deleted
     */
    public void deleteProject(ProjectBean bean) {
    	String sql = "DELETE name FROM project WHERE id = ?";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // TO-DO
            preparedStatement.setString(1, bean.getDescription());

            int affectedRows = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** TODO: look to move this potentially
     * 
     * @param projectName
     * @return
     */
    public ObservableList<TicketBean> readAllTicketsByName(String projectName) {
        ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
        String query = "SELECT * FROM ticket WHERE projectName = ?";
        try (Connection connection = SqliteConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
     
            preparedStatement.setString(1, projectName);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                TicketBean ticket = new TicketBean(projectName, title, description);
                ticketBeans.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketBeans;
    }  
}