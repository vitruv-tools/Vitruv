package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.extensions.UserInteractingProvider;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

public class UserInteractingProviderImpl implements UserInteractingProvider {

	@Override
	public UserInteracting get() {
		return new UserInteracting() {
			
			@Override
			public void showMessage(UserInteractionType type, String message) {
				// ignore the message
			}
			
			@Override
			public int selectFromModel(UserInteractionType type, String message,
					ModelInstance... modelInstances) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int selectFromMessage(UserInteractionType type, String message,
					String... selectionDescriptions) {
				throw new UnsupportedOperationException();
			}
		};
	}

}
