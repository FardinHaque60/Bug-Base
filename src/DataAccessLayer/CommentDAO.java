package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommentDAO {

	final static CommentDAO CommentConnection = new CommentDAO();
	
	private static Connection connection;
	
	private CommentDAO() {
		connection = SqliteConnection.getConnection();
	}
	
	public static CommentDAO getCommentConnection() { return CommentConnection; }
	
	/** Method to read all comments from the database
	 */
    public static ObservableList<CommentBean> readAllComments() {
        ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
        String sql = "SELECT * FROM comment";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
            	String parentAncestor = resultSet.getString("ParentAncestor");
                String ticketParent = resultSet.getString("TicketParent");
                String date = resultSet.getString("date");
                String description = resultSet.getString("description");
                commentBeans.add(new CommentBean(parentAncestor, ticketParent, date, description));
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
        String sql = "INSERT INTO comment (ParentAncestor, TicketParent, date, description) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        	preparedStatement.setString(1, bean.getProjectAncestor());
            preparedStatement.setString(2, bean.getTicketParent());
            preparedStatement.setString(3, bean.getDate());
            preparedStatement.setString(4, bean.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
