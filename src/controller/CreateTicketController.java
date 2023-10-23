package controller;

import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.ProjectBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class CreateTicketController extends AbstractCreateController {

	@FXML ChoiceBox<String> projectDropdownList;
	@FXML TextField ticketTitle;
	@FXML TextArea ticketDescription;
	
	@FXML public void initialize() {
		
		// get all the names of the projects
		List<String> projectNameList = new ArrayList<String>();
		for (ProjectBean projectBean : ProjectBean.getAllProjectInfo()) {
			projectNameList.add(projectBean.getName());
		}
		
		System.out.println(ProjectBean.getAllProjectInfo().size());
		
		// put list of names into the dropdown list
		ObservableList<String> projectNameObservableList = FXCollections.observableList(projectNameList);
		projectDropdownList.setItems(projectNameObservableList);
		
		
	}
	
	
	@Override
	@FXML public void save() {
		
	}

}
