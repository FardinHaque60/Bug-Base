package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class ViewProjectController extends AbstractViewController implements Initializable {

	@FXML TextField nameInfo;
	@FXML TextArea descriptionInfo;
	@FXML TextField dateInfo;
	CommonObjs common = CommonObjs.getInstance();
	
	private static String nameFill, descriptionFill, dateFill;
	private static ProjectBean thisBean;
	@FXML TableView<TicketBean>  TicketTable;
	@FXML TableColumn<TicketBean, String> TicketTitles;
	@FXML TableColumn<TicketBean, String> TicketDescriptions;

	//TODO: get description to fit not all in one line
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//TODO: fix so if we press go back on view ticket page we go back to correct page
		//two options: create stack so it goes back to last clicked page
		//goes back to its project parent page using bean info 
		try {
			nameInfo.setText(nameFill);
			dateInfo.setText(dateFill);
			descriptionInfo.setText(descriptionFill);
			
			TicketTitles.setCellValueFactory(new PropertyValueFactory<>("title"));
			TicketDescriptions.setCellValueFactory(new PropertyValueFactory<>("description"));
			TicketTable.setItems(thisBean.getTicketInfo());
		}
		catch (NullPointerException e) {
			//TODO:
			//do nothing for now
			//this handles special case where user goes Search Page -> ticket page -> go back -> Error project page wont load correctly
			//so now it will load a blank project page, but we have to fix this
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
			//do nothing, put it in a system log later or something
		}
	}
		
	//TODO: implement editing project by seeing which fields changed
	@Override
	@FXML public void edit() {
		thisBean.updateProject(nameInfo.getText(), dateInfo.getText(), descriptionInfo.getText());
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
