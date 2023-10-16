package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//TODO change implementation to have Javabeans -> DAO -> etc.
	@FXML public void saveProj() {
		
		try {
			File projDB = new File("FlatFiles/ProjectDB.txt");
			//System.out.println("printed");
			FileWriter fw = new FileWriter(projDB, true);
			PrintWriter pw = new PrintWriter(fw);
			
			String tmp = projName.getText();
			pw.println(projName.getText());
			pw.println("\t" + projDate.getValue());
			pw.println("\t" + projDesrip.getText());	
			
			pw.close();
			
			URL url = getClass().getClassLoader().getResource("view/ProjDisplay.fxml");
			AnchorPane projDisp = (AnchorPane) FXMLLoader.load(url);
			
			//HBox mainBox = commonObjs.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			VBox tmp1 = (VBox) projDisp.getChildren().get(1);
			
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
			
			mainBox.getChildren().add(projDisp);
			
			//TODO add code to read DB and add project name to project list
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
