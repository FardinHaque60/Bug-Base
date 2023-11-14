package controller;

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
	}
	
	@FXML public void save() {
	    String selectedProjectName = projectDropdownList.getValue();

	    if (selectedProjectName != null) {
	        TicketBean ticketInfo = new TicketBean(selectedProjectName, ticketTitle.getText(), ticketDescription.getText());
	        ticketInfo.writeTicketBean();
	        goHome();
	    } else {
	        // Handle the case where no matching project ID was found
	    }
	   
	}


}
