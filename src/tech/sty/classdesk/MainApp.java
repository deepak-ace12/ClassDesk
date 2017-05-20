 package tech.sty.classdesk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tech.sty.classdesk.model.EnquiryDetails;
import tech.sty.classdesk.model.ExpensesDetails;
import tech.sty.classdesk.model.PersonDetails;
import tech.sty.classdesk.model.StaffDetails;
import tech.sty.classdesk.view.AddBatchController;
import tech.sty.classdesk.view.AddTestRecords;
import tech.sty.classdesk.view.EnquiryEditDialogController;
import tech.sty.classdesk.view.ExpensesController;
import tech.sty.classdesk.view.InstallmentEditController;
import tech.sty.classdesk.view.NewExpenses;
import tech.sty.classdesk.view.PaymentEditDialogController;
import tech.sty.classdesk.view.PersonEditDialogController;
import tech.sty.classdesk.view.PersonOverviewController;
import tech.sty.classdesk.view.RootLayoutController;
import tech.sty.classdesk.view.StaffController;
import tech.sty.classdesk.view.StaffEditDialogController;
import tech.sty.classdesk.view.StaffEditDialogController1;
import tech.sty.classdesk.view.StudentsFullDetailsController;

public class MainApp extends Application {

	private static Stage primaryStage;
	private BorderPane rootLayout;
	ActionEvent event;

	PersonDetails person;

	/**
	 * Constructor
	 */
	public MainApp() {

	}

	@Override
	public void start(Stage primaryStage) {
		MainApp.primaryStage = primaryStage;
		MainApp.primaryStage.setTitle("");

		// setting application icon
		MainApp.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
         loginPanel();
		// loadingSplash();
		//home();
	}

