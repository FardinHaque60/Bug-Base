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
		// first time running the application, read from the database
		if (firstInitialization) {
			// tells Beans to initialize info from what was persisted in db
			ProjectBean.readAllProjectsInDatabase();
			TicketBean.readAllTicketsInDatabase();
			CommentBean.readAllCommentsInDatabase();
			firstInitialization = false;
		}
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

	/**
	 * Opens search page. For Main.fxml button.
	 */
	@FXML public void openSearch() {
		common.loadDisplay("view/Search.fxml");
	}	
}
