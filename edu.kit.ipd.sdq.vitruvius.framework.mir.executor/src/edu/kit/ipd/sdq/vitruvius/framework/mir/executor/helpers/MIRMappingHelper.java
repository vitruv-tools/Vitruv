package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Helper class for MIR mapping realizations.
 * @author Dominik Werle
 *
 */
public final class MIRMappingHelper {
	private final static Logger LOGGER = Logger.getLogger(MIRMappingHelper.class);
	
	public static Collection<EObject> getReverseFeature(EObject target, EStructuralFeature feature) {
		Collection<Setting> settings = EcoreUtil.UsageCrossReferencer.find(target, target.eResource());
		
		Collection<EObject> result = new ArrayList<EObject>();
		for (Setting setting : settings) {
			if (feature.equals(setting.getEStructuralFeature())) {
				result.add(setting.getEObject());
			}
		}
		
		return result;
	}
	
	public static Pair<EObject, EObject> getReverseFeatureMappedBy(EObject target,
			EStructuralFeature feature, MappedCorrespondenceInstance correspondenceInstance,
			MIRMappingRealization mapping) {
		Collection<EObject> candidates = getReverseFeature(target, feature);
		for (EObject candidate : candidates) {
			EObject candidateTarget = correspondenceInstance.getMappingTarget(candidate, mapping);
			if (candidateTarget != null) { // i.e. the candidate is not mapped by mapping
				return new Pair<EObject, EObject>(candidate, candidateTarget);
			}
		}
		return null;
	}
	
	
	
	public static Collection<EObject> getAllAffectedObjects(EChange eChange) {
		Collection<EObject> result = new HashSet<EObject>();
		
		if (eChange instanceof EFeatureChange<?>) {
			EObject newAffectedEObject = ((EFeatureChange<?>) eChange).getNewAffectedEObject();
			result.add(newAffectedEObject);
			
			if (eChange instanceof UpdateEReference<?>) {
				UpdateEReference<?> updateEReference = (UpdateEReference<?>) eChange;
				EReference affectedFeature = updateEReference.getAffectedFeature();
				Object featureValue = newAffectedEObject.eGet(affectedFeature);
				
				if (featureValue instanceof Collection<?>) {
					for (Object affectedObject : (Collection<?>) featureValue) {
						if (affectedObject instanceof EObject) {
							result.add((EObject) affectedObject);
						}
					}
				} else if (featureValue instanceof EObject) {
					result.add((EObject) featureValue);
				}
			}
		} else if (eChange instanceof CreateRootEObject<?>) {
			EObject newValue = ((CreateRootEObject<?>) eChange).getNewValue();
			result.add(newValue);
		}
		
		return result;
	}
	
	public static List<Resource> getResources(Collection<EObject> eObjects) {
		return eObjects.stream().map(it -> it.eResource()).distinct().collect(Collectors.toList());
	}
	
	public static void addEmptyResourcesToTransformationResult(Iterable<Resource> resources, TransformationResult result) {
		for (Resource res : resources) {
			if (res.getContents().isEmpty()) {
				result.addVURIToDeleteIfNotNull(VURI.getInstance(res));
			}
		}
	}
	
	public static void removeAll(Collection<EObject> eObjects) {
		for (EObject obj : eObjects) {
			EcoreUtil.remove(obj);
		}
	}
}
