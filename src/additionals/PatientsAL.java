package additionals;
import java.util.Collections;
import java.util.ArrayList;

public class PatientsAL {
	private ArrayList<Patient> PatientAl;

	public PatientsAL() {
		PatientAl = new ArrayList<Patient>();
	}

	public ArrayList<Patient> getPatientAl() {
		return PatientAl;
	}

	public void setPatientAl(ArrayList<Patient> patientAl) {
		PatientAl = patientAl;
	}


	//====Added Methods===================================

	/** 
	 * Returns a patient using an specific
	 * index.
	 * @param event
	 */
	public Patient getPatient (int index) {
		return PatientAl.get(index);
	}
	/** 
	 * Adds a patient on the arraylist
	 * @param event
	 */
	public void addPatient (Patient patient) {
		PatientAl.add(patient);
		selectionSortByName();
	}
	/** 
	 * Sorts using selection sort by name
	 * @param event
	 */
	public void selectionSortByName() {
		for (int i = 0; i < PatientAl.size(); i++) {
			for (int j = i+1; j < PatientAl.size(); j++) {
				if (PatientAl.get(i).getName().compareToIgnoreCase(PatientAl.get(j).getName()) > 0) {
					Patient swapper = PatientAl.get(i);
					PatientAl.set(i, PatientAl.get(j));
					PatientAl.set(j, swapper);
				}
			}
		}
	}
	/** 
	 * Search a patient's index using its name and 
	 * using binary search.
	 * @param event
	 */
	public int binarySearchByName(String name) {
		selectionSortByName();
		int start = 0;
		int end = PatientAl.size()-1; 
		int mid;

		while (start <= end ) {
			mid = (start + end) /2;
			if (name.compareTo(PatientAl.get(mid).getName()) == 0) {
				return mid;
			}
			if (name.compareTo(PatientAl.get(mid).getName()) < 0) {
				end = mid - 1;
			}else {
				start = mid + 1;  
			}
		}
		return -1;
	}
}
