package controller;

import java.time.LocalDate;

import DataAccessLayer.CommentBean;
import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewCommentController extends AbstractViewController {	
	@FXML TextField projectParent;
	@FXML TextField ticketTitle;
	@FXML TextField timestamp;
	@FXML TextArea commentDescription;
	
	private static CommentBean thisBean;
	private static String projectParentFill;
	private static String ticketTitleFill;
	private static String commentDescriptionFill;
	
	CommonObjs common = CommonObjs.getInstance();

	// static fields that save old data when errors occur
	private static ErrorType errorType = ErrorType.NO_ERROR;
	private enum ErrorType {
		NO_ERROR, NO_DESCRIPTION, SAME_DESCRIPTION;
	}
	private static String commentDescriptionData;
	
	@FXML
	public void initialize() {
		projectParent.setText(projectParentFill);
		ticketTitle.setText(ticketTitleFill);
		timestamp.setText(formatter.format(LocalDate.now()));
		commentDescription.setText(commentDescriptionFill);
		
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
	
	public static void initializeComment(CommentBean c) {
		thisBean = c;
		projectParentFill = c.getProjectAncestor();
		ticketTitleFill = c.getTicketParent();
		commentDescriptionFill = c.getDescription();
	}
	
	@Override
	@FXML public void edit() {
		
		// Edge case: no comment description
		if (commentDescription.getText() == null || commentDescription.getText().length() == 0) {
			
			// for initialize method
			errorType = ErrorType.NO_DESCRIPTION;
			
			// goes to error fxml
			goTo("view/ViewCommentError/NoDescription.fxml");
			
			return;
		}
		
		// create comment bean and write to database
		TicketBean ticketParent = findTicketParent();
		CommentBean commentInfo = new CommentBean(ticketParent.getProjectName(), ticketParent.getTitle(), timestamp.getText(), commentDescription.getText());
		
		// Edge case: same comment description
		boolean isCommentUnique = common.checkCommentUniqueness(commentInfo, ticketParent);
		if (!isCommentUnique  && !commentDescription.getText().equals(thisBean.getDescription())) {
			
			// for initialize method
			errorType = ErrorType.SAME_DESCRIPTION;
			
			// save data
			commentDescriptionData = commentDescription.getText();
			
			// goes to error fxml
			goTo("view/ViewCommentError/SameDescription.fxml");
			
			return;
		}
		
		thisBean.updateComment(commentDescription.getText(), timestamp.getText());
		
		goBack();
	}

	//deletes this comment
	@FXML public void deleteComment() {
		//finds what project and ticket bean this comment belongs to
		TicketBean ticketParent = findTicketParent();
		
		thisBean.deleteComment(ticketParent);
		goBack();
	}

	//goes back to ticket parent
	@FXML public void goBack() {
		ViewTicketController.initalizeTicket(findTicketParent());
		common.loadDisplay("view/viewTicket.fxml");
	}
	
	private TicketBean findTicketParent() {
		//finds what project and ticket bean this comment belongs to
		ProjectBean parentAncestor = null;
		TicketBean ticketParent = null;
		for (ProjectBean p: ProjectBean.getProjectBeanList()) {
			if (p.getName().equals(projectParentFill)) {
				parentAncestor = p;
			}
		}
		for (TicketBean t: parentAncestor.getTicketInfo()) {
			if (t.getTitle().equals(ticketTitleFill)) {
				ticketParent = t;
				break;
			}
		}
		return ticketParent;
	}
}
