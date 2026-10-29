package additionals;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Consultation {
	private String consultationDate;
	private String name;
	private String consultationTime; 
	private String consultationEndingTime;
	private Patient patient;
	private String hospital;
	public Consultation(Patient patient, String hospital, String consultationTime, String consultationDate) {
		super();
		this.consultationDate = consultationDate;
		this.consultationTime = consultationTime;
		this.patient = patient;
		this.hospital = hospital;
		this.name = patient.getName();
		updateEndingTime();
		
	}
	public String getConsultationEndingTime() {
		return consultationEndingTime;
	}
	public void setConsultationEndingTime(String consultationEndingTime) {
		this.consultationEndingTime = consultationEndingTime;
	}
	public String getConsultationDate() {
		return consultationDate;
	}
	public void setConsultationDate(String consultationDate) {
		this.consultationDate = consultationDate;
	}
	public String getConsultationTime() {
		return consultationTime;
	}
	public void setConsultationTime(String consultationTime) {
		this.consultationTime = consultationTime;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	
	/**
	 * This  fills gets the ending time of a consultation
	 * from the consultation time, and assigns its value in the
	 * consultationEndintTime instance variable
	 * @param event
	 */
	public void updateEndingTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime time = LocalTime.parse(consultationTime, formatter);
		consultationEndingTime = time.plusHours(2) + "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return patient + "\t" + hospital + "\t" + consultationTime + "\t" + consultationDate ;
	}
	
}
