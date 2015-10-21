package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EclipseHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRUserInteracting;

public class EclipseDialogMIRUserInteracting implements MIRUserInteracting {

	@Override
	public URI askForNewResource(String message) {
		return EclipseHelper.askForNewResource(message);
	}

}
