package application;

import java.io.IOException;
import java.net.URL;

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
	
	/** TODO: This method is called by common.checkProjectUniqueness(ProjectBean new, old) 
	 * 
	 * @return
	 */
	public boolean checkProjectUniqueness() {
		//compare parameter a project against all existing projects (ProjectBean.getAllInfo())
		return false;
	}
	
	/**
	 * 
	 * @param t the new, modified ticket
	 * @param p
	 * @return
	 */
	public boolean checkTicketUniqueness(TicketBean t, ProjectBean p) {
		for (TicketBean temp: p.getTicketInfo()) {
			if (temp.getTitle().equals(t.getTitle())) {
				return false;
			}
		}
		return true;
		
		//get all the tickets siblings by calling ProjectParent.getTicketInfo() 
		//make sure that this ticket does not have same title as any of its siblings
		
		//do the same as above but compare parameter a (just the new ticket) against projectParent.getTicketInfo()
	}
	
	//TODO: checking comment uniqueness
}
