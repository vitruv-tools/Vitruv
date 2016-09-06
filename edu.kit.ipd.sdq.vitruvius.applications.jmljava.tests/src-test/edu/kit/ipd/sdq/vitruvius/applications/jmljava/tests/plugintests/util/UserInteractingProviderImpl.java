package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.util;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.extensions.UserInteractingProvider;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

public class UserInteractingProviderImpl implements UserInteractingProvider {

    @Override
    public UserInteracting get() {
        return new UserInteracting() {

            @Override
            public void showMessage(final UserInteractionType type, final String message) {
                // ignore the message
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
