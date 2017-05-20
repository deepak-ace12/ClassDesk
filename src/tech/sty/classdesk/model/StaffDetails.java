package tech.sty.classdesk.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tech.sty.classdesk.util.LocalDateAdapter;


public class StaffDetails {
	private final IntegerProperty staffId;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty subject1;
    private final StringProperty subject2;
    private final ComboBoxBase<LocalDate> joining;
    private final StringProperty contact;
    private final StringProperty department;
    private final ImageView imgView;

    /**
     * Default constructor.
     */
    public StaffDetails() {
        this(0, null,null, null , null, null,null,null,null);
    }
    
    
    
    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     *  @param middleName
     * @param lastName
     */
    public StaffDetails(int id, String firstName, String lastName, String email, String subject1, String subject2, LocalDate joining, String contact, String department) {
    	this.staffId = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        // Some initial dummy data, just for convenient testing.
        this.email = new SimpleStringProperty(email);
        this.subject1 = new SimpleStringProperty(subject1);  
        this.subject2 = new SimpleStringProperty(subject2);  
        this.joining = new DatePicker(joining);
        this.contact = new SimpleStringProperty(contact);
        this.department = new SimpleStringProperty(department); 
        this.imgView = new ImageView();
    }
    
    
	public int getStaffId() {
        return staffId.get();
    }

    public void setStaffId(int id) {
    	this.staffId.set(id);
    }

    public IntegerProperty staffIdProperty() {
        return staffId;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
    	this.firstName.set(firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase().trim());
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase().trim());
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getSubject1() {
        return subject1.get();
    }

    public void setSubject1(String subject1) {
        this.subject1.set(subject1.substring(0, 1).toUpperCase() + subject1.substring(1).toLowerCase().trim());
    }

    public StringProperty subjectProperty1() {
        return subject1;
    }
    
    public String getSubject2() {
        return subject2.get();
    }

    public void setSubject2(String subject2) {
        this.subject2.set(subject2.substring(0, 1).toUpperCase() + subject2.substring(1).toLowerCase().trim());
    }

    public StringProperty subjectProperty2() {
        return subject2;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getJoining() {
          return joining.getValue();
    }

    public void setJoining(LocalDate joining) {
        this.joining.setValue(joining);
        
    }

    public ComboBoxBase<LocalDate> joiningProperty() {
        return joining;
    }
   
    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty contactProperty() {
        return contact;
    }
    
    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department.substring(0, 1).toUpperCase() + department.substring(1).toLowerCase().trim());
    }

    public StringProperty departmentProperty() {
        return department;
    }
    
    public void setView(Image img1) {
   	 File outputFile = new File("E:/images/"+getFirstName()+getLastName()+".jpg");
    	try {
        	BufferedImage cImage = SwingFXUtils.fromFXImage(img1, null);
            ImageIO.write(cImage, "png", outputFile);
        }
        catch (IOException e) { 
        	throw new RuntimeException(e);
        	}
    	this.imgView.setImage(img1);
   	}
    
  }

