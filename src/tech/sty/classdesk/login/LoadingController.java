package tech.sty.classdesk.login;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tech.sty.classdesk.MainApp;

public class LoadingController {
	private final String Mac = "A4-17-31-56-DC-41";
	@FXML
	Text txtState = new Text();
	@FXML
	ProgressBar pbar = new ProgressBar(0);
	@FXML
	ProgressIndicator pind = new ProgressIndicator(0);
	@FXML
	Button load = new Button();
	@FXML
	public void loadingBar(ActionEvent event)throws Exception{
		
        txtState.setFont(Font.font(12));
        txtState.setFill(Color.BLUE);
        //TextField fldSec = new TextField();
        //fldSec.setPromptText("Insert Seconds Here");
        
        //ProgressBar
        
        pbar.setPrefSize(400, 15);
        
        pbar.indeterminateProperty().addListener(new ChangeListener<Boolean>() {
        
           @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1){
                    txtState.setText("Calculating Time");
                }
                else{
                    txtState.setText("Loading");
                            
                }
            }
        });
        
        pbar.progressProperty().addListener(new ChangeListener<Number>() {

         
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                
                if(t1.doubleValue()==0.99){
                	txtState.setText("Starting");
                	
                }
                if(t1.doubleValue()==1){
                	new MainApp().loginPanel();
                	((Node) (event.getSource())).getScene().getWindow().hide();
                }
                
                 
                
            }

        });
        
        //PrograssIndicator
        
        
        pind.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

           @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if(t1){
                    txtState.setText("Calculating Time");
                }
                else{
                    txtState.setText("Loading...");
                            
                }
            }
        });
        if(!checkMacAddress(Mac)){
        	
          	 Alert alert = new Alert(AlertType.WARNING);
               alert.initOwner(new MainApp().getPrimaryStage());
               alert.setTitle("System Error!!!");
               alert.setHeaderText("");
               alert.setContentText("The Application can not run on this system");
              
               alert.showAndWait();
               
          	System.exit(0);
       }
        
        
       handle(pbar, pind);
       
        
       
    }
	
	public void handle(ProgressBar pbar, ProgressIndicator pind) {
        
//      
           Task<?> task = taskCreator(100);
           pbar.progressProperty().unbind();
           pbar.progressProperty().bind(task.progressProperty());
           pind.progressProperty().unbind();
           pind.progressProperty().bind(task.progressProperty());
           new Thread(task).start();
           
       }
   
    
	
	private Task<?> taskCreator(int seconds){
		return new Task<Object>() {

               @Override
               protected Object call() throws Exception {
                   for(int i=0; i<seconds;i++){
                    Thread.sleep(100);
                    updateProgress(i+1, seconds);
                   
                   }
                   return true;
               }
           };
	}
	
	public boolean checkMacAddress(String Mac){
		StringBuilder sb = new StringBuilder();
	    InetAddress ip;
	    try {

	        ip = InetAddress.getLocalHost();
	        System.out.println("Current IP address : " + ip.getHostAddress());

	        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

	        byte[] mac = network.getHardwareAddress();

	        for (int i = 0; i < mac.length; i++) {
	            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
	        }
	        System.out.println(sb.toString().trim()+"  "+Mac.equals(sb.toString().trim()));

	    } catch (UnknownHostException e) {

	        e.printStackTrace();

	    } catch (SocketException e){

	        e.printStackTrace();

	    }
	    return Mac.equals(sb.toString().trim());

	   }

	@FXML
	public void initialize() {
		System.out.println("In Initialize");
		// TODO Auto-generated method stub
		load.fire();
		load.setVisible(false);
	}

}
