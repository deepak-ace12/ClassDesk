package tech.sty.classdesk.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddBatchController {
	@FXML
	private ComboBox<String> chooseStd;
	@FXML
	public TextField chooseBatch;
	@FXML
	private Button addButton;

	ObservableList<String> options;

	private Stage dialogStage1;
	private String std;
	public static String batchName;
	private boolean okClicked = false;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage1 = dialogStage;
	}

	@FXML
	public void setStd() {
		options = FXCollections.observableArrayList("FYJC", "SYJC");
		chooseStd.setItems(options);
		chooseStd.setOnAction((event) -> {
			std = chooseStd.getSelectionModel().getSelectedItem();
		});
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleAdd() throws IOException {
		batchName = chooseBatch.getText();
		if (std == "FYJC") {
			PersonOverviewController.options2.addAll(batchName);
		}

		if (std == "SYJC") {
			PersonOverviewController.options2.addAll(batchName);
		}
		createDBForNewBatch(std, batchName);
		okClicked = true;
		dialogStage1.close();
	}	

		
	public void createDBForNewBatch(String std, String batchName) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		if (isInputValid()) {		
		try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		Connection con = DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sakila","root","root");  
    		  
    		//here test is database name, root is username and password  
    		  
    		Statement stmt2 = con.createStatement();  
    		  
    		stmt2.executeUpdate("INSERT INTO `sakila`.`classes2` (`sr`, `class`, `batch`) "
    				+ "VALUES ('"+getRowCount()+"', '"+std+"', '"+batchName+"')");   
    		con.close();  
    		  
    		}catch(Exception e){ System.out.println(e);}  
    		  
		
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, "root", "root");
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating the tables...");
			stmt = conn.createStatement();

			if (batchName != null) {
				String sql = "CREATE TABLE `sakila`.`" + batchName
						+ "` ( `id` INT NOT NULL,`firstName` VARCHAR(45) NOT NULL,`middleName` VARCHAR(45) NOT NULL,"
						+ " `lastName` VARCHAR(45) NOT NULL, `email` VARCHAR(45) NOT NULL, `branchName` VARCHAR(45) NOT NULL, `collegeName` VARCHAR(45) NOT NULL,"
						+ "`joiningDate` DATE NOT NULL,`contact` VARCHAR(45) NOT NULL, `guardianContact` VARCHAR(45) NOT NULL,"
						+ " `dob` DATE NOT NULL, `address` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`))";
                 
				 String sql1 = "CREATE TABLE `sakila`.`feestructure"+batchName+"` ( `id` INT NOT NULL,`totalFee` INT NULL,`feePaid` INT NULL,"
		              		+ " `noInstallment` INT NULL, `amtPerInst` INT NULL, `instGap` INT NULL, `feePending` VARCHAR(45) NULL,"
		              		+ "`lastInstallment` INT NULL,`lastPaidOn` VARCHAR(45) NULL, `nextDueOn` VARCHAR(45) NULL, PRIMARY KEY (`id`))";
				stmt.executeUpdate(sql);
				stmt.executeUpdate(sql1);

				System.out.println("tables are created...");

			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		dialogStage1.close();
		}
		else{
			dialogStage1.showAndWait();
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
    		  
    		ResultSet rs =stmt.executeQuery("SELECT Count(*) FROM classes2");  
    		while(rs.next())
    		  count = rs.getInt(1);   		    		 
    		con.close();  
    		  
    		}catch(Exception e){ System.out.println(e);}  
    		  
         
		return ++count;
		
	}
	
	private boolean isInputValid() {
        String errorMessage = "";
       
        if (chooseBatch.getText() == null || chooseBatch.getText().length() == 0) {
            errorMessage += "Enter Batch Name!\n"; 
        }
      
        if (chooseStd.getSelectionModel().getSelectedItem() == null || chooseStd.getValue() == null) {
            errorMessage += "Select The Standard!\n"; 
        }
                
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage1);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

}
