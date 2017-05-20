package tech.sty.classdesk.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tech.sty.classdesk.MainApp;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	
	@FXML
	private TextField userNameField;
	@FXML
	private PasswordField passwordField;
	
	MainApp mainApp = new MainApp();
	@FXML
	private void handleLogin(ActionEvent event)throws Exception{
		if(userNameField.getText().trim().equals("root") && passwordField.getText().equals("root")){
			((Node) (event.getSource())).getScene().getWindow().hide();
			//mainApp.initRootLayout();
			//mainApp.showTestRecord();
			//mainApp.showPersonOverview();
			//mainApp.showFeeTable();
			mainApp.home();
			
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Login Error");
	        alert.setContentText("Invalid Username or Password");
	        alert.showAndWait();
			passwordField.clear();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
