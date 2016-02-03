/**
 * 
 */
package fr.mfa.aim.datamodel;

/**
 * This is the representation for the Users, which is a extension of Identity, the main datamodel to manage in IamCore
 * @author marcelo
 *
 */
public class User extends Identity {

	private String username;
	private String password;
	
	/**
	 * Constructor of the class, set all the information of the user.
	 * @param displayName
	 * @param emailAddress
	 * @param uid
	 */
	public User(String displayName, String emailAddress, String uid) {
		super(displayName, emailAddress, uid);
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Set the username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Get the password.
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * SEt the password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
