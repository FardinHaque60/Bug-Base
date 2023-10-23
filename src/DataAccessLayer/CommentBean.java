package DataAccessLayer;

import javafx.beans.property.SimpleStringProperty;

public class CommentBean {
	
	int uid;
	SimpleStringProperty title;
	SimpleStringProperty date;
	SimpleStringProperty description;
	final String TYPE = "Comment";
	final Connection projectConnection = new Connection(TYPE);
	
	
}
