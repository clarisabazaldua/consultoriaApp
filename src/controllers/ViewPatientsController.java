package controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import additionals.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class ViewPatientsController {

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab tabTablePatients;

	@FXML
	private TableView<Patient> tablePatients;

	@FXML
	private TableColumn<Patient, String> colName;

	@FXML
	private TableColumn<Patient, String> colSex;

	@FXML
	private TableColumn<Patient, Integer> colAge;

	@FXML
	private TableColumn<Patient, String> colDiagnosis;

	@FXML
	private TableColumn<Patient, Doctor> colReference;

	@FXML
	private TextField txtSearchPatient;

	@FXML
	private Tab tabContactInfo;

	@FXML
	private TextField txtDocName;

	@FXML
	private TextField txtCellPhone;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextArea txtOccupation;

	@FXML
	private TextArea txtResidence;

	@FXML
	private DatePicker datePickerDateOfBirth;

	@FXML
	private ComboBox<String> cbMaritalStatus;

	@FXML
	private ComboBox<String> cbSex;

	@FXML
	private ComboBox<String> cbInsurance;

	@FXML
	private ComboBox<Doctor> cbReferringDoc;

	@FXML
	private Button btnAddDoc;

	@FXML
	private Label lblAge;
	
    @FXML
    private Label lblName;

	@FXML
	private Tab tabMedicalHistory;

	@FXML
	private TextField txtHeight;

	@FXML
	private TextField txtWeight;

	@FXML
	private TextArea txtAlergies;

	@FXML
	private TextArea txtCdiagnosis;

	@FXML
	private TextArea txtcPrescription;

	@FXML
	private Label lblBMI;

	@FXML
	private Tab tabNota;

	@FXML
	private TextArea txtNota;

	boolean plusActivated = true;
	ObservableList<Patient> patientsOl;
	ObservableList<Doctor> ReferredDoctorOL;
	
	/** 
	 * Fills up Patients TableView with 
	 * patientsAl
	 * @param event
	 */
	public void initialize(){
		setPlusDoctor(false);
		application.Main.nP.fillcbo(cbInsurance,cbSex,cbMaritalStatus,cbReferringDoc);
		patientsOl = FXCollections.observableArrayList();
		ReferredDoctorOL = FXCollections.observableArrayList();
		patientsOl.setAll(application.Main.patientsAl.getPatientAl());

		colName.setCellValueFactory(new PropertyValueFactory<Patient,String>("name"));
		colSex.setCellValueFactory(new PropertyValueFactory<Patient,String>("sex"));
		colAge.setCellValueFactory(new PropertyValueFactory<Patient,Integer>("age"));
		colDiagnosis.setCellValueFactory(new PropertyValueFactory<Patient,String>("cDiagnosis"));
		colReference.setCellValueFactory(new PropertyValueFactory<Patient,Doctor>("DocReferred"));
		tablePatients.setItems(patientsOl);
		tablePatients.refresh();

	}
	/** 
	 * Fills information of selected patient 
	 * by the user. 
	 * @param event
	 */
	@FXML
	void OnMouseClickedTablePatients(MouseEvent event) {
		Patient temp = tablePatients.getSelectionModel().getSelectedItem();
		setTemplate(temp);
	}
	/** 
	 * Adds doctor to the doctors array list
	 * using the doctors name
	 * @param event
	 */
	@FXML
	void btnAddDocOnClick(ActionEvent event) {
		if (txtDocName.getText() != null) {
			Alert confirmAddDoctor = new Alert(AlertType.CONFIRMATION);
			confirmAddDoctor.setTitle("CONFIRM ADDITION");
			confirmAddDoctor.setHeaderText(null);
			confirmAddDoctor.setContentText("Are you sure you want to add a new doctor?\nPlease make sure to not add already existing doctors");
			confirmAddDoctor.showAndWait();
			if(confirmAddDoctor.getResult() == ButtonType.OK) {
				if (application.Main.doctorsAl.findDoctorBinary(txtDocName.getText()) == -1) {
					application.Main.doctorsAl.addDoctor(txtDocName.getText());

					updateReferringDocCboxvP(application.Main.doctorsAl.getDocsAl());
					application.Main.nP.updateReferringDoctorCboxNp(application.Main.doctorsAl.getDocsAl());
					application.Main.vD.updateViewDoctorsTable(application.Main.doctorsAl.getDocsAl());
					
					//Update csv File
					application.Main.updateDoctorsAlCsvFile();
					

					setPlusDoctor(false);
					plusActivated= true;
				} else if (application.Main.doctorsAl.findDoctorBinary(txtDocName.getText()) != -1) {
					Alert nullAddDoctor = new Alert (AlertType.ERROR);
					nullAddDoctor.setHeaderText(null);
					nullAddDoctor.setTitle("ERROR!");
					nullAddDoctor.setContentText("This doctor already exists");
					nullAddDoctor.show();
					txtDocName.setText(null);
				}
				//Testing
				for(int i = 0; i < application.Main.doctorsAl.getDocsAl().size(); i++) {
					System.out.println(application.Main.doctorsAl.getDocsAl().get(i).getName());
				}

				System.out.println();
			}
		} else {
			Alert nullAddDoctor = new Alert (AlertType.ERROR);
			nullAddDoctor.setHeaderText(null);
			nullAddDoctor.setTitle("EMPTY SLOT!");
			nullAddDoctor.setContentText("Please type the name of the doctor.");
			nullAddDoctor.show();
		}

	}
	/** 
	 * Clears up the information of a patient in
	 * the scene.
	 * @param event
	 */
	@FXML
	void btnCancelOnClick(ActionEvent event) {
		TableViewSelectionModel<Patient> selectionModel = tablePatients.getSelectionModel();
		clearTemplate();
		tabPane.getSelectionModel().select(tabTablePatients);
		selectionModel.clearSelection();
	}
	/** 
	 * Deletes doctors from the patientAl and updated all TableView that uses
	 * this ArrayList.
	 * @param event
	 */
	@FXML
	void btnDeleteOnClick(ActionEvent event) {

		Patient temp = tablePatients.getSelectionModel().getSelectedItem();

		if (temp != null) {
			Alert deletionConfirmation = new Alert (AlertType.CONFIRMATION);
			deletionConfirmation.setTitle("CONFIRMATION");
			deletionConfirmation.setHeaderText(null);
			deletionConfirmation.setContentText("Are you sure yo want to delete this patient?");
			deletionConfirmation.showAndWait();
			if (deletionConfirmation.getResult() == ButtonType.OK) {
				application.Main.patientsAl.getPatientAl().remove(temp);
				application.Main.updatePatientsAlCsvFile();
				updateNewPatientTable(application.Main.patientsAl.getPatientAl());
				clearTemplate();
				tabPane.getSelectionModel().select(tabTablePatients);
				
			}
		} else {
			Alert noPatientFoundAlert = new Alert(AlertType.INFORMATION);
			noPatientFoundAlert.setTitle("No Patient Found");
			noPatientFoundAlert.setHeaderText(null);
			noPatientFoundAlert.setContentText("Please select a patient");
			noPatientFoundAlert.showAndWait();
		}
	}

	/** 
	 * Edits the selected doctor in the patientAl and updates all TableViews that uses
	 * this ArrayList.
	 * @param event
	 */
	@FXML
	void btnEditOnClick(ActionEvent event) {
		int position = tablePatients.getSelectionModel().getSelectedIndex();

		if(position != -1) {
			Patient x = getPatientData();
			if (application.Main.patientsAl.binarySearchByName(x.getName()) != -1 ) {													
				application.Main.patientsAl.getPatientAl().set(position, x);
				
				// Updating CSV file and tables across the program.
				application.Main.updatePatientsAlCsvFile();
				updateNewPatientTable(application.Main.patientsAl.getPatientAl());
				application.Main.nC.updateNewConsultationTable(application.Main.patientsAl.getPatientAl());
				clearTemplate();
				tabPane.getSelectionModel().select(tabTablePatients);
			} 
		}
		TableViewSelectionModel<Patient> selectionModel = tablePatients.getSelectionModel();
		selectionModel.clearSelection();

	}
	/** 
	 * Hides or Shows the section in which the
	 * user can add a doctor.
	 * @param event
	 */
	@FXML
	void btnPlusReferringDocOnClicked(ActionEvent event) {
		setPlusDoctor(true);
		if (plusActivated == true) {
			setPlusDoctor(true);
			plusActivated = false;
		} else if (plusActivated == false) {
			setPlusDoctor(false);
			plusActivated = true;
		}

	}
	/** 
	 * Changes scene to Home Page Scene
	 * @param event
	 */
	@FXML
	void mItemReturntoHomePageClick(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.homePage);
	}
	/** 
	 * Looks for a patient in the Patients
	 * TableView.
	 * @param event
	 */
	@FXML
	void btnSearchClicked(ActionEvent event) {
		int index =	application.Main.patientsAl.binarySearchByName(txtSearchPatient.getText());
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
		
	}
	
	/** 
	 * Edits the selected doctor in the patientAl and updates all TableViews that uses
	 * this ArrayList.
	 * @param event
	 */
	@FXML
	public void btnCreateFileOnClick(ActionEvent event) {
		LocalDate today = LocalDate.now();
		String todaystring = today + "";
		Patient temp = tablePatients.getSelectionModel().getSelectedItem();
		String name  = temp.getName().replace(" ", "_");
		if (temp != null) {

			try {
				PrintWriter writer = new PrintWriter (new FileWriter("src/PatientsFiles/" + name + "_" + todaystring + ".txt",false));
				writer.println(todaystring);
				writer.println(temp.printWithLabels());
				writer.close();

				Alert confirmation = new Alert(AlertType.CONFIRMATION);
				confirmation.setTitle("File created!");
				confirmation.setHeaderText(null);
				confirmation.setContentText("File succesfully created! (" +  name + "_" + todaystring + ".txt");
				confirmation.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}
	/** 
	 * Hides or Shows the section in which the user can 
	 * add a new doctor.
	 * @param event
	 */
	public void setPlusDoctor (boolean on) {
		txtDocName.setVisible(on);
		txtDocName.setText(null);
		btnAddDoc.setVisible(on);
	}
	/** 
	 * Clears scene from the information of a selected
	 * patient.
	 * @param event
	 */
	public void clearTemplate() {
		lblName.setText("Name: ");
		datePickerDateOfBirth.setValue(null);
		cbSex.setValue(null);
		cbMaritalStatus.setValue(null);
		cbInsurance.setValue(null);
		cbReferringDoc.setValue(null);
		txtEmail.setText(null);
		txtCellPhone.setText(null);
		txtResidence.setText(null);
		txtHeight.setText(null);
		txtWeight.setText(null);
		txtAlergies.setText(null);
		txtNota.setText(null);
		txtCdiagnosis.setText(null);
		txtcPrescription.setText(null);
		txtOccupation.setText(null);
	}
	
	/** 
	 * Updates Referring doctors Combo box with a 
	 * doctors arrayList
	 * @param event
	 */
	public void updateReferringDocCboxvP(ArrayList<Doctor> ReferringDocAl){
		ReferredDoctorOL.setAll(ReferringDocAl);
		cbReferringDoc.setItems(ReferredDoctorOL);
	}
	/** 
	 * Updates Patients TableView with a 
	 * patients arrayList
	 * @param event
	 */
	public void updateNewPatientTable (ArrayList<Patient> patientsAl) {
		patientsOl.setAll(patientsAl);
		tablePatients.setItems(patientsOl);
	}
	/** 
	 * Get patient data using the information 
	 * inputed by the user
	 * @param event
	 */
	public Patient getPatientData() {
		Patient x = tablePatients.getSelectionModel().getSelectedItem();
		
		String name = x.getName(); //The only characteristic that cannot be changed.
		LocalDate dateOfBirth = datePickerDateOfBirth.getValue();
		String maritalStatus= cbMaritalStatus.getValue();
		String sex = cbSex.getValue();
		String insurance = cbInsurance.getValue();
		String doctor = cbReferringDoc.getValue().getName();
		String email = txtEmail.getText();
		String phone = txtCellPhone.getText();
		String residence = txtResidence.getText();
		int height = Integer.parseInt(txtHeight.getText());
		double weight = Double.parseDouble(txtWeight.getText());
		String alergies = txtAlergies.getText();
		String nota = txtNota.getText();
		String diagnosis = txtCdiagnosis.getText();
		String prescription = txtcPrescription.getText();
		String occupation = txtOccupation.getText();
		Patient temp = new Patient(name, dateOfBirth,maritalStatus,sex,insurance,doctor,email,phone,residence,height,weight,alergies,nota,occupation);
		if(diagnosis != null) {
			temp.setCDiagnosis(diagnosis);
		}
		if(prescription != null) {
			temp.setcPrescription(prescription);
		}
		return temp;
	}
	/** 
	 * Finds the a patient using its name 
	 * in the Patient TableView
	 * @param event
	 */
	
	/*
	public int findPatientByName (Patient x) {
		application.Main.patientsAl.selectionSortByName();
		int start = 0;
		int end = application.Main.patientsAl.getPatientAl().size()-1; 
		int mid;

		while (start <= end ) {
			mid = (start + end) /2;
			if (x.getName().compareTo(application.Main.patientsAl.getPatient(mid).getName()) == 0) {
				return mid;
			}
			if (x.getName().compareTo(application.Main.patientsAl.getPatient(mid).getName()) < 0) {
				end = mid - 1;
			}else {
				start = mid + 1;  
			}
		}
		return -1;
	}
	*/
	

	/** 
	 * Fills up the Scene boxes with a patient's 
	 * information
	 * @param event
	 */
	public void setTemplate(Patient temp) {
		if(temp != null) {
			lblName.setText("Name: " + temp.getName());
			datePickerDateOfBirth.setValue(temp.getDateOfbirth());
			cbSex.setValue(temp.getSex());
			cbMaritalStatus.setValue(temp.getMaritalStatus());
			cbInsurance.setValue(temp.getInsuranceCompany());
			Doctor tempdoc = new Doctor(temp.getDocReferred());
			cbReferringDoc.setValue(tempdoc);
			txtEmail.setText(temp.getEmail());
			txtCellPhone.setText(temp.getPhone());
			txtResidence.setText(temp.getResidence());
			txtHeight.setText(temp.getHeight()+"");
			txtWeight.setText(temp.getWeight()+"");
			txtAlergies.setText(temp.getAlergies());
			txtNota.setText(temp.getNotas());
			txtCdiagnosis.setText(temp.getCDiagnosis());
			txtcPrescription.setText(temp.getcPrescription());
			txtOccupation.setText(temp.getOccupation());
			tabPane.getSelectionModel().select(tabContactInfo);
			lblAge.setText("Age: " + temp.getAge());
			lblBMI.setText("BMI: " + temp.getBMI());
		}
	}
}
