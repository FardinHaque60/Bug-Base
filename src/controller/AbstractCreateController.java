package controller;

import java.time.format.DateTimeFormatter;

import application.CommonObjs;
import javafx.scene.layout.HBox;

public abstract class AbstractCreateController {
	CommonObjs common = CommonObjs.getInstance();
	HBox mainBox = common.getMainBox();
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
	
	/**
	 * Loads ProjectDisplay page. Used for fxml actions.
	 */
	public void goHome() {
		common.loadDisplay("view/ProjDisplay.fxml");
	}
	
	/**
	 * Loads a fxml with a given string path. More abstract version of goHome method.
	 * @param path  fxml string path
	 */
	public void goTo(String path) {
		common.loadDisplay(path);
	}
	
	/**
	 * Saves information into the database
	 */
	public abstract void save();
}
