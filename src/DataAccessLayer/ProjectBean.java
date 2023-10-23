package DataAccessLayer;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;

public class ProjectBean {

	//potentially remove list of beans
	static ArrayList<ProjectBean> projectBeans = new ArrayList<ProjectBean>();
	SimpleStringProperty name;
	SimpleStringProperty date;
	SimpleStringProperty description;
	final String TYPE = "Project";
	final Connection projectConnection = new Connection(TYPE);
	
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
	
		addBean(this);
	}
	
	public void writeProjectBean() {
		projectConnection.writeProject(this);
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
	
	//TODO: Poor implementation of reading from db
	public static ArrayList<ProjectBean> readAllProjectInfo() {
		ProjectBean tmp = new ProjectBean();
		return tmp.projectConnection.readAllProjects();
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
