package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.CommentBean;
import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ViewTicketController extends AbstractViewController implements Initializable {

	@FXML TextArea ticketDescription;
	@FXML TextField ticketTitle;
	@FXML TextField projectParent;
	@FXML TableView<CommentBean>  CommentTable;
	@FXML TableColumn<CommentBean, String> CommentDate;
	@FXML TableColumn<CommentBean, String> CommentDescription;
	@FXML Button newComment;
	
	CommonObjs common = CommonObjs.getInstance();
	
	private static String titleFill;
	private static String descriptionFill;
	private static TicketBean thisBean;
	private static String projectParentFill;
	
	// static fields that save old data when errors occur
	private static String projectData, titleData, descriptionData;
	private static ErrorType errorType = ErrorType.NO_ERROR;
	private enum ErrorType {
		NO_ERROR, NO_PROJECT, NO_TITLE, SAME_TITLE
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ticketTitle.setText(titleFill);
		ticketDescription.setText(descriptionFill);
		projectParent.setText(projectParentFill);
		
		CommentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		CommentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		CommentTable.setItems(thisBean.getCommentInfo()); 
		
		switch (errorType) {
		
		case NO_ERROR:
			break;
			
		case NO_PROJECT:
			ticketTitle.setText(titleData);
			ticketDescription.setText(descriptionData);
			break;
			
		case NO_TITLE:
			projectParent.setText(projectData);
			ticketDescription.setText(descriptionData);
			break;
			
		case SAME_TITLE:
			projectParent.setText(projectData);
			ticketDescription.setText(descriptionData);
			break;
				
		}
		errorType = ErrorType.NO_ERROR;
	}

	public static void initalizeTicket(TicketBean ticket) {
		thisBean = ticket;
	    titleFill = ticket.getTitle();
	    
	    descriptionFill = ticket.getDescription();
	    projectParentFill = ticket.getProjectName();
	}		
	
	@FXML public void makeNewComment() {
		CreateCommentController.initializeComment(thisBean);
		goTo("view/CreateComment.fxml");
	}
	
	@FXML public void goBack() {
		try {
			ProjectBean parent = findProjectParent();
			
			ViewProjectController.initialize(parent);
			goTo("view/ViewProject.fxml");
		}
		catch (NullPointerException e) {
			e.printStackTrace(); //in situation where we cannot find the tickets parent
		}
	}
	
	//if user changes fields ticket gets updates
	@Override
	public void edit() {
		
	    String selectedProjectName = projectParent.getText();
	    
	    // Edge case: no project name
	    if (selectedProjectName == null) {
	    	// for initialize method
			errorType = ErrorType.NO_PROJECT;
			
			// save data
			titleData = ticketTitle.getText();
			descriptionData = ticketDescription.getText();
			
			// goes to error fxml
			goTo("view/ViewTicketError/NoProject.fxml");
			
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
			projectData = projectParent.getText();
			descriptionData = ticketDescription.getText();
			
			// goes to error fxml
			goTo("view/ViewTicketError/NoTitle.fxml");
			
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
			projectData = projectParent.getText();
			descriptionData = ticketDescription.getText();
			
			// goes to error fxml
			goTo("view/ViewTicketError/SameTitle.fxml");
			
			return;
        }
        
        thisBean.updateTicket(ticketTitle.getText(), ticketDescription.getText());
        goHome();

	}

	@FXML public void deleteTicket() {
		try {
			ProjectBean ticketParent = findProjectParent();
			
			thisBean.deleteTicket(ticketParent);
			
			ViewProjectController.initialize(ticketParent);
			common.loadDisplay("view/viewProject.fxml");
		}
		catch (NullPointerException e) {
			e.printStackTrace(); //in situation that ticketParent cannot find project parent for some reason
		}
	}
	
	private ProjectBean findProjectParent() {
		ProjectBean ticketParent = null;
	    for (ProjectBean projectBean : ProjectBean.getProjectBeanList()) {
			if (projectParentFill.equals(projectBean.getName())) {
				ticketParent = projectBean;
			}
	    }
	    return ticketParent;
	}
	
	@FXML public void getComment(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) {
			return;
		}
		
		try {
			CommentBean selectedComment = CommentTable.getSelectionModel().getSelectedItem();
			
			ViewCommentController.initializeComment(selectedComment);
			common.loadDisplay("view/viewComment.fxml");
		}
		catch (NullPointerException e) {
			//do nothing, this is if user selects empty entry in table
		}
	}

	@FXML public void deleteComment() {
		try {
			CommentBean selectedComment = CommentTable.getSelectionModel().getSelectedItem();
			
			selectedComment.deleteComment(thisBean);
			CommentTable.setItems(thisBean.getCommentInfo());
		}
		catch (NullPointerException e){
			//do nothing, put it in a system log later or something
		}
	}
}
