package tech.sty.classdesk.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddTestRecords {
	    @FXML
	    private ComboBox<String> selectClass = new ComboBox<String>(); 
	    @FXML
	    private ComboBox<String> selectBatch = new ComboBox<String>();
	    @FXML
	    private ComboBox<String> selectSubjects = new ComboBox<String>();
	    @FXML
	    private TextField testNameField;
	    @FXML
	    private DatePicker testDate;
	    @FXML
	    private TextField subjectsField;
	    @FXML
	    private TextField maxField;
	    @FXML
	    private Button btnOk = new Button("Ok");
	    @FXML
	    private Button btnCancel = new Button("Cancel");
	    @FXML
	    private Button btnAdd = new Button();
	    private Stage dialogStage;
	    private boolean okClicked = false;
	    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	    static final String DB_URL = "jdbc:mysql://localhost:3306/sty";
	    ArrayList<String> subjects = new ArrayList<String>();
	    private ObservableList<String> options;
		public static ObservableList<String> options2 = FXCollections.observableArrayList();
		public static ObservableList<String> options3 = FXCollections.observableArrayList();
		public static ObservableList<String> options4 = FXCollections.observableArrayList();
		public static String std;
		public static String batch;
		public static String testName;
		TestRecordController tc = new TestRecordController();
	    
	    
	    @FXML
		public void initialize(){
         subjects.clear();			
		}
	    
	    
	public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	        btnOk.setOnKeyPressed(new EventHandler<KeyEvent>()
	    	{
	    	     public void handle(KeyEvent evt)
	    	     {
	    	          if (evt.getCode() == KeyCode.ENTER)
						try {
							handleOk();
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
	     
   public boolean isOkClicked() {
        return okClicked;             
   }
       

	@FXML
	private void classSelection() throws IOException {
		options = FXCollections.observableArrayList("FYJC", "SYJC");
		selectClass.setItems(options);
		selectClass.setOnAction((event) -> {
			std = selectClass.getSelectionModel().getSelectedItem();
		});

	}

	@FXML
	private void batchSelection() throws IOException {
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");
			Statement stmt = conn.createStatement();

			if (std == "FYJC") {
				ResultSet rs = stmt.executeQuery("SELECT batch FROM sakila.classes2 where class = 'FYJC'");
				while (rs.next()) {
					arrayList.add(rs.getString(1));
				}

				options2 = FXCollections.observableArrayList(arrayList);
				arrayList.clear();
				selectBatch.setItems(options2);
			}

			if (std == "SYJC") {
				ResultSet rs = stmt.executeQuery("SELECT batch FROM sakila.classes2 where class = 'SYJC'");
				while (rs.next()) {
					arrayList.add(rs.getString(1));
				}

				options3 = FXCollections.observableArrayList(arrayList);
				arrayList.clear();
				selectBatch.setItems(options3);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		selectBatch.setOnAction((event) -> {
			batch = selectBatch.getSelectionModel().getSelectedItem();
		});
	}
	
	@FXML
    private void handleOk() throws IOException {
    	        
	            testName = testNameField.getText();
	            LocalDate date = testDate.getValue();
	            String max = maxField.getText();	            
	       
	            Connection conn = null;
	            Statement stmt = null;
	            if (isInputValid()) {
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
	       	    	
	               String sql = "INSERT INTO `sakila`.`test` (`sr`, `class`, `batch`, `testName`, `date`, `maxMarks`,`subject1`, `subject2`, `subject3`, `subject4`)"
		       				+ "VALUES ('"+getRowCount()+"', '"+std+"', '"+batch+"','"+testName+"','"+date+"','"+Integer.parseInt(max)+"','"+subjects.get(0)+"','"+subjects.get(1)+"','"+subjects.get(2)+"','"+subjects.get(3)+"')";
	               
	               String sql1 = "CREATE TABLE `sakila`."+testName+" ( `id` INT NOT NULL,`obtainedMarks1` INT NULL,`obtainedMarks2` INT NULL,"
		              		+ " `obtainedMarks3` INT NULL, `obtainedMarks4` INT NULL, PRIMARY KEY (`id`))";
	               
	               stmt.executeUpdate(sql);
	               System.out.println("table is created...");
	               stmt.executeUpdate(sql1);	               
	               System.out.println("Inserted records into the table...");
	              
	       	        
	       	    	               
	            }catch(SQLException se){
	               //Handle errors for JDBC
	               se.printStackTrace();
	            }catch(Exception e){
	               //Handle errors for Class.forName
	               e.printStackTrace();
	            }
	            dialogStage.close();
	            }
	            else{
	            	dialogStage.showAndWait();
	            }
	           
	         //end main
	         //end JDBCExample                   
            
        
	    okClicked = true;
    }
    
    
    @FXML
	private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
	private void handleAdd() throws IOException {
    	subjects.add(subjectsField.getText());
    	if(subjects.size()<4){
		subjectsField.clear();
		subjectsField.setPromptText(4-subjects.size()+ " Subjects Now");
    	}
    	else{
    		btnAdd.setDisable(true);
    		subjectsField.setDisable(true);
    		btnAdd.setOpacity(0);
    		subjectsField.setOpacity(0);
    		selectSubjects.setDisable(false);
    		options4 = FXCollections.observableArrayList(subjects);
    		selectSubjects.setItems(options4);
    	}
    	}
    
    
    private int getRowCount() {
		int count = 0;
		try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sakila","root","root");  
    		  
    		//here test is database name, root is username and password  
    		  
    		Statement stmt=con.createStatement();  
    		  
    		ResultSet rs =stmt.executeQuery("SELECT Count(*) FROM test");  
    		while(rs.next())
    		  count = rs.getInt(1);   		    		 
    		con.close();  
    		  
    		}catch(Exception e){ System.out.println(e);}  
    		  
         
		return ++count;
		
	}
    
    private boolean isInputValid() {
        String errorMessage = "";
        if (subjectsField.getText() == null || subjectsField.getText().length() == 0) {
            errorMessage += "Add Required Subjects!\n"; 
        }
        if (testNameField.getText() == null || testNameField.getText().length() == 0) {
            errorMessage += "Enter Test Name!\n"; 
        }
        if (testDate.getValue() == null) {
            errorMessage += "Select Test Date!\n"; 
        }
        if (maxField.getText() == null || maxField.getText().length() == 0) {
            errorMessage += "Enter Maximum Marks Field!\n"; 
        }
      
        if (selectClass.getSelectionModel().getSelectedItem() == null || selectClass.getValue() == null) {
            errorMessage += "Select The Class!\n"; 
        }
        
        if (selectBatch.getSelectionModel().getSelectedItem() == null || selectBatch.getValue() == null) {
            errorMessage += "Select The Batch!\n"; 
        }
        
        if (selectSubjects.getItems() == null) {
            errorMessage += "Add Subjects!\n"; 
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
