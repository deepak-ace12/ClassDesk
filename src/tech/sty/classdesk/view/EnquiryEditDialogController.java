package tech.sty.classdesk.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.sty.classdesk.model.EnquiryDetails;

public class EnquiryEditDialogController {
	@FXML
    private TextField srNoField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField purposeField;
    @FXML
    private TextField emailIdField;
    @FXML
    private TextField contactField;
    @FXML
    private DatePicker enquiryDateField;
    @FXML
    private DatePicker revertDateField;
    

    
    private Stage dialogStage;
    private boolean okClicked = false;
    private static int index ;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/sakila";
    private EnquiryDetails enquiry;
    EnquiryController ec = new EnquiryController();
    
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
    }

    /**
     * Sets the Enquiry to be edited in the dialog.
     * 
     * @param Enquiry
     */
    
        public void setEnquiry(EnquiryDetails enquiry) {
        	this.enquiry = enquiry;
        srNoField.setText(Integer.toString(enquiry.getSrNo()));
        fullNameField.setText(enquiry.getFullName());
        emailIdField.setText(enquiry.getEmail());
        purposeField.setText(enquiry.getPurpose());
        enquiryDateField.setValue(enquiry.getEnquiryDate2());
        enquiryDateField.setPromptText("dd/mm/yyyy");
        contactField.setText(enquiry.getContact());
		revertDateField.setValue(enquiry.getRevertDate2());
        revertDateField.setPromptText("dd/mm/yyyy");
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
    	
	            int srNo = Integer.parseInt(srNoField.getText());
	            String fullName = fullNameField.getText();
	            String emailId = emailIdField.getText();
	            String purpose = purposeField.getText();
	            String enquiryDate = enquiryDateField.getValue().toString();
	            LocalDate enquiryDate2 = enquiryDateField.getValue();
	            String contactNo = contactField.getText();
	            String revertDate = revertDateField.getValue().toString();
	            LocalDate revertDate2 = revertDateField.getValue();

	            
	            
	            //  Database credentials
	            
	          
	    	            enquiry.setSrNo(srNo);;
	    	            enquiry.setFullName(fullName);
	    	            enquiry.setEmail(emailId);
	    	            enquiry.setPurpose(purpose);
	    	            enquiry.setEnquiryDate(enquiryDate);
	    	            enquiry.setContact(contactNo);
	    	            enquiry.setRevertDate(revertDate);
	    	            enquiry.setEnquiryDate2(enquiryDate2);
	    	            enquiry.setRevertDate2(revertDate2);
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
	               if(ec.isNewClicked()){
	       	        if (isInputValid()) {
	               
	               String sql = "INSERT INTO `sakila`.`enquiry` (`sr`, `name`, `date`, `purpose`, `email`, `contact`, `revertDate`) "
	               		+ "VALUES ('"+srNo+"', '"+fullName+"', '"+enquiryDate+"', '"+purpose+"', '"+emailId+
	               		"', '"+contactNo+"', '"+revertDate+"')";

	               stmt.executeUpdate(sql);

	               System.out.println("Inserted records into the table...");
	               
	               
	               
	       	        }
	               }
	               if(ec.isEditClicked()){
	            	   contactField.setEditable(false);
	   	        	if (isInputValid()) {
	               
	               String sql = "UPDATE `sakila`.`enquiry` SET `sr`='"+srNo+"', `name`='"+fullName+"', `date`='"+enquiryDate+"', "
	               		+ "`purpose`='"+purpose+"',"
	               		+ " `email`='"+emailId+"', `contact`='"+contactNo+"', `revertDate`='"+revertDate+"' WHERE `sr`='"+srNo+"'";

	               stmt.executeUpdate(sql);
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
        if (srNoField.getText() == null || srNoField.getText().length() == 0) {
            errorMessage += "No valid Student Id!\n"; 
        }
        if (fullNameField.getText() == null || fullNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }

        if (purposeField.getText() == null || purposeField.getText().length() == 0) {
            errorMessage += "No valid college!\n"; 
        }
        
        if (enquiryDateField.getValue() == null || revertDateField.getValue() == null) {
            errorMessage += "No valid dates!\n";
        } 
        
        if (contactField.getText() == null || contactField.getText().length() == 0) {
            errorMessage += "Not a valid contact!\n"; 
        } else {
        	if (contactField.getText().length() != 10)
        		 errorMessage += "enter 10 digit contact number!\n"; 
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

            return false;
        }
    }
    

}
