package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import DataAccessLayer.ProjectBean;
import application.CommonObjs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
	@FXML HBox mainBox;
	private static CommonObjs common = CommonObjs.getInstance();

	@FXML
	public void initialize() { 
		common.setMainBox(mainBox);
		showProjects();
	}
		
	@FXML public void showProjects() {
		common.loadProjectDisplay();
	}
	
	@FXML public void showCreateProject() {
		
		URL url = getClass().getClassLoader().getResource("view/CreateProject.fxml");
		try {
			
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
@FXML public void showCreateProjectError() {
		
		URL url = getClass().getClassLoader().getResource("view/CreateProjectError.fxml");
		try {
			
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void showCreateTicket() {
		URL url = getClass().getClassLoader().getResource("view/CreateTicket.fxml");
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static CommonObjs getCommonObjs() {
		return common;
	}
}
