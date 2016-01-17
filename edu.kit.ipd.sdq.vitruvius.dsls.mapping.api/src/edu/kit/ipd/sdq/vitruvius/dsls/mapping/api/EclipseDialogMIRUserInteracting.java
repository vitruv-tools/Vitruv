package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.EclipseHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MIRUserInteracting;

public class EclipseDialogMIRUserInteracting implements MIRUserInteracting {

	@Override
	public URI askForNewResource(String message) {
		return EclipseHelper.askForNewResource(message);
	}

}
