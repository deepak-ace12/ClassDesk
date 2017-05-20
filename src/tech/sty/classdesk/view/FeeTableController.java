package tech.sty.classdesk.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
import tech.sty.classdesk.MainApp;
import tech.sty.classdesk.model.PersonDetails;

public class FeeTableController {
	@FXML
	TableView<PersonDetails> studentFee = new TableView<PersonDetails>();
	
    @FXML
    private TableColumn<PersonDetails, Number> studentIdColumn;
    @FXML
    private TableColumn<PersonDetails, String> studentNameColumn;
    @FXML
    private TableColumn<PersonDetails, Number> totalFeeColumn;
    @FXML
    private TableColumn<PersonDetails, Number> feePaidColumn;
    @FXML
    private TableColumn<PersonDetails, Number> nInstallmentColumn;
    @FXML
    private TableColumn<PersonDetails, Number> amtPerInstallmentColumn;
    @FXML
    private TableColumn<PersonDetails, String> dateOfAdmissionColumn;
    @FXML
    private TableColumn<PersonDetails, Number> gapPeriodColumn;
    @FXML
    private TableColumn<PersonDetails, Number> feePendingColumn;
    @FXML
    private ComboBox<String> selectClass = new ComboBox<String>();
    @FXML
    private ComboBox<String> selectBatch = new ComboBox<String>();
    
    MainApp mainApp = new MainApp();
    public ObservableList<PersonDetails> feeData = FXCollections.observableArrayList();
    ObservableList<String> options;
    public static ObservableList<String> options2 = FXCollections.observableArrayList();
    public static ObservableList<String> options3 = FXCollections.observableArrayList();
    String sql = null;
    Connection conn = null;
    Statement stmt = null;
    public static String std;
    public static String batch;
    
    
    @FXML
    private void classSelection()throws IOException {
     options = FXCollections.observableArrayList("FYJC","SYJC");
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
			
		}catch (Exception e) {
			System.out.println(e);
		}
        
