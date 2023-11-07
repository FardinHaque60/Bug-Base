package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketDAO {

    public ObservableList<TicketBean> readAllTickets() {
        ObservableList<TicketBean> ticketBeans = FXCollections.observableArrayList();
        String query = "SELECT * FROM ticket";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int projectId = resultSet.getInt("project");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                TicketBean ticket = new TicketBean(projectId, title, description);
                ticketBeans.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketBeans;
    }

    public void writeTicket(TicketBean bean) {
        String sql = "INSERT INTO ticket (ProjectID, Title, Description) VALUES (?, ?, ?)";

        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, bean.getProjectId()); // Set ProjectID
            preparedStatement.setString(2, bean.getTitle());
            preparedStatement.setString(3, bean.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTicket(TicketBean bean) {
        String query = "UPDATE ticket SET title = ?, description = ? WHERE project_parent = ?";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bean.getTitle());
            preparedStatement.setString(2, bean.getDescription());
            preparedStatement.setInt(3, bean.getProjectId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTicket(String projectParent, String title) {
        String query = "DELETE FROM ticket WHERE project_parent = ? AND title = ?";
        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, projectParent);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
