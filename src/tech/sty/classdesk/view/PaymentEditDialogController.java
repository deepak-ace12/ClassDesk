package tech.sty.classdesk.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.sty.classdesk.model.StaffDetails;
import tech.sty.classdesk.model.StaffPayment;

public class PaymentEditDialogController {
	@FXML
    private Label staffIdLabel;
    @FXML
    private Label staffNameLabel;
    @FXML
    private DatePicker date;
    @FXML
    private TextField amountField;
    @FXML
    private RadioButton cash;
    @FXML
    private RadioButton cheque;
    @FXML
    private TextField modeField;
    @FXML
    private Label paymentType;
    
    
    private Stage dialogStage;
    private static int index ;
    private boolean okClicked = false;
    int feePaid, feePending, gapPeriod, totalfee;
    String nextDue;
    StaffDetails staff;
    String command;
    
    StaffPayment sp = new StaffPayment();
    public void setLineIndex(int indexNo){
    	index = indexNo;
    	
    }
    public int getLineIndex(){
    	return index;
    }
    
    @FXML
    private void initialize() {
    	modeField.setVisible(false);
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setStaff(StaffDetails staff) {
    	this.staff = staff;
		staffIdLabel.setText(Integer.toString(staff.getStaffId()));
        staffNameLabel.setText(staff.getFirstName()+" "+staff.getLastName());
       
    }
    
    public boolean isOkClicked() {
        return okClicked;             
    }
    
    @FXML
    private void handleSubmit(){
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sakila","root","root");  
    		  
    		  
    		Statement stmt = con.createStatement();  
    		int payment = Integer.parseInt(amountField.getText());
    		int staffId = Integer.parseInt(staffIdLabel.getText());
    		int count = new StaffController().getNonEmptyRowCount(staffId)/4+1;
    		if(cash.isSelected()){
    			command = "UPDATE `sakila`.`paymentinfo` SET `date"+count+"`='"+date.getValue()+"', `payment"+count+"`='"+payment+"',"
    					+ " `mode"+count+"`='cash', `receipt"+count+"`='"+modeField.getText()+"' WHERE `staffId`='"+staffId+"'";
    			
    			
    			sp.setStaffId(staffId);
    			sp.setPaymentDate(date.getValue());
    			sp.setPaid(payment);
    			sp.setMode("cash");
    			sp.setReceiptNo(modeField.getText());
    		}else if(cheque.isSelected()){
    			command = "UPDATE `sakila`.`paymentinfo` SET `date"+count+"`='"+date.getValue()+"', `payment"+count+"`='"+payment+"',"
    					+ " `mode"+count+"`='cheque', `receipt"+count+"`='"+modeField.getText()+"' WHERE `staffId`='"+staffId+"'";
    			System.out.println(command);
    			sp.setStaffId(staffId);
    			sp.setPaymentDate(date.getValue());
    			sp.setPaid(payment);
    			sp.setMode("cheque");
    			sp.setReceiptNo(modeField.getText());

    		}
    		stmt.executeUpdate(command);
    		
    		con.close();  
//    		 staff.setFeePaid(feePaid);
//             staff.setFeePending(feePending);
//             System.out.println(feePending);
//    		//feePaid = 0;
//    		//feePending = 0;

    		}catch(Exception e){ System.out.println(e);}  
         
        
         
         okClicked = true;
         dialogStage.close();
    }

    @FXML
    private void handleCancel() {
    	System.out.println(cash.isSelected()+" "+cheque.isSelected());

        dialogStage.close();
    }
    
    @FXML
	private void handleCash(){
		cash.setSelected(true);
		cheque.setSelected(false);
    	paymentType.setText("Enter Receipt No.");
    	modeField.setVisible(true);
	}
    
    @FXML
	private void handleCheque(){
		cash.setSelected(false);
		cheque.setSelected(true);
    	paymentType.setText("Enter Cheque No.");
    	modeField.setVisible(true);

	}
}
