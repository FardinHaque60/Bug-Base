package application;

import java.io.IOException;
import java.net.URL;

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
}
