package DataAccessLayer;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;

public class ProjectBean {

	static ArrayList<ProjectBean> projectBeans = new ArrayList<ProjectBean>();
	SimpleStringProperty name;
	SimpleStringProperty date;
	SimpleStringProperty description;
	final String TYPE = "Project";
	
	//takes in information from scene and adds attributes to the bean
	public void makeProjectBean(String name, String date, String description) {
		this.name = new SimpleStringProperty(name);
		this.date = new SimpleStringProperty(date);
		
		//adds description as one line if user has new lines for description
		String[] tmp = description.split("\n");
		int i;
		this.description = new SimpleStringProperty("");
		for (String s: tmp) {
			this.description.bind(Bindings.concat(this.description.get() + s + " "));
		}
		
		Connection projectConnection = new Connection(TYPE);
		projectConnection.writeProject(this);
		
		addBean(this);
	}
	
	public static ArrayList<ProjectBean> getAllProjectInfo() {
		return projectBeans;
	}

	public static void addBean(ProjectBean b) {
		projectBeans.add(b);
	}
	
	public static int numBeans() {
		return projectBeans.size();
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
