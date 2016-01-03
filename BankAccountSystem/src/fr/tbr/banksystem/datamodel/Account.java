package fr.tbr.banksystem.datamodel;

public class Account {
	
	private String name;
	
	
	/*
	 * numerical value, common to represent amounts of money
	 */
	private double balance;


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}


	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	/* wrapper class for double, allow to have some utilities methods*/
//	Double balance2 ;


	
}
