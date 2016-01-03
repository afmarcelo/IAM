package fr.tbr.iamcore.tests.services.match;

public interface Matcher<T> {
	
	public boolean match(T criteria, T toBeChecked);

}
