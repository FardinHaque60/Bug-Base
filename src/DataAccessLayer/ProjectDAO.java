package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectDAO {
	final static ProjectDAO ProjectConnection = new ProjectDAO();
	
	private static Connection connection;
	
	private ProjectDAO() {
		connection = SqliteConnection.getConnection();
	}
	
	public static ProjectDAO getProjectConnection() { return ProjectConnection; }

//METHODS FOR INITIALIZATION START
	
	/**
	 * Reads all projects from DB, should be called only once
	 * @return returns list of projectBeans 
	 */
    public static ObservableList<ProjectBean> readAllProjects() {
        ObservableList<ProjectBean> projectBeans = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM project");
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
    
//METHODS FOR INITIALIZATION END
    
    /**
     * Writes project bean onto DB
     * @param bean to inserted
     */
    public void writeProject(ProjectBean bean) {
        String sql = "INSERT INTO project (name, date, description) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getDate());
            preparedStatement.setString(3, bean.getDescription());
            
            preparedStatement.executeUpdate();

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // TODO: not finished implementing yet
            preparedStatement.setString(1, bean.getDescription());

            int affectedRows = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }  
}