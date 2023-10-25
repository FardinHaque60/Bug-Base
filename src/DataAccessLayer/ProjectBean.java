package DataAccessLayer;

import DataAccessLayer.Connection.ConnectionType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectBean {

	private static ObservableList<ProjectBean> projectBeans = FXCollections.observableArrayList();
	private final static Connection projectConnection = new Connection(ConnectionType.PROJECT);
	
	private SimpleStringProperty name, date, description;
	
	/**
	 * Creates a ProjectBean with the given name, date, and description.
	 * @param name			name of the project
	 * @param date			date of the project
	 * @param description	description of the project
	 */
	public ProjectBean(String name, String date, String description) {
		
		// adds name and date
		this.name = new SimpleStringProperty(name);
		this.date = new SimpleStringProperty(date);
		
		//adds description as one line if user has new lines for description
		String[] tmp = description.split("\n");
		this.description = new SimpleStringProperty("");
		for (String s: tmp) {
			this.description.bind(Bindings.concat(this.description.get() + s + " "));
		}
	}
	
	/**
	 * Adds project into the database and in the list.
	 */
	public void writeProjectBean() {
		projectConnection.writeProject(this);
		projectBeans.add(this);
	}
	
	/**
	 * Gets the project bean observable list.
	 * @return all the project beans as an observable list
	 */
	public static ObservableList<ProjectBean> getAllProjectInfo() {
		return projectBeans;
	}
	
	/**
	 * Fills projectBeans list will all the project data in the database. Should only run once.
	 */
	public static void readAllProjectsInDatabase() {
		projectBeans.clear(); // don't really need this line, but added just in case it runs more than once.
		projectBeans.addAll(projectConnection.readAllProjects());
		
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
