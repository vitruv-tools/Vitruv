package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class MIRMappingHelper {
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
}
