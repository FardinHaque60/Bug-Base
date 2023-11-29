package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.CommentBean;
import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ticketTitle.setText(titleFill);
		ticketDescription.setText(descriptionFill);
		projectParent.setText(projectParentFill);
		
		CommentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		CommentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		CommentTable.setItems(thisBean.getCommentInfo()); 
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
		//make sure to save old title and description incase this title is not unique
		
		//this will write updated information to DB, make sure this does not happen before we valid it
		// move this below potentially
		thisBean.updateTicket(ticketTitle.getText(), ticketDescription.getText());
		
		
		//thisBean has new fields assigned to it
		if (common.checkTicketUniqueness(thisBean, findProjectParent())) {
			//if this is true that means this ticket has unique title compared to its siblings
		}
		else {
			//restore old values that we saved above
		}
		
		
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
