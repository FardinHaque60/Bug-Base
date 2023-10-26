package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessLayer.ProjectBean;
import DataAccessLayer.TicketBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class ViewProjectController implements Initializable {

	@FXML TextField nameInfo;
	@FXML TextArea descriptionInfo;
	@FXML TextField dateInfo;
	CommonObjs common = CommonObjs.getInstance();
	
	private static String nameFill;
	private static String descriptionFill;
	private static String dateFill;
	private static ProjectBean thisBean;
	@FXML TableView<TicketBean>  TicketTable;
	@FXML TableColumn<TicketBean, String> TicketTitles;
	@FXML TableColumn<TicketBean, String> TicketDescriptions;

	//TODO: get description to fit not all in one line
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		nameInfo.setText(nameFill);
		dateInfo.setText(dateFill);
		descriptionInfo.setText(descriptionFill);
		
		TicketTitles.setCellValueFactory(new PropertyValueFactory<>("title"));
		TicketDescriptions.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		TicketTable.setItems(thisBean.getTicketInfo());
		
	}
	
	public static void initalize(ProjectBean b) {
		thisBean = b;
		nameFill = b.getName();
		dateFill = b.getDate();
		descriptionFill = b.getDescription();
	}
	
	@FXML public void update() {
		// TODO opens new edit project page
	}
	
	@FXML public void goHome() {
		common.loadDisplay("view/ProjDisplay.fxml");
	}
	
}
