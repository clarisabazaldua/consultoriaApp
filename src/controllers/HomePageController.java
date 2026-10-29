package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomePageController {

	@FXML
	private Label lblUpcomingPatient;

	@FXML
	private Label lblUpcomingDate;

	@FXML
	private ImageView imgLogo;

	Image logo = new Image("images/DrMBLogo.png");

	@FXML
	void initialize(){
		imgLogo.setImage(logo);
	
		updateUpcomingConsultationScene();

	}
	/** 
	 * Changes scene to Consultations Frequency Scene.
	 * @param event
	 */
	@FXML
	void mItemAppointmentF(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.consultationsFrequency);
	}
	/** 
	 * Changes scene to view Consultations Scene.
	 * @param event
	 */
	@FXML
	void mItemAppointmentsClick(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.viewConsultations);
	}

	/** 
	 * Changes scene to Referring Doctors Scene.
	 * @param event
	 */
	@FXML
	void mItemDoctorsReferedClick(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.referringDoctors);
	}
	/** 
	 * Changes scene to new Consultations Scene.
	 * @param event
	 */
	@FXML
	void mItemNewAppointmentClick(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.newConsultations);
	}
	
	/** 
	 * Changes scene to view Doctors Scene.
	 * @param event
	 */
	@FXML
	void mItemViewDoctorsClick(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.viewDoctors);
	}
	
	/** 
	 * Changes scene to new Patient Scene.
	 * @param event
	 */
	@FXML
	void mItemNewPatientClick(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.newPatient);
	}
	/** 
	 * Changes scene to view Patients Scene.
	 * @param event
	 */
	@FXML
	void mItemViewPatientsClick(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.viewPatients);
	}
	
	/** 
	 * Gets the closest consultation and display it.
	 * @param event
	 */
	public void updateUpcomingConsultationScene() {
		
		if(application.Main.consultationsAl != null) {
			lblUpcomingPatient.setText("Patient: " + application.Main.consultationsAl.getConsultation(0).getPatient().getName());
			String dateString = application.Main.consultationsAl.getConsultation(0).getConsultationDate() + "";
			String [] date = dateString.split("-");
			int monthnum = Integer.parseInt(date[1]);
			lblUpcomingDate.setText(application.Main.monthsOfTheYear[monthnum] +", " + date[2] +  ", "+ date[0] + ", " + application.Main.consultationsAl.getConsultation(0).getConsultationTime());
		}
		
		
	}

}