	public void loadingSplash() {
		// TODO Auto-generated method stub

		try {
			Parent root = FXMLLoader.load(getClass().getResource("login/Loader.fxml"));

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("Loading");
			stage.setScene(scene);
			stage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void timetable() {
		MainApp.primaryStage.close();
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TimeTable.fxml"));
			AnchorPane timeTable = (AnchorPane) loader.load();
			timeTable.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getX() == 0.0) {
						sideScreen();

					}
				}
			});

			primaryStage = new Stage();
			Scene scene = new Scene(timeTable);
			primaryStage.setTitle("Time Table");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);

			primaryStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void loginPanel() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("Login");
			stage.setScene(scene);
			stage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void attendanceFeeder() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/FeedAttendance.fxml"));
			Scene scene = new Scene(root);
			primaryStage = new Stage();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Attendance");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void home() {
		if(MainApp.primaryStage!=null){
			MainApp.primaryStage.close();
		}


		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
			Scene scene = new Scene(root);
			primaryStage = new Stage();
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			primaryStage.setTitle("HOME");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void sideScreen() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/SideScreen.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setX(0);
			stage.setY(0);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			stage.setHeight(primaryScreenBounds.getHeight());

			stage.initStyle(StageStyle.UNDECORATED);

			stage.show();
			stage.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					// System.out.println("mouse click detected! " +
					// mouseEvent.getSource());

					if (mouseEvent.getX() > stage.getWidth()) {
						stage.close();
					}

				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void enquiries() {
		MainApp.primaryStage.close();
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EnquiryTable.fxml"));
			AnchorPane enquiry = (AnchorPane) loader.load();
			enquiry.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getX() == 0.0) {
						sideScreen();

					}
				}
			});

			primaryStage = new Stage();
			Scene scene = new Scene(enquiry);
			primaryStage.setTitle("Enquiries");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			 
			primaryStage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Stage primary = new Stage();
			Scene scene = new Scene(rootLayout);
			primary.setScene(scene);
			primary.setMaximized(true);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primary.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Try to load last opened person file.
		File file = getPersonFilePath();
		if (file != null) {
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showPersonOverview() {
		MainApp.primaryStage.close();
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			personOverview.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getX() == 0.0) {
						sideScreen();

					}
				}
			});

			primaryStage = new Stage();
			Scene scene = new Scene(personOverview);
			primaryStage.setTitle("Batch Details");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);

			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public boolean showAddBatch() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddBatch.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Batches");
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the batch into the controller.
			AddBatchController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void showStudentFullDetails(PersonDetails tempPerson) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/StudentFullDetails.fxml"));
			AnchorPane studentDetails = (AnchorPane) loader.load();
			studentDetails.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getX() == 0.0) {
						sideScreen();

					}
				}
			});

			primaryStage = new Stage();
			Scene scene = new Scene(studentDetails);
			primaryStage.setTitle("Student Details");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);

			StudentsFullDetailsController controller = loader.getController();
			controller.setPerson(tempPerson);

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public void showStaffRecord() {
			MainApp.primaryStage.close();
	        try {
	        	FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("fxml/Staff.fxml"));
				AnchorPane staff = (AnchorPane) loader.load();
				staff.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {

						if (mouseEvent.getX() == 0.0) {
							sideScreen();

						}
					}
				});

				primaryStage = new Stage();
				Scene scene = new Scene(staff);
				primaryStage.setScene(scene);
				primaryStage.setMaximized(true);
	            StaffController controller = loader.getController();
	            controller.setMainApp(this);
				primaryStage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	 
	 public void showExpenses() {
		 MainApp.primaryStage.close();	
		 try {

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("fxml/Expenses.fxml"));
				AnchorPane expensesOvereview = (AnchorPane) loader.load();
				expensesOvereview.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {

						if (mouseEvent.getX() == 0.0) {
							sideScreen();

						}
					}
				});

				primaryStage = new Stage();
				Scene scene = new Scene(expensesOvereview);
				primaryStage.setScene(scene);
				primaryStage.setMaximized(true);

				ExpensesController controller = loader.getController();
				controller.setMainApp(this);

				primaryStage.show();
				

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}	    
	    
	    public boolean editExpenses(ExpensesDetails temp){
	    	try{
	    	FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("fxml/ExpensesEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Add Expenses");
	        dialogStage.initStyle(StageStyle.UTILITY);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        

	        // Set the person into the controller.
	        NewExpenses controller = loader.getController();
	        controller.setDialogStage(dialogStage);
			controller.setExpenses(temp);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
	        return controller.isOkClicked();
	    	}
	    	catch(Exception ex){
	    		ex.printStackTrace();
	    		 
	    	}
			return false;
	    }


	 
	public void showTestRecord() {
		MainApp.primaryStage.close();
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/TestRecordsLayout.fxml"));
			AnchorPane studentDetails = (AnchorPane) loader.load();
			studentDetails.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getX() == 0.0) {
						sideScreen();

					}
				}
			});

			primaryStage = new Stage();
			Scene scene = new Scene(studentDetails);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showFeeTable() {
		MainApp.primaryStage.close();
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/FeeStructureTable.fxml"));
			AnchorPane feeTable = (AnchorPane) loader.load();
			feeTable.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					// System.out.println("mouse click detected! " +
					// mouseEvent.getSource());

					if (mouseEvent.getX() == 0.0) {
						sideScreen();
					}

				}
			});
			// Set person overview into the center of root layout.

			primaryStage = new Stage();
			Scene scene = new Scene(feeTable);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();

			// Give the controller access to the main app.

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 * 
	 * @param tempPerson
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(PersonDetails tempPerson) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(tempPerson);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showEnquiryEditDialog(EnquiryDetails tempPerson) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EnquiryEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Enquiry");
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			EnquiryEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setEnquiry(tempPerson);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showInstallmentEditDialog(PersonDetails tempPerson) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/InstallmentEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add New Installment");
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			InstallmentEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(tempPerson);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			


			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean showPaymentEditDialog(StaffDetails tempPerson) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddStaffPayment.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add New Payment");
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PaymentEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setStaff(tempPerson);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			System.out.println("mainApp new");


			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	
	public boolean addTest() {
		try{
	    	FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("fxml/SetTestRecords.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("New Test Record");
	        dialogStage.initStyle(StageStyle.UTILITY);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
            AddTestRecords controller = loader.getController();
            controller.setDialogStage(dialogStage);
	        // Set the person into the controller.
	       

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
	       return controller.isOkClicked();
	    	}
	    	catch(Exception ex){
	    		ex.printStackTrace();
	    		return false;
	    	}
    }

	public boolean showStaffEditDialog(StaffDetails tempStaff) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/StaffEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Staff");
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            

            // Set the person into the controller.
            StaffEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStaff(tempStaff);
            

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	
	public boolean showStaffEditDialog1(StaffDetails tempStaff) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/StaffEditDialog1.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Staff");
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            

            // Set the person into the controller.
            StaffEditDialogController1 controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStaff(tempStaff);
            

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file
	 *            the file or null to remove the path
	 */
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("AddressApp - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("AddressApp");
		}
	}

	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */

	/**
	 * Opens a dialog to show birthday statistics.
	 */
	public void showBirthdayStatistics() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/BirthdayStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Birthday Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the persons into the controller.
			// BirthdayStatisticsController controller = loader.getController();
			// controller.setPersonData(personData);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File removeDuplicates(File file) throws IOException {
		LinkedHashSet<String> lhs = new LinkedHashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String data;
		while ((data = br.readLine()) != null) {
			lhs.add(data);

		}
		br.close();
		file.delete();
		System.out.println("file: " + file.exists());
		File dirPath2 = new File("C:/A_CSV");
		dirPath2.mkdirs();// Make the directory

		File file2 = new File(dirPath2, "People.csv");
		if (!file2.exists()) {
			file2.delete();
			file2.createNewFile();
		}

		for (String s : lhs) {
			System.out.println(s);
			FileWriter fileWriter = new FileWriter(file2.getPath(), true);
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			try {
				bufferWriter.write(s);
				bufferWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bufferWriter.close();
		}

		lhs.clear();
		return file2;

	}

	public static void main(String[] args) {
		launch(args);
	}
}

/**
 * 
 * Describes the characteristics of a graphics destination such as monitor. In a
 * virtual device multi-screen environment in which the desktop area could span
 * multiple physical screen devices, the bounds of the Screen objects are
 * relative to the Screen.primary. For example:
 * 
 * 
 * Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
 * 
 * //set Stage boundaries to visible bounds of the main screen
 * stage.setX(primaryScreenBounds.getMinX());
 * stage.setY(primaryScreenBounds.getMinY());
 * stage.setWidth(primaryScreenBounds.getWidth());
 * stage.setHeight(primaryScreenBounds.getHeight());
 * 
 * stage.show();
 */