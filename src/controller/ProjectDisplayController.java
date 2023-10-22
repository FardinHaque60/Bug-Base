package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import application.CommonObjs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjectDisplayController implements Initializable {

	private CommonObjs common = CommonObjs.getInstance();
	@FXML TableColumn<ProjectBean, String> ProjectName;
	@FXML TableColumn<ProjectBean, String> ProjectDate;
	@FXML TableView<ProjectBean> ProjectTable;
	HBox mainBox = common.getMainBox();
	ObservableList<ProjectBean> observableList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//TODO: need to clear and reset list everytime, look for alt
		observableList = FXCollections.observableArrayList();
		ProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		ArrayList<ProjectBean> allProjects = ProjectBean.readAllProjectInfo();

		int i;
		for (i = 0; i < allProjects.size(); i++) {
			observableList.add(allProjects.get(i));
		}
		
		ProjectTable.setItems(observableList);
	}

}
