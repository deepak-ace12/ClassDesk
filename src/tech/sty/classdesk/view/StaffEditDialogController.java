package tech.sty.classdesk.view;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tech.sty.classdesk.model.StaffDetails;


/**
 * Dialog to edit details of a person.
 * 
 * @author STY Technologies
 */
public class StaffEditDialogController {
	
	@FXML
    private TextField staffIdField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField subjectField1;
    @FXML
    private TextField subjectField2;
    @FXML
    private DatePicker joining;
    @FXML
    private TextField contactField;
    @FXML
    private TextField deptField;
    @FXML
    Image img1 = new Image("file:E:/images/Photo.jpg");
    @FXML
    private Button btnOk = new Button("OK");
    @FXML
    private Button btnCancel = new Button("Cancel");
    

    
    private Stage dialogStage;
    private StaffDetails staff;
    private boolean okClicked = false;
    private static int index ;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/sty";

    StaffController sc = new StaffController();
    public void setLineIndex(int indexNo){
    	index = indexNo;
    	
    }
    public int getLineIndex(){
    	return index;
    }
    

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }

    /**€
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        btnOk.setOnKeyPressed(new EventHandler<KeyEvent>()
    	{
    	     public void handle(KeyEvent evt)
    	     {
    	          if (evt.getCode() == KeyCode.ENTER)
					try {
						if(isInputValid()){
						handleOk();
						dialogStage.close();
						}
						else{
							dialogStage.showAndWait();
						}
					} catch (IOException e) {						
						e.printStackTrace();
					}
    	     }
    	});
        
        btnCancel.setOnKeyPressed(new EventHandler<KeyEvent>()
    	{
    	     public void handle(KeyEvent evt)
    	     {
    	          if (evt.getCode() == KeyCode.ENTER)
					handleCancel();
					
    	     }
    	});
        
        joining.setOnKeyPressed(new EventHandler<KeyEvent>()
    	{
    	     public void handle(KeyEvent evt)
    	     {
    	          if (evt.getCode() == KeyCode.ENTER)
					try {
						if(isInputValid()){
						handleOk();
						dialogStage.close();
						}
						else{
							dialogStage.showAndWait();
						}
					} catch (IOException e) {						
						e.printStackTrace();
					}
    	     }
    	});
    	
    	    }
    

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    
        public void setStaff(StaffDetails staff) {
        this.staff = staff;
        staffIdField.setText(Integer.toString(staff.getStaffId()));
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        emailField.setText(staff.getEmail());
        subjectField1.setText(staff.getSubject1());
        subjectField2.setText(staff.getSubject2());
        joining.setValue(staff.getJoining());
        joining.setPromptText("dd/mm/yyyy");
        contactField.setText(staff.getContact());
        if(sc.isNewClicked())
        deptField.setText("None");
        else
        deptField.setText(staff.getDepartment());
        
    }
     
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
    */
    public boolean isOkClicked() {
        return okClicked;             
    }

    /**
     * Called when the user clicks ok.
     */
    
    @FXML
    private void handleOk() throws IOException {
	            int id = Integer.parseInt(staffIdField.getText());
	            String firstName = firstNameField.getText().substring(0, 1).toUpperCase() + firstNameField.getText().substring(1).toLowerCase();
	            String lastName = lastNameField.getText().substring(0, 1).toUpperCase() + lastNameField.getText().substring(1).toLowerCase();
	            String email = emailField.getText();
	            String subjectName = subjectField1.getText().substring(0, 1).toUpperCase() + subjectField1.getText().substring(1).toLowerCase();
	            String subjectName1 = subjectField2.getText().substring(0, 1).toUpperCase() + subjectField2.getText().substring(1).toLowerCase();
	            LocalDate joiningDate = joining.getValue();
	            String contactNo = contactField.getText();
	            String department = deptField.getText();
	            
	            
	            //  Database credentials
	            
	            String data = (firstName + "," + lastName+ "," + email+ "," + subjectName +"," + subjectName1 +"," + 
	    	            joiningDate + "," + contactNo +"," + department);
	    	            System.out.println(data);
	    	            staff.setStaffId(id);
	    	            staff.setFirstName(firstName);
	    	            staff.setLastName(lastName);
	    	            staff.setEmail(email);
	    	            staff.setSubject1(subjectName);
	    	            staff.setSubject2(subjectName1);
	    	            staff.setJoining(joiningDate);
	    	            staff.setContact(contactNo);
	    	            staff.setDepartment(department);
	    	            staff.setView(img1);
	            Connection conn = null;
	            Statement stmt = null;
	            try{
	               //STEP 2: Register JDBC driver
	               Class.forName("com.mysql.jdbc.Driver");

	               //STEP 3: Open a connection
	               System.out.println("Connecting to a selected database...");
	               conn = DriverManager.getConnection(DB_URL,"root","root");
	               System.out.println("Connected database successfully...");
	               
	               //STEP 4: Execute a query
	               System.out.println("Inserting records into the table...");
	               stmt = conn.createStatement();
	               Statement stmt2 = conn.createStatement();
	               if(sc.isNewClicked()){
	       	       if (isInputValid()) {
	               
	               String sql = "INSERT INTO `sty`.`teachingstaff` (`id`, `firstName`,  `lastName`, "
	               				+ "`email`, `subject1`,`subject2`,`joiningDate`, `contact`,`department`)"+
	            		   		"VALUES ("+"'"+id+"'"+", "+"'"+firstName+"'"+","+ "'"+lastName
	            		   		+"'"+","+"'"+email+"', "+"'"+subjectName+"'"+", "+"'"+subjectName1+"'"+","+"'"+joiningDate+"'"+", "+"'"+contactNo+"'"+", "+"'"+department+"'"+""+")";
	               stmt.executeUpdate(sql);
	               stmt2.executeUpdate("INSERT INTO `sakila`.`paymentinfo` (`staffId`) VALUES ('"+id+"')");

	               System.out.println("Inserted records into the table...");
	               dialogStage.close();
	   	        	}
	   	        	else {
	   	        		dialogStage.showAndWait();
	       	    	
	       	       }
	               }
	               if(sc.isEditClicked()){
	               
	            	if(sc.isTSClicked()){
	   	        	if (isInputValid()) {
	               
	   	        		String sql = "UPDATE `sty`.`teachingstaff` SET `firstName`="+"'"+firstName+"'"+","+
	   	  	               " `lastName`="+"'"+lastName+"'"+","+" `email`="+"'"+email+"'"+","+
	   	  	               " `subject1`="+"'"+subjectName+"'"+","+" `subject2`="+"'"+subjectName1+"'"+","+" `joiningDate`="+"'"+joiningDate+"'"+","+" `contact`="+"'"+contactNo+"'"+","
	   	  	               		+" `department`="+"'"+department+"'"+"  WHERE `id`="+"'"+(index+1)+"'";
	   	  	               stmt.executeUpdate(sql);
	   	  	          System.out.println("Updated records into the table...");
	   	              
	   	  	          dialogStage.close();
	   	        	}
	   	        	else {
	   	        		dialogStage.showAndWait();
	   	        	}
	               }
	                }


	           }catch(SQLException se){
	               //Handle errors for JDBC
	               se.printStackTrace();
	            }catch(Exception e){
	               //Handle errors for Class.forName
	               e.printStackTrace();
	            }
	            System.out.println("Goodbye!");
	         //end main
	         //end JDBCExample                   
            
	            okClicked = true; 
	           
    }
    
    
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    
  
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (staffIdField.getText() == null || staffIdField.getText().length() == 0) {
            errorMessage += "No valid Student Id!\n"; 
        }
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No valid email!\n"; 
        }

        if (subjectField1.getText() == null || subjectField1.getText().length() == 0) {
            errorMessage += "No valid Subject!\n"; 
        }
        
        if (subjectField2.getText() == null || subjectField2.getText().length() == 0) {
            errorMessage += "No valid Subject!\n"; 
        }

        if (contactField.getText() == null || contactField.getText().length() == 0) {
            errorMessage += "Not a valid contact!\n"; 
        } else {
        	if (contactField.getText().length() != 10)
        		 errorMessage += "enter 10 digit contact number!\n"; 
        }
         
        if (joining.getValue() == null) {
            errorMessage += "No valid dates!\n";
        } 
        

        if (errorMessage.length() == 0) {
            return true;
            
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            
            return false;
 
        }
    }
    
}