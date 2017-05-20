package tech.sty.classdesk.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
import tech.sty.classdesk.MainApp;
import tech.sty.classdesk.model.StudentScoreDetails;

public class TestRecordController {
	@FXML
	    private ComboBox<String> selectClass = new ComboBox<String>(); 
	@FXML
	    private ComboBox<String> selectBatch = new ComboBox<String>();
	@FXML
        private ComboBox<String> selectTest = new ComboBox<String>();
	@FXML
	private TableView<StudentScoreDetails> personScores = new TableView<StudentScoreDetails>();
	@FXML
    private TableColumn<StudentScoreDetails, Number> sr;
    @FXML
    private TableColumn<StudentScoreDetails, Number> studentIdColumn;
    @FXML
    private TableColumn<StudentScoreDetails, String> studentNameColumn;
    @FXML
    private TableColumn<StudentScoreDetails,Number> s1Column;
    @FXML
    private TableColumn<StudentScoreDetails,Number> s2Column;
    @FXML
    private TableColumn<StudentScoreDetails,Number> s3Column;
    @FXML
    private TableColumn<StudentScoreDetails,Number> s4Column;
    @FXML
    private TableColumn<StudentScoreDetails, Number> obtainedMarksColumn1;
    @FXML
    private TableColumn<StudentScoreDetails, Number> maximumMarksColumn1;
    @FXML
    private TableColumn<StudentScoreDetails, Number> obtainedMarksColumn2;
    @FXML
    private TableColumn<StudentScoreDetails, Number> maximumMarksColumn2;
    @FXML
    private TableColumn<StudentScoreDetails, Number> obtainedMarksColumn3;
    @FXML
    private TableColumn<StudentScoreDetails, Number> maximumMarksColumn3;
    @FXML
    private TableColumn<StudentScoreDetails, Number> obtainedMarksColumn4;
    @FXML
    private TableColumn<StudentScoreDetails, Number> maximumMarksColumn4;
    @FXML
    private TableColumn<StudentScoreDetails, Number> averagePercentageColumn;
    @FXML
    private Button btnNew = new Button();
    
    public ObservableList<StudentScoreDetails> scoreData = FXCollections.observableArrayList();
    ObservableList<String> options;
	public static ObservableList<String> options2 = FXCollections.observableArrayList();
	public static ObservableList<String> options3 = FXCollections.observableArrayList();
	public static ObservableList<String> options4 = FXCollections.observableArrayList();
	public static String std;
	public static String batch;
	public static String test;
	public static int max;
	public static String s1,s2,s3,s4;
    String sql = null;
    Connection conn = null;
    Statement stmt = null;
    Statement stmt1 = null;
    private MainApp mainApp = new MainApp();
    
    
    
    public TestRecordController(){
    	
    }
    
