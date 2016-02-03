package fr.mfa.iam.tests.services.match.impl;

import fr.mfa.aim.datamodel.Identity;

import fr.mfa.iam.tests.services.match.Matcher;

/**
 * This class implement the Matcher interface with the search criteria of "match".
 * @author marcelo
 *
 */
public class StartsWithIdentityMatchStrategy implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {

		return toBeChecked.getDisplayName().startsWith(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().startsWith(criteria.getEmailAddress());
	}

}
