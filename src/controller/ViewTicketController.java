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
import javafx.scene.text.Text;
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
		
		CommentTable.setItems(thisBean.getCommentInfo()); //make a observable list of comments in ticketBean class
		
	}
	public static void initalizeTicket(TicketBean b) {
		thisBean = b;
		projectParentFill = b.getProjectParent();
		titleFill = b.getTitle();
		descriptionFill = b.getDescription();
	}
	
	@FXML public void makeNewComment() {
		CreateCommentController.initialize(thisBean);
		goTo("view/CreateComment.fxml");
	}
	
	//if user clicks on entry in comment table they can edit its fields
	@FXML public void editComment() {
		
	}
	
	@FXML public void goBack() {
		common.loadDisplay("view/ViewProject.fxml");
	}
	
	//TODO add implementation if user changes fields ticket gets updates
	@Override
	public void edit() {
	
	}
}
