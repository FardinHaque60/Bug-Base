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
	
	public abstract void save();
}
