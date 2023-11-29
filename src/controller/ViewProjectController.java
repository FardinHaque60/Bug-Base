package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ViewProjectController extends AbstractViewController implements Initializable {

	@FXML TextField nameInfo;
	@FXML TextArea descriptionInfo;
	@FXML DatePicker dateInfo;
	CommonObjs common = CommonObjs.getInstance();
	
	private static String nameFill, descriptionFill, dateFill;
	private static ProjectBean thisBean;
	@FXML TableView<TicketBean>  TicketTable;
	@FXML TableColumn<TicketBean, String> TicketTitles;
	@FXML TableColumn<TicketBean, String> TicketDescriptions;
	
	
	// static fields that save old data when errors occur
	private static String nameData;
	private static LocalDate dateData;
	private static String descriptionData;
	private static ErrorType errorType = ErrorType.NO_ERROR;
	private enum ErrorType {
		NO_ERROR, NO_NAME, SAME_NAME, NO_DATE
	}

	//TODO: get description to fit not all in one line
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//TODO: fix so if we press go back on view ticket page we go back to correct page
		//two options: create stack so it goes back to last clicked page
		//goes back to its project parent page using bean info 
		try {
			nameInfo.setText(nameFill);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate localDate = LocalDate.parse(dateFill, formatter);
			dateInfo.setValue(localDate);
			descriptionInfo.setText(descriptionFill);
			
			TicketTitles.setCellValueFactory(new PropertyValueFactory<>("title"));
			TicketDescriptions.setCellValueFactory(new PropertyValueFactory<>("description"));
			TicketTable.setItems(thisBean.getTicketInfo());
			
			switch (errorType) {
			
			case NO_ERROR:
				break;
				
			case NO_NAME:
				// fill back date and description data
				dateInfo.setValue(dateData);
				descriptionInfo.setText(descriptionData);
				break;
				
			case SAME_NAME:
				// fill back data
				nameInfo.setText(nameData);
				dateInfo.setValue(dateData);
				descriptionInfo.setText(descriptionData);
				break;
				
			case NO_DATE:
				// fill back name & data data
				nameInfo.setText(nameData);
				descriptionInfo.setText(descriptionData);
				break;
					
			}
			errorType = ErrorType.NO_ERROR;
		}
		catch (NullPointerException e) {
			//incase this page does not load properly
			//it will just leave fields blank
		}
	}
	
	public static void initialize(ProjectBean b) {
	    thisBean = b;
	    nameFill = b.getName();
	    dateFill = b.getDate();
	    descriptionFill = b.getDescription();
	}	
	
	@FXML public void getTicket(MouseEvent event) {
		
		if (event.getButton() != MouseButton.PRIMARY) {
			return;
		}
		
		try {
			TicketBean selectedTicket = TicketTable.getSelectionModel().getSelectedItem();
			
			ViewTicketController.initalizeTicket(selectedTicket);
			common.loadDisplay("view/ViewTicket.fxml");
		}
		catch (NullPointerException e){
			//do nothing, this is case where user selects empty entry
		}
	}
		
	@Override
	@FXML public void edit() {
		
		// check if name is nothing or the same as another existing project
		if (nameInfo.getText() == null || nameInfo.getText().length() == 0) {
			// for initialize method
			errorType = ErrorType.NO_NAME;
			
			// save name, date and description
			dateData = dateInfo.getValue();
			descriptionData = descriptionInfo.getText();
			
			// goes to error fxml
			goTo("view/ViewProjectError/NoName.fxml");
			
			return;
		}
		if (!nameInfo.getText().equals(nameFill)) {
			ProjectBean projectBean = new ProjectBean(nameInfo.getText(), dateFill, descriptionFill);
			boolean isProjectUnique = common.checkProjectUniqueness(projectBean);
			
			if (!isProjectUnique) {
				// for initialize method
				errorType = ErrorType.SAME_NAME;
				
				// save name, date and description
				nameData = nameInfo.getText();
				dateData = dateInfo.getValue();
				descriptionData = descriptionInfo.getText();
				
				// goes to error fxml
				goTo("view/ViewProjectError/SameName.fxml");
				
				return;
			}
		}
		
		// check if date is valid
		boolean foundException = false;
		try {
			new SimpleStringProperty(formatter.format(dateInfo.getValue()));
		} catch (Exception e) {
			foundException = true;
		}
		if (foundException || dateInfo.getValue() == null) {
			
			// for initialize method
			errorType = ErrorType.NO_DATE;
			
			// save name and description
			nameData = nameInfo.getText();
			descriptionData = descriptionInfo.getText();
			
			// goes to error fxml
			goTo("view/ViewProjectError/NoDate.fxml");
			
			return;
		}
		
		thisBean.updateProject(nameInfo.getText(), formatter.format(dateInfo.getValue()), descriptionInfo.getText());
	}

	@FXML public void deleteProject() { //if user presses delete project option on this page
		thisBean.deleteProject();
		common.loadDisplay("view/ProjDisplay.fxml");
	}

	@FXML public void deleteTicket() { //user right clicks on ticket entry and they click delete option
		try {
			TicketBean selectedTicket = TicketTable.getSelectionModel().getSelectedItem();
			
			ProjectBean parent = null;
			for (ProjectBean p: ProjectBean.getProjectBeanList()) {
				if (p.getName().equals(selectedTicket.getProjectName())) {
					parent = p;
					break;
				}
			}
			
			selectedTicket.deleteTicket(parent);
			TicketTable.setItems(parent.getTicketInfo());
		}
		catch (NullPointerException e){
			//do nothing, put it in a system log later or something
		}
	}
}
