package controller;

import DataAccessLayer.CommentBean;
import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MainController {
	@FXML HBox mainBox;
	private static CommonObjs common = CommonObjs.getInstance();
	private static boolean firstInitialization = true;

	@FXML public void initialize() { 
		common.setMainBox(mainBox);
		showProjects();
		// first time running the application, read from the database
		if (firstInitialization) {
			// tells Beans to initalize info from what was persisted in db
			ProjectBean.getAllProjectInfo();
			TicketBean.readAllTicketsInDatabase();
			CommentBean.readAllCommentsInDatabase();
			firstInitialization = false;
		}
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
}
