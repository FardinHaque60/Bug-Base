package DataAccessLayer;

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
    public void writeProject(ProjectBean bean) {
        String sql = "INSERT INTO project (name, date, description) VALUES (?, ?, ?)";

        try (Connection connection = SqliteConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getDate());
            preparedStatement.setString(3, bean.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
}
