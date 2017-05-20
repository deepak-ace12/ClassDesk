package tech.sty.classdesk.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BatchNames {
	
    private final StringProperty batchName;
    
    public BatchNames(){
    	this(null);
    	
    }
    
    public BatchNames(String batchName){
    	this.batchName = new SimpleStringProperty(batchName);
    }
    
    public String getBatchName() {
        return batchName.get();
    }

    public void setBatchName(String batchName) {
    	this.batchName.set(batchName);
    }

    public StringProperty batchNameProperty() {
        return batchName;
    }


}
