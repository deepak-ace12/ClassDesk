package tech.sty.classdesk.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.sty.classdesk.model.PersonDetails;

public class InstallmentEditController {
	
	@FXML
    private Label studentIdLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private DatePicker date;
    @FXML
    private TextField amountField;
    
    private Stage dialogStage;
    private static int index ;
    private boolean okClicked = false;
    int feePaid, feePending, gapPeriod, totalfee;
    String nextDue;
    PersonDetails person;

    
    public void setLineIndex(int indexNo){
    	index = indexNo;
    	
    }
    public int getLineIndex(){
    	return index;
    }
    
    @FXML
    private void initialize() {
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setPerson(PersonDetails person) {
    	this.person = person;
		studentIdLabel.setText(Integer.toString(person.getStudentId()));
        fullNameLabel.setText(person.getFirstName()+" "+person.getLastName());
       
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
    		  
    		  
    		Statement stmt1 = con.createStatement();  
    		Statement stmt2 = con.createStatement();  

    		ResultSet rs = stmt1.executeQuery("SELECT * FROM sakila.feestructure"+FeeTableController.batch+" where id = "+(index+1)+"");
    		while(rs.next()){
    			totalfee = rs.getInt(2);
    			feePaid = rs.getInt(3);
    			feePending = totalfee-feePaid;
                System.out.println(feePending);

    			gapPeriod = rs.getInt(6);
    			 Calendar calendar = Calendar.getInstance();
    	    	 calendar.add(Calendar.DATE, gapPeriod*30);
    	    	 Date next = calendar.getTime();
    	    	nextDue = new SimpleDateFormat("yyyy-MM-dd").format(next);

    		}
    		feePaid = feePaid+Integer.parseInt(amountField.getText());
    		feePending = totalfee-feePaid;

    		stmt2.executeUpdate("UPDATE `sakila`.`feestructure"+FeeTableController.batch+"` SET `feePaid`='"+feePaid+"', `feePending`='"+feePending+"', "
			+ "`lastInstallment`='"+Integer.parseInt(amountField.getText())+"', `lastPaidOn`='"+date.getValue()+"', "
					+ "`nextDueON`='"+nextDue+"' WHERE `id`='"+(index+1)+"';");  
    		
    		
    		con.close();  
    		 person.setFeePaid(feePaid);
             person.setFeePending(feePending);
             System.out.println(feePending);
    		//feePaid = 0;
    		//feePending = 0;

    		}catch(Exception e){ System.out.println(e);}  
         
        
         
         okClicked = true;
         dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
