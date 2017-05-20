package tech.sty.classdesk.home;

import javafx.fxml.FXML;
import javafx.scene.Node;
import tech.sty.classdesk.MainApp;

public class HomePageConrtroller {
	private MainApp mainApp = new MainApp();
	private @FXML Node root ;
	
	@FXML
	private void handleHome(){
		mainApp.home();
		root.getScene().getWindow().hide();

		}
 
	
	@FXML
	private void handleBatches(){
		mainApp.showPersonOverview();
		root.getScene().getWindow().hide();
		
	}
	
	@FXML
	private void handleStaff(){
		mainApp.showStaffRecord();
		root.getScene().getWindow().hide();

	}
	
	@FXML
	private void handleExpenses(){
		mainApp.showExpenses();
		root.getScene().getWindow().hide();

	}
	
	@FXML
	private void handleStaffPayments(){
		root.getScene().getWindow().hide();

	}
	
	@FXML
	private void handleFeeDetails(){
		mainApp.showFeeTable();
		root.getScene().getWindow().hide();

	}
	
	@FXML
	private void handleTestRecords(){
		mainApp.showTestRecord();
		root.getScene().getWindow().hide();

		
	}
	
	@FXML
	private void handleSMS(){
		root.getScene().getWindow().hide();
	}
	
	@FXML
	private void handleTimeTable(){
		mainApp.timetable();
		root.getScene().getWindow().hide();
		

	}
	
	@FXML
	private void handleEnquiry(){
		mainApp.enquiries();
		root.getScene().getWindow().hide();
	}
	
	@FXML
	private void handleExit(){
		System.exit(0);
	}
	
	

}
