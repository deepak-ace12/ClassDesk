package tech.sty.classdesk.view;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import tech.sty.classdesk.MainApp;
import tech.sty.classdesk.model.PersonDetails;
import tech.sty.classdesk.util.Calender;

public class PersonOverviewController {
	@FXML
	public TableView<PersonDetails> personTable = new TableView<PersonDetails>();
	private ObservableList<PersonDetails> personData = FXCollections.observableArrayList();

	private @FXML Node root;

	@FXML
	private TableColumn<PersonDetails, Number> studentIdColumn;
	@FXML
	private TableColumn<PersonDetails, String> firstNameColumn;
	@FXML
	private TableColumn<PersonDetails, String> middleNameColumn;
	@FXML
	private TableColumn<PersonDetails, String> lastNameColumn;
	@FXML
	private Label studentIdLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label middleNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label branchLabel;
	@FXML
	private Label collegeLabel;
	@FXML
	private Label admissionLabel;
	@FXML
	private Label contactLabel;
	@FXML
	private Label birthdayLabel;
	@FXML
	private Text caption;
	@FXML
	private TextField searchBar;

	// Reference to the main application.
	private MainApp mainApp;
	String sql = null;
	Connection conn = null;
	Statement stmt = null;
	 Statement stmt1 = null;

	@FXML
	private Button btnChange = new Button("Change File");
	@FXML
	private Button btnUpload = new Button("Upload File");
	@FXML
	private Button newBatch = new Button();
	@FXML
	public ImageView imgView = new ImageView();

	@FXML
	private Button btnLoad = new Button("Load");
	@FXML
	private Button btnNew = new Button("New");
	@FXML
	private Button btnEdit = new Button("Edit");
	@FXML
	private ComboBox<String> selectClass = new ComboBox<String>();
	@FXML
	private ComboBox<String> selectBatch = new ComboBox<String>();
	// Reference to the main application.
	FileChooser fc = new FileChooser();
	public ArrayList<PersonDetails> list;
	ObservableList<PieChart.Data> pieChartData;
	ObservableList<String> options;
	public static ObservableList<String> options2 = FXCollections.observableArrayList();
	public static ObservableList<String> options3 = FXCollections.observableArrayList();
	String path = "";
	File file;

	Image img;
	public static boolean isNewClicked;
	public static boolean isEditClicked;
	public static String std;
	public static String batch;
	public static String test;
	public static int lastId;
	public static String s1,s2,s3,s4;
	PersonDetails person;

