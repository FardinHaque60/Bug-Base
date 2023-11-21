package controller;

import java.time.LocalDate;

import DataAccessLayer.CommentBean;
import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

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

	@FXML
	public void initialize() {
		projectParent.setText(projectParentFill);
		ticketTitle.setText(ticketTitleFill);
		timestamp.setText(formatter.format(LocalDate.now()));
		commentDescription.setText(commentDescriptionFill);
	}
	
	public static void initializeComment(CommentBean c) {
		thisBean = c;
		projectParentFill = c.getProjectAncestor();
		ticketTitleFill = c.getTicketParent();
		commentDescriptionFill = c.getDescription();
	}
	
	@Override
	@FXML public void edit() {
		thisBean.updateComment(commentDescription.getText(), timestamp.getText());
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
