package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {
	@FXML HBox mainBox;

	@FXML
	public void initialize() { 
		showProjectsOp();
	}
		
	//TODO: use this method often when returning to main page
	@FXML public void showProjectsOp() {
		
		URL url = getClass().getClassLoader().getResource("view/ProjDisplay.fxml");
		try {
			AnchorPane pane0 = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			VBox tmp1 = (VBox) pane0.getChildren().get(1);
			
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
			
			mainBox.getChildren().add(pane0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void showCreateProjectOp() {
		
		URL url = getClass().getClassLoader().getResource("view/CreateProject.fxml");
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			DatePicker dateP = (DatePicker) pane1.getChildren().get(3);
			pane1.getChildren().remove(3);
            dateP.setValue(LocalDate.now());
            pane1.getChildren().add(3, dateP);
			
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void showCreateTicketOp() {
		URL url = getClass().getClassLoader().getResource("view/CreateTicket.fxml");
		try {
			AnchorPane pane2 = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
