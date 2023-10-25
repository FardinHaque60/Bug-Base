package controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {
	@FXML HBox mainBox;
	private static CommonObjs common = CommonObjs.getInstance();

	@FXML
	public void initialize() { 
		common.setMainBox(mainBox);
		showProjects();
	}
		
	@FXML public void showProjects() {
		common.loadDisplay("view/ProjDisplay.fxml");
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
