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
import tech.sty.classdesk.util.Calender;
import tech.sty.classdesk.util.LocalDateAdapter;
/**
 * Model class for a Person.
 *
 * @author STY Technologies
 */
public class PersonDetails{
	private final IntegerProperty studentId;
    private final StringProperty firstName;
    private final StringProperty middleName;
    private final StringProperty emailId;
    private final StringProperty lastName;
    private final StringProperty branch;
    private final StringProperty college;
    private final ComboBoxBase<LocalDate> admission;
    private final StringProperty contact;
    private final StringProperty guardianContact;
    private final ComboBoxBase<LocalDate> birthday;
    private final StringProperty address;
    private final IntegerProperty nInstallment;
    private final IntegerProperty amtOfInstallment;
    private final IntegerProperty gapPeriod;
    private final IntegerProperty feePaid;
    private final IntegerProperty feePending;
    private final IntegerProperty feeTotal;
    private final IntegerProperty lastInstallment;
    private final StringProperty paidOn;
    private final StringProperty nextDue;
    private final StringProperty fullName;
    private final ImageView imgView;
    private final StringProperty admissionDate;


   
    /**
     * Default constructor.
     */
    public PersonDetails() {
        this(0, null, null, null, null, null, null, null, null, null, null, null,0,0,0,0,0,0,0, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     *  @param middleName
     * @param lastName
     */
    public PersonDetails(int id, String firstName, String middleName,String lastName,String emailId,
    		String branchName,String collegeName,LocalDate admissionDate, String contactNo,String guardianContact, 
    		LocalDate dateOfBirth, String address, int feeTotal, int feePaid,int nInstallment, int amtOfInstallment, int gapPeriod,
    		int feePending, int lastInstallment, String paidOn,String nextDue, String fullName) {
    	this.studentId = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lastName = new SimpleStringProperty(lastName);
        this.emailId = new SimpleStringProperty(emailId);

        // Some initial dummy data, just for convenient testing.
        this.branch = new SimpleStringProperty(branchName);
        this.college = new SimpleStringProperty(collegeName);
        this.admission = new DatePicker(admissionDate);
        this.admissionDate = new SimpleStringProperty(Calender.format(admissionDate));

        this.contact = new SimpleStringProperty(contactNo);
        this.guardianContact = new SimpleStringProperty(guardianContact);

        this.birthday = new DatePicker(dateOfBirth);
        this.address = new SimpleStringProperty(address);
        this.nInstallment = new SimpleIntegerProperty(nInstallment);
		this.amtOfInstallment = new SimpleIntegerProperty(amtOfInstallment);
		this.gapPeriod = new SimpleIntegerProperty(gapPeriod);
        this.feePaid = new SimpleIntegerProperty(feePaid);
		this.feePending = new SimpleIntegerProperty(feePending);
		this.feeTotal = new SimpleIntegerProperty(feeTotal);
		this.lastInstallment = new SimpleIntegerProperty(lastInstallment);
		this.paidOn = new SimpleStringProperty(paidOn);
        this.nextDue = new SimpleStringProperty(nextDue);
        this.fullName = new SimpleStringProperty(fullName);
        this.imgView = new ImageView();

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

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
    	this.firstName.set(firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase().trim());
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public String getMiddleName() {
        return middleName.get();
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName.substring(0, 1).toUpperCase() + middleName.substring(1).toLowerCase().trim());
    }

    public StringProperty middleNameProperty() {
        return middleName;
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
    
    public String getEmailId() {
        return emailId.get();
    }

    public void setEmailId(String emailId) {
        this.emailId.set(emailId);
    }

    public StringProperty emaildProperty() {
        return emailId;
    }

    public String getBranch() {
        return branch.get();
    }

    public void setBranch(String branch) {
        this.branch.set(branch.substring(0, 1).toUpperCase() + branch.substring(1).toLowerCase().trim());
    }

    public StringProperty branchProperty() {
        return branch;
    }

    public String getCollege() {
        return college.get();
    }

    public void setCollege(String college) {
        this.college.set(college.substring(0, 1).toUpperCase() + college.substring(1).toLowerCase().trim());
    }

    public StringProperty collegeProperty() {
        return college;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getAdmission() {
          return admission.getValue();
    }

    public void setAdmission(LocalDate admission) {
        this.admission.setValue(admission);
    }

    public ComboBoxBase<LocalDate> admissionProperty() {
        return admission;
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
    
    public String getGuardianContact() {
        return guardianContact.get();
    }

    public void setGuardianContact(String guardianContact) {
        this.guardianContact.set(guardianContact);
    }

    public StringProperty guardianContactProperty() {
        return guardianContact;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.getValue();
  }

  public void setBirthday(LocalDate birthday) {
      this.birthday.setValue(birthday);
  }

  public ComboBoxBase<LocalDate> birthdayProperty() {
      return birthday;
  }
  
  public String getAddress() {
      return address.get();
  }

  public void setAddress(String address) {
      this.address.set(address);
  }

  public StringProperty addressProperty() {
      return address;
  }
  
  
  
  /****
   * 
   * 
   * Student fee details
   * 
   */
  
  public int getnInstallment() {
      return nInstallment.get();
  }

  public void setnInstallment(int nInstallment) {
  	this.nInstallment.set(nInstallment);
  }

  public IntegerProperty nInstallmentProperty() {
      return nInstallment;
  }
  
  public int getamtOfInstallment() {
      return amtOfInstallment.get();
  }

  public void setAmtOfInstallment(int amtOfInstallment) {
  	this.amtOfInstallment.set(amtOfInstallment);
  }

  public IntegerProperty amtOfInstallmentProperty() {
      return amtOfInstallment;
  }
  
  public int getGapPeriod() {
      return gapPeriod.get();
  }

  public void setGapPeriod(int gapPeriod) {
  	this.gapPeriod.set(gapPeriod);
  }

  public IntegerProperty gapPeriodProperty() {
      return gapPeriod;
  }
  
  public int getFeePaid() {
      return feePaid.get();
  }

  public void setFeePaid(int feePaid) {
  	this.feePaid.set(feePaid);
  }

  public IntegerProperty feePaidProperty() {
      return feePaid;
  }

  public int getFeePending() {
      return feePending.get();
  }

  public void setFeePending(int feePending) {
  	this.feePending.set(feePending);
  }

  public IntegerProperty feePendingProperty() {
      return feePending;
  }
  
  
  
  public int getFeeTotal() {
      return feeTotal.get();
  }

  public void setFeeTotal(int feeTotal) {
  	this.feeTotal.set(feeTotal);
  }

  public IntegerProperty feeTotalProperty() {
      return feeTotal;
  }

  
  
  public int getlastInstallment() {
      return lastInstallment.get();
  }

  public void setlastInstallment(int lastInstallment) {
  	this.lastInstallment.set(lastInstallment);
  }

  public IntegerProperty lastInstallmentProperty() {
      return lastInstallment;
  }


  public String getPaidOn() {
      return paidOn.get();
  }

  public void setPaidOn(String paidOn) {
  	this.paidOn.set(paidOn);
  }

  public StringProperty paidOnProperty() {
      return paidOn;
  }
  
  
  public String getNextDue() {
      return nextDue.get();
  }

  public void setNextDue(String nextDue) {
  	this.nextDue.set(nextDue);
  }

  public StringProperty nextDueProperty() {
      return nextDue;
  }
  
  public StringProperty fullNameProperty() {
      return fullName;
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
  
  public void setAdmissionDate(String admissionDate) {
      this.admissionDate.set(admissionDate);
  }
  
  public StringProperty admissionDateProperty(){
	  return admissionDate;
	 
  }
    
	    
  
  
 }

