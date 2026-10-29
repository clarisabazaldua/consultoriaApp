package additionals;

public class User {
	private String username;
	private String name;
	private String password;
	
	public User(String username,String name, String password) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/** 
	 * Returns whether if the user's information corresponds with 
	 * the one inputed or not
	 * @param event
	 */
	public boolean validateUser(String user, String pass) {
		if (username.equals(user) && password.equals(pass)) {
			return true;
		}
		return false;
	}
	
	
	
}


