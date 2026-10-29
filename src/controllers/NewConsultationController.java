package controllers;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import additionals.Consultation;
import additionals.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class NewConsultationController {

	@FXML
	private TableView<Patient> tablePatients;

	@FXML
	private TableColumn<Patient,String> colName;

	@FXML
	private TableColumn<Patient,String> conDiagnosis;

	@FXML
	private Label lblName;

	@FXML
	private DatePicker datePickerConsultationDate;

	@FXML
	private TextField txtConsultationTime;

	@FXML
	private ComboBox<String> cboHospital;

	@FXML
	private ImageView imgHospitals;

	@FXML 
	private TextField txtSearch;

	ObservableList<String> hospitals = FXCollections.observableArrayList();
	ObservableList<Patient> patientsOl;
	Image mugerza = new Image("images/mugerza.png");
	Image ginequito = new Image("images/Ginequito.webp");

	/** 
	 * Fills information in combo boxes and table view
	 * @param event
	 */
	public void initialize(){
		hospitals.addAll("Ginequito", "Christus Mugerza Alta Especialidad");
		patientsOl = FXCollections.observableArrayList();
		patientsOl.setAll(application.Main.patientsAl.getPatientAl());

		colName.setCellValueFactory(new PropertyValueFactory<Patient,String>("name"));
		conDiagnosis.setCellValueFactory(new PropertyValueFactory<Patient,String>("cDiagnosis"));
		tablePatients.setItems(patientsOl);
		tablePatients.refresh();
		cboHospital.setItems(hospitals);

	}
	/** 
	 * Clears the scene information boxes.
	 * @param event
	 */
	@FXML
	void btnCancelClicked(ActionEvent event) {
		clearTemplate();
	}
	/** 
	 * Creates a new consultation if all data was
	 * imputed correctly, and if not, it displays an error.
	 * @param event
	 */
	@FXML
	void btnCreateClicked(ActionEvent event) {

		Patient temp = tablePatients.getSelectionModel().getSelectedItem();


		if(temp !=  null && datePickerConsultationDate.getValue() != null && datePickerConsultationDate.getValue().isBefore(LocalDate.now()) == false  && txtConsultationTime.getText() != null && cboHospital.getValue() != null) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime time = LocalTime.parse(txtConsultationTime.getText(), formatter);

				String timestring =  time + "";
				String dateStirng = datePickerConsultationDate.getValue() + "";
				Consultation tempCon = new Consultation(tablePatients.getSelectionModel().getSelectedItem(), cboHospital.getValue(),timestring, dateStirng);
				application.Main.consultationsAl.addConsultation(tempCon);
				application.Main.updateConsultationsAlCsvFile();
				application.Main.addConsultationsHistoryAl(tempCon);
				application.Main.hp.updateUpcomingConsultationScene();
				application.Main.updateConsultationsHistoryAlCsvFile();
				application.Main.vC.updateConsultationsTableVC();
				application.Main.consultationsAl.sorting();
				TableViewSelectionModel<Patient> selectionModel = tablePatients.getSelectionModel();
				selectionModel.clearSelection();
				clearTemplate();

				//TESTING CONSULTATION LIST
				for(int i = 0; i < application.Main.consultationsAl.getConsultationsAl().size(); i++) {
					System.out.println(application.Main.consultationsAl.getConsultation(i));
				}
			} catch (DateTimeParseException e) {
				Alert timeFormatIncorrect = new Alert(AlertType.ERROR);
				timeFormatIncorrect.setTitle("Incorrect Format");
				timeFormatIncorrect.setHeaderText(null);
				timeFormatIncorrect.setContentText("Please type the appointment time in the correct format (HH:mm) ");
				timeFormatIncorrect.showAndWait();
			}
		}else if (datePickerConsultationDate.getValue().isBefore(LocalDate.now()) == true) {

			Alert pastConsulation = new Alert(AlertType.ERROR);
			pastConsulation.setTitle("Non-valid date");
			pastConsulation.setHeaderText(null);
			pastConsulation.setContentText("Please select a future date.");
			pastConsulation.showAndWait();

		}else {
			Alert incompleteInformation = new Alert(AlertType.ERROR);
			incompleteInformation.setTitle("Incomplete Information");
			incompleteInformation.setHeaderText(null);
			incompleteInformation.setContentText("Please fill all necessary information.");
			incompleteInformation.showAndWait();
		}
	}

	/** 
	 * Changes Scene to the Hme Page Scene. 
	 * @param event
	 */
	@FXML
	void mItemReturnToHomePageClicked(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.homePage);

	}
	/** 
	 * Looks for a patient in the Patients Table view
	 *@param event 
	 */
	@FXML
	void btnSearchOnClicked(ActionEvent event) {
		int index =	application.Main.patientsAl.binarySearchByName(txtSearch.getText());
		TableViewSelectionModel<Patient> selectionModel = tablePatients.getSelectionModel();
		if(index == -1) {
			Alert patientNotFound = new Alert(AlertType.ERROR);
			patientNotFound.setTitle("No Patient Found");
			patientNotFound.setHeaderText(null);
			patientNotFound.setContentText("This patient was not found, please try again or register a new patient.");
			patientNotFound.showAndWait();
		}else {
			selectionModel.select(index);
		}
		txtSearch.setText(null);
		

	}
	/** 
	 * Changes label "Name: " to the name of the patients selected
	 * @param event
	 */
	@FXML
	void tablePatientsOnMouseClicked(MouseEvent event) {
		lblName.setText("Name: " + tablePatients.getSelectionModel().getSelectedItem().getName());
	}
	
	/** 
	 * Refills the table View with an update version
	 * of the array List
	 * @param event
	 */
	public void updateNewConsultationTable(ArrayList<Patient> patientsAl) {
		patientsOl.setAll(patientsAl);
		tablePatients.setItems(patientsOl);
	}

	/** 
	 * Reset the Scene information leaving spaces
	 * in blank
	 * @param event
	 */
	public void clearTemplate() {
		lblName.setText("Name: ");
		datePickerConsultationDate.setValue(null);
		txtConsultationTime.setText(null);
		cboHospital.setValue(null);
	}

}
