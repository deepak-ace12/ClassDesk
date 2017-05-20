package tech.sty.classdesk.model;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
public class ExpensesDetails {
	private final IntegerProperty srNo;
    private final StringProperty month;
    private final DoubleProperty stationary;
    private final DoubleProperty rental;
    private final DoubleProperty payment;
    private final DoubleProperty others;
    private final DoubleProperty total;
    

    /**
     * Default constructor.
     */
    public ExpensesDetails() {
        this(0,null,0,0,0,0,0);
    }
    
    
    public ExpensesDetails(int srNo, String month, double stationary, double rental, double payment, double others, double total) {
    	this.srNo = new SimpleIntegerProperty(srNo);
    	this.month = new SimpleStringProperty(month);
        this.stationary = new SimpleDoubleProperty(stationary);
        this.rental = new SimpleDoubleProperty(rental);
        this.payment = new SimpleDoubleProperty(payment);
        this.others = new SimpleDoubleProperty(others);
        this.total = new SimpleDoubleProperty(total);
        
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
    
    public String getMonth() {
        return month.get();
    }

    public void setMonth(String month) {
    	this.month.set(month);
    }

    public StringProperty monthProperty() {
        return month;
    }

    public Double getStationary() {
        return stationary.get();
    }

    public void setStationary(Double stationary) {
    	this.stationary.set(stationary);
    }

    public DoubleProperty stationaryProperty() {
        return stationary;
    }
    

    public Double getRental() {
        return rental.get();
    }

    public void setRental(Double rental) {
        this.rental.set(rental);
    }

    public DoubleProperty rentalProperty() {
        return rental;
    }

    public Double getPayments() {
        return payment.get();
    }

    public void setPayment(Double payment) {
        this.payment.set(payment);
    }

    public DoubleProperty paymentProperty() {
        return payment;
    }

    public Double getOthers() {
        return others.get();
    }

    public void setOthers(Double others) {
        this.others.set(others);
    }

    public DoubleProperty othersProperty() {
        return others;
    }
    
    public Double getTotal() {
        return total.get();
    }

    public void setTotal(Double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }
    
}
