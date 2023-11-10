package DataAccessLayer;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectDAO {
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
    public int writeProject(ProjectBean bean) {
        String sql = "INSERT INTO project (name, date, description) VALUES (?, ?, ?)";
        int generatedId = -1;

        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getDate());
            preparedStatement.setString(3, bean.getDescription());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Use a separate query to retrieve the last inserted row ID
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1); 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }
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


    public static String getProjectNameById(int projectId) {
        String projectName = "";
        String sql = "SELECT name FROM project WHERE id = ?";
        try (Connection conn = SqliteConnection.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)) { 
            pstmt.setInt(1, projectId);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                projectName = rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return projectName;
    }
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