    @FXML
    private void initialize(){
    	
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
	private void testSelection() throws IOException {
		ArrayList<String> arrayList1 = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");
			Statement stmt = conn.createStatement();			
				ResultSet rs = stmt.executeQuery("SELECT testName FROM sakila.test where batch = '"+batch+"' ");
				while (rs.next()) {
					arrayList1.add(rs.getString(1));
				}
				options4 = FXCollections.observableArrayList(arrayList1);
				arrayList1.clear();
				selectTest.setItems(options4);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		selectTest.setOnAction((event) -> {			
			test = selectTest.getSelectionModel().getSelectedItem();
			scoreData.clear();
			if(test!=null){
			load(batch,test);
			}
		});
		
	}
	
    public void load(String batch, String test){
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		conn = DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sakila","root","root"); 
    		
    		stmt1 = conn.createStatement();
    		ResultSet rs1 =stmt1.executeQuery("SELECT maxMarks,Subject1,Subject2,Subject3,Subject4 FROM sakila.test where testName = '"+test+"'");
    		while(rs1.next()){
             max = rs1.getInt(1);
             s1 = rs1.getString(2);
             s2 = rs1.getString(3);
             s3 = rs1.getString(4);
             s4 = rs1.getString(5);
    		}
    		stmt = conn.createStatement();      		  
    		ResultSet rs2=stmt.executeQuery("SELECT sakila." + batch + ".id, sakila." + batch + ".firstName, sakila." + batch + ".lastName, "
     				+ "sakila."+test+".obtainedMarks1, sakila."+test+".obtainedMarks2,"
     				+ "sakila."+test+".obtainedMarks3,sakila."+test+".obtainedMarks4 "
     				+ "FROM sakila." + batch + " left outer join sakila."+test+" on sakila." + batch + ".id = sakila."+test+".id");   
    		int count = 1;
    		double average;
    		 
    		while(rs2.next()){  
    			average = ((rs2.getInt(4)+rs2.getInt(5)+rs2.getInt(6)+rs2.getInt(7))/(max*4.0))*100;
    			NumberFormat nf = NumberFormat.getInstance();
    			nf.setMaximumFractionDigits(2);
    			average = Double.parseDouble(nf.format(average));
    			String fullName = rs2.getString(2)+" "+rs2.getString(3);
    			scoreData.add(new StudentScoreDetails(fullName,count,rs2.getInt(1),
    					rs2.getInt(4),max,rs2.getInt(5),max,rs2.getInt(6),max,rs2.getInt(7),max,average));
    			count++;   			
    		}  
    		
    		  
    		}catch(Exception e){ 
    			System.out.println(e);
    		}  
    	
    	
    	s1Column.setText(s1);
    	s2Column.setText(s2);
    	s3Column.setText(s3);
    	s4Column.setText(s4);
    	personScores.setItems(scoreData);
    	personScores.setEditable(true);
    	sr.setCellValueFactory(
    			cellData -> cellData.getValue().srProperty());
    	studentIdColumn.setCellValueFactory(
    			cellData -> cellData.getValue().studentIdProperty());
    	studentNameColumn.setCellValueFactory(
    			cellData -> cellData.getValue().studentNameProperty());
    	obtainedMarksColumn1.setCellValueFactory(
    			cellData -> cellData.getValue().obtainedMarks1Property());
    	maximumMarksColumn1.setCellValueFactory(
    			cellData -> cellData.getValue().maximumMarks1Property());
    	obtainedMarksColumn2.setCellValueFactory(
    			cellData -> cellData.getValue().obtainedMarks2Property());
    	maximumMarksColumn2.setCellValueFactory(
    			cellData -> cellData.getValue().maximumMarks2Property());
    	obtainedMarksColumn3.setCellValueFactory(
    			cellData -> cellData.getValue().obtainedMarks3Property());
    	maximumMarksColumn3.setCellValueFactory(
    			cellData -> cellData.getValue().maximumMarks3Property());
    	obtainedMarksColumn4.setCellValueFactory(
    			cellData -> cellData.getValue().obtainedMarks4Property());
    	maximumMarksColumn4.setCellValueFactory(
    			cellData -> cellData.getValue().maximumMarks4Property());
    	averagePercentageColumn.setCellValueFactory(
    			cellData -> cellData.getValue().averagePercenageProperty());
    	
    	/**
    	 * **
    	 * to make the number cells editable NumberStringConverter is used
    	 */
        
    	obtainedMarksColumn1.setCellFactory(TextFieldTableCell.<StudentScoreDetails, Number>forTableColumn(new NumberStringConverter()));
    	obtainedMarksColumn2.setCellFactory(TextFieldTableCell.<StudentScoreDetails, Number>forTableColumn(new NumberStringConverter()));
        obtainedMarksColumn3.setCellFactory(TextFieldTableCell.<StudentScoreDetails, Number>forTableColumn(new NumberStringConverter()));
        obtainedMarksColumn4.setCellFactory(TextFieldTableCell.<StudentScoreDetails, Number>forTableColumn(new NumberStringConverter()));
        

    	setScoresInTableCells(test);
    	
    	}

	
		public void setScoresInTableCells(String test) {	
			/*personScores.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			    @Override
			    public void handle(KeyEvent event) {
			        if (event.getCode() == KeyCode.TAB && event.isShiftDown()) {
			            event.consume();
			            personScores.requestFocus();
			            personScores.getSelectionModel().select(0);
			            personScores.focusModelProperty().get().focusRightCell(); 		 
			            ;
			        }
			    }
			});*/
			
			try{
	            Class.forName("com.mysql.jdbc.Driver");

	            conn = DriverManager.getConnection("jdbc:mysql://localhost/sakila","root","root");
	            
	            stmt = conn.createStatement();
	           
	            obtainedMarksColumn1.setOnEditCommit(
	            		 t -> {
	            			 t.getTableView().edit(t.getTablePosition().getRow(),t.getTablePosition().getTableColumn());
	            			 int selectedIndex = personScores.getSelectionModel().getSelectedIndex();
	         		        ((StudentScoreDetails) t.getTableView().getItems().get(
	         		        t.getTablePosition().getRow())
	         		         ).setObtainedMarks1(t.getNewValue().intValue());
	         		        
	         		        double s1 = obtainedMarksColumn1.getTableView().getItems().get(
	     		                    t.getTablePosition().getRow()).getObtainedMarks1();
	     		        	double s2 = obtainedMarksColumn2.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks2();
	     		        	double s3 = obtainedMarksColumn3.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks3();
	     		        	double s4 = obtainedMarksColumn4.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks4();
	     		        	double total = ((s1+s2+s3+s4)/(4.0*max))*100.0;  
	     		        	
	     		        	((StudentScoreDetails) t.getTableView().getItems().get(
	     	         		        t.getTablePosition().getRow())
	     	         		        ).setAveragePercentage(total);
	     		        	System.out.println(test);
	         		       
	        	            sql = "UPDATE `sakila`."+test+" SET `obtainedMarks1`='"+t.getNewValue()+"' WHERE `id`='"+(selectedIndex+1)+"'";

	        	            try {
								stmt.executeUpdate(sql);
							} catch (Exception e) {
								e.printStackTrace();
							}

	        		    });
	            		
	            
	            obtainedMarksColumn2.setOnEditCommit(
	            		 t -> {
	            			 int selectedIndex = personScores.getSelectionModel().getSelectedIndex();
	         		        ((StudentScoreDetails) t.getTableView().getItems().get(
	         		        t.getTablePosition().getRow())
	         		         ).setObtainedMarks2(t.getNewValue().intValue());
	         		        
	         		       double s1 = obtainedMarksColumn1.getTableView().getItems().get(
	     		                    t.getTablePosition().getRow()).getObtainedMarks1();
	     		        	double s2 = obtainedMarksColumn2.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks2();
	     		        	double s3 = obtainedMarksColumn3.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks3();
	     		        	double s4 = obtainedMarksColumn4.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks4();
	     		        	double total = ((s1+s2+s3+s4)/(4.0*max))*100.0;  
	     		        	
	     		        	((StudentScoreDetails) t.getTableView().getItems().get(
	     	         		        t.getTablePosition().getRow())
	     	         		        ).setAveragePercentage(total);     
	         		      
	         		        t.getTableView().edit(t.getTablePosition().getRow(),obtainedMarksColumn3);
	        	            sql = "UPDATE `sakila`."+test+" SET `obtainedMarks2`='"+t.getNewValue()+"' WHERE `id`='"+(selectedIndex+1)+"'";

	        	            try {
								stmt.executeUpdate(sql);
							} catch (Exception e) {
								e.printStackTrace();
							}

	        		    });
	            
	            obtainedMarksColumn3.setOnEditCommit(
	            		 t -> {
	            			 int selectedIndex = personScores.getSelectionModel().getSelectedIndex();
	         		        ((StudentScoreDetails) t.getTableView().getItems().get(
	         		        t.getTablePosition().getRow())
	         		        ).setObtainedMarks3(t.getNewValue().intValue());
	         		        
	         		       double s1 = obtainedMarksColumn1.getTableView().getItems().get(
	     		                    t.getTablePosition().getRow()).getObtainedMarks1();
	     		        	double s2 = obtainedMarksColumn2.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks2();
	     		        	double s3 = obtainedMarksColumn3.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks3();
	     		        	double s4 = obtainedMarksColumn4.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks4();
	     		        	double total = ((s1+s2+s3+s4)/(4.0*max))*100.0;  
	     		        	
	     		        	((StudentScoreDetails) t.getTableView().getItems().get(
	     	         		        t.getTablePosition().getRow())
	     	         		        ).setAveragePercentage(total);     
	        	            sql = "UPDATE `sakila`."+test+" SET `obtainedMarks3`='"+t.getNewValue()+"' WHERE `id`='"+(selectedIndex+1)+"'";

	        	            try {
								stmt.executeUpdate(sql);
							} catch (Exception e) {
								e.printStackTrace();
							}

	        		    });
	            
	            obtainedMarksColumn4.setOnEditCommit(
	            		 t -> {
	            			 int selectedIndex = personScores.getSelectionModel().getSelectedIndex();
	         		        ((StudentScoreDetails) t.getTableView().getItems().get(
	         		        t.getTablePosition().getRow())
	         		        ).setObtainedMarks4(t.getNewValue().intValue());
	         		        
	         		       double s1 = obtainedMarksColumn1.getTableView().getItems().get(
	     		                    t.getTablePosition().getRow()).getObtainedMarks1();
	     		        	double s2 = obtainedMarksColumn2.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks2();
	     		        	double s3 = obtainedMarksColumn3.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks3();
	     		        	double s4 = obtainedMarksColumn4.getTableView().getItems().get(
	             		            t.getTablePosition().getRow()).getObtainedMarks4();
	     		        	double total = ((s1+s2+s3+s4)/(4.0*max))*100.0;  
	     		        	
	     		        	((StudentScoreDetails) t.getTableView().getItems().get(
	     	         		        t.getTablePosition().getRow())
	     	         		        ).setAveragePercentage(total);     
	        	            sql = "UPDATE `sakila`."+test+" SET `obtainedMarks4`='"+t.getNewValue()+"' WHERE `id`='"+(selectedIndex+1)+"'";

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
		private void handleNewTest() {
			boolean okClicked = mainApp.addTest();
			if (okClicked){
				personScores.getItems().clear();
				load(AddTestRecords.batch,AddTestRecords.testName);
			}
		}
}



