package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// sets the project table
		ProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		//ProjectTable.refresh();
		ProjectTable.setItems(ProjectBean.getProjectBeanList());
	}

	@FXML public void getProject(MouseEvent event) {
		try {
			ProjectBean selectedProject = ProjectTable.getSelectionModel().getSelectedItem();
			
			ViewProjectController.initialize(selectedProject);
			common.loadDisplay("view/ViewProject.fxml");
		}
		catch (NullPointerException e){
			//do nothing, put it in a system log later or something
		}
	}

}
