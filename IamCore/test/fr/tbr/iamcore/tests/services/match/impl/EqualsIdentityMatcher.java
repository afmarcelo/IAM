package fr.tbr.iamcore.tests.services.match.impl;

import fr.tbr.iamcore.datamodel.Identity;
import fr.tbr.iamcore.tests.services.match.Matcher;

public class EqualsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().equals(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().equals(
						criteria.getEmailAddress());
	}

}
