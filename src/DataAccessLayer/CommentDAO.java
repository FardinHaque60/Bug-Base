package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommentDAO {

	final static CommentDAO CommentConnection = new CommentDAO();
	
	private CommentDAO() {
		
	}
	
	public static CommentDAO getCommentConnection() {
		return CommentConnection;
	}
	
    // Method to read all comments from the database
	/** TODO: current not doing anything with this
	 */
    public static ObservableList<CommentBean> readAllComments() {
        ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
        String sql = "SELECT * FROM comment";

        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int ticketParent = resultSet.getInt("TicketID");
                String date = resultSet.getString("date");
                String description = resultSet.getString("description");
                commentBeans.add(new CommentBean(ticketParent, date, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentBeans;
    }

    /** Method to write a comment into the database
     * 
     * @param bean to be written to db
     */
    public void writeComment(CommentBean bean) {
        String sql = "INSERT INTO comment (TicketID, date, description) VALUES (?, ?, ?)";

        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, bean.getTicketParent());
            preparedStatement.setString(2, bean.getDate());
            preparedStatement.setString(3, bean.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
}
