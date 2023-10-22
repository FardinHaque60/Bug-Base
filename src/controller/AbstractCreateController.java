package controller;

import java.time.format.DateTimeFormatter;

import application.CommonObjs;
import javafx.scene.layout.HBox;

public abstract class AbstractCreateController {
	CommonObjs common = CommonObjs.getInstance();
	HBox mainBox = common.getMainBox();
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
	
	public void goHome() {
		common.loadProjectDisplay();
	}
	
	/**
	 * Loads a fxml with a given string path. More abstract version of goHome method.
	 * @param path  fxml string path
	 */
	public void goTo(String path) {
		common.loadDisplay(path);
	}
	
	public abstract void save();
}
