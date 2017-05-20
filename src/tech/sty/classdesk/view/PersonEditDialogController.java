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
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.sty.classdesk.model.PersonDetails;

/**
 * Dialog to edit details of a person.
 * 
 * @author STY Technologies
 */
public class PersonEditDialogController {
	
	@FXML
    private TextField studentIdField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailIdField;
    @FXML
    private TextField branchField;
    @FXML
    private TextField collegeField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField guardianContactField;
    @FXML
    private TextArea addressField;
    @FXML
    private DatePicker admission;
    @FXML
    private DatePicker birthday;
    @FXML
    private Button btnOk = new Button("OK");
    @FXML
    private Button btnCancel = new Button("Cancel");
    @FXML
    Image img1 = new Image("file:E:/images/Photo.jpg");
    
    
    

    
    private Stage dialogStage;
    private PersonDetails person;
    private boolean okClicked = false;
    private static int index ;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/sakila";
    

    PersonOverviewController poc = new PersonOverviewController();
    
    
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
    	
    	
        birthday.setOnKeyPressed(new EventHandler<KeyEvent>()
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
        
        admission.setOnKeyPressed(new EventHandler<KeyEvent>()
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
        
         addressField.setOnKeyPressed(new EventHandler<KeyEvent>()
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
    
        public void setPerson(PersonDetails person) {
        this.person = person;
        studentIdField.setText(Integer.toString(person.getStudentId()));
        firstNameField.setText(person.getFirstName());
        middleNameField.setText(person.getMiddleName());
        lastNameField.setText(person.getLastName());
        emailIdField.setText(person.getEmailId());
        branchField.setText(person.getBranch());
        collegeField.setText(person.getCollege());
        admission.setValue(person.getAdmission());
        admission.setPromptText("dd/mm/yyyy");
        contactField.setText(person.getContact());
        guardianContactField.setText(person.getGuardianContact());
		birthday.setValue(person.getBirthday());
        birthday.setPromptText("dd/mm/yyyy");
        addressField.setText(person.getAddress());
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
    	
	            int id = Integer.parseInt(studentIdField.getText());
	            String firstName = firstNameField.getText().substring(0, 1).toUpperCase() + firstNameField.getText().substring(1).toLowerCase();
	            String middleName = middleNameField.getText().substring(0, 1).toUpperCase() + middleNameField.getText().substring(1).toLowerCase();
	            String lastName = lastNameField.getText().substring(0, 1).toUpperCase() + lastNameField.getText().substring(1).toLowerCase();
	            String emailId = emailIdField.getText();
	            String branchName = branchField.getText().substring(0, 1).toUpperCase() + branchField.getText().substring(1).toLowerCase();
	            String collegeName = collegeField.getText().substring(0, 1).toUpperCase() + collegeField.getText().substring(1).toLowerCase();
	            LocalDate admissionDate = admission.getValue();
	            String contactNo = contactField.getText();
	            String guardianContact = guardianContactField.getText();
	            LocalDate dateOfBirth = birthday.getValue();
	            String address = addressField.getText();
	           
	            
	            
	            //  Database credentials
	            
	            String data = (firstName + "," + middleName+ "," + lastName+ ","  + emailId+ "," + branchName + "," 
	            + collegeName +"," + admissionDate + "," + contactNo + "," + guardianContact+ "," +  "," + dateOfBirth+ "," + address);
	    	            System.out.println(data);
	    	            person.setStudentId(id);
	    	            person.setFirstName(firstName);
	    	            person.setMiddleName(middleName);
	    	            person.setLastName(lastName);
	    	            person.setEmailId(emailId);
	    	            person.setBranch(branchName);
	    	            person.setCollege(collegeName);
	    	            person.setAdmission(admissionDate);
	    	            person.setContact(contactNo);
	    	            person.setGuardianContact(guardianContact);
	    	            person.setBirthday(dateOfBirth);
	    	            person.setAddress(address);
	    	            person.setView(img1);
	    	           
	            Connection conn = null;
	            Statement stmt = null;
	           // Statement stmt2 = null;
	            Statement stmt3 = null;
	            //Statement stmt4 = null;

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
	             //  stmt2 = conn.createStatement();
	               stmt3 = conn.createStatement();
	               //stmt4 = conn.createStatement();


	               if(poc.isNewClicked()){
	       	        if (isInputValid()) {
	               
	               String sql = "INSERT INTO `sakila`."+PersonOverviewController.batch+" (`id`, `firstName`, `middleName`, `lastName`, `email`,"
	               		+ " `branchName`, `collegeName`, `joiningDate`, `contact`, `guardianContact`, `dob`, `address`)"
	               		+ " VALUES ('"+id+"', '"+firstName+"', '"+middleName+"', '"+lastName+"', '+"+emailId
	               		+"', '"+branchName+"', '"+collegeName+"', '"+admissionDate+"', '"+contactNo+
	               		"', '"+guardianContact+"', '"+dateOfBirth+"', '"+address+"')";

	               stmt.executeUpdate(sql);
	               //sab me
	               //stmt2.executeUpdate("INSERT INTO `sakila`.`test"+PersonOverviewController.batch+"` (`id`) VALUES ('"+id+"')");
	               stmt3.executeUpdate("INSERT INTO `sakila`.`feestructure"+PersonOverviewController.batch+"` (`id`) VALUES ('"+id+"')");
	               //stmt4.executeUpdate("INSERT INTO `sakila`.`temp` (`id`) VALUES ('"+id+"')");
	               System.out.println("Inserted records into the table...");
	               dialogStage.close();	               
	       	        }
	       	        
	               }
	               if(poc.isEditClicked()){
	   	        	if (isInputValid()) {
	               
	                String sql = "UPDATE `sakila`.`"+PersonOverviewController.batch+"` SET `firstName`='"+firstName+"', `middleName`='"+middleName+
	            		   "', `lastName`='"+lastName+"', `email`='"+emailId+"', `branchName`='"+branchName+
	            		   "', `collegeName`='"+collegeName+"', `joiningDate`='"+admissionDate+"', `contact`=' "+contactNo+
	            		   "', `guardianContact`='"+guardianContact+"',`dob`='"+dateOfBirth+"', `address`='"+address+"' "
	            		   		+ "WHERE `id`='"+id+"'";

	               stmt.executeUpdate(sql);
	               System.out.println("Updated records into the table...");
	               
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
	    dialogStage.close();
        
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
        if (studentIdField.getText() == null || studentIdField.getText().length() == 0) {
            errorMessage += "No valid Student Id!\n"; 
        }
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (middleNameField.getText() == null || middleNameField.getText().length() == 0) {
            errorMessage += "No valid middle name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (branchField.getText() == null || branchField.getText().length() == 0) {
            errorMessage += "No valid branch!\n"; 
        }

        if (collegeField.getText() == null || collegeField.getText().length() == 0) {
            errorMessage += "No valid college!\n"; 
        }
        
        if (admission.getValue() == null) {
            errorMessage += "No valid dates!\n";
        } 
        
        if (birthday.getValue() == null) {
            errorMessage += "No valid dates!\n";
        } 
        
        if (contactField.getText() == null || contactField.getText().length() == 0) {
            errorMessage += "Not a valid contact!\n"; 
        } else {
        	if (contactField.getText().length() != 10)
        		 errorMessage += "enter 10 digit contact number!\n"; 
        }
        
        if (guardianContactField.getText() == null || guardianContactField.getText().length() == 0) {
            errorMessage += "Not a valid contact!\n"; 
        } else {
        	if (guardianContactField.getText().length() != 10)
        		 errorMessage += "enter 10 digit contact number!\n"; 
        }
        
        if (emailIdField.getText() == null || emailIdField.getText().length() == 0) {
            errorMessage += "No valid Email!\n"; 
        }
        
        if (addressField.getText() == null || addressField.getText() == null) {
            errorMessage += "Enter Address!\n";
        } 
        

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            dialogStage.showAndWait();
            return false;
        }
    }
    
}
    
    
    
    
    
    
    
    
    
    
    
    
  