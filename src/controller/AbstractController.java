package controller;

import application.CommonObjs;
import javafx.scene.layout.HBox;

public abstract class AbstractController {
	CommonObjs common = CommonObjs.getInstance();
	HBox mainBox = common.getMainBox();
	
	public void goHome() {
		common.loadProjectDisplay();
	}
	
	public abstract void save();
}
