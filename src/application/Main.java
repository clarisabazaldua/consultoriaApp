package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import additionals.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {

	//Stage
	public static Stage mainstage;

	//Scenes
	public static Scene logIn;
	public static Scene homePage;  
	public static Scene newPatient;
	public static Scene viewPatients;
	public static Scene newConsultations;
	public static Scene viewDoctors;
	public static Scene viewConsultations;
	public static Scene referringDoctors;
	public static Scene consultationsFrequency;

	//Controllers
	public static controllers.HomePageController hp;
	public static controllers.NewPatientController nP;
	public static controllers.ViewPatientsController vP;
	public static controllers.NewConsultationController nC;
	public static controllers.ViewDoctorsController vD;
	public static controllers.ViewConsultationsController vC;
	public static controllers.ReferringDoctorsController rD;
	public static controllers.ConsultationsFrequencyController cF;


	//Object creation;
	public static User currUser = new User ("manu_baza", "Dr. Manuel Bazaldua", "manu2023");	
	public static DoctorsAL doctorsAl = new DoctorsAL();
	public static PatientsAL patientsAl = new PatientsAL();
	public static ConsultationsAL consultationsAl = new ConsultationsAL();
	public static ConsultationsAL consultationsHistoryAl = new ConsultationsAL();
	public static DataAL doccountAl = new DataAL();
	public static DataAL consultationsCountAl = new DataAL();

	public static String monthsOfTheYear [] = {"None", "January", "February","March","April", "May","June", "July","August", "September", "October","November", "December"};

	@Override
	public void start(Stage primaryStage) {
		try {
			mainstage = primaryStage;

			//Log in Scene Loader
			Parent rootLG = FXMLLoader.load(getClass().getResource("LogIn.fxml")); 
			logIn = new Scene(rootLG);

			//Home Page Scene Loader
			FXMLLoader loaderHP = new FXMLLoader(getClass().getResource("HomePage.fxml"));
			Parent rootHP = loaderHP.load();
			hp = loaderHP.getController();
			homePage = new Scene(rootHP);

			// New Patient Scene Loader
			FXMLLoader loaderNP = new FXMLLoader(getClass().getResource("NewPatient.fxml"));
			Parent rootNP = loaderNP.load();
			nP = loaderNP.getController();
			newPatient = new Scene(rootNP);

			//ViewPatients Scene Loader	
			FXMLLoader loaderVP = new FXMLLoader(getClass().getResource("ViewPatients.fxml"));
			Parent rootVP = loaderVP.load();
			vP = loaderVP.getController();
			viewPatients = new Scene(rootVP);

			//New Consultation
			FXMLLoader loaderNC = new FXMLLoader(getClass().getResource("NewConsultations.fxml"));
			Parent rootNC = loaderNC.load();
			nC = loaderNC.getController();
			newConsultations = new Scene(rootNC);

			FXMLLoader loaderVD = new FXMLLoader(getClass().getResource("ViewDoctors.fxml"));
			Parent rootVD = loaderVD.load();
			vD = loaderVD.getController();
			viewDoctors = new Scene(rootVD);

			FXMLLoader loaderVC = new FXMLLoader(getClass().getResource("ViewConsultations.fxml"));
			Parent rootVC = loaderVC.load();
			vC = loaderVC.getController();
			viewConsultations = new Scene(rootVC);

			FXMLLoader loaderRD = new FXMLLoader(getClass().getResource("ReferringDoctors.fxml"));
			Parent rootRD = loaderRD.load();
			rD = loaderRD.getController();
			referringDoctors = new Scene(rootRD);

			FXMLLoader loaderCF = new FXMLLoader(getClass().getResource("ConsultationsFrequency.fxml"));
			Parent rootCF = loaderCF.load();
			cF = loaderCF.getController();
			consultationsFrequency = new Scene(rootCF);

			//primary Scene and Stage
			logIn.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(logIn);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method refills the DoctorsAlFile.csv file with 
	 * its arrayList "doctorsAL", saving any change such as 
	 * the edition, the addition or the deletion of a doctor.
	 * @param event
	 */
	public static void updateDoctorsAlCsvFile() {
		try {
			PrintWriter writer = new PrintWriter (new FileWriter("src/txtFiles/DoctorsAlFile.csv"));

			for (int i = 0; i < application.Main.doctorsAl.getDocsAl().size(); i ++) {
				writer.println(application.Main.doctorsAl.getDocsAl().get(i));

			}
			writer.close();

		}catch( Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method refills the PatientsAlFile.csv file with 
	 * its arrayList "patientsAL", saving any change such as 
	 * the edition, the addition or the deletion of a patients.
	 * @param event
	 */

	public static void updatePatientsAlCsvFile() {
		try {
			PrintWriter writer = new PrintWriter (new FileWriter("src/txtFiles/PatientsAlFile.csv"));

			for (int i = 0; i < application.Main.patientsAl.getPatientAl().size(); i ++) {
				writer.println(application.Main.patientsAl.getPatientAl().get(i));
			}

			writer.close();

		}catch( Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * This method refills the ConsultationsAlCsvFile.csv file with 
	 * its arrayList "consultationsAl", saving any change such as 
	 * the edition, the addition or the deletion of a consultation.
	 * @param event
	 */
	public static void updateConsultationsAlCsvFile() {
		try {
			PrintWriter writer = new PrintWriter (new FileWriter("src/txtFiles/ConsultationsAlCsvFile.csv"));

			for (int i = 0; i < application.Main.consultationsAl.getConsultationsAl().size(); i ++) {
				writer.println(application.Main.consultationsAl.getConsultationsAl().get(i));
			}
			writer.close();
		}catch( Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * This method read the information in the PatientsAlCsvFile.csv 
	 * and fills it in its respective ArrayList which is patientsAl.
	 * @param event
	 */
	public static void readPatientsAlCsvFile() {
		try {
			BufferedReader reader = new BufferedReader (new FileReader("src/txtFiles/PatientsAlFile.csv"));
			String line = reader.readLine();

			while (line != null) {
				//Posible addition of intance variables consultationDate and consultationTime

				String [] values = line.split("\t");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(values[2], formatter);
				Doctor temp = new Doctor(values[7]);
				int height = Integer.parseInt(values[11]);
				double weight = Double.parseDouble(values[12]);

				Patient tempPatient = new Patient(values[1],date, values[4],values[5],values[6],temp.getName(),
						values[8],values[9],values[10],height,weight,values[14],values[15],values[18]);

				tempPatient.setCDiagnosis(values[16]);					
				tempPatient.setcPrescription(values[17]);


				application.Main.patientsAl.addPatient(tempPatient);

				line = reader.readLine();
			}
			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * This method read the information in the DoctorsAlFile.csv 
	 * and fills it in its respective ArrayList which is doctorsAl.
	 * @param event
	 */
	public static void readDoctorsAlCsvFile() {
		try {
			BufferedReader reader = new BufferedReader (new FileReader("src/txtFiles/DoctorsAlFile.csv"));
			String line = reader.readLine();

			while (line != null) {
				String[] values =line.split("\t");
				Doctor temp = new Doctor(values[0]);

				temp.setPhone(values[1]);
				temp.setEmail(values[2]);
				temp.setDocnum(values[3]);
				application.Main.doctorsAl.getDocsAl().add(temp);
				line = reader.readLine();
			}
			reader.close();


		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * This method read the information in the ConsultationsAlCsvFile.csv 
	 * and fills it in its respective ArrayList which is consultationsAl.
	 * @param event
	 */
	public static void readConsultationsAlCsvFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/txtFiles/ConsultationsAlCsvFile.csv"));
			String line = reader.readLine();

			while(line != null) {
				String[] values = line.split("\t");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(values[2], formatter);
				Doctor temp = new Doctor(values[7]);
				int height = Integer.parseInt(values[11]);
				double weight = Double.parseDouble(values[12]);

				Patient tempPatient = new Patient(values[1],date, values[4],values[5],values[6],temp.getName(),values[8],values[9],values[10],height,weight,values[14],values[15],values[18]);
				tempPatient.setCDiagnosis(values[16]);					
				tempPatient.setcPrescription(values[17]);

				Consultation tempConsultation = new Consultation(tempPatient, values[19], values[20],values[21]);
				//tempConsultation.setConsultationEndingTime(values[22]);
				application.Main.consultationsAl.addConsultation(tempConsultation);
				application.Main.consultationsAl.sorting();
				line = reader.readLine();
			}


			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method refills the DoctorsAlFile.csv file with 
	 * its arrayList "doctorsAL", saving any change such as 
	 * the edition, the addition or the deletion of a doctor.
	 * @param event
	 */
	public static void updateConsultationsHistoryAlCsvFile() { 
		try {
			PrintWriter writer = new PrintWriter (new FileWriter("src/txtFiles/ConsultationsHistoryCsvFile.csv"));

			for (int i = 0; i < application.Main.consultationsHistoryAl.getConsultationsAl().size(); i ++) {
				writer.println(application.Main.consultationsHistoryAl.getConsultationsAl().get(i));
			}
			writer.close();
		}catch( Exception e) {
			e.printStackTrace();
		}		
	}
	/**
	 * This method refills the ConsultationsAlCsvFile.csv file with 
	 * its arrayList "consultationsAl", ignoring the deletion of a 
	 * consultation, creating a log. 
	 * @param event
	 */
	public static void readConsultationsHistoryAlCsvFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/txtFiles/ConsultationsHistoryCsvFile.csv")); // CUIDADO, cambialo
			String line = reader.readLine();

			while(line != null) {
				String[] values = line.split("\t");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(values[2], formatter);
				Doctor temp = new Doctor(values[7]);
				int height = Integer.parseInt(values[11]);
				double weight = Double.parseDouble(values[12]);

				Patient tempPatient = new Patient(values[1],date, values[4],values[5],values[6],temp.getName(),values[8],values[9],values[10],height,weight,values[14],values[15],values[18]);
				tempPatient.setCDiagnosis(values[16]);					
				tempPatient.setcPrescription(values[17]);

				Consultation tempConsultation = new Consultation(tempPatient, values[19], values[20],values[21]);
				//tempConsultation.setConsultationEndingTime(values[22]);
				application.Main.consultationsHistoryAl.addConsultation(tempConsultation);
				application.Main.consultationsHistoryAl.sorting();
				line = reader.readLine();
			}


			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	
	public static boolean existsInConsultationsHistory(Consultation x) {
		for (int i = 0; i < application.Main.consultationsHistoryAl.getConsultationsAl().size(); i++) {
			if(x.getConsultationDate().equalsIgnoreCase(application.Main.consultationsHistoryAl.getConsultation(i).getConsultationDate() )&& x.getName().equals(application.Main.consultationsHistoryAl.getConsultation(i).getName())) {
				return true;
			}
		}
		return false;
	}

	public static void addConsultationsHistoryAl(Consultation x) {
		if (existsInConsultationsHistory(x) == false) {
			application.Main.consultationsHistoryAl.addConsultation(x);
		}	
	}






}
