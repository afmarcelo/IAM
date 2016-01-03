/**
 * 
 */
package fr.tbr.banksystem.launcher;

import fr.tbr.banksystem.datamodel.Customer;

/**
 * This is a javadoc comment to explain how to launch the Main class
 * <p>Here is how to execute the Main class</p>
 * <pre><code>java fr.tbr.banksystem.launcher.Main [arg1,2,3] </code></pre>
 * This is launching the {@link #main(String[])} method
 * @author tbrou
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//This is to define a customer instance as a variable
		Customer thomas = new Customer("thomas", "Paris");
		
		thomas.setName("clément");
		thomas.setName(null);
		
		
		/*
		 * multiline
		 */
		
		System.out.println("The following line displays the customer value");
		System.out.println(thomas);
		
		
		
	}

}