	@FXML
	Image img1 = new Image("file:E:/images/Photo.jpg");
	FileChooser fileChooser;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public PersonOverviewController() {

	}

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	private void showPersonDetails(PersonDetails person) {
		this.person = person;
		if (person != null) {
			// Fill the labels with info from the person object.
			studentIdLabel.setText(Integer.toString(person.getStudentId()));
			firstNameLabel.setText(person.getFirstName());
			middleNameLabel.setText(person.getMiddleName());
			lastNameLabel.setText(person.getLastName());
			branchLabel.setText(person.getBranch());
			collegeLabel.setText(person.getCollege());
			contactLabel.setText(person.getContact());
			admissionLabel.setText(Calender.format(person.getAdmission()));
			birthdayLabel.setText(Calender.format(person.getBirthday()));
			caption.setText(null);
			showPieChart(person.getStudentId());
			getImg();

		}

		else if (person == null) {
			// Person is null, remove all the text.
			studentIdLabel.setText("");
			firstNameLabel.setText("");
			middleNameLabel.setText("");
			lastNameLabel.setText("");
			branchLabel.setText("");
			collegeLabel.setText("");
			admissionLabel.setText("");
			contactLabel.setText("");
			birthdayLabel.setText("");
			imgView.setImage(img1);

		}

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
			if (batch != null) {
				personData.clear();
				loadBatch(batch);
			}
		});
	}

	@FXML
	private void handleAddBatch() {
		mainApp.showAddBatch();

	}

	@FXML
	private void feedAttendance() {
		if (std == null | batch == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(new MainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Batch Selection Error");
			alert.setContentText("Please Select a Class and Batch");

			alert.showAndWait();
		} else {
			mainApp.attendanceFeeder();

		}

	}

	private void loadBatch(String batch) {
		searchBar.clear();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT sakila." + batch + ".*, sakila.feestructure"+batch+".* FROM sakila." + batch
					+ " " + "left outer join sakila.feestructure"+batch+" on sakila." + batch + ".id = sakila.feestructure"+batch+".id");

			while (rs.next()) {
				String fullName = rs.getString(2) + " " + rs.getString(4);

				personData.add(new PersonDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8).toLocalDate(), rs.getString(9),
						rs.getString(10), rs.getDate(11).toLocalDate(), rs.getString(12), rs.getInt(14), rs.getInt(15),
						rs.getInt(16), rs.getInt(17), rs.getInt(18), rs.getInt(19), rs.getInt(20), rs.getString(21),
						rs.getString(22), fullName));

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ObservableList<PersonDetails> getPersonData() {
		return personData;
	}

	public void handleRefresh() {
		personTable.getItems().clear();
		searchBar.clear();
		// mainApp.filltableData(personData);
		loadBatch(batch);
		personTable.setItems(personData);
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// Initialize the person table with the three columns.
		studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();

		firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		middleNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		updateTable(selectedIndex);
		// Clear person details.
		showPersonDetails(null);
		
		// Listen for selection changes and show the person details when
		// changed.
		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

	}

	private void updateTable(int selectedIndex) {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost/sakila", "root", "root");

			stmt = conn.createStatement();

			firstNameColumn.setOnEditCommit(t -> {
				((PersonDetails) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setFirstName(t.getNewValue());
				System.out.println(t.getNewValue());
				sql = "UPDATE `sakila`.`batch1` SET `firstName`= " + "'" + t.getNewValue() + "'" + " WHERE `id`='5'";
				try {
					stmt.executeUpdate(sql);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		personTable.setItems(getPersonData());
		
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleMore() {

		PersonDetails selectedPerson = personTable.getSelectionModel().getSelectedItem();

		if (selectedPerson != null) {
			mainApp.showStudentFullDetails(selectedPerson);

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();

		}

		// personTable.getItems().clear();

		// int selectedIndex =
		// personTable.getSelectionModel().getSelectedIndex();
		// if (selectedIndex >= 0) {
		// personTable.getItems().remove(selectedIndex);
		// } else {
		// // Nothing selected.
		// Alert alert = new Alert(AlertType.WARNING);
		// alert.initOwner(mainApp.getPrimaryStage());
		// alert.setTitle("No Selection");
		// alert.setHeaderText("No Person Selected");
		// alert.setContentText("Please select a person in the table.");
		//
		// alert.showAndWait();
		// }

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		getNewBoolean();

		PersonDetails tempPerson = new PersonDetails();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
		if (okClicked) {
			getPersonData().add(tempPerson);
			
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
	private void handleEditPerson() {
		getEditBoolean();
		PersonDetails selectedPerson = personTable.getSelectionModel().getSelectedItem();
		int indexNo = personTable.getSelectionModel().getSelectedIndex();
		System.out.println(indexNo);
		new PersonEditDialogController().setLineIndex(indexNo);
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);

			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}

	}

	private void getEditBoolean() {
		isEditClicked = true;
		isNewClicked = false;
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

	}

	public String getPath() {
		return path;
	}

	private void getImg() {
		img = new Image("file:E:/images/" + firstNameLabel.getText() + lastNameLabel.getText() + ".jpg", 154.0, 150.0,
				false, true, true);
		imgView.setImage(img);
	}

	private void update(Image img) {
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

	@FXML
	public void handleLoad() throws IOException {

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		fileChooser.getExtensionFilters().add(extFilter);

		file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		// file = mainApp.removeDuplicates(file);
		path = file.getAbsolutePath().replace('\\', '/');

		if (file != null) {
			onLoad(file);
		}

	}

	public void onLoad(File file) throws IOException {
		Path dirP = Paths.get(String.valueOf(file));
		InputStream in = Files.newInputStream(dirP);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		list = new ArrayList<PersonDetails>();
		Scanner scan = new Scanner(reader);
		scan.useDelimiter(",");
		scan.useDelimiter("\\s*,\\s*");
		List<String> choices = new ArrayList<>();
		choices.add("SSC");
		choices.add("FYJC");
		choices.add("SYJC");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
		dialog.setTitle("Class Selection");
		dialog.setHeaderText("");
		dialog.setContentText("Choose Class For Loaded File:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Your choice: " + result.get());
		}

		result.ifPresent(letter -> System.out.println("Your choice: " + letter));

		TextInputDialog dialog2 = new TextInputDialog("");
		dialog2.setTitle("Naming The Batch");
		dialog2.setHeaderText("");
		dialog2.setContentText("Enter Batch Name For Loaded File");

		Optional<String> result2 = dialog2.showAndWait();
		if (result2.isPresent()) {
			System.out.println("Your name: " + result2.get());
		}

		result2.ifPresent(name -> System.out.println("Your name: " + name));
		new AddBatchController().createDBForNewBatch(result.get(), result2.get());

		while (scan.hasNext()) {
			String id = scan.next();
			String firstName = scan.next();
			String middleName = scan.next();
			String lastName = scan.next();
			String emailId = scan.next();
			String branchName = scan.next();
			String collegeName = scan.next();
			String admission = scan.next().replaceAll("'", "");
			String contactNo = scan.next();
			String guardianContact = scan.next();
			String birthDate = scan.next().replaceAll("'", "");
			String address = scan.next();
			String feeTotal = scan.next();
			String feePaid = scan.next();
			String nInstallment = scan.next();
			String amtOfInstallment = scan.next();
			String gapPeriod = scan.next();
			String feePending = scan.next();
			String lastInstallment = scan.next();
			String paidOn = scan.next().replaceAll("'", "");
			String nextDue = scan.nextLine().replaceAll("'", "");
			nextDue = nextDue.replaceAll(",", "");
			String fullName = firstName + " " + lastName;
			System.out.println("id " + id);

			// studentIdLabel.setText(id);
			// firstNameLabel.setText(String.valueOf(firstName));
			// middleNameLabel.setText(String.valueOf(middleName));
			// lastNameLabel.setText(String.valueOf(lastName));
			// branchLabel.setText(String.valueOf(branchName));
			// collegeLabel.setText(String.valueOf(collegeName));
			// admissionLabel.setText(String.valueOf(admissionDate));
			// contactLabel.setText(String.valueOf(contactNo));
			// birthdayLabel.setText(String.valueOf(dateOfBirth));
			//

			LocalDate admissionDate = LocalDate.parse(admission);
			LocalDate dateOfBirth = LocalDate.parse(birthDate);
			System.out.println(admissionDate + " " + dateOfBirth);

			personData.add(new PersonDetails(Integer.parseInt(id), firstName, middleName, lastName, emailId, branchName,
					collegeName, admissionDate, contactNo, guardianContact, dateOfBirth, address,
					Integer.parseInt(feeTotal), Integer.parseInt(feePaid), Integer.parseInt(nInstallment),
					Integer.parseInt(amtOfInstallment), Integer.parseInt(gapPeriod), Integer.parseInt(feePending),
					Integer.parseInt(lastInstallment), paidOn, nextDue, fullName));

			list.add(new PersonDetails(Integer.parseInt(id), firstName, middleName, lastName, emailId, branchName,
					collegeName, null, contactNo, guardianContact, null, address, Integer.parseInt(feeTotal),
					Integer.parseInt(feePaid), Integer.parseInt(nInstallment), Integer.parseInt(amtOfInstallment),
					Integer.parseInt(gapPeriod), Integer.parseInt(feePending), Integer.parseInt(lastInstallment),
					paidOn, nextDue, fullName));
			personTable.getItems().clear();
			personTable.setItems(personData);

			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

				// here test is database name, root is username and password

				Statement stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO `sakila`.`" + result2.get()
						+ "` (`id`, `firstName`, `middleName`, `lastName`, `email`, "
						+ "`branchName`, `collegeName`, `joiningDate`, `contact`, `guardianContact`, `dob`, `address`) "
						+ "VALUES ('" + id + "', '" + firstName + "', '" + middleName + "', '" + lastName + "', '"
						+ emailId + "', '" + branchName + "'," + " '" + collegeName + "', '" + admissionDate + "', '"
						+ contactNo + "', '" + guardianContact + "', " + "'" + dateOfBirth + "', '" + address + "');");

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}
		scan.close();

		showTableData();

	}

	public void showTableData() {
		studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

	}

	@FXML
	public void handleSearch() {
		personTable.getItems().clear();
		String toBeSearched = searchBar.getText();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT sakila." +batch+ ".*, sakila.feestructure"+batch+".* FROM sakila." + batch
					+ "" + " left outer join sakila.feestructure"+batch+" on sakila." + batch + ".id = sakila.feestructure"+batch+".id"
					+ " WHERE firstName LIKE '" + toBeSearched + "%' OR sakila." + batch + ".lastName LIKE '" + toBeSearched + "%' OR sakila." + batch + ".id LIKE '" + toBeSearched + "%'");
			personData = FXCollections.observableArrayList();

			while (rs.next()) {
				String fullName = rs.getString(2) + " " + rs.getString(4);

				personData.add(new PersonDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8).toLocalDate(), rs.getString(9),
						rs.getString(10), rs.getDate(11).toLocalDate(), rs.getString(12), rs.getInt(14), rs.getInt(15),
						rs.getInt(16), rs.getInt(17), rs.getInt(18), rs.getInt(19), rs.getInt(20), rs.getString(21),
						rs.getString(22), fullName));

				showTableData();
				personTable.setItems(personData);

			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/************************ Pie Chart *******************//************************
															 * Pie Chart
															 *******************/
	/************************ Pie Chart *******************//************************
															 * Pie Chart
															 *******************/
	/************************ Pie Chart *******************//************************
															 * Pie Chart
															 *******************/

	/************************ Pie Chart *******************/
	/************************ Pie Chart *******************/
	@FXML
	private PieChart piechart;

	private void showPieChart(int index) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			stmt1 = conn.createStatement();
			ResultSet rs2 = stmt1.executeQuery("SELECT MAX(sr) FROM sakila.test");
			while(rs2.next()){
				lastId = rs2.getInt(1);
			}
			System.out.println(lastId);
    		ResultSet rs1 =stmt1.executeQuery("SELECT Subject1,Subject2,Subject3,Subject4,testName FROM sakila.test where sr = '"+lastId+"' ");
    		while(rs1.next()){
             s1 = rs1.getString(1);
             s2 = rs1.getString(2);
             s3 = rs1.getString(3);
             s4 = rs1.getString(4);
             test ="studentscores";
    		}
    		System.out.println(test);
    		
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM sakila."+test+" WHERE id = '" + (index + 1) + "'");

			while (rs.next()) {
				if (Integer.toString(rs.getInt(2)) == null) {
					pieChartData = FXCollections.observableArrayList(new PieChart.Data(s1, 0),
							new PieChart.Data(s3, 0), new PieChart.Data(s2, 0),
							new PieChart.Data(s4, 0));
					System.out.println(Integer.toString(rs.getInt(2)) == null);

				} else {
					pieChartData = FXCollections.observableArrayList(new PieChart.Data(s1,60),
							new PieChart.Data(s2, 80), new PieChart.Data(s3, 75),
							new PieChart.Data(s4, 85));
					piechart.setTitle("Student Performance (Average)");
					

					piechart.setData(pieChartData);
					piechart.setLabelLineLength(10);
					piechart.setLegendSide(Side.RIGHT);

					for (PieChart.Data data : piechart.getData()) {
						data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								caption.setText(String.valueOf(data.getPieValue()) + "%");

							}
						});
					}

				}

				// new PieChart.Data("English", rs.getInt(6)));

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// @FXML
	// private void handleButtonClearAction(ActionEvent event) {
	// pieChartData =
	// FXCollections.observableArrayList();
	// piechart.setTitle("");
	// piechart.setData(pieChartData);
	// }

}