       	selectBatch.setOnAction((event) -> {
    	    batch = selectBatch.getSelectionModel().getSelectedItem();
    	    if(batch !=null) {
    	    feeData.clear();
       	    load(batch);
    	    }
       	    });  
    }
    
    
    @FXML
    public void initialize(){
    }
    
    
    private void load(String batch){
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sakila","root","root");  
    		  
    		  
    		Statement stmt=con.createStatement();  
    		  
    		ResultSet rs=stmt.executeQuery("SELECT sakila."+batch+".*, sakila.feestructure"+batch+".* FROM sakila."+batch+" "
    				+ "left outer join sakila.feestructure"+batch+" on sakila."+batch+".id = sakila.feestructure"+batch+".id");  
    		  
    		while(rs.next()){  
    			int feePending = rs.getInt(14)- rs.getInt(15);
    			String fullName = rs.getString(2)+" "+rs.getString(4);
    			feeData.add(new PersonDetails(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
    					rs.getString(6),rs.getString(7),rs.getDate(8).toLocalDate(),rs.getString(9),rs.getString(10),rs.getDate(11).toLocalDate(),
    					rs.getString(12),rs.getInt(14),rs.getInt(15),rs.getInt(16),rs.getInt(17),rs.getInt(18),feePending,
    					rs.getInt(20),rs.getString(21),rs.getString(22),fullName));
    			
    			
    		}  
    		con.close();  
    		  
    		}catch(Exception e){ System.out.println(e);}  
    	
    	studentFee.setItems(feeData);
    	
    	studentIdColumn.setCellValueFactory(
    			cellData -> cellData.getValue().studentIdProperty());
    	studentNameColumn.setCellValueFactory(
    			cellData -> cellData.getValue().fullNameProperty());
    	totalFeeColumn.setCellValueFactory(
    			cellData -> cellData.getValue().feeTotalProperty());
    	feePaidColumn.setCellValueFactory(
    			cellData -> cellData.getValue().feePaidProperty());
    	nInstallmentColumn.setCellValueFactory(
    			cellData -> cellData.getValue().nInstallmentProperty());
    	amtPerInstallmentColumn.setCellValueFactory(
    			cellData -> cellData.getValue().amtOfInstallmentProperty());
    	//dateOfAdmissionColumn.setCellValueFactory(
    		//	cellData ->  new ReadOnlyStringWrapper(cellData.getValue().admissionProperty()));
    	gapPeriodColumn.setCellValueFactory(
    			cellData -> cellData.getValue().gapPeriodProperty());
    	feePendingColumn.setCellValueFactory(
    			cellData -> cellData.getValue().feePendingProperty());
    	
    	/**
    	 * **
    	 * to make the number cells editable NumberStringConverter is used
    	 */
        totalFeeColumn.setCellFactory(TextFieldTableCell.<PersonDetails, Number>forTableColumn(new NumberStringConverter()));
        //feePaidColumn.setCellFactory(TextFieldTableCell.<PersonDetails, Number>forTableColumn(new NumberStringConverter()));
        //feePendingColumn.setCellFactory(TextFieldTableCell.<PersonDetails, Number>forTableColumn(new NumberStringConverter()));
        nInstallmentColumn.setCellFactory(TextFieldTableCell.<PersonDetails, Number>forTableColumn(new NumberStringConverter()));
        amtPerInstallmentColumn.setCellFactory(TextFieldTableCell.<PersonDetails, Number>forTableColumn(new NumberStringConverter()));
        gapPeriodColumn.setCellFactory(TextFieldTableCell.<PersonDetails, Number>forTableColumn(new NumberStringConverter()));

        updateFeeRecords(batch);
    
    }

	private void updateFeeRecords(String batch) {
		try{

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/sakila","root","root");
            
            stmt = conn.createStatement();
           
            totalFeeColumn.setOnEditCommit(
            		 t -> {
            			 int selectedIndex = studentFee.getSelectionModel().getSelectedIndex();
         		        ((PersonDetails) t.getTableView().getItems().get(
         		        t.getTablePosition().getRow())
         		        ).setFeeTotal(t.getNewValue().intValue());
        	            sql = "UPDATE sakila.feestructure"+batch+" SET totalFee = "+t.getNewValue()+" where id = "+(selectedIndex+1);

        	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

        		    });
            		
            
            feePaidColumn.setOnEditCommit(
            		 t -> {
            			 int selectedIndex = studentFee.getSelectionModel().getSelectedIndex();
         		        ((PersonDetails) t.getTableView().getItems().get(
         		        t.getTablePosition().getRow())
         		        ).setFeePaid(t.getNewValue().intValue());
        	            sql = "UPDATE sakila.feestructure"+batch+" SET feePaid = "+t.getNewValue()+" where id = "+(selectedIndex+1);

        	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

        		    });
            
            nInstallmentColumn.setOnEditCommit(
           		 t -> {
           			 int selectedIndex = studentFee.getSelectionModel().getSelectedIndex();
        		        ((PersonDetails) t.getTableView().getItems().get(
        		        t.getTablePosition().getRow())
        		        ).setnInstallment(t.getNewValue().intValue());
       	            sql = "UPDATE sakila.feestructure"+batch+" SET noInstallment = "+t.getNewValue()+" where id = "+(selectedIndex+1);

       	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

       		    });
                       
            amtPerInstallmentColumn.setOnEditCommit(
           		 t -> {
           			 int selectedIndex = studentFee.getSelectionModel().getSelectedIndex();
        		        ((PersonDetails) t.getTableView().getItems().get(
        		        t.getTablePosition().getRow())
        		        ).setAmtOfInstallment(t.getNewValue().intValue());
       	            sql = "UPDATE sakila.feestructure"+batch+" SET amtPerInst = "+t.getNewValue()+" where id = "+(selectedIndex+1);

       	            try {
							stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

       		    });
            
            gapPeriodColumn.setOnEditCommit(
           		 t -> {
           			 int selectedIndex = studentFee.getSelectionModel().getSelectedIndex();
        		        ((PersonDetails) t.getTableView().getItems().get(
        		        t.getTablePosition().getRow())
        		        ).setGapPeriod(t.getNewValue().intValue());
       	            sql = "UPDATE sakila.feestructure"+batch+" SET instGap = "+t.getNewValue()+" where id = "+(selectedIndex+1);

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
	private void handleNewInstallment(){
		PersonDetails selectedPerson = studentFee.getSelectionModel().getSelectedItem();
		int indexNo = studentFee.getSelectionModel().getSelectedIndex();
		System.out.println(indexNo);
		new InstallmentEditController().setLineIndex(indexNo);
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showInstallmentEditDialog(selectedPerson);
			if (okClicked) {
				//showPersonDetails(selectedPerson);
				System.out.println("newIn");

			}

		}else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

    
}
