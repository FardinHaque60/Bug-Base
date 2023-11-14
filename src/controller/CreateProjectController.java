package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import DataAccessLayer.ProjectBean;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
	
	//TODO: potentially change implementation so error message populated textbox instead of underneath textbox
	
	//runs when create project page is displayed
	//main functions: check if any fields are missing, populate fields with current date, etc
	@FXML public void initialize() {
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
	
	@Override
	@FXML public void save() {
		// edge case: Name is the same as another project
		for (ProjectBean projectBean : ProjectBean.getProjectBeanList()) {
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
			new SimpleStringProperty(formatter.format(projDate.getValue()));
		} catch (Exception e) {
			foundException = true;
		}
		if (foundException || projDate.getValue() == null) {
			
			// for initialize method
			errorType = ErrorType.NO_DATE;
			
			// save name and description
			nameData = projName.getText();
			descriptionData = projDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateProjectError/NoDate.fxml");
			
			return;
		}
		
		// add project to database and go to home page
		String formattedDate = projDate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE); // Adjust the formatter to your needs.
	    ProjectBean projectInfo = new ProjectBean(projName.getText(), formattedDate, projDescription.getText()); // ID is 0 as a placeholder.
	    projectInfo.writeProjectBean();
	    goHome();
	}


}
