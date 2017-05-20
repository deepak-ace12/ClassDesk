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

public class StaffPayment extends StaffDetails {
	private final IntegerProperty staffId;
    private final IntegerProperty jpaid;
    //private final DoubleProperty fpaid;
    private final StringProperty mode;	
    private final ComboBoxBase<LocalDate> paymentDate;
    private final StringProperty payDate;
    private final StringProperty receiptNo;



    
    
    public StaffPayment() {
        this(0, null, 0, null, null);
    }
    
    
    public StaffPayment(int id,LocalDate paymentDate, int jpaid, String mode, String receiptNo) {
    	this.staffId = new SimpleIntegerProperty(id);
        this.jpaid = new SimpleIntegerProperty(jpaid);
        //this.fpaid = new SimpleDoubleProperty(fpaid);
        this.mode = new SimpleStringProperty(mode);
        this.paymentDate = new DatePicker(paymentDate);
        this.payDate = new SimpleStringProperty(Calender.format(paymentDate));
        this.receiptNo = new SimpleStringProperty(receiptNo);

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

    
    public Integer getPaid() {
        return jpaid.get();
    }

    public void setPaid(int paid) {
        this.jpaid.set(paid);
    }

    public IntegerProperty paidProperty() {
        return jpaid;
    }

    public String getMode() {
        return mode.get();
    }

    public void setMode(String mode) {
        this.mode.set(mode.substring(0,1).toUpperCase() + mode.substring(1).toLowerCase().trim());
    }

    public StringProperty modeProperty() {
        return mode;
    }
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getPaymentDate() {
        return paymentDate.getValue();
  }

  
  public void setPaymentDate(LocalDate paymentDate) {
      this.paymentDate.setValue(paymentDate);
  }

  public ComboBoxBase<LocalDate> paymentDateProperty() {
      return paymentDate;
  }
  
  public void setPayDate(String payDate) {
      this.payDate.set(payDate);
  }
  public StringProperty payDateProperty(){
	  return payDate;
  }
  
  public String getReceiptNo() {
      return receiptNo.get();
  }

  public void setReceiptNo(String receiptNo) {
      this.receiptNo.set(receiptNo);
  }

  public StringProperty receiptNoProperty() {
      return receiptNo;
  }
  
  
}
