package fr.mfa.iam.services.match;
/**
 * Matcher interface, this interface permit select the match criteria used in the dao file for searches.
 * @author marcelo
 *
 * @param <T>
 */
public interface Matcher<T> {
	
	public boolean match(T criteria, T toBeChecked);

}
