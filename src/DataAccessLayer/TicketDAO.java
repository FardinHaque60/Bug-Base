package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketDAO {

	final static TicketDAO TicketConnection = new TicketDAO();
	
	private TicketDAO() {
		
	}
	
	public static TicketDAO getTicketConnection() {
		return TicketConnection;
	}
	
	/** reads all tickets in DB 
	 * @returns list of tickets
	 */
	public ObservableList<TicketBean> readAllTickets() {
        ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
        String query = "SELECT * FROM ticket";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String projectName = resultSet.getString("projectName");
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

	/**
	 * Writes Ticket Bean to DB
	 * @param bean to be written
	 */
    public void writeTicket(TicketBean bean) {
        String sql = "INSERT INTO ticket (projectName, title, description) VALUES (?, ?, ?)";

        try (Connection connection = SqliteConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, bean.getProjectName()); 
            preparedStatement.setString(2, bean.getTitle());
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
    public void updateTicket(TicketBean bean) {
        String query = "UPDATE ticket SET title = ?, description = ? WHERE projectName = ?";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bean.getTitle());
            preparedStatement.setString(2, bean.getDescription());
            preparedStatement.setString(3, bean.getProjectName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** TODO: To be used, delete bean functionality
     * 
     * @param projectParent that this ticket belongs to
     * @param title of this ticket
     */
    public void deleteTicket(String projectParent, String title) {
        String query = "DELETE FROM ticket WHERE projectName = ? AND title = ?";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, projectParent);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** given a tickets title we can return its id
     * 
     * @param title of the ticket
     * @return id of the ticket
     */
    public int getTicketIDByTitle(String title) {
        String query = "SELECT ticketID FROM ticket WHERE title = ?";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);

         
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ticketID");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In case of SQL error, handle accordingly
            // You could return a default value or throw a custom exception
            return -1;
        }
    }
    
    /** TODO: move to different class?
     * 
     * @param id
     * @return
     */
    public ObservableList<CommentBean> readAllCommentsByID(int id) {
        ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
        // Updated query with a WHERE clause to filter by project name
        String query = "SELECT * FROM comment WHERE TicketID = ?";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set the project name parameter in the prepared statement
            preparedStatement.setLong(1, id);
            
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Since we're filtering by projectName, we already know it matches the input parameter
                String date = resultSet.getString("date");
                String description = resultSet.getString("description");
                CommentBean comment = new CommentBean(id, date, description);
                commentBeans.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentBeans;
    }
}
