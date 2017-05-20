package tech.sty.classdesk.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.NumberStringConverter;
import tech.sty.classdesk.MainApp;
import tech.sty.classdesk.model.ExpensesDetails;

public class ExpensesController {
	@FXML
    public TableView<ExpensesDetails> expensesTable = new TableView<ExpensesDetails>();
	
    public ObservableList<ExpensesDetails> expensesData = FXCollections.observableArrayList();

	@FXML
    private TableColumn<ExpensesDetails, Number> srNoColumn;
    @FXML
    private TableColumn<ExpensesDetails, Number> stationaryColumn;
    @FXML
    private TableColumn<ExpensesDetails, String> monthColumn;
    @FXML
    private TableColumn<ExpensesDetails, Number> rentalColumn;
    @FXML
    private TableColumn<ExpensesDetails, Number> paymentColumn;
    @FXML
    private TableColumn<ExpensesDetails, Number> othersColumn;
    @FXML
    private TableColumn<ExpensesDetails, Number> totalColumn;
    
    
   private MainApp mainApp; 
   private static boolean isNewClicked;
   private static boolean isEditClicked;
    
    String sql = null;
    Connection conn = null;
    Statement stmt = null;
    
    @FXML
    private Button btnNew = new Button("Add");
    @FXML
    private Button btnDelete = new Button("Delete");
    
    
    public ObservableList<ExpensesDetails> getData() {
        return expensesData;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    public void load(){
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		conn = DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sty","root","root");  
    		  
    		//here test is database name, root is username and password  
    		  
    		stmt = conn.createStatement();  
    		  
    		ResultSet rs=stmt.executeQuery("SELECT * from sty.expenses");  
    		  
    		while(rs.next()){  
    			expensesData.add(new ExpensesDetails(rs.getInt(1),rs.getString(2), rs.getDouble(3),rs.getDouble(4),rs.getDouble(5),
    					rs.getDouble(6),rs.getDouble(7)));
    			
    			expensesTable.setItems(expensesData);
    		}  
    		  
    		}catch(Exception e){ System.out.println(e);}
    	
    	  btnNew.setOnKeyPressed(new EventHandler<KeyEvent>()
      	  {
      	     public void handle(KeyEvent evt)
      	     {
      	          if (evt.getCode() == KeyCode.ENTER)
  					{
      	        	  handleNew();
  					}
      	     }
      	});
      	
      	btnDelete.setOnKeyPressed(new EventHandler<KeyEvent>()
      	{
      	     public void handle(KeyEvent evt)
      	     {
      	    	 if (evt.getCode() == KeyCode.ENTER)
   					{
      	    		 handleDelete();
   					}
      	     }
      
      	});
      }
    
    
    @FXML
    private void initialize(){	 
    	        load();
    	    	expensesTable.setEditable(true);
    	    	srNoColumn.setCellValueFactory(
    	    			cellData -> cellData.getValue().srNoProperty());
    	    	monthColumn.setCellValueFactory(
    	    			cellData -> cellData.getValue().monthProperty());
    	    	stationaryColumn.setCellValueFactory(
    	    			cellData -> cellData.getValue().stationaryProperty());
    	    	rentalColumn.setCellValueFactory(
    	    			cellData -> cellData.getValue().rentalProperty());
    	    	paymentColumn.setCellValueFactory(
    	    			cellData -> cellData.getValue().paymentProperty());
    	    	othersColumn.setCellValueFactory(
    	    			cellData -> cellData.getValue().othersProperty());
    	    	totalColumn.setCellValueFactory(
    	    			cellData -> cellData.getValue().totalProperty());
    	    
    	        
    	        stationaryColumn.setCellFactory(TextFieldTableCell.<ExpensesDetails, Number>forTableColumn(new NumberStringConverter()));
    	        monthColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	        rentalColumn.setCellFactory(TextFieldTableCell.<ExpensesDetails, Number>forTableColumn(new NumberStringConverter()));
    	        paymentColumn.setCellFactory(TextFieldTableCell.<ExpensesDetails, Number>forTableColumn(new NumberStringConverter()));
    	        othersColumn.setCellFactory(TextFieldTableCell.<ExpensesDetails, Number>forTableColumn(new NumberStringConverter()));
    	        totalColumn.setCellFactory(TextFieldTableCell.<ExpensesDetails, Number>forTableColumn(new NumberStringConverter()));
    	        
    	        updateData();
    	     }
    
    
    private void updateData() {
		
		try{

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sty","root","root");
            
            stmt = conn.createStatement();
            
            monthColumn.setOnEditCommit(
           		 t -> {
           			 int selectedIndex = expensesTable.getSelectionModel().getSelectedIndex();
        		        ((ExpensesDetails) t.getTableView().getItems().get(
        		        t.getTablePosition().getRow())
        		        ).setMonth(t.getNewValue().toString());        		        	
        		             		       
       	            sql = "UPDATE `sty`.`expenses` SET `month`='"+t.getNewValue()+"' WHERE `srNo`='"+(selectedIndex+1)+"'";
                    
       	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}
       	            
       		    });
           
            stationaryColumn.setOnEditCommit(
            		 t -> {
            			 int selectedIndex = expensesTable.getSelectionModel().getSelectedIndex();
         		        ((ExpensesDetails) t.getTableView().getItems().get(
         		        t.getTablePosition().getRow())
         		        ).setStationary(t.getNewValue().doubleValue()); 
         		       
         		        	double stationary = stationaryColumn.getTableView().getItems().get(
         		                    t.getTablePosition().getRow()).getStationary();
         		        	double rental = stationaryColumn.getTableView().getItems().get(
                 		            t.getTablePosition().getRow()).getRental();
         		        	double payments = stationaryColumn.getTableView().getItems().get(
                 		            t.getTablePosition().getRow()).getPayments();
         		        	double others = stationaryColumn.getTableView().getItems().get(
                 		            t.getTablePosition().getRow()).getOthers();
         		        	double total = (stationary+rental+payments+others);  
         		        	
         		        	((ExpensesDetails) t.getTableView().getItems().get(
         	         		        t.getTablePosition().getRow())
         	         		        ).setTotal(total); 	
         		             		       
        	            sql = "UPDATE `sty`.`expenses` SET `stationary`='"+t.getNewValue()+"',`total`='"+total+"' WHERE `srNo`='"+(selectedIndex+1)+"'";
                     
        	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}
        	            
        		    });
            		
            
            rentalColumn.setOnEditCommit(
           		 t -> {
           			 int selectedIndex = expensesTable.getSelectionModel().getSelectedIndex();
        		        ((ExpensesDetails) t.getTableView().getItems().get(
        		        t.getTablePosition().getRow())
        		        ).setRental(t.getNewValue().doubleValue());
        		        
        		        double stationary = stationaryColumn.getTableView().getItems().get(
     		                    t.getTablePosition().getRow()).getStationary();
     		        	double rental = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getRental();
     		        	double payments = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getPayments();
     		        	double others = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getOthers();
     		        	double total = (stationary+rental+payments+others);  
     		        	
     		        	((ExpensesDetails) t.getTableView().getItems().get(
     	         		        t.getTablePosition().getRow())
     	         		        ).setTotal(total); 	
     		             		       
    	            sql = "UPDATE `sty`.`expenses` SET `stationary`='"+t.getNewValue()+"',`total`='"+total+"' WHERE `srNo`='"+(selectedIndex+1)+"'";

       	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

       		    });
           		
            
            paymentColumn.setOnEditCommit(
           		 t -> {
           			 int selectedIndex = expensesTable.getSelectionModel().getSelectedIndex();
        		        ((ExpensesDetails) t.getTableView().getItems().get(
        		        t.getTablePosition().getRow())
        		        ).setPayment(t.getNewValue().doubleValue());
        		        
        		        double stationary = stationaryColumn.getTableView().getItems().get(
     		                    t.getTablePosition().getRow()).getStationary();
     		        	double rental = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getRental();
     		        	double payments = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getPayments();
     		        	double others = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getOthers();
     		        	double total = (stationary+rental+payments+others);  
     		        	
     		        	((ExpensesDetails) t.getTableView().getItems().get(
     	         		        t.getTablePosition().getRow())
     	         		        ).setTotal(total); 	
     		             		       
    	            sql = "UPDATE `sty`.`expenses` SET `stationary`='"+t.getNewValue()+"',`total`='"+total+"' WHERE `srNo`='"+(selectedIndex+1)+"'";

       	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

       		    });
           		
            
            othersColumn.setOnEditCommit(
           		 t -> {
           			 int selectedIndex = expensesTable.getSelectionModel().getSelectedIndex();
        		        ((ExpensesDetails) t.getTableView().getItems().get(
        		        t.getTablePosition().getRow())
        		        ).setOthers(t.getNewValue().doubleValue());
        		        
        		        double stationary = stationaryColumn.getTableView().getItems().get(
     		                    t.getTablePosition().getRow()).getStationary();
     		        	double rental = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getRental();
     		        	double payments = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getPayments();
     		        	double others = stationaryColumn.getTableView().getItems().get(
             		            t.getTablePosition().getRow()).getOthers();
     		        	double total = (stationary+rental+payments+others);  
     		        	
     		        	((ExpensesDetails) t.getTableView().getItems().get(
     	         		        t.getTablePosition().getRow())
     	         		        ).setTotal(total); 	
     		             		       
    	            sql = "UPDATE `sty`.`expenses` SET `stationary`='"+t.getNewValue()+"',`total`='"+total+"' WHERE `srNo`='"+(selectedIndex+1)+"'";

       	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

       		    });
            
            
            
         }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
         }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
         }
	
}
    
    @FXML
    public void handleNew(){    	
        getNewBoolean();
        ExpensesDetails temp = new ExpensesDetails();
        boolean okClicked = mainApp.editExpenses(temp);
        if (okClicked) {
        	getData().add(temp);
        	
        }
    }
    
    
    private void getNewBoolean() {
    	isNewClicked = true;
    	isEditClicked = false;
		
	}
    
    @FXML
    private void handleEdit() {
    	getEditBoolean();
        ExpensesDetails selectedData = expensesTable.getSelectionModel().getSelectedItem();
        int indexNo = expensesTable.getSelectionModel().getSelectedIndex();
        System.out.println(indexNo);
        new NewExpenses().setLineIndex(indexNo);
        if (selectedData != null) {
            boolean okClicked = mainApp.editExpenses(selectedData);
            if (okClicked) {
            	initialize();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Data Selected");
            alert.setContentText("Please select a data in the table.");

            alert.showAndWait();
        }
        
    }
    
    private void getEditBoolean() {
		isEditClicked = true;
		isNewClicked = false;
	}

    
    public boolean isNewClicked(){
		System.out.println("New: "+isNewClicked);	
		return isNewClicked;
	}

    public boolean isEditClicked(){
		System.out.println("Edit: "+isEditClicked);
		
		return isEditClicked;
	}
    
    @FXML
    private void handleDelete() {
    	
    	Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.initOwner(mainApp.getPrimaryStage());
        alert1.setTitle("Confirm Delete");
        alert1.setHeaderText("Confirm Delete");
        alert1.setContentText("Are You really want to dalete the data");
       
        	int selectedIndex = expensesTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
            	 Optional<ButtonType> result = alert1.showAndWait();
                if (result.get() == ButtonType.OK){
         	   
                try{
         		   Class.forName("com.mysql.jdbc.Driver");

                    //STEP 3: Open a connection
                    System.out.println("Connecting to a selected database...");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sty","root","root");
                    System.out.println("Connected database successfully...");
                    
                    //STEP 4: Execute a query
                    System.out.println("Deleting records into the table...");
                    stmt = conn.createStatement();
                    
                    String sql = "DELETE FROM `sty`.`expenses` WHERE `srNo`="+"'"+(selectedIndex+1)+"'";
                    String sql1 = "Update sty.expenses Set srNo = srNo - 1 Where srNo > " + (selectedIndex + 1);
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql1);

                    System.out.println("Deleted records into the table...");
                    
                    expensesTable.getItems().remove(selectedIndex);
                    }catch(SQLException se){
                    //Handle errors for JDBC
                    se.printStackTrace();
         	   }catch(Exception e){
                    //Handle errors for Class.forName
                    e.printStackTrace();
                 }
            }
            
            else {
                alert1.close();
              }
            }
                            
           else {
               // Nothing selected.
                Alert alert2 = new Alert(AlertType.WARNING);
                alert2.initOwner(mainApp.getPrimaryStage());
                alert2.setTitle("No Selection");
                alert2.setHeaderText("No data Selected");
                alert2.setContentText("Please select a data in the table.");

                alert2.showAndWait();
            }
           
        } 
      
       
  }
    