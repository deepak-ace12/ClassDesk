package tech.sty.classdesk.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import tech.sty.classdesk.util.Calender;
import tech.sty.classdesk.util.LocalDateAdapter;

public class EnquiryDetails {
	private final IntegerProperty srNo;
    private final StringProperty fullName;
    private final ComboBoxBase<LocalDate> enquiryDate;
    private final StringProperty purpose;
    private final StringProperty email;
    private final StringProperty contact;
    private final ComboBoxBase<LocalDate> revertDate;
    private final StringProperty eDate;
    private final StringProperty rDate;
    
    public EnquiryDetails(){
    	this(0, null, null, null, null, null, null);
    	
    }
    
    public EnquiryDetails(int srNo, String fullName, LocalDate enquiryDate, String purpose, String email, String contact, 
    		LocalDate revertDate){
    	this.srNo = new SimpleIntegerProperty(srNo);
        this.fullName = new SimpleStringProperty(fullName);
        this.enquiryDate = new DatePicker(enquiryDate);
        this.purpose = new SimpleStringProperty(purpose);
        this.email = new SimpleStringProperty(email);
        this.contact = new SimpleStringProperty(contact);
        this.revertDate = new DatePicker(revertDate);
        this.eDate = new SimpleStringProperty(Calender.format(enquiryDate));
        this.rDate = new SimpleStringProperty(Calender.format(revertDate));

    	
    }
    
    public int getSrNo() {
        return srNo.get();
    }

    public void setSrNo(int srNo) {
    	this.srNo.set(srNo);
    }

    public IntegerProperty srNoProperty() {
        return srNo;
    }

    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String fullName) {
    	this.fullName.set(fullName);
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getEnquiryDate2() {
        return enquiryDate.getValue();
  }

  public void setEnquiryDate(String enquiryDate) {
      this.eDate.set(enquiryDate);
  }
  public void setEnquiryDate2(LocalDate enquiryDate) {
      this.enquiryDate.setValue(enquiryDate);
  }

  public ComboBoxBase<LocalDate> enquiryDateProperty() {
      return enquiryDate;
  }
    
    public String getPurpose() {
        return purpose.get();
    }

    public void setPurpose(String purpose) {
    	this.purpose.set(purpose);
    }

    public StringProperty purposeProperty() {
        return purpose;
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
    
    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
    	this.contact.set(contact);
    }

    public StringProperty contactProperty() {
        return contact;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getRevertDate2() {
        return revertDate.getValue();
  }

  public void setRevertDate2(LocalDate revertDate) {
      this.revertDate.setValue(revertDate);
  }
  
  public void setRevertDate(String revertDate) {
      this.rDate.set(revertDate);
  }

  public ComboBoxBase<LocalDate> revertDateProperty() {
      return revertDate;
  }
  
  public StringProperty enqDateProperty(){
	  return eDate;
  }
   
  public StringProperty revDateProperty(){
	  return rDate;
	 
  }
    

}
