package tech.sty.classdesk.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import tech.sty.classdesk.MainApp;
import tech.sty.classdesk.model.StaffDetails;
import tech.sty.classdesk.model.StaffPayment;
import tech.sty.classdesk.util.Calender;

public class StaffController {

	private ObservableList<StaffDetails> staffData = FXCollections.observableArrayList();
	private ObservableList<StaffDetails> staffData1 = FXCollections.observableArrayList();
	private ObservableList<StaffPayment> staffPay = FXCollections.observableArrayList();

	@FXML
	private TableView<StaffPayment> paymentTable = new TableView<StaffPayment>();
	@FXML
	private TableColumn<StaffPayment, String> monthColumn;
	@FXML
	private TableColumn<StaffPayment, Number> paidColumn;
	@FXML
	private TableColumn<StaffPayment, String> modeColumn;
	@FXML
	private TableColumn<StaffPayment, String> receiptNoColumn;

	@FXML
	private Label staffIdLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label subjectLabel1;
	@FXML
	private Label subjectLabel2;
	@FXML
	private Label joiningLabel;
	@FXML
	private Label contactLabel;
	@FXML
	private Label deptLabel;

	// Reference to the main application.
	private MainApp mainApp;
	StaffDetails staff;
	StaffDetails nstaff;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sty";
	String sql = null;
	Connection conn = null;
	Statement stmt = null;

	@FXML
	private Button btnChange = new Button("Change File");
	@FXML
	private Button btnUpload = new Button("Upload File");
	@FXML
	private Button btnNew = new Button("Add New");
	@FXML
	private Button btnEdit = new Button("Edit");
	@FXML
	private Button btnDelete = new Button("Delete");
	@FXML
	public ImageView imgView = new ImageView();
	@FXML
	private ComboBox<StaffDetails> selectTStaff = new ComboBox<StaffDetails>();
	@FXML
	private ComboBox<StaffDetails> selectNStaff = new ComboBox<StaffDetails>();

	// Reference to the main application.
	FileChooser fc = new FileChooser();

	String path = "";
	File file;
	Image img;
	@FXML
	Image img1 = new Image("file:E:/images/Photo.jpg");
	FileChooser fileChooser;

	private static boolean isNewClicked;
	private static boolean isEditClicked;
	private static boolean isTSClicked;
	private static boolean isNSClicked;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public StaffController() {

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		showStaffDetails(null);
		loadStaff();
		loadStaff1();

	}

