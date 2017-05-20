package tech.sty.classdesk.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import tech.sty.classdesk.model.BatchNames;
	

public class TimeTable {
	@FXML
	TableView<BatchNames> timeTable = new TableView<BatchNames>();
	
    ObservableList<BatchNames> tableData = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<BatchNames, String> batchNameColumn;
    @FXML
    private TableColumn<BatchNames, String> monTimeColumn;
    @FXML
    private TableColumn<BatchNames, String> monSubColumn;
    @FXML
    private TableColumn<BatchNames, String> tueTimeColumn;
    @FXML
    private TableColumn<BatchNames, String> tueSubColumn;
    @FXML
    private TableColumn<BatchNames, String> wedTimeColumn;
    @FXML
    private TableColumn<BatchNames, String> wedSubColumn;
    @FXML
    private TableColumn<BatchNames, String> thuTimeColumn;
    @FXML
    private TableColumn<BatchNames, String> thuSubColumn;
    @FXML
    private TableColumn<BatchNames, String> friTimeColumn;
    @FXML
    private TableColumn<BatchNames, String> friSubColumn;
    @FXML
    private TableColumn<BatchNames, String> satTimeColumn;
    @FXML
    private TableColumn<BatchNames, String> satSubColumn;
    @FXML
    private TableColumn<BatchNames, String> sunTimeColumn;
    @FXML
    private TableColumn<BatchNames, String> sunSubColumn;
    
	@FXML
	public void initialize(){
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			  
			Connection conn = DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/sakila","root","root"); 
			
			Statement stmt = conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select table_name from information_schema.tables where table_schema='sakila'");   
			while(rs.next()){  
				tableData.add(new BatchNames(rs.getString(1)));
			}  
			  
			}catch(Exception e){ 
				System.out.println(e);
			}  
		
		timeTable.setItems(tableData);
		batchNameColumn.setCellValueFactory(
    			cellData -> cellData.getValue().batchNameProperty());
    	monSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	tueSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	wedSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	thuSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	friSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	satSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	sunSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	monTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	tueTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	wedTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	thuTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	friTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	satTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	sunTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	


	}
	

	
}
