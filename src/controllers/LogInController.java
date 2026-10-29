package controllers;

import java.io.BufferedReader;
import java.io.FileReader;

import additionals.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController {

	@FXML
	private TextField txtUsername;

	@FXML
	private Button btnLogIn;

	@FXML
	private Label lblWrongWarning;

	@FXML
	private PasswordField pswdPassword;

	int triesleft = 3;


	/** 
	 * Reads all CSV files 
	 * @param event
	 */
	@FXML
	void initialize(){
		lblWrongWarning.setVisible(false);	  

		application.Main.readDoctorsAlCsvFile();
		application.Main.readPatientsAlCsvFile();
		application.Main.readConsultationsAlCsvFile();
		application.Main.readConsultationsHistoryAlCsvFile();    
	}
	/** 
	 * Allows user to use de application if the data inputed 
	 * corresponds to the correct username and password
	 * @param event
	 */
	@FXML
	void btnLogInClick(ActionEvent event) {

		if (application.Main.currUser.validateUser(txtUsername.getText(), pswdPassword.getText())== false && triesleft != 0) {
			triesleft = triesleft -1;
			lblWrongWarning.setText("The user or the password are not correct. You have " + triesleft + " tries left."  );
			lblWrongWarning.setVisible(true);
		}else if (triesleft != 0 && application.Main.currUser.validateUser(txtUsername.getText(), pswdPassword.getText()) == true) {
			application.Main.mainstage.setScene(application.Main.homePage);
		}	

		if (triesleft == 0) {
			lblWrongWarning.setText("You have no tries left.");
		}	
	}
}

