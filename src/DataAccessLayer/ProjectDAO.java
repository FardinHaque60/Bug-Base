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
    
    /** TODO: to be used, edit bean functionality
     * 
     * @param bean to be edited/updated
     */
    public void updateProject(ProjectBean bean, String oldName) {
        String query = "UPDATE project SET name = ?, date = ?, description = ? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getDate());
            preparedStatement.setString(3, bean.getDescription());
            preparedStatement.setString(4, oldName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method used to delete beans
     * @param bean to be deleted
     */
    public void deleteProject(ProjectBean bean) {
    	String sql = "DELETE FROM project WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getName());

            int affectedRows = preparedStatement.executeUpdate();
            //TEST
            if (affectedRows == 1) {
            	System.out.println("Successfully Deleted " + bean.getName() + " from DB");
            }
            else {
            	System.out.println("Error deleting " + bean.getName() + " from DB");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }  
}