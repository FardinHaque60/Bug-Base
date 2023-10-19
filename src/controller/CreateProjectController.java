package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import DataAccessLayer.JavaBean;
import application.CommonObjs;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class CreateProjectController {

	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML TextField projName;
	@FXML DatePicker projDate;
	@FXML TextArea projDesrip;
	//@FXML VBox projList;
	HBox mainBox = commonObjs.getMainBox();
	
	//TODO: fix loading DB call
	@FXML public void goHomeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/ProjDisplay.fxml");
		try {
			AnchorPane pane3 = (AnchorPane) FXMLLoader.load(url);
			
			//HBox mainBox = commonObjs.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			VBox tmp1 = (VBox) pane3.getChildren().get(1);
			
			Scanner scn = new Scanner(new File("FlatFiles/ProjectDB.txt"));
			String name;
			int count = 1;
			while (scn.hasNextLine()) {
				name = scn.nextLine();
				tmp1.getChildren().add(new Label("\t" + count + ". " + name));
				count++;
				scn.nextLine();
				scn.nextLine();
			}
			
			mainBox.getChildren().add(pane3);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//TODO change implementation to have Javabeans -> DAO -> etc.
	//TODO: fix loading DB call
	@FXML public void saveProj() {
		
		try {
			File projDB = new File("FlatFiles/ProjectDB.txt");
			//System.out.println("printed");
			FileWriter fw = new FileWriter(projDB, true);
			PrintWriter pw = new PrintWriter(fw);
			
			String tmp = projName.getText();
			
			//JavaBean projN = new JavaBean(projDate.getValue(), projName.getText(), projDesrip.getText());

			pw.println(projName.getText());
			pw.println("\t" + projDate.getValue());
			pw.println("\t" + projDesrip.getText());	
			
			pw.close();
			
			URL url = getClass().getClassLoader().getResource("view/ProjDisplay.fxml");
			AnchorPane projDisp = (AnchorPane) FXMLLoader.load(url);
			
			//HBox mainBox = commonObjs.getMainBox();
			
			
			//just so we can add labels to the Vbox that is in project display, vBox is the
			//white screen behind labels
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			VBox tmp1 = (VBox) projDisp.getChildren().get(1);
			
			// TODO fix implementation to solve newline in description field
			Scanner scn = new Scanner(new File("FlatFiles/ProjectDB.txt"));
			//replace with reading information from javaBean
			String name;
			int count = 1;
			while (scn.hasNextLine()) {
				name = scn.nextLine();
				Label temp = new Label("\t" + count + ". " + name);
				tmp1.getChildren().add(temp);
				count++;
				scn.nextLine();
				scn.nextLine();
			}
			mainBox.getChildren().add(projDisp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
