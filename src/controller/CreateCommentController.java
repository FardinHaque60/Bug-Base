package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DataAccessLayer.CommentBean;
import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateCommentController extends AbstractCreateController implements Initializable {
	
	@FXML TextArea commentDescription;
	@FXML DatePicker commentTimestamp;
	@FXML TableView<CommentBean> CommentTable;
	@FXML TableColumn<CommentBean, String> DescriptionColumn;
	@FXML TableColumn<CommentBean, String> DateColumn;
	
	private static TicketBean ticketParent;

	//populates existing comment table with comments that are already made
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		CommentTable.setItems(ticketParent.getCommentInfo()); //make a observable list of comments from ticketParent
	}
	
	//get information for what ticket this comment will belong to based on where it was clicked
	public static void initialize(TicketBean t) {
		ticketParent = t;
	}
	
	@Override
	public void save() {
		CommentBean commentInfo = new CommentBean(ticketParent.getTitle(), formatter.format(commentTimestamp.getValue()), commentDescription.getText());
		commentInfo.writeCommentBean();
		goHome();
	}
}
