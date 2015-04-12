package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMapping;

public abstract class AbstractMIRMapping implements MIRMapping {
	/**
	 * Returns the {@link EClassifier} mapped by this mapping.
	 * @return the {@link EClassifier} mapped by this mapping
	 */
	protected abstract EClassifier getMappedEClassifier();
	
	/**
	 * Returns {@link EObject EObjects} that are possibly affected by this change.
	 * @param eChange
	 * @return
	 */
	protected Collection<EObject> getCandidates(EChange eChange) {
		// TODO: implement
		throw new UnsupportedOperationException("getting candidates not supported");
	}
	
	
	
	@Override
	public EMFChangeResult applyEChange(
			EChange eChange,
			CorrespondenceInstance correspondenceInstance,
			AbstractMIRTransformationExecuting abstractMIRTransformationExecuting) {
		return null;
	}

}
