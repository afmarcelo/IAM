package fr.mfa.iam.services.match.impl;

import fr.mfa.aim.datamodel.Identity;

import fr.mfa.iam.tests.services.match.Matcher;

/**
 * This class implement the Matcher interface with the search criteria of "contains".
 * @author marcelo
 *
 */
public class ContainsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().contains(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().contains(criteria.getEmailAddress());
	}

}
