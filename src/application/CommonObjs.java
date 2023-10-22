package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import DataAccessLayer.ProjectBean;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
	
	public void loadProjectDisplay() {
		URL url = getClass().getClassLoader().getResource("view/ProjDisplay.fxml");
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a fxml with a given string path. More abstract version of loadProjectDisplay method.
	 * @param path  fxml string path
	 */
	public void loadDisplay(String path) {
		URL url = getClass().getClassLoader().getResource(path);
		try {
			
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
