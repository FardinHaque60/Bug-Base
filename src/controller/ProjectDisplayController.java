package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
	ObservableList<ProjectBean> observableList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		//TODO: add implementation so it can read all javabeans that have been added
		if (observableList.size() < ProjectBean.numBeans()) {
			int i;
			for (i = observableList.size(); i < ProjectBean.numBeans(); i++) {
				observableList.add(ProjectBean.getAllProjectInfo().get(i));
			}
		}
		
		ProjectTable.setItems(observableList);
	}

}
