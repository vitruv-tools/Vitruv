package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class EcoreHelper {
	
	// TODO: more efficient with EcoreUtil?
	public static Set<EObject> findOppositeForFeature(EObject target, EReference reference) {
		Set<EObject> result = new HashSet<EObject>();
		
		Resource resource = target.eResource();
		TreeIterator<Object> iter = EcoreUtil.getAllProperContents(resource, true);
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof EObject) {
				EObject eobj = (EObject) obj;
				
				if (reference.getEContainingClass().isSuperTypeOf(eobj.eClass())) {
					Object referencedObject = eobj.eGet(reference);
					if (referencedObject instanceof EList<?>) {
						if (((EList<?>) referencedObject).contains(target)) {
							result.add(eobj);
						} else if (referencedObject.equals(target)) {
							result.add(eobj);
						}
					}
				}
			}
		}
		
		return result;
	}
	
	public static Set<EObject> findOppositeForFeatures(EObject target, EReference... references) {
		Set<EObject> currentLevel = new HashSet<EObject>();
		currentLevel.add(target);
		Set<EObject> nextLevel = new HashSet<EObject>();
		
		for (EReference reference : references) {
			nextLevel.clear();
			for (EObject eObj : currentLevel)
				nextLevel.addAll(findOppositeForFeature(eObj, reference));
			
			// swap references
			Set<EObject> swap = currentLevel;
			currentLevel = nextLevel;
			nextLevel = swap;
		}
		
		return currentLevel;
	}
}
