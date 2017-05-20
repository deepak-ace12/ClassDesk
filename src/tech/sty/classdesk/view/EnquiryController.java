package tech.sty.classdesk.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import tech.sty.classdesk.MainApp;
import tech.sty.classdesk.model.EnquiryDetails;

public class EnquiryController {
	@FXML
	private Node root;

	@FXML
	TableView<EnquiryDetails> enquiryTable = new TableView<EnquiryDetails>();
	@FXML
	private TableColumn<EnquiryDetails, Number> srColumn;
	@FXML
	private TableColumn<EnquiryDetails, String> fullNameColumn;
	@FXML
	private TableColumn<EnquiryDetails, String> enquiryDateColumn;
	@FXML
	private TableColumn<EnquiryDetails, String> purposeColumn;
	@FXML
	private TableColumn<EnquiryDetails, String> emailColumn;
	@FXML
	private TableColumn<EnquiryDetails, String> contactColumn;
	@FXML
	private TableColumn<EnquiryDetails, String> revertDateColumn;

	public ObservableList<EnquiryDetails> enquiryData = FXCollections.observableArrayList();

	MainApp mainApp = new MainApp();
	int columnNo;
	String sql = null;
	Connection conn = null;
	Statement stmt = null;
	private static boolean isNewClicked;
	private static boolean isEditClicked;

	public ObservableList<EnquiryDetails> getPersonData() {
		return enquiryData;
	}

	public EnquiryController() {

	}

	@FXML
	private Button shut = new Button();

	public void shutStage(ActionEvent event) throws Exception {
		System.out.println("Running");
		root.getScene().getWindow().hide();

	}

	@FXML
	private void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM sakila.enquiry");
			while (rs.next()) {
				enquiryData.add(new EnquiryDetails(rs.getInt(1), rs.getString(2), rs.getDate(3).toLocalDate(),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7).toLocalDate()));
				columnNo = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		enquiryTable.setItems(enquiryData);
		srColumn.setCellValueFactory(cellData -> cellData.getValue().srNoProperty());
		fullNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
		enquiryDateColumn.setCellValueFactory(cellData -> cellData.getValue().enqDateProperty());
		purposeColumn.setCellValueFactory(cellData -> cellData.getValue().purposeProperty());
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
		revertDateColumn.setCellValueFactory(cellData -> cellData.getValue().revDateProperty());

		fullNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		purposeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	public void updateTable() {
		srColumn.setCellValueFactory(cellData -> cellData.getValue().srNoProperty());
		fullNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
		enquiryDateColumn.setCellValueFactory(cellData -> cellData.getValue().enqDateProperty());
		purposeColumn.setCellValueFactory(cellData -> cellData.getValue().purposeProperty());
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
		revertDateColumn.setCellValueFactory(cellData -> cellData.getValue().revDateProperty());

		setDataInTableCells();

	}

	private void setDataInTableCells() {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost/sakila", "root", "root");

			stmt = conn.createStatement();

			srColumn.setOnEditCommit(t -> {
				int selectedIndex = enquiryTable.getSelectionModel().getSelectedIndex();
				System.out.println(selectedIndex);
				((EnquiryDetails) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setSrNo(t.getNewValue().intValue());
				System.out.println(t.getNewValue());
				sql = "UPDATE `sakila`.`enquiry` SET `sr`='" + t.getNewValue() + "' WHERE `sr`='" + (selectedIndex + 1)
						+ "'";

				try {
					stmt.executeUpdate(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});

			fullNameColumn.setOnEditCommit(t -> {
				int selectedIndex = enquiryTable.getSelectionModel().getSelectedIndex();
				System.out.println(selectedIndex);
				((EnquiryDetails) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setFullName(t.getNewValue());
				System.out.println(t.getNewValue());
				sql = "UPDATE `sakila`.`enquiry` SET `name`='" + t.getNewValue() + "' WHERE `sr`='"
						+ (selectedIndex + 1) + "'";

				try {
					stmt.executeUpdate(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});

			purposeColumn.setOnEditCommit(t -> {
				int selectedIndex = enquiryTable.getSelectionModel().getSelectedIndex();
				System.out.println(selectedIndex);
				((EnquiryDetails) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setPurpose(t.getNewValue());
				System.out.println(t.getNewValue());
				sql = "UPDATE `sakila`.`enquiry` SET `purpose`='" + t.getNewValue() + "' WHERE `sr`='"
						+ (selectedIndex + 1) + "'";

				try {
					stmt.executeUpdate(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});

			emailColumn.setOnEditCommit(t -> {
				int selectedIndex = enquiryTable.getSelectionModel().getSelectedIndex();
				System.out.println(selectedIndex);
				((EnquiryDetails) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setEmail(t.getNewValue());
				System.out.println(t.getNewValue());
				sql = "UPDATE `sakila`.`enquiry` SET `email`='" + t.getNewValue() + "' WHERE `sr`='"
						+ (selectedIndex + 1) + "'";

				try {
					stmt.executeUpdate(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});

			contactColumn.setOnEditCommit(t -> {
				int selectedIndex = enquiryTable.getSelectionModel().getSelectedIndex();
				System.out.println(selectedIndex);
				((EnquiryDetails) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setContact(t.getNewValue());
				System.out.println(t.getNewValue());
				sql = "UPDATE `sakila`.`enquiry` SET `contact`='" + t.getNewValue() + "' WHERE `sr`='"
						+ (selectedIndex + 1) + "'";

				try {
					stmt.executeUpdate(sql);
				} catch (Exception e) {
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

	@FXML
	private void handleNewEnquiry() {

		getNewBoolean();

		EnquiryDetails tempPerson = new EnquiryDetails();
		boolean okClicked = mainApp.showEnquiryEditDialog(tempPerson);
		if (okClicked) {
			getPersonData().add(tempPerson);

		}

	}

	private void getNewBoolean() {
		isNewClicked = true;
		isEditClicked = false;

	}

	@FXML
	private void handleEditEnquiry() {
		getEditBoolean();
		EnquiryDetails selectedPerson = enquiryTable.getSelectionModel().getSelectedItem();
		int indexNo = enquiryTable.getSelectionModel().getSelectedIndex();
		System.out.println(indexNo);
		new PersonEditDialogController().setLineIndex(indexNo);
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showEnquiryEditDialog(selectedPerson);
			if (okClicked) {
				updateTable();

			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
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
	public void handleRemoveEnquiry() {
		int selectedIndex = enquiryTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			enquiryTable.getItems().remove(selectedIndex);
			try {
				Class.forName("com.mysql.jdbc.Driver");

				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

				Statement stmt1 = conn.createStatement();
				stmt1.executeUpdate("Delete  FROM sakila.enquiry where sr = '" + (selectedIndex + 1) + "'");
				Statement stmt2 = conn.createStatement();
				stmt2.executeUpdate("Update sakila.enquiry Set sr = sr - 1 Where sr > " + (selectedIndex + 1));
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
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

}