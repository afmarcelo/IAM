package fr.tbr.iamcore.tests.services.match.impl;

import fr.tbr.iamcore.datamodel.Identity;
import fr.tbr.iamcore.tests.services.match.Matcher;

public class StartsWithIdentityMatchStrategy implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {

		return toBeChecked.getDisplayName().startsWith(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().startsWith(criteria.getEmailAddress());
	}

}
