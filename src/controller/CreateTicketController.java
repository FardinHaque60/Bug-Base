package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateTicketController extends AbstractCreateController {
	
	@FXML ChoiceBox<String> projectDropdownList;
	@FXML TextField ticketTitle;
	@FXML TextArea ticketDescription;
	
	// static fields that save old data when errors occur
	private static String projectData, titleData, descriptionData;
	private static ErrorType errorType = ErrorType.NO_ERROR;
	private enum ErrorType {
		NO_ERROR, NO_PROJECT, NO_TITLE, SAME_TITLE
	}
	
	//populates drop down menu of projects to choose from
	@FXML public void initialize() {
		
		// get all the names of the projects
		List<String> projectNameList = new ArrayList<String>();
		for (ProjectBean projectBean : ProjectBean.getProjectBeanList()) {
			projectNameList.add(projectBean.getName());
		}
		
		// put list of names into the dropdown list
		ObservableList<String> projectNameObservableList = FXCollections.observableList(projectNameList);
		projectDropdownList.setItems(projectNameObservableList);
		
		
		
		switch (errorType) {
		
		case NO_ERROR:
			break;
			
		case NO_PROJECT:
			ticketTitle.setText(titleData);
			ticketDescription.setText(descriptionData);
			break;
			
		case NO_TITLE:
			projectDropdownList.setValue(projectData);
			ticketDescription.setText(descriptionData);
			break;
			
		case SAME_TITLE:
			projectDropdownList.setValue(projectData);
			ticketDescription.setText(descriptionData);
			break;
				
		}
		errorType = ErrorType.NO_ERROR;
	}
	
	@FXML public void save() {
	    String selectedProjectName = projectDropdownList.getValue();
	    
	    // Edge case: no project name
	    if (selectedProjectName == null) {
	    	// for initialize method
			errorType = ErrorType.NO_PROJECT;
			
			// save data
			titleData = ticketTitle.getText();
			descriptionData = ticketDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateTicketError/NoProject.fxml");
			
			return;
	    }
	    
	    //finds what projectBean object the user selected
	    //find this tickets project parent
	    ProjectBean ticketParent = null;
	    for (ProjectBean projectBean : ProjectBean.getProjectBeanList()) {
			if (selectedProjectName.equals(projectBean.getName())) {
				ticketParent = projectBean;
			}
	    }
	    
	    // Edge case: no ticket title
	    if (ticketTitle.getText() == null || ticketTitle.getText().length() == 0) {
	    	// for initialize method
			errorType = ErrorType.NO_TITLE;
			
			// save data
			projectData = projectDropdownList.getValue();
			descriptionData = ticketDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateTicketError/NoTitle.fxml");
			
			return;
	    }
	    
	    
	    //make sure user selects a project for this ticket to live under
        TicketBean ticketInfo = new TicketBean(selectedProjectName, ticketTitle.getText(), ticketDescription.getText());
        
        // Edge case: same ticket title
        boolean ticketIsUnique = common.checkTicketUniqueness(ticketInfo, ticketParent);
        if (!ticketIsUnique) {
        	// for initialize method
			errorType = ErrorType.SAME_TITLE;
			
			// save data
			projectData = projectDropdownList.getValue();
			descriptionData = ticketDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateTicketError/SameTitle.fxml");
			
			return;
        }
        
        ticketInfo.writeTicketBean(ticketParent);
        goHome();
    }
	
}
