package fr.mfa.iam.tests.services.match.impl;

import fr.mfa.aim.datamodel.Identity;

/**
 * This class implement the Matcher interface with the search criteria of "equals".
 */
import fr.mfa.iam.tests.services.match.Matcher;

public class EqualsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().equals(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().equals(
						criteria.getEmailAddress());
	}

}
