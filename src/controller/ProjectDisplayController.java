package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

public class ProjectDisplayController implements Initializable {

	private CommonObjs common = CommonObjs.getInstance();
	@FXML TableColumn<ProjectBean, String> ProjectName;
	@FXML TableColumn<ProjectBean, String> ProjectDate;
	@FXML TableView<ProjectBean> ProjectTable;
	HBox mainBox = common.getMainBox();
	
	private static boolean firstInitialization = true;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// first time running the application, read from the database
		if (firstInitialization) {
			// tells ProjectBean to fill all the projects in the database
			ProjectBean.readAllProjectsInDatabase();
			firstInitialization = false;
		}
		
		// sets the project table
		ProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		ProjectTable.setItems(ProjectBean.getAllProjectInfo());
	}

	@FXML public void getProject(MouseEvent event) {
		
		ProjectBean selectedProject = ProjectTable.getSelectionModel().getSelectedItem();
		
		
	}

}
