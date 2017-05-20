package tech.sty.classdesk.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AttendanceController {
	
	@FXML
	private Label stdLabel;
	@FXML
	private Label batchLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private TextArea textArea;
	
	private @FXML Node root ;
	
	String std, batch, yesterday, today;
	@FXML
	private void initialize(){
		stdLabel.setText(PersonOverviewController.std);
		batchLabel.setText(PersonOverviewController.batch);
		Date date = new Date();
		//DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		today = new SimpleDateFormat("dd_MM_yyyy").format(date);
		//today = df.format(date);
		dateLabel.setText(today);
	}
	
	@FXML
	private void handleSubmit(){
		LinkedHashSet< String> hs1 = new LinkedHashSet<String>();

		String texts = textArea.getText();
		String[] ids = texts.split("\\n");
		for(String id : ids)
			hs1.add(id);
		try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/sakila","root","root"); 
    		
       		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

    	    Calendar calendar = Calendar.getInstance();
    	    calendar.add(Calendar.DATE, -1);
    	    Date lastDay = calendar.getTime();
    	    yesterday = new SimpleDateFormat("dd_MM_yyyy").format(lastDay);
    	    System.out.println(dateFormat.format(lastDay));
    		  
    		Statement stmt1 = con.createStatement();  
    		Statement stmt2 = con.createStatement(); 
    		Statement stmt3 = con.createStatement(); 
    		
    		//Statement stmt4 = con.createStatement(); 


    		ResultSet rs=stmt1.executeQuery("SELECT sakila.batch1.id FROM sakila.batch1");  
    		stmt2.executeUpdate("ALTER TABLE `sakila`.`temp` ADD COLUMN `"+today+"` VARCHAR(45) NULL After `id`");

    		
    		while(rs.next()){
    			
    			//stmt4.executeUpdate("INSERT INTO `sakila`.`temp` (`id`) VALUES ('"+rs.getInt(1)+"')");
    		
    			if(hs1.contains(Integer.toString(rs.getInt(1)))){
    				stmt3.executeUpdate("UPDATE `sakila`.`temp` SET `"+today+"`='Absent' WHERE `id`='"+rs.getInt(1)+"'");
    			}
    			else{
    				stmt3.executeUpdate("UPDATE `sakila`.`temp` SET `"+today+"`='Present' WHERE `id`='"+rs.getInt(1)+"'");

    			}
    		}  
    		con.close();  
    		  
    		}catch(Exception e){ System.out.println(e);}  
		root.getScene().getWindow().hide();

	}
	
}
