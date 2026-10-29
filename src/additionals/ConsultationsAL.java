package additionals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConsultationsAL {
	private ArrayList<Consultation> consultationsAl;
	
	public ConsultationsAL() {
		consultationsAl = new ArrayList<Consultation>();
	}
	
	public ArrayList<Consultation> getConsultationsAl(){
		return consultationsAl;
	}
	public void setConsultationsAl(ArrayList<Consultation> al){
		consultationsAl = al;
	}
	
	public Consultation getConsultation(int index) {
		return consultationsAl.get(index);
	}
	
	public void addConsultation (Consultation patient) {
		consultationsAl.add(patient);
		sorting();
	}

	/**
	 * This methods sorts ConsultationsAl by date
	 * using selection sort
	 * @param event
	 */
	public void selectionSortByDate() {
		for (int i = 0; i < consultationsAl.size(); i++) {
			for (int j = i+1; j < consultationsAl.size(); j++) {
				if (consultationsAl.get(i).getConsultationDate().compareToIgnoreCase(consultationsAl.get(j).getConsultationDate()) > 0) {
					Consultation swapper = consultationsAl.get(i);
					consultationsAl.set(i, consultationsAl.get(j));
					consultationsAl.set(j, swapper);
				}
			}
		}
	}
	/**
	 * This methods sorts ConsultationsAl by time
	 * using selection sort
	 * @param event
	 */
	public void selectionSortByTime() {
		for (int i = 0; i < consultationsAl.size(); i++) {
			for (int j = i+1; j < consultationsAl.size(); j++) {
				if (consultationsAl.get(i).getConsultationTime().compareToIgnoreCase(consultationsAl.get(j).getConsultationTime()) > 0) {
					Consultation swapper = consultationsAl.get(i);
					consultationsAl.set(i, consultationsAl.get(j));
					consultationsAl.set(j, swapper);
				}
			}
		}
	}
	/**
	 * This methods sorts ConsultationsAl by date
	 * and time
	 * @param event
	 */
	public void sorting() {
		selectionSortByTime();
		selectionSortByDate();
	}
	
	/**
	 * This method refills ArrayList with another 
	 * ArrayList and sorts the list by date and time
	 * @param event
	 */
	public void updateConsultationsAl(ArrayList<Consultation> al) {
		setConsultationsAl(al);
		sorting();
	}
	/**
	 * This method looks for a consultation by linear Search using
	 * the consultation's name and date.
	 * @param event
	 */
	public int LinearSearchByNameAndDate(String name, LocalDate date) {
		String dateString = date + "";
		selectionSortByDate();
		selectionSortByTime();
		
	for (int i = 0; i < consultationsAl.size(); i++) {
		if (name.equals(consultationsAl.get(i).getName()) && dateString.equals(consultationsAl.get(i).getConsultationDate())) {
		return i;
		}
	}
		return -1;
	}
	
	/**
	 * This method checks if the given time is not between
	 * an already existing consultation time. 
	 * @param event
	 */
	public boolean existingConsultationTime(LocalTime givenTime) {
		for(int i = 0; i < consultationsAl.size(); i ++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime time = LocalTime.parse(consultationsAl.get(i).getConsultationTime(), formatter);
			LocalTime ending = LocalTime.parse(consultationsAl.get(i).getConsultationEndingTime(),formatter);
			
			if(givenTime.isAfter(time) && givenTime.isBefore(ending) ){
				return true;
			}	
		}
		return false;
	}
	/**
	 * This method checks if the given date is not between
	 * an already existing consultation date. 
	 * @param event
	 */
	public boolean existingConsultationDate(LocalDate givenDate) {
		for(int i = 0; i < consultationsAl.size(); i ++ ) {
			if(consultationsAl.get(i).getConsultationDate().equals(givenDate) == true) { //ConsultationDate is a string, transform it to a local date and then compare it. 
				return true;
			}
		}
		return false;
	}

}
