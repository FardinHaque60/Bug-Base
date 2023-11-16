package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketDAO {

	final static TicketDAO TicketConnection = new TicketDAO();
	
	private static Connection connection;
	
	private TicketDAO() {
		connection = SqliteConnection.getConnection();
	}
	
	public static TicketDAO getTicketConnection() { return TicketConnection; }
	
	/** reads all tickets in DB 
	 * @returns list of tickets
	 */
	public ObservableList<TicketBean> readAllTickets() {
        ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
        String query = "SELECT * FROM ticket";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bean.getTitle());
            preparedStatement.setString(2, bean.getDescription());
            preparedStatement.setString(3, bean.getProjectName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateProjectName(TicketBean bean) {
    	String query = "UPDATE ticket SET projectName = ? WHERE title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bean.getProjectName());
            preparedStatement.setString(2, bean.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param projectParent that this ticket belongs to
     * @param title of this ticket
     */
    public void deleteTicket(String projectParent, String title) {
        String query = "DELETE FROM ticket WHERE projectName = ? AND title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, projectParent);
            preparedStatement.setString(2, title);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            //TEST
            if (rowsAffected == 1) {
            	System.out.println("Successfully deleted " + title + " from DB");
            }
            else {
            	System.out.println("Error deleting " + title + " from DB");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
