package controller;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MainController {
	@FXML HBox mainBox;
	private static CommonObjs common = CommonObjs.getInstance();

	@FXML public void initialize() { 
		common.setMainBox(mainBox);
		showProjects();
	}
	
	/**
	 * Shows Project Display page. For Main.fxml button.
	 */
	@FXML public void showProjects() {
		common.loadDisplay("view/ProjDisplay.fxml");
	}
	
	/**
	 * Shows Create Project page. For Main.fxml button.
	 */
	@FXML public void showCreateProject() {
		common.loadDisplay("view/CreateProject.fxml");
	}
	
	/**
	 * Shows Create Ticket page. For Main.fxml button.
	 */
	@FXML public void showCreateTicket() {
		common.loadDisplay("view/CreateTicket.fxml");
	}
	
	public static CommonObjs getCommonObjs() {
		return common;
	}
}
