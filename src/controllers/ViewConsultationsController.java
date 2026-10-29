package controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import additionals.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ViewConsultationsController {

	@FXML
	private TextField txtSearchName;

	@FXML
	private TableView<Consultation> tableConsultations;

	@FXML
	private TableColumn<Consultation, String> colName;

	@FXML
	private TableColumn<Consultation, String> colDate;

	@FXML
	private TableColumn<Consultation, String>colTime;

	@FXML
	private TableColumn<Consultation, String> colHospital;

	@FXML
	private DatePicker dpDateSearch;

	@FXML
	private Label lblName;

	@FXML
	private TextField txtTime;

	@FXML
	private ComboBox<String> cboHospital;

	@FXML
	private DatePicker datePickerDate;

	ObservableList<Consultation> consultationsOl;
	/** 
	 * Fills table view with information 
	 * consultationsAl 
	 * @param event
	 */
	public void initialize() {
		consultationsOl = FXCollections.observableArrayList();  
		consultationsOl.addAll(application.Main.consultationsAl.getConsultationsAl());
		colName.setCellValueFactory(new PropertyValueFactory<Consultation,String>("name"));
		colDate.setCellValueFactory(new PropertyValueFactory<Consultation,String>("consultationDate"));
		colTime.setCellValueFactory(new PropertyValueFactory<Consultation,String>("consultationTime"));
		colHospital.setCellValueFactory(new PropertyValueFactory<Consultation,String>("hospital"));
cboHospital.getItems().setAll("Ginequito", "Chrisuts Mugerza Alta Especialidad");

tableConsultations.setItems(consultationsOl);
		tableConsultations.refresh();

	}
	/** 
	 * Deletes a consultation form the Consultation ArrayList
	 * and from updates all require information. 
	 * @param event
	 */
	@FXML
	void btnDelete(ActionEvent event) {
		Consultation temp = tableConsultations.getSelectionModel().getSelectedItem();
		if (temp != null) {
			Alert deletionConfirmation = new Alert (AlertType.CONFIRMATION);
			deletionConfirmation.setTitle("CONFIRMATION");
			deletionConfirmation.setHeaderText(null);
			deletionConfirmation.setContentText("Are you sure yo want to delete this Consultation?");
			deletionConfirmation.showAndWait();
			if( deletionConfirmation.getResult() == ButtonType.OK) {
				application.Main.consultationsAl.getConsultationsAl().remove(temp);
				//consultationsOl.setAll(application.Main.consultationsAl.getConsultationsAl());
				updateConsultationsTableVC();
				application.Main.updateConsultationsAlCsvFile();


				clearTemplate();
			}
		}
	}
	/** 
	 * looks for a consultation on the tableview
	 * 
	 * @param event
	 */
	@FXML
	void btnSearch(ActionEvent event) {
		int index = application.Main.consultationsAl.LinearSearchByNameAndDate(txtSearchName.getText(),dpDateSearch.getValue());
		TableViewSelectionModel<Consultation> selectionModel = tableConsultations.getSelectionModel();
		if (index == -1) {
			Alert patientNotFound = new Alert(AlertType.ERROR);
			patientNotFound.setTitle("No Patient Found");
			patientNotFound.setHeaderText(null);
			patientNotFound.setContentText("This patient was not found, please try again or schedule another consultation.");
			patientNotFound.showAndWait();
		}else {
			selectionModel.select(index);
			System.out.println(application.Main.consultationsAl.getConsultation(index));
		}
		txtSearchName.setText(null);
	}
	/** 
	 * Updates the information of a consultation. 
	 * @param event
	 */
	@FXML
	void btnUpdate(ActionEvent event) {
		int index = tableConsultations.getSelectionModel().getSelectedIndex();
		if (index != 1) {
			Consultation temp = getConsultationInfo();
			application.Main.consultationsAl.getConsultationsAl().set(index, temp);
			application.Main.updateConsultationsAlCsvFile();
			application.Main.updateConsultationsHistoryAlCsvFile();
			application.Main.addConsultationsHistoryAl(temp);
			
			updateConsultationsTableVC();
			clearTemplate();
		}
	}
	/** 
	 * Change scene to the Home Page Scene
	 * @param event
	 */
	@FXML
	void mItemReturnToHomePageClicked(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.homePage);
	}
	
	/** 
	 * Displays the information of the selected
	 * consultation
	 * @param event
	 */
	@FXML
	void tableConsultationsOnMouseClicked(MouseEvent event) {
		Consultation temp = tableConsultations.getSelectionModel().getSelectedItem();
		setInfo(temp);
		System.out.println(temp);
	}
	
	/** 
	 * Fills boxes in the scene with the consultation's 
	 * information
	 * @param event
	 */
	public void setInfo(Consultation c) {
		lblName.setText(c.getName());
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(c.getConsultationDate(), formatterDate);
		datePickerDate.setValue(date);
		txtTime.setText(c.getConsultationTime());
		cboHospital.setValue(c.getHospital());
	}
	
	/** 
	 * Refills the table view with 
	 * the consulationsAl.
	 * @param event
	 */
	public void updateConsultationsTableVC() {
		consultationsOl.setAll(application.Main.consultationsAl.getConsultationsAl());
		tableConsultations.setItems(consultationsOl);
	}
	/** 
	 * Clears information in the Scene
	 * @param event
	 */
	public void clearTemplate() {
lblName.setText("Name: ");
txtTime.setText(null);
cboHospital.setValue(null);
datePickerDate.setValue(null);
	}
	/** 
	 * Gets information inputed by the user
	 * in the scenes boxes
	 * @param event
	 */
	public Consultation getConsultationInfo() {
		Consultation temp = tableConsultations.getSelectionModel().getSelectedItem();
		String date = datePickerDate.getValue() + "";
		Consultation consul = new Consultation (temp.getPatient(), cboHospital.getValue(), txtTime.getText(), date );
		return consul;
	}


}
