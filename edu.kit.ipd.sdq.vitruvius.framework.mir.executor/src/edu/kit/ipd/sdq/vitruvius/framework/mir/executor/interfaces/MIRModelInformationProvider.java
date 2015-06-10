package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Provides services for gathering information about a model (i.e.
 * cross references) to a MIRMapping.
 * @author Dominik Werle
 */
public interface MIRModelInformationProvider {
	public Collection<EObject> getReverseFeature(EObject target, EStructuralFeature feature);
	
	public boolean isReferencedFromTypeByFeature(EObject target, EClassifier sourceType, EStructuralFeature feature);
	
	/**
	 * Returns a {@link Pair} of {@link EObject EObjects} the first references <code>target</code>
	 * by the {@link EStructuralFeature} <code>feature</code> and is
	 * mapped by <code>mapping</code> to the second EObject of the Pair.
	 * @param target
	 * @param feature
	 * @param correspondenceInstance
	 * @param mapping
	 * @return <code>null</code> if no such {@link Pair} of {@link EObject EObjects} exists.
	 */
	public Pair<EObject, EObject> getReverseFeatureMappedBy(EObject target, EStructuralFeature feature,
			CorrespondenceInstance correspondenceInstance, MIRMapping mapping);
}