package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class SearchController implements Initializable {
	
	private CommonObjs common = CommonObjs.getInstance();
	
	@FXML TextField ProjectSearchBar;
	@FXML TextField TicketSearchBar;

	@FXML TableView<ProjectBean> ProjectResult;
	@FXML TableColumn<ProjectBean, String> ProjectName;
	@FXML TableColumn<ProjectBean, String> ProjectDate;
	
	@FXML TableView<TicketBean> TicketResult;
	@FXML TableColumn<TicketBean, String> TicketTitle;
	@FXML TableColumn<TicketBean, String> ProjectParent;
	
	ObservableList<TicketBean> ticketBeans;
	ObservableList<ProjectBean> projectBeans;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ticketBeans = TicketBean.getAllTicketInfo();
		projectBeans = ProjectBean.getAllProjectInfo();
		
		// sets the project table
		ProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		ProjectResult.setItems(projectBeans);
		
		
		TicketTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		ProjectParent.setCellValueFactory(new PropertyValueFactory<>("projectParent"));
		TicketResult.setItems(ticketBeans);
		
//Project Search Bar implementation below
		
		FilteredList<ProjectBean> filteredProjects = new FilteredList<>(projectBeans, b -> true);
		
		ProjectSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredProjects.setPredicate(ProjectBean -> {
				//if we want to change so it says no result found for when user leaves field blank change line below
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}
				
				String searchKeyword = newValue.toLowerCase();
				
				if (ProjectBean.getName().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
				} else {
					return false;
				}
			});
		});
		
		/* if we want to show the data in sorted order
		SortedList<ProjectBean> sortedData = new SortedList<>(filteredProjects);
		
		sortedData.comparatorProperty().bind(ProjectResult.comparatorProperty());
		*/
		
		ProjectResult.setItems(filteredProjects);
		
//Ticket Search Bar Implementation
		
		FilteredList<TicketBean> filteredTickets = new FilteredList<>(ticketBeans, b -> true);
		
		TicketSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredTickets.setPredicate(TicketBean -> {
				//if we want to change so it says no result found for when user leaves field blank change line below
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}
				
				String searchKeyword = newValue.toLowerCase();
				
				if (TicketBean.getTitle().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
				} else if (TicketBean.getProjectParent().toLowerCase().indexOf(searchKeyword) > -1){
					return true;
				} else {
					return false;
				}
			});
		});
		
		/* if we want to show the data in sorted order
		SortedList<TicketBean> sortedData = new SortedList<>(filteredTickets);
		
		sortedData.comparatorProperty().bind(TicketResult.comparatorProperty());
		*/
		
		TicketResult.setItems(filteredTickets);
	}
	
	//when table entry is clicked it will open the view ticket or project page respectively
	//TODO: Some repeated code here from projDisp and viewProj classes, look into abstraction
	@FXML public void getProject() {
		ProjectBean selectedProject = ProjectResult.getSelectionModel().getSelectedItem();
		
		ViewProjectController.initalize(selectedProject);
		common.loadDisplay("view/ViewProject.fxml");
	}
	
	@FXML public void getTicket() {
		TicketBean selectedTicket = TicketResult.getSelectionModel().getSelectedItem();
		
		ViewTicketController.initalizeTicket(selectedTicket);
		common.loadDisplay("view/ViewTicket.fxml");
	}
}
