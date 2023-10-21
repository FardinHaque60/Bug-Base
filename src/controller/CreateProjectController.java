package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;

import DataAccessLayer.ProjectBean;
import application.CommonObjs;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;

public class CreateProjectController extends AbstractController {

	//Scene fields/inputs:
	@FXML TextField projName;
	@FXML DatePicker projDate;
	@FXML TextArea projDescription;
	
	@FXML
	public void initialize() {
		projDate.setValue(LocalDate.now());
	}
	
	@Override
	@FXML public void save() {
		try {
			ProjectBean projectInfo = new ProjectBean();
			//JavaBean -> Connection -> Write to DB
			projectInfo.makeProjectBean(projName.getText(), projDate.getValue().toString(), projDescription.getText());
			
			goHome();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
