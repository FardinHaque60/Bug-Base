package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

import javafx.beans.property.SimpleStringProperty;

public class CreateProjectController extends AbstractCreateController {

	//Scene fields/inputs:
	@FXML TextField projName;
	@FXML DatePicker projDate;
	@FXML TextArea projDescription;
	
	// static fields that save old data when errors occur
	private static String nameData;
	private static LocalDate dateData;
	private static String descriptionData;
	private static ErrorType errorType = ErrorType.NO_ERROR;
	
	private enum ErrorType {
		NO_ERROR, NO_NAME, SAME_NAME, NO_DATE
	}
	
	@FXML
	public void initialize() {
		switch (errorType) {
		
			case NO_ERROR:
				// set local date to current date
				projDate.setValue(LocalDate.now());
				break;
				
			case NO_NAME:
				// fill back date and description data
				projDate.setValue(dateData);
				projDescription.setText(descriptionData);
				break;
				
			case SAME_NAME:
				// fill back data
				projName.setText(nameData);
				projDate.setValue(dateData);
				projDescription.setText(descriptionData);
				break;
				
			case NO_DATE:
				// fill back name & data data
				projName.setText(nameData);
				projDescription.setText(descriptionData);
				break;
				
		}
		errorType = ErrorType.NO_ERROR;
	}
	
	//TODO: handle if any required field is invalid, Example: empty name field or manually input date in invalid format
	@Override
	@FXML public void save() {
		
		// edge case: Name was not inputed
		if (projName.getText() == null || projName.getText().equals("")) {
			
			// for initialize method
			errorType = ErrorType.NO_NAME;
			
			// save date and description
			dateData = projDate.getValue();
			descriptionData = projDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateProjectError/NoName.fxml");

			return;
		}
		
		// edge case: Name is the same as another project
		for (ProjectBean projectBean : ProjectBean.getAllProjectInfo()) {
			if (! projectBean.getName().equals(projName.getText())) {
				continue;
			}
			
			// for initialize method
			errorType = ErrorType.SAME_NAME;
			
			// save name, date and description
			nameData = projName.getText();
			dateData = projDate.getValue();
			descriptionData = projDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateProjectError/SameName.fxml");

			return;
		}
		
		// edge case: Date is not correctly inputed
		boolean foundException = false;
		try {
			SimpleStringProperty temp = new SimpleStringProperty(formatter.format(projDate.getValue()));
		} catch (Exception e) {
			foundException = true;
		}
		if (foundException || projDate.getValue() == null) {
			errorType = ErrorType.NO_DATE;
			
			// save name and description
			nameData = projName.getText();
			descriptionData = projDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateProjectError/NoDate.fxml");
			
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
