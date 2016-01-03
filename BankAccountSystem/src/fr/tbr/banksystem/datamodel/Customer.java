/**
 * 
 */
package fr.tbr.banksystem.datamodel;

import java.util.List;

/**
 * @author tbrou
 *
 */
public class Customer {
	//line comment
	
	/*
	 * In Java, the type is preceding the field name
	 * "String" defines a textual data 
	 */
	private String name;
	private String address;
	
	
	/**
	 * This the main constructor for a customer
	 * @param name the name of this customer
	 * @param address the adress where he lives
	 */
	public Customer(String name, String address) {
		this.name = name;
		this.address = address;
	}


	@Override
	public String toString() {
		return "This customer (" + this.name + ") lives at this address : " + this.address;
	}



	public void setName(String name){
		if (name != null){
			this.name = name;
		}
	}
	
	
	public String getName(){
		return this.name;
		
	}

	

	
	

}
