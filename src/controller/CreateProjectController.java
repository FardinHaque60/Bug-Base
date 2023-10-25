package controller;

import java.time.LocalDate;

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
		ProjectBean projectInfo = new ProjectBean(projName.getText(), formatter.format(projDate.getValue()), projDescription.getText());
		projectInfo.writeProjectBean();
		goHome(); // TODO: would probably want to go to view project page to add tickets later
	}
}