	@FXML
	private void staffSelection1() throws IOException {
		selectNStaff.getSelectionModel().clearSelection();		
		selectTStaff.setItems(staffData);
		selectTStaff.setCellFactory((comboBox) -> {
			return new ListCell<StaffDetails>() {
				@Override
				protected void updateItem(StaffDetails item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getFirstName() + " " + item.getLastName());
						
					}
				}
			};
		});

		selectTStaff.setConverter(new StringConverter<StaffDetails>() {
			@Override
			public String toString(StaffDetails staff) {
				if (staff == null) {
					return null;
				} else {
					return staff.getFirstName() + " " + staff.getLastName();
				}
			}

			@Override
			public StaffDetails fromString(String string) {
				return null;
			}
		});

		selectTStaff.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StaffDetails>() {
			public void changed(ObservableValue<? extends StaffDetails> ov, StaffDetails old_val,
					StaffDetails new_val) {
				paymentTable.getItems().clear();
				showStaffDetails(new_val);
				staffTable();
				getTSBoolean();

			}
		});

	}

	private void getTSBoolean() {
		isTSClicked = true;
		isNSClicked = false;

	}

	@FXML
	private void staffSelection2() throws IOException {
		selectTStaff.getSelectionModel().clearSelection();
		selectNStaff.setItems(staffData1);
		selectNStaff.setCellFactory((comboBox) -> {
			return new ListCell<StaffDetails>() {
				@Override
				protected void updateItem(StaffDetails item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getFirstName() + " " + item.getLastName());
					}
				}
			};
		});

		selectNStaff.setConverter(new StringConverter<StaffDetails>() {
			@Override
			public String toString(StaffDetails staff) {
				if (staff == null) {
					return null;
				} else {
					return staff.getFirstName() + " " + staff.getLastName();
				}
			}

			@Override
			public StaffDetails fromString(String string) {
				return null;
			}
		});

		selectNStaff.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StaffDetails>() {
			public void changed(ObservableValue<? extends StaffDetails> ov, StaffDetails old_val,
					StaffDetails new_val) {
				paymentTable.getItems().clear();
				showStaffDetails(new_val);
				staffTable1();
				getNSBoolean();

			}
		});

	}

	private void getNSBoolean() {
		isNSClicked = true;
		isTSClicked = false;
	}

	private void loadStaff() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sty", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM sty.teachingstaff");

			while (rs.next()) {
				staffData.add(new StaffDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getDate(7).toLocalDate(), rs.getString(8),
						rs.getString(9)));
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void loadStaff1() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sty", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM sty.nonteachingstaff");

			while (rs.next()) {
				staffData1.add(new StaffDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getDate(7).toLocalDate(), rs.getString(8),
						rs.getString(9)));
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void staffTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();
			int staffId = Integer.parseInt(staffIdLabel.getText());

			ResultSet rs = stmt.executeQuery(
					
					"SELECT * FROM sakila.paymentinfo where staffId = "+staffId+"");

					//"SELECT * FROM sty.payment where name = '" + staff.getFirstName() + staff.getLastName() + "'");
			while (rs.next()) {
				for(int i=1,count =1;i<=getNonEmptyRowCount(staffId)/4;i++)
					staffPay.add(new StaffPayment(rs.getInt(1), rs.getDate(++count).toLocalDate(), rs.getInt(++count), 
							rs.getString(++count), rs.getString(++count)));
			}
			paymentTable.setItems(staffPay);
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		paidColumn.setCellValueFactory(cellData -> cellData.getValue().paidProperty());
		modeColumn.setCellValueFactory(cellData -> cellData.getValue().modeProperty());
		monthColumn.setCellValueFactory(cellData -> cellData.getValue().payDateProperty());
		receiptNoColumn.setCellValueFactory(cellData -> cellData.getValue().receiptNoProperty());

		paidColumn
				.setCellFactory(TextFieldTableCell.<StaffPayment, Number> forTableColumn(new NumberStringConverter()));
		modeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	public int getNonEmptyRowCount(int id) {
		int count = 0;
		try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sakila","root","root");  
    		  
    		//here test is database name, root is username and password  
    		  
    		Statement stmt=con.createStatement();  
    		  
    		ResultSet rs =stmt.executeQuery("SELECT * FROM sakila.paymentinfo where staffId = "+id);  
    		while(rs.next())
    			for(int i=1;i<=21;i++)
					if(rs.getString(i)!=null)
						++count;

			con.close();
    		  
    		}catch(Exception e){ System.out.println(e);}  
    		  
         
		return count;
		
	}

	private void staffTable1() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();
			int staffId = Integer.parseInt(staffIdLabel.getText());

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM sakila.paymentinfo where staffId = "+staffId+"");

			while (rs.next()) {
				for(int i=1,count =1;i<=getNonEmptyRowCount(staffId)/4;i++)
					staffPay.add(new StaffPayment(rs.getInt(1), rs.getDate(++count).toLocalDate(), rs.getInt(++count), 
							rs.getString(++count), rs.getString(++count)));

			}
			paymentTable.setItems(staffPay);


			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		paidColumn.setCellValueFactory(cellData -> cellData.getValue().paidProperty());
		modeColumn.setCellValueFactory(cellData -> cellData.getValue().modeProperty());

	}

	public ObservableList<StaffDetails> getStaffData() {
		return staffData;
	}

	public ObservableList<StaffDetails> getStaffData1() {
		return staffData;
	}

	private void showStaffDetails(StaffDetails staff) {
		this.staff = staff;
		if (staff != null) {
			// Fill the labels with info from the person object.
			staffIdLabel.setText(Integer.toString(staff.getStaffId()));
			firstNameLabel.setText(staff.getFirstName());
			lastNameLabel.setText(staff.getLastName());
			emailLabel.setText(staff.getEmail());
			subjectLabel1.setText(staff.getSubject1());
			subjectLabel2.setText(staff.getSubject2());
			joiningLabel.setText(Calender.format(staff.getJoining()));
			contactLabel.setText(staff.getContact());
			deptLabel.setText(staff.getDepartment());
			getImg();

		}

		else if (staff == null) {
			// Person is null, remove all the text.
			staffIdLabel.setText("");
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			emailLabel.setText("");
			subjectLabel1.setText("");
			subjectLabel2.setText("");
			joiningLabel.setText("");
			contactLabel.setText("");
			deptLabel.setText("");
			imgView.setImage(img1);

		}

	}

