package fr.mfa.iam.tests.services.match;

public interface Matcher<T> {
	
	public boolean match(T criteria, T toBeChecked);

}
