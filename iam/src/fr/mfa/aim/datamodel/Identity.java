package fr.mfa.aim.datamodel;

import java.util.Date;

/**
 * This is the representation for the Identity, the main datamodel to manage in IamCore
 * @author marcelo
 *
 */
public class Identity {
	
	private String displayName;
	private String emailAddress;
	private String uid;
	private Date birthDate;
	
	/** 
	 * Constructor of the class it will initialize the values of the instance.
	 * @param displayName
	 * @param emailAddress
	 * @param uid
	 */
	public Identity(String displayName, String emailAddress, String uid) {
		this.displayName = displayName;
		this.emailAddress = emailAddress;
		this.uid = uid;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
    /**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @see java.lang.Object#toString()
	 * Overrride the toSTring method for returning the identity information.
	 */
	@Override
	public String toString() {
		return "Identity [displayName=" + displayName + ", emailAddress="
				+ emailAddress + ", uid=" + uid + "]";
	}
	/**
	 * @return The birthDate.
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	/**
	 * Set the birthDate
	 * @param birthDate
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
