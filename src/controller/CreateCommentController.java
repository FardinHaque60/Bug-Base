package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class CreateCommentController extends AbstractCreateController {
	
	@FXML TextArea commentDescription;
	@FXML TableView CommentTable;
	@FXML TableColumn DescriptionColumn;
	@FXML TableColumn DateColumn;
	@FXML TextField commentTimestamp;

	@Override
	public void save() {
		
	}

}
