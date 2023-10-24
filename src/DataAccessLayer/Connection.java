package DataAccessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Connection {
	final String PROJECT_DB = "FlatFiles/ProjectDB.txt";
	final String TICKET_DB = "FlatFiles/TicketDB.txt";
	final String COMMENT_DB = "FlatFiles/CommentDB.txt";
	
	File file;
	FileWriter fw;
	PrintWriter pw;
	Scanner scn;
	
	public Connection(String type) {
		if (type.equals("Project")) {
			file = new File(PROJECT_DB);
		}
		else if (type.equals("Ticket")) {
			file = new File(TICKET_DB);
		}
		else {
			file = new File(COMMENT_DB);
		}
		
		try {
			fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeProject(ProjectBean bean) { 	
		pw.println(bean.getName());
		pw.println("\t" + bean.getDate());
		pw.println("\t" + bean.getDescription());	
		pw.close();
	}
	
	//TODO: Look into read from database
	public ArrayList<ProjectBean> readAllProjects() {
		ArrayList<ProjectBean> out = new ArrayList<ProjectBean>();
		try {
			scn = new Scanner(file);
			String name, date, description;
<<<<<<< HEAD
			ProjectBean.clearAllProjectInfo();
=======
			//ProjectBean.clearAllProjectInfo();
>>>>>>> fardinBranch
			while (scn.hasNextLine()) {
				name = scn.nextLine();
				date = scn.nextLine().replace("\t", "");
				description = scn.nextLine().replace("\t", "");
				out.add(new ProjectBean(name, date, description));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
}
