package tech.sty.classdesk.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tech.sty.classdesk.MainApp;
import tech.sty.classdesk.model.PersonDetails;

public class StudentsFullDetailsController {
	@FXML
	private Label feePaidLabel;
	@FXML
	private Label feePendingLabel;
	@FXML
	private Label feeTotalLabel;
	@FXML
	private Label lastInstallmentLabel;
	@FXML
	private Label paidOnLabel;
	@FXML
	private Label nextDueDateLabel;
	@FXML
	private Label fullNameLabel;
	@FXML
	private Label guardianNameLabel;
	@FXML
	private Label studentContactLabel;
	@FXML
	private Label emailIdLabel;
	@FXML
	private Label guardianContactLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Text caption;
	@FXML
	private Label countTotal;
	@FXML
	private Label countMissed;
	@FXML
	private DatePicker checkDate;
	@FXML
	private Label checkLabel;

	PersonDetails person;
	@FXML
	public ImageView imgView = new ImageView();

	ObservableList<XYChart.Series<String, Number>> barChartData;
	ObservableList<PieChart.Data> pieChartData;

	@FXML
	NumberAxis yAxis;
	@FXML
	CategoryAxis xAxis;
	@FXML
	BarChart<String, Number> barChart;

	public void setPerson(PersonDetails person) {
		this.person = person;
		if (person != null) {
			idLabel.setText(Integer.toString(person.getStudentId()));
			fullNameLabel.setText(person.getFirstName() + " " + person.getLastName());
			studentContactLabel.setText(person.getContact());
			emailIdLabel.setText(person.getEmailId());
			guardianNameLabel.setText(person.getMiddleName() + " " + person.getLastName());
			guardianContactLabel.setText(person.getGuardianContact());
			addressLabel.setText(person.getAddress());
			feePaidLabel.setText(Integer.toString(person.getFeePaid()));
			feePendingLabel.setText(Integer.toString(person.getFeePending()));
			feeTotalLabel.setText(Integer.toString(person.getFeeTotal()));
			lastInstallmentLabel.setText(Integer.toString(person.getlastInstallment()));
			paidOnLabel.setText(person.getPaidOn());
			nextDueDateLabel.setText(person.getNextDue());
			int indexNo = person.getStudentId();
			getImg(person.getFirstName() + person.getLastName());
			showBarChart(indexNo);
			showPieChart(indexNo);

		} else if (person == null) {
			// Person is null, remove all the text.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(new MainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();

		}

	}

	private void getImg(String name) {
		Image img = new Image("file:E:/images/" + name + ".jpg", 154.0, 150.0, false, true, true);
		imgView.setImage(img);
	}

	@FXML
	public void initialize() {
	}

	@SuppressWarnings("unchecked")
	public void showBarChart(int index) {
		xAxis.setLabel("Subject");
		xAxis.setTickLabelRotation(0);
		yAxis.setLabel("Score");
		XYChart.Series<String, Number> series1 = new Series<String, Number>();
		XYChart.Series<String, Number> series2 = new Series<String, Number>();
		XYChart.Series<String, Number> series3 = new Series<String, Number>();
		XYChart.Series<String, Number> series4 = new Series<String, Number>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM sakila.studentscores where id =" + index + "");

			while (rs.next()) {

				series1.getData().addAll(new Data<String, Number>("Physics", rs.getInt(2)),
						new Data<String, Number>("Chemistry", rs.getInt(3)),
						new Data<String, Number>("Maths", rs.getInt(4)),
						new Data<String, Number>("Biology", rs.getInt(5)));

				series2.getData().addAll(new Data<String, Number>("Physics", 60),
						new Data<String, Number>("Chemistry", 70), new Data<String, Number>("Maths", 80),
						new Data<String, Number>("Biology", 90));

				series3.getData().addAll(new Data<String, Number>("Physics", 69),
						new Data<String, Number>("Chemistry", 78), new Data<String, Number>("Maths", 85),
						new Data<String, Number>("Biology", 92));

				series4.getData().addAll(new Data<String, Number>("Physics", 58),
						new Data<String, Number>("Chemistry", 65), new Data<String, Number>("Maths", 45),
						new Data<String, Number>("Biology", 38));

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		barChart.setTitle("Student's Overall Performance");
		series1.setName("Test 1");
		series2.setName("Test 2");
		series3.setName("Test 3");
		series4.setName("Test 4");

		barChart.getData().addAll(series1, series2, series3, series4);
		barChart.setLegendSide(Side.BOTTOM);

	}

	@FXML
	private PieChart piechart;

	private void showPieChart(int indexNo) {
		ArrayList<String> arrayList = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM sakila.temp WHERE id = '" + indexNo + "'");

			while (rs.next()) {

				if (rs.getString(2) == null) {
					pieChartData = FXCollections.observableArrayList(new PieChart.Data("Present", 0),
							new PieChart.Data("Absent", 0));

				} else {
					int countPresent = 0;
					int countAbsent = 0;
					for (int i = 2; i <= getColumnCount(); i++) {
						if (rs.getString(i).equalsIgnoreCase("Present"))
							++countPresent;
						else if (rs.getString(i).equalsIgnoreCase("Absent"))
							++countAbsent;

					}
					double presentPercent = ((countPresent * 1.0) / (countPresent + countAbsent)) * 100;
					double absentPercent = ((countAbsent * 1.0) / (countPresent + countAbsent)) * 100;
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(2);
					System.out.println(nf.format(presentPercent) + " " + nf.format(absentPercent));
					pieChartData = FXCollections.observableArrayList(
							new PieChart.Data("Present", Double.parseDouble(nf.format(presentPercent))),
							new PieChart.Data("Absent", Double.parseDouble(nf.format(absentPercent))));
					arrayList.clear();
					countTotal.setText("" + (countAbsent + countPresent));
					countMissed.setText(countAbsent + "");

					piechart.setTitle("Attendance Record (Average)");

					piechart.setData(pieChartData);
					piechart.setLabelLineLength(10);
					piechart.setLegendSide(Side.RIGHT);

					for (PieChart.Data data : piechart.getData()) {
						data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								// caption.setTranslateX(e.getX());
								// caption.setTranslateY(e.getY());

								caption.setText(String.valueOf(data.getPieValue()) + "%");

							}
						});
					}
				}
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@FXML
	private void handleBack() {

	}

	private int getColumnCount() {
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT Count(*) FROM INFORMATION_SCHEMA.Columns where TABLE_NAME = 'temp'");
			while (rs.next())
				count = rs.getInt(1);

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return count;
	}

	@FXML
	private void handleCheck() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");

			// here test is database name, root is username and password

			Statement stmt = con.createStatement();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
			System.out.println();
			String date = checkDate.getValue().format(formatter);

			ResultSet rs = stmt.executeQuery("SELECT " + date + " FROM sakila.temp where id = " + idLabel.getText());
			while (rs.next())
				checkLabel.setText(rs.getString(1));

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
