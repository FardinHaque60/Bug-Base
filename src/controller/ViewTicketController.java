package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ViewTicketController implements Initializable{
	@FXML Text name;
	@FXML Text description;
	@FXML TableView<TicketBean>  CommentTable;
	@FXML TableColumn<TicketBean, String> CommentDate;
	@FXML TableColumn<TicketBean, String> CommentDescriptions;
	
	CommonObjs common = CommonObjs.getInstance();
	
	private static String dateFill;
	private static String descriptionFill;
	private static ProjectBean thisBean;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		name.setText(dateFill);
		description.setText(descriptionFill);
		
		CommentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		CommentDescriptions.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		CommentTable.setItems(thisBean.getTicketInfo());
		
	}
	public static void initalize(ProjectBean b) {
		thisBean = b;
		dateFill = b.getDate();
		descriptionFill = b.getDescription();
	}
	@FXML public void makeNewComment() {
		//TO-DO implement comments 
	}
	@FXML public void goHome() {
		common.loadDisplay("view/ProjDisplay.fxml");
	}
	

}
