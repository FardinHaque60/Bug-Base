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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
		ticketBeans = TicketBean.getTicketBeanList();
		projectBeans = ProjectBean.getProjectBeanList();
		
		// sets the project table
		ProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		ProjectResult.setItems(projectBeans);
		
		
		TicketTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		ProjectParent.setCellValueFactory(new PropertyValueFactory<>("projectName"));
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
				} else if (TicketBean.getProjectName().toLowerCase().indexOf(searchKeyword) > -1){
					return true;
				}else {
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
	@FXML public void getProject(MouseEvent e) {
		
		if (e.getButton() != MouseButton.PRIMARY) {
			return;
		}
		
		try {
			ProjectBean selectedProject = ProjectResult.getSelectionModel().getSelectedItem();
			
			ViewProjectController.initialize(selectedProject);
			common.loadDisplay("view/ViewProject.fxml");
		}
		catch (NullPointerException exception){
			//do nothing, put it in a system log later or something
		}
	}
	
	@FXML public void getTicket(MouseEvent e) {
		
		if (e.getButton() != MouseButton.PRIMARY) {
			return;
		}
		
		try {
			TicketBean selectedTicket = TicketResult.getSelectionModel().getSelectedItem();
			
			ViewTicketController.initalizeTicket(selectedTicket);
			common.loadDisplay("view/ViewTicket.fxml");
		}
		catch (NullPointerException exception){
			//do nothing, put it in a system log later or something
		}
	}

	//TODO: this delete menu will show up when you right click
	//but since we can right click and click the project normally it may cause some issues
	@FXML public void deleteProject() {
		ProjectBean selectedProject = ProjectResult.getSelectionModel().getSelectedItem();
		
		selectedProject.deleteProject();
		
		TicketResult.setItems(TicketBean.getTicketBeanList());
		ProjectResult.refresh();
		
		//just to test if hitting delete works
		//common.loadDisplay("view/ProjDisplay.fxml");
	}

	//TODO: Same problem as deleteProjects
	@FXML public void deleteTicket() {
		//just to test if hitting delete works
		common.loadDisplay("view/ProjDisplay.fxml");
	}
}
