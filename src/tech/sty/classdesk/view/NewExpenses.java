package tech.sty.classdesk.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.sty.classdesk.model.ExpensesDetails;

public class NewExpenses {
	 
	   @FXML
	    private TextField srNoField;
	    @FXML
	    private TextField stationaryField;
	    @FXML
	    private ComboBox<String> selectMonth = new ComboBox<String>();
	    @FXML
	    private TextField rentalField;
	    @FXML
	    private TextField paymentField;
	    @FXML
	    private TextField othersField;
	    @FXML
	    private Button btnOk = new Button("OK");
	    @FXML
	    private Button btnCancel = new Button("Cancel");
	    private Stage dialogStage;
	    private ExpensesDetails expenses;
	    private boolean okClicked = false;
	    private static int index ;
	    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	    static final String DB_URL = "jdbc:mysql://localhost:3306/sty";
	    public static double total;
	    public static ObservableList<String> calender;
	    public static String month;
	    
	    ExpensesController ec = new ExpensesController();
	    public void setLineIndex(int indexNo){
	    	index = indexNo;
	    	
	    }
	    public int getLineIndex(){
	    	return index;
	    }
	    
	    @FXML
		public void initialize(){
			
		}
	    
	    
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
	        
	        selectMonth.setOnKeyPressed(new EventHandler<KeyEvent>()
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
	        
	        selectMonth.setOnKeyPressed(new EventHandler<KeyEvent>()
	    	{
	    	     public void handle(KeyEvent evt)
	    	     {
	    	          if (evt.getCode() == KeyCode.DOWN)
						selectMonth.show();
	    	        	  try {
							monthSelection();
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
	    	
	    }
	 
	 public void setExpenses(ExpensesDetails expense) {
	        this.expenses = expense;
	        srNoField.setText(Integer.toString(expenses.getSrNo()));
	        stationaryField.setText(Double.toString(expenses.getStationary()));
	        rentalField.setText(Double.toString(expenses.getRental()));
	        paymentField.setText(Double.toString(expenses.getPayments()));
	        othersField.setText(Double.toString(expenses.getOthers()));      
	    }
	 
	 

     
    public boolean isOkClicked() {
         return okClicked;             
    }
        
    @FXML
    private void monthSelection()throws IOException {
     calender = FXCollections.observableArrayList(DateFormatSymbols.getInstance(Locale.ENGLISH).getShortMonths());
     selectMonth.setItems(calender);
     selectMonth.setOnAction((event) -> {
    	    month = selectMonth.getSelectionModel().getSelectedItem();
    	});
       
     }
	
    @FXML
    private void handleOk() throws IOException {
    	
	            int srNo = Integer.parseInt(srNoField.getText());
	            double stationary = Double.parseDouble(stationaryField.getText());
	            double rental = Double.parseDouble(rentalField.getText());
	            double payments = Double.parseDouble(paymentField.getText());
	            double others = Double.parseDouble(othersField.getText());
      	        total = (stationary+rental+payments+others); 
	            
	            //  Database credentials
	            
	            String data = (srNo + "," + month+ "," + stationary+ "," + rental+ "," + payments +"," + others);
	    	            System.out.println(data);
	    	            expenses.setSrNo(srNo);
	    	            expenses.setMonth(month);
	    	            expenses.setStationary(stationary);
	    	            expenses.setRental(rental);
	    	            expenses.setPayment(payments);
	    	            expenses.setOthers(others);
	    	            expenses.setTotal(total);
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
	               
	               String sql = "INSERT INTO `sty`.`expenses` (`srNo`, `month`, `stationary`,  `rental`, "
	               				+ "`payments`, `others`,`total`)"+
	            		   		"VALUES ("+"'"+srNo+"'"+", "+"'"+month+"'"+","+"'"+stationary+"'"+","+ "'"+rental
	            		   		+"'"+","+"'"+payments+"',"+"'"+others+"', "+"'"+total+"'"+")";
	               stmt.executeUpdate(sql);

	               System.out.println("Inserted records into the table...");
	               dialogStage.close();
	             	               
	       	        }
	       	    else{
   	        		dialogStage.showAndWait();
   	        	}
	               }
	               
	               if(ec.isEditClicked()){
	            	  srNoField.setEditable(false);
	   	        	if (isInputValid()) {
	               
	               String sql = "UPDATE `sty`.`expenses` SET `month`='"+month+"', `stationary`='"+stationary+"', `rental`='"+rental+
	            		   "', `payments`='"+payments+"', `others`='"+others+"', `total`='"+total+"' "            		  
	            		   		+ "WHERE `srNo`='"+srNo+"'";

	               stmt.executeUpdate(sql);
	               System.out.println("updated records into the table...");
	               dialogStage.close();
	   	        	}
	   	        	else{
	   	        		dialogStage.showAndWait();
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
            errorMessage += "No valid Serial Number!\n"; 
        }
        if (month == null || month.length() == 0) {
            errorMessage += "Select Month!\n"; 
        }
        if (stationaryField.getText() == null || stationaryField.getText().length() == 0) {
            errorMessage += "No valid Stationary expense amount!\n"; 
        }
        if (rentalField.getText() == null || rentalField.getText().length() == 0) {
            errorMessage += "No valid rental expense amount!\n"; 
        }

        if (paymentField.getText() == null || paymentField.getText().length() == 0) {
            errorMessage += "Not a valid payment!\n"; 
        }
        
        if (othersField.getText() == null || othersField.getText().length() == 0) {
            errorMessage += "Enter others expenses amount!\n"; 
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
