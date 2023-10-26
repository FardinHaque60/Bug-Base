package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class ViewProjectController extends AbstractViewController {

	@FXML TextField nameInfo;
	@FXML TextArea descriptionInfo;
	@FXML TextField dateInfo;
	
	private static String nameFill;
	private static String descriptionFill;
	private static String dateFill;

	//TODO: get description to fit not all in one line
	@FXML public void initialize() {
		nameInfo.setText(nameFill);
		dateInfo.setText(dateFill);
		descriptionInfo.setText(descriptionFill);
	}
	
	public static void initalize(String name, String date, String description) {
		nameFill = name;
		dateFill = date;
		descriptionFill = description;
	}
	
	@Override
	public void edit() {
		// TODO opens new edit project page
	}
	
}
