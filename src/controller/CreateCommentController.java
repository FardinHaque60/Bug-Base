package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DataAccessLayer.CommentBean;
import DataAccessLayer.TicketBean;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateCommentController extends AbstractCreateController implements Initializable {
	
	@FXML TextField ticketTitle;
	@FXML TextArea ticketDescription;
	@FXML TextArea commentDescription;
	@FXML TextField commentTimestamp;
	@FXML TableView<CommentBean> CommentTable;
	@FXML TableColumn<CommentBean, String> DescriptionColumn;
	@FXML TableColumn<CommentBean, String> DateColumn;
	
	private static TicketBean ticketParent;
	
	// static fields that save old data when errors occur
	private static ErrorType errorType = ErrorType.NO_ERROR;
	private enum ErrorType {
		NO_ERROR, NO_DESCRIPTION, SAME_DESCRIPTION;
	}
	private static String commentDescriptionData;

	//populates existing comment table with comments that are already made
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ticketTitle.setText(ticketParent.getTitle());
		ticketDescription.setText(ticketParent.getDescription());
		commentTimestamp.setText(formatter.format(LocalDate.now()));
		DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		CommentTable.setItems(ticketParent.getCommentInfo()); //make a observable list of comments from ticketParent
		
		switch (errorType) {
		case NO_ERROR:
			break;
		case NO_DESCRIPTION:
			break;
		case SAME_DESCRIPTION:
			commentDescription.setText(commentDescriptionData);
			break;
		}
		
		errorType = ErrorType.NO_ERROR;
	}
	
	//get information for what ticket this comment will belong to based on which ticket entry was clicked
	public static void initializeComment(TicketBean t) {
		ticketParent = t;
	}
	
	@Override
	public void save() {
		
		
		// Edge case: no comment description
		if (commentDescription.getText().length() == 0) {
			
			// for initialize method
			errorType = ErrorType.NO_DESCRIPTION;
			
			// goes to error fxml
			goTo("view/CreateCommentError/NoDescription.fxml");
			
			return;
		}
		
		// create comment bean and write to database
		CommentBean commentInfo = new CommentBean(ticketParent.getProjectName(), ticketParent.getTitle(), commentTimestamp.getText(), commentDescription.getText());
		
		// Edge case: same comment description
		boolean isCommentUnique = common.checkCommentUniqueness(commentInfo, ticketParent);
		if (!isCommentUnique) {
			
			// for initialize method
			errorType = ErrorType.SAME_DESCRIPTION;
			
			// save data
			commentDescriptionData = commentDescription.getText();
			
			// goes to error fxml
			goTo("view/CreateCommentError/SameDescription.fxml");
			
			return;
		}
		
		commentInfo.writeCommentBean(ticketParent);
		goBack();
	}
	
	@FXML public void goBack() {
		common.loadDisplay("view/ViewTicket.fxml");
	}
}
