package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

/**
 * Provides services for gathering information about a model (i.e.
 * cross references) to a MIRMapping.
 * @author Dominik Werle
 */
public interface MIRModelInformationProvider {
	public Collection<EObject> getReverseFeature(EObject target, EStructuralFeature feature);
	
	public boolean isReferencedFromTypeByFeature(EObject target, EClassifier sourceType, EStructuralFeature feature);
	
	/**
	 * Returns an {@link EObject} that references <code>target</code>
	 * by the {@link EStructuralFeature} <code>feature</code> and is
	 * mapped by <code>mapping</code>
	 * @param target
	 * @param feature
	 * @param correspondenceInstance
	 * @param mapping
	 * @return <code>null</code> if no such {@link EObject} exists.
	 */
	public EObject getReverseFeatureMappedBy(EObject target, EStructuralFeature feature,
			CorrespondenceInstance correspondenceInstance, MIRMapping mapping);

	
	/**
	 * Returns an {@link EObject} that references <code>target</code>
	 * by the {@link EStructuralFeature} <code>feature</code> and is
	 * mapped by a mapping of type <code>mappingClass</code>
	 * @param target
	 * @param feature
	 * @param correspondenceInstance
	 * @param mappingClass
	 * @return <code>null</code> if no such {@link EObject} exists.
	 */
	public EObject getReverseFeatureMappedBy(EObject target,
			EStructuralFeature feature,
			CorrespondenceInstance correspondenceInstance,
			Class<MIRMapping> mappingClass);
}
