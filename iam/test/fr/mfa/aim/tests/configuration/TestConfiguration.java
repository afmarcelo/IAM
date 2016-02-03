package fr.mfa.aim.tests.configuration;

import fr.mfa.aim.configuration.Configuration;

/**
 * Test class for validating the configuration loading.
 * @author marcelo
 *
 */
public class TestConfiguration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration config = new Configuration();
		System.out.println("The current configuration is: ");
		System.out.println("-----------------------------");
		System.out.println("ServerIP: "+config.getServerIP());
		System.out.println("ServerPort: "+config.getServerPort());
		System.out.println("WorkingPath: "+config.getWorkingPath());
		System.out.println("XmlFilePath: "+config.getXmlFilePath());

	}

}
