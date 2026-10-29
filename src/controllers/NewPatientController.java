package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import additionals.Doctor;
import additionals.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class NewPatientController {

	@FXML
	private TextField txtName;

	@FXML
	private DatePicker datePickDateOfBirth;

	@FXML
	private ComboBox<String> cBoSex;

	@FXML
	private ComboBox<String> cboMaritalStatus;

	@FXML
	private ComboBox<String> cboInsuranceCompany;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtCellPhone;

	@FXML
	private TextArea txtResidence;

	@FXML
	private ComboBox<Doctor> cboReferredDoc;

	@FXML
	private Button btnPlusDoctors;


	@FXML
	private Button btnAddDoctor;

	@FXML
	private TextField txtAddDoctor;

	@FXML
	private TextField txtHeight;

	@FXML
	private TextField txtWeight;

	@FXML
	private TextArea txtAlergies;

	@FXML
	private TextArea txtPastMedicalProc;

	@FXML
	private TextArea txtDiagnosis;

	@FXML
	private TextArea txtPrescription;

	@FXML
	private TextArea txtOccupation;

	boolean plusActivated = true;

	ObservableList<Doctor> ReferredDoctorOL;


	/** 
	 * Fill combo boxes with its necessary information.
	 * @param event
	 */
	public void initialize() {
		setPlusDoctor(false);
		fillcbo(cboInsuranceCompany,cBoSex,cboMaritalStatus,cboReferredDoc);
		ReferredDoctorOL = FXCollections.observableArrayList();
	}

	/** 
	 * Displays and Hides the section in where the 
	 * user can add a new doctor. 
	 * @param event
	 */
	@FXML
	void btnPlusDoctorsClick(ActionEvent event) {
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
	 * Adds a doctor only if the correct information is inputed, 
	 * and if the doctor does not exist still, and if not, displays an error. 
	 * @param event
	 */
	@FXML
	void btnAddDoctorClick(ActionEvent event) {	
		if (txtAddDoctor.getText() != null) {
			Alert confirmAddDoctor = new Alert(AlertType.CONFIRMATION);
			confirmAddDoctor.setTitle("CONFIRM ADDITION");
			confirmAddDoctor.setHeaderText(null);
			confirmAddDoctor.setContentText("Are you sure you want to add a new doctor?\nPlease make sure to not add already existing doctors");
			confirmAddDoctor.showAndWait();
			if(confirmAddDoctor.getResult() == ButtonType.OK) {
				if (application.Main.doctorsAl.findDoctorBinary(txtAddDoctor.getText()) == -1) {
					application.Main.doctorsAl.addDoctor(txtAddDoctor.getText());
					//	ReferredDoctorOL.addAll(application.Main.doctorsAl.getDocsAl());		Checar methodo despues. 
					//	cboReferredDoc.setItems(ReferredDoctorOL);	
					application.Main.vP.updateReferringDocCboxvP(application.Main.doctorsAl.getDocsAl());
					updateReferringDoctorCboxNp(application.Main.doctorsAl.getDocsAl());
					application.Main.vD.updateViewDoctorsTable(application.Main.doctorsAl.getDocsAl());
					//Updating csv Information
					application.Main.updateDoctorsAlCsvFile();

					setPlusDoctor(false);
					plusActivated= true;
				} else if (application.Main.doctorsAl.findDoctorBinary(txtAddDoctor.getText()) != -1) {
					Alert nullAddDoctor = new Alert (AlertType.ERROR);
					nullAddDoctor.setHeaderText(null);
					nullAddDoctor.setTitle("ERROR!");
					nullAddDoctor.setContentText("This doctor already exists");
					nullAddDoctor.show();
					txtAddDoctor.setText(null);
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
	 * Adds A new patient and updates necessary table views and csv files 
	 * if all data necessary is correctly 
	 * inputed, and if not, displays its specific error.
	 * @param event
	 */
	@FXML
	void btnAddNewPatientClick(ActionEvent event) {
		//Required information filter
		if (txtName.getText() != null && 
				datePickDateOfBirth.getValue() != null && 
				cBoSex.getValue() != null && 
				cboMaritalStatus.getValue() != null && 
				cboInsuranceCompany.getValue() != null &&
				txtEmail.getText() != null &&
				txtCellPhone.getText() != null &&
				txtResidence.getText() != null && 
				cboReferredDoc.getValue() != null && 
				!txtHeight.getText().equals(null) && 
				!txtWeight.getText().equals(null) && 
				!txtAlergies.getText().equals(null) && 
				!txtPastMedicalProc.getText().equals(null)) {
			try { //Data type filter/Checker

				int tempHeight = Integer.parseInt(txtHeight.getText());
				double tempWeight = Double.parseDouble(txtWeight.getText());

				Patient tempPatient = new Patient(txtName.getText(), 
						datePickDateOfBirth.getValue(),
						cboMaritalStatus.getValue(),
						cBoSex.getValue(),
						cboInsuranceCompany.getValue(),
						cboReferredDoc.getValue().getName(),
						txtEmail.getText(),txtCellPhone.getText(),
						txtResidence.getText(),
						tempHeight,
						tempWeight,
						txtAlergies.getText(),
						txtPastMedicalProc.getText(), 
						txtOccupation.getText());


				//Addition of optional information.
				if (txtDiagnosis.getText() != null) {
					tempPatient.setCDiagnosis(txtDiagnosis.getText());
				}

				if (txtPrescription.getText() != null) {
					tempPatient.setcPrescription(txtPrescription.getText());
				}

				//Adding new patient to ArrayLists
				application.Main.patientsAl.addPatient(tempPatient);

				//Updating Tables 
				application.Main.vP.updateNewPatientTable(application.Main.patientsAl.getPatientAl());
				application.Main.nC.updateNewConsultationTable(application.Main.patientsAl.getPatientAl());

				//File Writer.
				application.Main.updatePatientsAlCsvFile();

				Alert patientCreated = new Alert (AlertType.INFORMATION);
				patientCreated.setHeaderText(null);
				patientCreated.setTitle("Patient Created!");
				patientCreated.setContentText("New Patient Succesfully Created!");
				patientCreated.show();

				clearTemplate();



			}catch (Exception e) { //Detects wrong datatype input in both Height and Weight
				Alert notNum = new Alert (AlertType.ERROR);
				notNum.setHeaderText(null);
				notNum.setTitle("ERROR!");
				notNum.setContentText("Please input only numbers in 'Height' and 'Weight' section. ");
				notNum.show();
			}

		}else { // Required information filter
			Alert notRequieredInfo = new Alert(AlertType.ERROR);
			notRequieredInfo.setHeaderText(null);
			notRequieredInfo.setTitle("ERROR!");
			notRequieredInfo.setContentText("Please fill all the required information");
			notRequieredInfo.show();
		}
	}
	/** 
	 * Clears all inputed information by the user
	 * on the scene. 
	 * @param event
	 */
	@FXML
	void btnCancelNewPatientClick(ActionEvent event) {
		Alert confirmCancel = new Alert(AlertType.CONFIRMATION);
		confirmCancel.setTitle("CONFIRM DELETION");
		confirmCancel.setHeaderText(null);
		confirmCancel.setContentText("Are you sure you want to delete all inputed information?");
		confirmCancel.showAndWait();

		if(confirmCancel.getResult() == ButtonType.OK) {
			clearTemplate();
		}

	}

	/** 
	 * Changes scene to the Home Page Scene
	 * @param event
	 */
	@FXML
	void mItemReturn(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.homePage);
	}

	/** 
	 * Displays and hide the section in which
	 * the user can add a new doctor
	 * @param event
	 */
	public void setPlusDoctor (boolean on) {
		txtAddDoctor.setVisible(on);
		txtAddDoctor.setText(null);
		btnAddDoctor.setVisible(on);
	}
	/** 
	 * Clears all information inputed by the user.
	 * @param event
	 */
	public void clearTemplate() {
		txtName.setText(null);
		datePickDateOfBirth.setValue(null);
		cBoSex.setValue(null);
		cboMaritalStatus.setValue(null);
		cboInsuranceCompany.setValue(null);
		cboReferredDoc.setValue(null);
		txtEmail.setText(null);
		txtCellPhone.setText(null);
		txtResidence.setText(null);
		txtHeight.setText(null);
		txtWeight.setText(null);
		txtAlergies.setText(null);
		txtPastMedicalProc.setText(null);
		txtDiagnosis.setText(null);
		txtPrescription.setText(null);
		txtOccupation.setText(null);
	}
	/** 
	 * Fills up combo boxes with their 
	 * corresponding information. 
	 * @param event
	 */
	public void fillcbo(ComboBox<String> cboinsurance, ComboBox<String> cboSex, ComboBox<String> cboMaritalStatus, ComboBox <Doctor> cboDoctors) {
		ArrayList<String> insuranceCAL = new ArrayList<String>();
		ObservableList<String> insuranceCOL = FXCollections.observableArrayList();
		ObservableList<Doctor> ReferredDoctorOL = FXCollections.observableArrayList();

		try {
			BufferedReader reader = new BufferedReader(new FileReader ("src/txtFiles/InsuranceCompaniesMx.txt"));
			String line = reader.readLine();
			while(line != null) {
				insuranceCAL.add(line);
				line = reader.readLine();
			}
			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		insuranceCOL.addAll(insuranceCAL);
		ReferredDoctorOL.addAll(application.Main.doctorsAl.getDocsAl());
		cboinsurance.setItems(insuranceCOL);
		cboDoctors.setItems(ReferredDoctorOL);
		cboSex.getItems().addAll("Female","Male");
		cboMaritalStatus.getItems().addAll("Married", "Single", "Divorced", "Common Law marriage", "Widow/Widower");

	}
	/** 
	 * Refills the doctors Combo Box with an
	 * updated version of the arrayList
	 * @param event
	 */
	public void updateReferringDoctorCboxNp(ArrayList<Doctor> ReferringDocAl) {

		ReferredDoctorOL.setAll(ReferringDocAl);
		cboReferredDoc.setItems(ReferredDoctorOL);
	}

}
