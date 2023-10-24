package DataAccessLayer;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectBean {

	//potentially remove list of beans
	//TODO: moving into observable instead of projectBeans static list
	//TODO: methods to use for new observableList, initialize list based on anything that is 
	//in DB, and write projects 
	
	ObservableList<ProjectBean> projectBeans_TO_ADD = FXCollections.observableArrayList();
	
	//TODO: delete below
	static ArrayList<ProjectBean> projectBeans = new ArrayList<ProjectBean>();
	
	SimpleStringProperty name;
	SimpleStringProperty date;
	SimpleStringProperty description;
	final static String TYPE = "Project";
	final static Connection projectConnection = new Connection(TYPE);
	
	public ProjectBean() {
		
	}
	
	//takes in information from scene and adds attributes to the bean
	public ProjectBean(String name, String date, String description) {
		this.name = new SimpleStringProperty(name);
		this.date = new SimpleStringProperty(date);
		
		//adds description as one line if user has new lines for description
		String[] tmp = description.split("\n");
		this.description = new SimpleStringProperty("");
		for (String s: tmp) {
			this.description.bind(Bindings.concat(this.description.get() + s + " "));
		}
		
		//Connection projectConnection = new Connection(TYPE);
		//projectConnection.writeProject(this);
	
		//addBean(this);
	}
	
	public void writeProjectBean() {
		projectConnection.writeProject(this);
		addBean(this);
	}
	
//OLD IMPLEMENTATION OF READING BEANS START
	
	//implementation using beans for info
	public static ArrayList<ProjectBean> getAllProjectInfo() {
		return projectBeans;
	}
	
	public static void clearAllProjectInfo() {
		projectBeans.clear();
	}
	
	public static int numBeans() {
		return projectBeans.size();
	}
	
	public static void addBean(ProjectBean b) {
		projectBeans.add(b);
	}
	
//OLD IMPLEMENTATION OF READING BEANS END
	
	
	//TODO: update this method to take this list returned from connection to populate obervableList
	public static ArrayList<ProjectBean> readAllProjectInfo() {
<<<<<<< HEAD
		return ProjectBean.projectConnection.readAllProjects();
=======
		//TODO: should populate observableLisst list directly
		return projectConnection.readAllProjects();
>>>>>>> fardinBranch
	}

	public String getName() {
		return this.name.get();
	}

	public String getDate() {
		return this.date.get();
	}

	public String getDescription() {
		return this.description.get();
	}
}
