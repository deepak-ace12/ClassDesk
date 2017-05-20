package tech.sty.classdesk.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentScoreDetails{
	private StringProperty studentName;
	private IntegerProperty sr;
	private IntegerProperty studentId;
	private IntegerProperty obtainedMarks1 ;
	private IntegerProperty maximumMarks1;
	private IntegerProperty obtainedMarks2;
	private IntegerProperty maximumMarks2;
	private IntegerProperty obtainedMarks3;
	private IntegerProperty maximumMarks3;
	private IntegerProperty obtainedMarks4;
	private IntegerProperty maximumMarks4;
	private DoubleProperty averagePercentage;
	
	public StudentScoreDetails(){
		this(null,null,null,null,null,null,null,null,null,null,null,null);
		
	}
	
	public StudentScoreDetails(String studentName, Integer sr, Integer studentId, Integer marks1, Integer max1, Integer marks2,
			Integer max2, Integer marks3, Integer max3, Integer marks4, Integer max4, Double average ) {
		this.studentName = new SimpleStringProperty(studentName);
		this.sr = new SimpleIntegerProperty(sr);
		this.studentId = new SimpleIntegerProperty(studentId);
		this.obtainedMarks1 = new SimpleIntegerProperty(marks1);
		this.maximumMarks1 = new SimpleIntegerProperty(max1);
		this.obtainedMarks2 = new SimpleIntegerProperty(marks2);
		this.maximumMarks2 = new SimpleIntegerProperty(max2);
		this.obtainedMarks3 = new SimpleIntegerProperty(marks3);
		this.maximumMarks3 = new SimpleIntegerProperty(max3);
		this.obtainedMarks4 = new SimpleIntegerProperty(marks4);
		this.maximumMarks4 = new SimpleIntegerProperty(max4);
		this.averagePercentage = new SimpleDoubleProperty(average);
		
	}
	
	public StudentScoreDetails(String name, int sr, int studentId){
		this.studentName = new SimpleStringProperty(name);
		this.sr = new SimpleIntegerProperty(sr);
		this.studentId = new SimpleIntegerProperty(studentId);
	}
	

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String name) {
    	this.studentName.set(name);
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }
    
    
    public int getStudentSr() {
        return sr.get();
    }

    public void setSr(int sr) {
    	this.sr.set(sr);
    }

    public IntegerProperty srProperty() {
        return sr;
    }

	

    public int getStudentId() {
        return studentId.get();
    }

    public void setStudentId(int id) {
    	this.studentId.set(id);
    }
    public IntegerProperty studentIdProperty() {
        return studentId;
    }
    
    

    
    public int getObtainedMarks1() {
        return obtainedMarks1.get();
    }

    public void setObtainedMarks1(int marks1) {
    	this.obtainedMarks1.set(marks1);
    }
    public IntegerProperty obtainedMarks1Property() {
        return obtainedMarks1;
    }
    
    
    
    
    public int getMaximumMarks1() {
        return maximumMarks1.get();
    }

    public void setMaximumMarks1(int max1) {
    	this.maximumMarks1.set(max1);
    }
    public IntegerProperty maximumMarks1Property() {
        return maximumMarks1;
    }
    
    
    
    public int getObtainedMarks2() {
        return obtainedMarks2.get();
    }

    public void setObtainedMarks2(int marks2) {
    	this.obtainedMarks2.set(marks2);
    }
    public IntegerProperty obtainedMarks2Property() {
        return obtainedMarks2;
    }
    
    
    
    
    public int getMaximumMarks2() {
        return maximumMarks2.get();
    }

    public void setMaximumMarks2(int max2) {
    	this.maximumMarks2.set(max2);
    }
    public IntegerProperty maximumMarks2Property() {
        return maximumMarks2;
    }
    
    
    public int getObtainedMarks3() {
        return obtainedMarks3.get();
    }

    public void setObtainedMarks3(int marks3) {
    	this.obtainedMarks3.set(marks3);
    }
    public IntegerProperty obtainedMarks3Property() {
        return obtainedMarks3;
    }
    
    
    
    
    public int getMaximumMarks3() {
        return maximumMarks3.get();
    }

    public void setMaximumMarks3(int max3) {
    	this.maximumMarks3.set(max3);
    }
    public IntegerProperty maximumMarks3Property() {
        return maximumMarks3;
    }
    
    
    public int getObtainedMarks4() {
        return obtainedMarks4.get();
    }

    public void setObtainedMarks4(int marks4) {
    	this.obtainedMarks4.set(marks4);
    }
    public IntegerProperty obtainedMarks4Property() {
        return obtainedMarks4;
    }
    
    
    
    
    public int getMaximumMarks4() {
        return maximumMarks4.get();
    }

    public void setMaximumMarks4(int max4) {
    	this.maximumMarks4.set(max4);
    }
    public IntegerProperty maximumMarks4Property() {
        return maximumMarks4;
    }
    
    
    
    public double getAveragePercentage() {
        return averagePercentage.get();
    }

    public void setAveragePercentage(double average) {
    	this.averagePercentage.set(average);
    }
    public DoubleProperty averagePercenageProperty() {
        return averagePercentage;
    }
    

    

	
	






}
