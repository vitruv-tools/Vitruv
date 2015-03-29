package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Provides services for gathering information about a model (i.e.
 * cross references) to a MIRMapping.
 * @author Dominik Werle
 */
public interface MIRModelInformationProvider {
	public Collection<EObject> getReverseFeature(EObject target, EStructuralFeature feature);
	public boolean isReferencedFromTypeByFeature(EObject target, EClassifier sourceType, EStructuralFeature feature);
}
