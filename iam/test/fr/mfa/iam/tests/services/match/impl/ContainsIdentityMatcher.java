package fr.mfa.iam.tests.services.match.impl;

import fr.mfa.aim.datamodel.Identity;

import fr.mfa.iam.tests.services.match.Matcher;

public class ContainsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().contains(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().contains(criteria.getEmailAddress());
	}

}
