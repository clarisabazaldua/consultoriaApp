package additionals;

public class Doctor {
	private static int id = 0;
	private String name;
	private String phone;
	private String email;
	private String docnum;
	
	public Doctor(String name) {
		super();
		this.name = name;
		id++;
		phone = " ";
		email = " ";
		docnum = " ";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocnum() {
		return docnum;
	}

	public void setDocnum(String docnum) {
		this.docnum = docnum;
	}

	@Override
	public String toString() {
		return name + "\t" + phone + "\t" + email + "\t" + docnum + "\t";
	}
	
}
