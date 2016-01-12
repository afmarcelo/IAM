/**
 * 
 */
package fr.mfa.aim.datamodel;

/**
 * This is the representation for the Users which is a extension of Identity, the main datamodel to manage in IamCore
 * @author marcelo
 *
 */
public class User extends Identity {

	private String username;
	private String password;
	
	public User(String displayName, String emailAddress, String uid) {
		super(displayName, emailAddress, uid);
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