//	private void showStaffDetails(StaffDetails staff) {
//		this.nstaff = staff;
//		if (staff != null) {
//			// Fill the labels with info from the person object.
//			staffIdLabel.setText(Integer.toString(staff.getStaffId()));
//			firstNameLabel.setText(staff.getFirstName());
//			lastNameLabel.setText(staff.getLastName());
//			emailLabel.setText(staff.getEmail());
//			subjectLabel.setText(staff.getSubject());
//			joiningLabel.setText(Calender.format(staff.getJoining()));
//			contactLabel.setText(staff.getContact());
//			paidLabel.setText(staff.getPaid());
//			pendingLabel.setText(staff.getPending());
//			deptLabel.setText(staff.getDepartment());
//			paidLabel1.setText(staff.getPaid());
//			pendingLabel1.setText(staff.getPending());
//			int a = Integer.parseInt(staff.getPaid());
//			int b = Integer.parseInt(staff.getPending());
//			String c = Integer.toString(a + b);
//			totalLabel.setText(c);
//			getImg();
//
//		}
//
//		else if (staff == null) {
//			// Person is null, remove all the text.
//			staffIdLabel.setText("");
//			firstNameLabel.setText("");
//			lastNameLabel.setText("");
//			emailLabel.setText("");
//			subjectLabel.setText("");
//			joiningLabel.setText("");
//			contactLabel.setText("");
//			paidLabel.setText("");
//			pendingLabel.setText("");
//			deptLabel.setText("");
//			paidLabel1.setText("");
//			pendingLabel1.setText("");
//			totalLabel.setText("");
//			imgView.setImage(img1);
//
//		}
//
//	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewStaff() {
		getNewBoolean();

		StaffDetails tempStaff = new StaffDetails();
		boolean okClicked = mainApp.showStaffEditDialog(tempStaff);
		if (okClicked) {
			getStaffData().add(tempStaff);
		}

	}

	@FXML
	private void handleNewStaff1() {
		getNewBoolean();

		StaffDetails tempStaff = new StaffDetails();
		boolean okClicked = mainApp.showStaffEditDialog1(tempStaff);
		if (okClicked) {
			getStaffData1().add(tempStaff);
		}

	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	private void getNewBoolean() {
		isNewClicked = true;
		isEditClicked = false;

	}

	@FXML
	private void handleEditStaff() {
		getEditBoolean();
    	if(isTSClicked){
    	 StaffDetails selectedPerson = selectTStaff.getSelectionModel().getSelectedItem();
    	 int indexNo = selectTStaff.getSelectionModel().getSelectedIndex();
         System.out.println(indexNo);
         new StaffEditDialogController().setLineIndex(indexNo);
         if (selectedPerson != null) {
             boolean okClicked = mainApp.showStaffEditDialog(selectedPerson);
             if (okClicked) {
                 showStaffDetails(selectedPerson);
                 
             }
         }

    	}
         
         else if(isNSClicked){
        	 StaffDetails selectedPerson = selectNStaff.getSelectionModel().getSelectedItem();
        	 int indexNo = selectNStaff.getSelectionModel().getSelectedIndex();
             System.out.println(indexNo);
             new StaffEditDialogController1().setLineIndex(indexNo);
             if (selectedPerson != null) {
                 boolean okClicked = mainApp.showStaffEditDialog1(selectedPerson);
                 if (okClicked) {
                     showStaffDetails(selectedPerson);
                     
                 }
             }
         }

             else {
                 // Nothing selected.
                 Alert alert = new Alert(AlertType.WARNING);
                 //alert.initOwner(mainApp.getPrimaryStage());
                 alert.setTitle("No Selection");
                 alert.setHeaderText("No Person Selected");
                 alert.setContentText("Please select a person from Box.");

                 alert.showAndWait();
             }
         }
		

	

	private void getEditBoolean() {
		isEditClicked = true;
		isNewClicked = false;
	}

	@FXML
	private void handleDeleteStaff() {
		Alert alert1 = new Alert(AlertType.CONFIRMATION);
		alert1.initOwner(mainApp.getPrimaryStage());
		alert1.setTitle("Confirm Delete");
		alert1.setHeaderText("Confirm Delete");
		alert1.setContentText("Are You really want to dalete the data");

			Optional<ButtonType> result = alert1.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					Class.forName("com.mysql.jdbc.Driver");

					// STEP 3: Open a connection
					System.out.println("Connecting to a selected database...");
					conn = DriverManager.getConnection(DB_URL, "root", "root");
					System.out.println("Connected database successfully...");

					// STEP 4: Execute a query
					System.out.println("Deleting records into the table...");
					stmt = conn.createStatement();
					if (isTSClicked) {  
					int selectedIndex = selectTStaff.getSelectionModel().getSelectedIndex();
					String sql = "DELETE FROM `sty`.`teachingstaff` WHERE `id`=" + "'" + (selectedIndex + 1) + "'";
					stmt.executeUpdate(sql);
					showStaffDetails(null);
				    paymentTable.getItems().clear();
					selectTStaff.getItems().clear();
					loadStaff();
					}
					else
					if(isNSClicked){
						int selectedIndex = selectNStaff.getSelectionModel().getSelectedIndex();
						String sql1 = "DELETE FROM `sty`.`nonteachingstaff` WHERE `id`=" + "'" + (selectedIndex + 1) + "'";
						stmt.executeUpdate(sql1);
						showStaffDetails(null);
					    paymentTable.getItems().clear();
						selectNStaff.getItems().clear();
						loadStaff1();
					}
					

					System.out.println("Deleted records into the table...");
					

				} catch (SQLException se) {
					// Handle errors for JDBC
					se.printStackTrace();
				} catch (Exception e) {
					// Handle errors for Class.forName
					e.printStackTrace();
				}
			} else {
				alert1.close();
			}
	}

	@FXML
	private void handleUploadFile() {

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ALL files (*.*)", "*.*");
		fileChooser.getExtensionFilters().add(extFilter);

		file = fileChooser.showOpenDialog(null);
		path += file.getAbsolutePath();

		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			img = SwingFXUtils.toFXImage(bufferedImage, null);
			img = new Image(file.toURI().toURL().toString(), 154.0, 150, false, true);
			imgView.setImage(img);
			update(img);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		path = path.replace('\\', '/');
	}

	public String getPath() {
		return path;
	}

	private void getImg() {
		img = new Image("file:E:/images/" + firstNameLabel.getText() + lastNameLabel.getText() + ".jpg", 154.0, 150.0,
				false, true, true);
		imgView.setImage(img);
	}

	public void update(Image img) {
		File outputFile = new File("E:/images/" + firstNameLabel.getText() + lastNameLabel.getText() + ".jpg");
		try {
			BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
			ImageIO.write(bImage, "png", outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isEditClicked() {
		System.out.println("Edit: " + isEditClicked);

		return isEditClicked;
	}

	public boolean isNewClicked() {
		System.out.println("New: " + isNewClicked);

		return isNewClicked;
	}

	public boolean isNSClicked() {
		System.out.println("Edit: " + isEditClicked);

		return isNSClicked;
	}

	public boolean isTSClicked() {
		System.out.println("New: " + isNewClicked);

		return isTSClicked;
	}
	
	
	
	
	@FXML
	private void handleAddPayment(){
		StaffDetails selectedPerson = selectTStaff.getSelectionModel().getSelectedItem();
//		System.out.println(indexNo);
//		new InstallmentEditController().setLineIndex(indexNo);
		if (staff != null) {
			
			boolean okClicked = mainApp.showPaymentEditDialog(selectedPerson);
			StaffPayment tempPerson = new StaffPayment();
			if (okClicked) {
				getPersonData().add(tempPerson);
				paymentTable.getItems().clear();
				staffTable();
				
			}

		}else{
			Alert alert = new Alert(AlertType.WARNING);
			// alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person from Box.");

			alert.showAndWait();
		}
	}
	public ObservableList<StaffPayment> getPersonData() {
		return staffPay;
	}

}