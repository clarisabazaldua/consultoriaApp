package controllers;

import java.util.ArrayList;

import additionals.Doctor;
import additionals.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ViewDoctorsController {

	@FXML
	private TableView<Doctor> tableDoctors;

	@FXML
	private TableColumn<Doctor,String > colName;

	@FXML
	private TableColumn<Doctor, String> colDocNum;

	@FXML
	private TextField txtSearchDoctor;

	@FXML
	private TextField txtDocNum;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtPhone;

	@FXML
	private Label lblName;

	ObservableList<Doctor> doctorsOL;
	/** 
	 * Fills Doctors table view with information 
	 * consultationsAl 
	 * @param event
	 */
	public void initialize() {
		doctorsOL = FXCollections.observableArrayList();   
		doctorsOL.addAll(application.Main.doctorsAl.getDocsAl());
		colName.setCellValueFactory(new PropertyValueFactory<Doctor,String>("name"));
		colDocNum.setCellValueFactory(new PropertyValueFactory<Doctor,String>("docnum"));
		tableDoctors.setItems(doctorsOL);
		tableDoctors.refresh();
	}

	/** 
	 * Deletes a doctor from the 
	 * doctorsAl using a confirmation.
	 * @param event
	 */
	@FXML
	void btnDeleteClicked(ActionEvent event) {
		Doctor temp = tableDoctors.getSelectionModel().getSelectedItem();
		if (temp != null) {
			Alert deletionConfirmation = new Alert (AlertType.CONFIRMATION);
			deletionConfirmation.setTitle("CONFIRMATION");
			deletionConfirmation.setHeaderText(null);
			deletionConfirmation.setContentText("Are you sure yo want to delete this Doctor?");
			deletionConfirmation.showAndWait();
			if( deletionConfirmation.getResult() == ButtonType.OK) {
				application.Main.doctorsAl.getDocsAl().remove(temp);
				doctorsOL.setAll(application.Main.doctorsAl.getDocsAl());
				application.Main.updateDoctorsAlCsvFile();
				application.Main.vP.updateReferringDocCboxvP(application.Main.doctorsAl.getDocsAl());
				application.Main.nP.updateReferringDoctorCboxNp(application.Main.doctorsAl.getDocsAl());
				updateViewDoctorsTable(application.Main.doctorsAl.getDocsAl());
				clearTemplate();
			}
		}
	}
	/** 
	 * Edits information from a selected doctor 
	 * @param event
	 */
	@FXML
	void btnEditClicked(ActionEvent event) {
		int index = tableDoctors.getSelectionModel().getSelectedIndex();
		System.out.println("Si es el boton");
		if (index != -1) {
			System.out.println("Si llego parte");
			Doctor temp = getDoctorData();
			System.out.println(application.Main.doctorsAl.findDocSeq(temp));
			System.out.println(temp);
			if(application.Main.doctorsAl.findDoctorBinary(temp) != -1) {
				application.Main.doctorsAl.getDocsAl().set(index, temp);
				application.Main.updateDoctorsAlCsvFile();

				// Updating DocsOL combo Boxes from nP and vP
				//doctorsOL.setAll(application.Main.doctorsAl.getDocsAl());
				application.Main.nP.updateReferringDoctorCboxNp(application.Main.doctorsAl.getDocsAl());
				application.Main.vP.updateReferringDocCboxvP(application.Main.doctorsAl.getDocsAl());
				updateViewDoctorsTable(application.Main.doctorsAl.getDocsAl());
				clearTemplate();
			//	System.out.println("Si llego");
			}
		}
	}
	/** 
	 * Looks for a doctor using its name 
	 * in the doctors TableView
	 * @param event
	 */
	@FXML
	void btnSearchClicked(ActionEvent event) {
		int index = application.Main.doctorsAl.findDoctorBinary(txtSearchDoctor.getText());
		TableViewSelectionModel<Doctor> selectionModel = tableDoctors.getSelectionModel();
		if (index == -1) {
			Alert patientNotFound = new Alert(AlertType.ERROR);
			patientNotFound.setTitle("No Patient Found");
			patientNotFound.setHeaderText(null);
			patientNotFound.setContentText("This patient was not found, please try again or register a new patient.");
			patientNotFound.showAndWait();
		}else {
			selectionModel.select(index);
		}

	}
	/** 
	 * Changes Scene to Home Page Scene
	 * @param event
	 */
	@FXML
	void mItemReturnToHomePageClickec(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.homePage);
	}
	
	/** 
	 * Selects a given doctor and fills up boxes in the 
	 * scene with the selected doctor 
	 * @param event
	 */
	@FXML
	void OnMouseClicked(MouseEvent event) {
		Doctor temp = tableDoctors.getSelectionModel().getSelectedItem();
		//System.out.println(temp);
		setInfo(temp);

	}
	/** 
	 * Fills up the information of a doctor 
	 * in the scene.
	 * @param event
	 */
	public void setInfo(Doctor doctor) {
		lblName.setText("Name: " + doctor.getName());
		txtEmail.setText(doctor.getEmail());
		txtDocNum.setText(doctor.getDocnum());
		txtPhone.setText(doctor.getPhone());
	}

	/** 
	 * Collects all doctor's data inputed in the scene
	 * by the user.
	 * @param event
	 */
	public Doctor getDoctorData() {
		int index = tableDoctors.getSelectionModel().getSelectedIndex();
		Doctor temp = new Doctor(application.Main.doctorsAl.getDoctor(index).getName());
		if (!txtDocNum.getText().equals("") && !txtDocNum.getText().equals(null)) {
			String docnum = txtDocNum.getText();
			temp.setDocnum(docnum);
		}
		if (!txtPhone.getText().equals("") && !txtPhone.getText().equals(null)) {
			String phone = txtPhone.getText();
			temp.setPhone(phone);
		}
		if (!txtEmail.getText().equals("") && !txtEmail.getText().equals(null)) {
			String email = txtEmail.getText();
			temp.setEmail(email);
		}

		return temp;
	}
	
	/** 
	 * Clears up information about a doctor
	 * off the scene. 
	 * @param event
	 */
	public void clearTemplate() {
		lblName.setText("Name: ");
		txtPhone.setText(null);
		txtEmail.setText(null);
		txtDocNum.setText(null);
	}
	/** 
	 * Refills the TableView with the 
	 * doctorsAl
	 * @param event
	 */
	public void updateViewDoctorsTable (ArrayList<Doctor> doctorAl) {
		doctorsOL.setAll(doctorAl);
		tableDoctors.setItems(doctorsOL);
	}
}
