package application;

import java.io.IOException;
import java.net.URL;

import DataAccessLayer.CommentBean;
import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CommonObjs {
	
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;	
	
	private CommonObjs() {
		
	}

	public static CommonObjs getInstance() {
		return commonObjs;
	}

	public HBox getMainBox() {
		return mainBox;
	}
	
	public void setMainBox(HBox mainBox) {
		this.mainBox = mainBox;
	}
	
	/**
	 * Loads a fxml sub page with a given string path.
	 * @param path  fxml string path
	 */
	public void loadDisplay(String path) {
		
		// grabs the fxml file as a url
		URL url = getClass().getClassLoader().getResource(path);
		AnchorPane pane = null;
		
		//grabs the fxml sub page
		try {
			pane = (AnchorPane) FXMLLoader.load(url);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		// removes the previous sub page
		if (mainBox.getChildren().size() > 1) {
			mainBox.getChildren().remove(1);
		}
		
		// add sub page into mainBox
		mainBox.getChildren().add(pane);
	}
	

	public boolean checkProjectUniqueness(ProjectBean projectBean) {
		//compare parameter a project against all existing projects (ProjectBean.getAllInfo())
		for (ProjectBean _projectBean : ProjectBean.getProjectBeanList()) {
			if (projectBean.getName().equals(_projectBean.getName())) {
				return false;
			}
		}
		return true;
	}
	

	public boolean checkTicketUniqueness(TicketBean ticketBean, ProjectBean projectBean) {
		// checks whether this ticket title exists in the project bean
		for (TicketBean _ticketBean: projectBean.getTicketInfo()) {
			if (_ticketBean.getTitle().equals(ticketBean.getTitle())) {
				return false;
			}
		}
		return true;
		
		//get all the tickets siblings by calling ProjectParent.getTicketInfo() 
		//make sure that this ticket does not have same title as any of its siblings
		
		//do the same as above but compare parameter a (just the new ticket) against projectParent.getTicketInfo()
	}
	
	public boolean checkCommentUniqueness(CommentBean commentBean, TicketBean ticketBean) {
		// checks whether this comment description exists in the ticket bean
		for (CommentBean _commentBean: ticketBean.getCommentInfo()) {
			if (_commentBean.getDescription().equals(commentBean.getDescription())) {
				return false;
			}
		}
		return true;
	}
}
