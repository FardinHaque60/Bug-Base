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

public class CreateProjectController extends AbstractCreateController {

	//Scene fields/inputs:
	@FXML TextField projName;
	@FXML DatePicker projDate;
	@FXML TextArea projDescription;
	
	// static fields that save old data when errors occur
	private static String nameData;
	private static LocalDate dateData;
	private static String descriptionData;
	private static boolean noNameError = false;
	private static boolean sameNameError = false;
	
	@FXML
	public void initialize() {
		
		// no name inputed occurred
		if (noNameError) {
			
			// fill back date and description data
			projDate.setValue(dateData);
			projDescription.setText(descriptionData);
			
			noNameError = true;
			return;
		}
		
		// same name inputed occurred
				if (sameNameError) {
					
					// fill back name, date and description data
					projName.setText(nameData);
					projDate.setValue(dateData);
					projDescription.setText(descriptionData);
					
					sameNameError = true;
					return;
				}
		
		projDate.setValue(LocalDate.now());
	}
	
	//TODO: handle if any required field is invalid, Example: empty name field or manually input date in invalid format
	@Override
	@FXML public void save() {
		
		// edge case: Name was not inputed
		if (projName.getText() == null || projName.getText().equals("")) {
			
			noNameError = true;
			
			// save date and description
			dateData = projDate.getValue();
			descriptionData = projDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateProjectError.fxml");

			return;
		}
		
		// add project to database and go to home page
		ProjectBean projectInfo = new ProjectBean(projName.getText(), formatter.format(projDate.getValue()), projDescription.getText());
		projectInfo.writeProjectBean();
		goHome();
		
		/* OLD IMPLEMENTATION
		try {
			ProjectBean projectInfo = new ProjectBean(projName.getText(), formatter.format(projDate.getValue()), projDescription.getText());
			projectInfo.writeProjectBean();
			
			goHome();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
	}
}
