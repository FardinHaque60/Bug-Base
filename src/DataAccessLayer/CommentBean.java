package DataAccessLayer;

import DataAccessLayer.Connection.ConnectionType;
import javafx.beans.property.SimpleStringProperty;

public class CommentBean {
	
	SimpleStringProperty date;
	SimpleStringProperty description;
	final Connection commentConnection = new Connection(ConnectionType.COMMENT);
	
	
}
