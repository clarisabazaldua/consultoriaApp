package additionals;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import javafx.scene.control.DatePicker;

public class Patient {
	private static int nextId = 1;
	private int id;
	private String name;
	private LocalDate dateOfbirth;
	private int age;
	private String maritalStatus;
	private String sex;
	private String insuranceCompany;
	private String DocReferred;
	private String email;
	private String phone;
	private String residence;
	private int height;
	private double weight;
	private double BMI;
	private String alergies;
	private String notas;
	private String cDiagnosis;
	private String cPrescription;
	private String occupation;


	public Patient(String name, LocalDate dateOfbirth, String maritalStatus, String sex, String insuranceCompany,
			String docReferred, String email, String phone, String residence, int height, double weight,
			String alergies, String notas, String occupation) {
		super();
		this.name = name;
		this.dateOfbirth = dateOfbirth;
		this.maritalStatus = maritalStatus;
		this.sex = sex;
		this.insuranceCompany = insuranceCompany;
		DocReferred = docReferred;
		this.email = email;
		this.phone = phone;
		this.residence = residence;
		this.height = height;
		this.weight = weight;
		this.alergies = alergies;
		this.notas = notas;
		this.occupation = occupation;
		id = nextId;
		nextId++;
		BMI = getBMI();
		age = getAge();
	}
	
	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateOfbirth() {
		return dateOfbirth;
	}
	public void setDateOfbirth(LocalDate dateOfbirth) {
		this.dateOfbirth = dateOfbirth;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	/** 
	 * Calculates age from a date of birth
	 * and returns age
	 * @param event
	 */
	public int getAge() {
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(dateOfbirth, currentDate);
		age = period.getYears();
		return age;
	}
	
	
	
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getDocReferred() {
		return DocReferred;
	}
	public void setDocReferred(String docReferred) {
		DocReferred = docReferred;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setBMI(double BMI) {
		this.BMI = BMI;
	}
	/** 
	 * Calculates BMI index using 
	 * the BMI formula and return BMI
	 */
	public double getBMI() {
		DecimalFormat decimals = new DecimalFormat("#.##");
		double heightdouble = height;
		heightdouble = heightdouble/100;
		BMI = Double.parseDouble(decimals.format(weight/ Math.pow(heightdouble, 2)));
		return BMI;
	}
	
	public String getAlergies() {
		return alergies;
	}
	public void setAlergies(String alergies) {
		this.alergies = alergies;
	}
	public String getCDiagnosis() {
		return cDiagnosis;
	}
	public void setCDiagnosis(String cDiagnosis) {
		this.cDiagnosis = cDiagnosis;
	}
	public String getcPrescription() {
		return cPrescription;
	}
	public void setcPrescription(String cPrescription) {
		this.cPrescription = cPrescription;
	}

	@Override
	public String toString() {
	/*	
		"Patient [id=" + id + ", name=" + name + ", dateOfbirth=" + dateOfbirth + ", age=" + age
		+ ", maritalStatus=" + maritalStatus + ", sex=" + sex + ", insuranceCompany=" + insuranceCompany
		+ ", DocReferred=" + DocReferred + ", email=" + email + ", phone=" + phone + ", residence=" + residence
		+ ", height=" + height + ", weight=" + weight + ", BMI=" + BMI + ", alergies=" + alergies + ", notas="
		+ notas + ", cDiagnosis=" + cDiagnosis + ", cPrescription=" + cPrescription + ", occupation="
		+ occupation + "]";
		*/
		
		return  id + "\t" + name + "\t" + dateOfbirth + "\t" + age
				+ "\t" + maritalStatus + "\t" + sex + "\t"+ insuranceCompany
				+ "\t" + DocReferred + "\t" + email + "\t" + phone + "\t" + residence
				+ "\t" + height + "\t" + weight + "\t"+ BMI + "\t"+ alergies + "\t"
				+ notas + "\t"+ cDiagnosis + "\t" + cPrescription + "\t"
				+ occupation;
	}
	/** 
	 * Prints all Instance Variables information
	 *  of the object with it's lables. 
	 * @param event
	 */
	public String printWithLabels() {
		return "Patient \n id=" + id + "\nname=" + name + "\ndateOfbirth=" + dateOfbirth + "\nage=" + age
				+ "\nmaritalStatus=" + maritalStatus + "\nsex=" + sex + "\ninsuranceCompany=" + insuranceCompany
				+ "\nDocReferred=" + DocReferred + "\nemail=" + email + "\nphone=" + phone + "\nresidence=" + residence
				+ "\nheight=" + height + "\nweight=" + weight + "\nBMI=" + BMI + "\nalergies=" + alergies + "\nnotes="
				+ notas + "\nCurrent Diagnosis=" + cDiagnosis + "\nCurrent Prescription=" + cPrescription + "\noccupation="
				+ occupation + "\nNext Consultation: Date: ";
	}

	
	
	
	
	

}
