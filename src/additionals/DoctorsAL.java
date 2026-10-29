package additionals;

import java.util.ArrayList;

public class DoctorsAL {
	private ArrayList<Doctor> DocsAl;

	//Constructor
	public DoctorsAL() {
		DocsAl = new ArrayList<Doctor>();
	}
	//Getter
	public ArrayList<Doctor> getDocsAl() {
		return DocsAl;
	}
	//Setter
	public void setDocsAl(ArrayList<Doctor> DocsAl) {
		this.DocsAl = DocsAl;
	}


	//===========Added Methods====================

	/** 
	 * This method returns a doctor
	 * using an index
	 * @param event
	 */
	public Doctor getDoctor (int index) {
		return DocsAl.get(index);
	}
	/** 
	 * This method adds a doctor to the array list
	 * using the doctors name and sorts the list
	 * @param event
	 */
	public void addDoctor (String docname) {
		DocsAl.add(new Doctor(docname));
		sortDocsAlByName();
	}
	/** 
	 * This method removes the doctor from the arraylist
	 * using the doctors name to look for it
	 * @param event
	 */
	public void removeDoctor (String docname){
		DocsAl.remove(findDoctorBinary(docname));
	}

	/** 
	 * Sorts arraylist using selection by doctors'names
	 * @param event
	 */
	public void sortDocsAlByName () {
		//Using Selection Sort

		for (int i = 0; i < DocsAl.size(); i++) {
			for (int j = i+1; j < DocsAl.size(); j++) {
				if (DocsAl.get(i).getName().compareToIgnoreCase(DocsAl.get(j).getName()) > 0) {
					Doctor swapper = DocsAl.get(i);
					DocsAl.set(i, DocsAl.get(j));
					DocsAl.set(j, swapper);
				}
			}
		}
	}


	/** 
	 * Finds a doctor using binary search using 
	 * the doctor's name.
	 * @param event
	 */
	public int findDoctorBinary (String name) {
		sortDocsAlByName();
		int start = 0;
		int end = DocsAl.size()-1; 
		int mid;

		while (start <= end ) {
			mid = (start + end) /2;
			if (name.compareTo(DocsAl.get(mid).getName()) == 0) {
				return mid;
			}
			if (name.compareTo(DocsAl.get(mid).getName()) < 0) {
				end = mid - 1;
			}else {
				start = mid + 1;  
			}
		}
		return -1;
	}
	/** 
	 * Finds a doctor using binary search using 
	 * a doctor's object.
	 * @param event
	 */
	public int findDoctorBinary(Doctor x) {
		sortDocsAlByName();
		int start = 0;
		int end = DocsAl.size()-1; 
		int mid;

		while (start <= end ) {
			mid = (start + end) /2;
			if (x.getName().compareTo(DocsAl.get(mid).getName()) == 0) {
				return mid;
			}
			if (x.getName().compareTo(DocsAl.get(mid).getName()) < 0) {
				end = mid - 1;
			}else {
				start = mid + 1;  
			}
		}


		return -1;
	}

	/** 
	 * Finds a doctor using sequential search using 
	 * a doctor's object.
	 * @param event
	 */
	public int findDocSeq(Doctor x) {

		for (int i = 0; i < DocsAl.size();i++) {
			if (DocsAl.get(i).getName() == x.getName() ) {
				return i;
			}
		}
		return -1;
	}
}
