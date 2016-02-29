package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.extensions.UserInteractingProvider;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

public class UserInteractingProviderImpl implements UserInteractingProvider {

    @Override
    public UserInteracting get() {
        return new UserInteracting() {

            @Override
            public void showMessage(final UserInteractionType type, final String message) {
                // ignore the message
            }

            @Override
            public int selectFromModel(final UserInteractionType type, final String message,
                    final ModelInstance... modelInstances) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int selectFromMessage(final UserInteractionType type, final String message,
                    final String... selectionDescriptions) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getTextInput(final String msg) {
                throw new UnsupportedOperationException();
            }

			@Override
			public URI selectURI(String message) {
				throw new UnsupportedOperationException();
			}
        };
    }

}
