package fr.mfa.iam.tests.services.match.impl;

import fr.mfa.aim.datamodel.Identity;

import fr.mfa.iam.tests.services.match.Matcher;

public class StartsWithIdentityMatchStrategy implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {

		return toBeChecked.getDisplayName().startsWith(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().startsWith(criteria.getEmailAddress());
	}

}
