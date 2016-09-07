package tools.vitruvius.applications.jmljava.synchronizers.java.compositerefiners

import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeBuilder
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruvius.framework.change.description.CompositeChange
import tools.vitruvius.framework.change.description.GeneralChange
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory
import tools.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import tools.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import java.util.ArrayList
import java.util.Collection
import java.util.List
import javax.activation.UnsupportedDataTypeException
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.TypeReference

class JavaMethodParameterNumberNotChangedCompositeChangeRefiner extends CompositeChangeRefinerBase {
	
	new(ShadowCopyFactory shadowCopyFactory) {
		super(shadowCopyFactory)
	}
	
	override match(CompositeChange change) {
		
		var CompositeChangeRefinerBase.AddDeleteContainer addAndDeleteChanges = null
		try {
			addAndDeleteChanges = getAddAndDeleteChanges(change)
		} catch (IllegalArgumentException e) {
			return false
		}
		
		val deleteChanges = addAndDeleteChanges.deleteChanges
		val addChanges = addAndDeleteChanges.addChanges
		
		if (deleteChanges.size != addChanges.size) {
			return false
		}
		
		if (deleteChanges.exists[!(oldAffectedEObject instanceof Method) || !(oldValue instanceof Parameter)]) {
			return false
		}
		
		if (addChanges.exists[!(newAffectedEObject instanceof Method) || !(newValue instanceof Parameter)]) {
			return false
		}
		
		return true
	}
	
	override refine(CompositeChange change) {
		var CompositeChangeRefinerBase.AddDeleteContainer addAndDeleteChanges = null
		addAndDeleteChanges = getAddAndDeleteChanges(change)	
		return new CompositeChangeRefinerResultAtomicTransformations(refine(addAndDeleteChanges.deleteChanges, addAndDeleteChanges.addChanges))
	}

	
	private def refine(Iterable<DeleteNonRootEObjectInList<EObject>> deleteChanges, Iterable<CreateNonRootEObjectInList<EObject>> addChanges) {
		// find unchanged and not matching parameters
		val originalParams = new ArrayList<Parameter>();
		deleteChanges.forEach[originalParams.add(oldValue as Parameter)]
		val changedParams = new ArrayList<Parameter>();
		addChanges.forEach[changedParams.add(newValue as Parameter)]
		
        val matchResult = match(originalParams, changedParams, [Parameter original, Iterable<Parameter> candidates | candidates.findFirst[!anyPropertyIsDifferent(original, it)] ])
		val originalUnmatched = matchResult.unmatchedOriginal
		val changedUnmatched = matchResult.unmatchedChanged
        
        if (originalUnmatched.size() + changedUnmatched.size() == 0) {
            // order of parameters might have been changed          
        	val change = ContainmentFactory.eINSTANCE.createPermuteContainmentEReferenceValues
        	change.oldAffectedEObject = originalParams.get(0).eContainer
        	change.newAffectedEObject = change.oldAffectedEObject
        	change.affectedFeature = originalParams.get(0).eContainingFeature as EReference
            	
            for (originalParam : originalParams) {
            	val matchedChangedParam = changedParams.findFirst[name.equals(originalParam.name) && typeReference.equals(originalParam.typeReference)]
            	if (matchedChangedParam != null) {
	            	change.newIndexForElementAt.add(changedParams.indexOf(matchedChangedParam))
            	}
            }
            if (change.newIndexForElementAt.size > 0 && change.newIndexForElementAt.size == changedParams.size) {
            	return #[change as EMFModelChange].toList
            }
        }
        
        if (originalUnmatched.size() == 1 && changedUnmatched.size() == 1) {
            // only one parameter is changed
            val changes = new ArrayList<EMFModelChange>();
            val originalParam = originalUnmatched.get(0);
            val changedParam = changedUnmatched.get(0);
            
            if (!originalParam.name.equals(changedParam.name)) {
            	changes.add(ChangeBuilder.createUpdateChange(originalParam, changedParam, originalParam.eClass.getEStructuralFeature("name")))
            }
            
            if (originalParam.arrayDimension != changedParam.arrayDimension) {
            	//TODO array dimensions are not supported at the moment
            }
            
            if (typeReferenceIsDifferent(originalParam.typeReference, changedParam.typeReference)) {
            	changes.add(ChangeBuilder.createUpdateChange(originalParam, changedParam, originalParam.typeReference.eContainingFeature))
            }
            
            changes.addAll(createModifierChanges(originalParam, changedParam))
            return changes;            
        }
        
        // fallback
        val changes = new ArrayList<EMFModelChange>();
        deleteChanges.forEach[changes.add(new EMFModelChange(it, VURI.getInstance(it.eResource)))]
        addChanges.forEach[changes.add(new EMFModelChange(it, VURI.getInstance(it.eResource)))]
        return changes
	}
	
	private static def boolean anyPropertyIsDifferent(Parameter p1, Parameter p2) {
        if (!p1.getName().equals(p2.getName())) {
            return true;
        }
        
        if (typeReferenceIsDifferent(p1.typeReference, p2.typeReference)) {
        	return true;
        }
        
        if (p1.getArrayDimension() != p2.getArrayDimension()) {
            return true;
        }
        
        if (p1.getModifiers().size() != p2.getModifiers().size()) {
            return true;
        }
        
        for (Modifier p1Mod : p1.getModifiers()) {
            for (Modifier p2Mod : p2.getModifiers()) {
                if (p1Mod.getClass() != p2Mod.getClass()) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static def dispatch boolean typeReferenceIsDifferent(PrimitiveType t1, PrimitiveType t2) {
    	return t1.class != t2.class
    }
    
    private static def dispatch boolean typeReferenceIsDifferent(NamespaceClassifierReference t1, NamespaceClassifierReference t2) {
    	val t1Refs = new StringBuilder()
    	t1.classifierReferences.forEach[t1Refs.append(it.target.name)]
    	val t2Refs = new StringBuilder()
    	t2.classifierReferences.forEach[t2Refs.append(it.target.name)]
    	return t1Refs.equals(t2Refs)
    }
    
    private static def List<EMFModelChange> createModifierChanges (Parameter oldParam, Parameter newParam) {
    	
    	val matchResult = match(oldParam.modifiers, newParam.modifiers, [Modifier original, Iterable<Modifier> candidates | candidates.findFirst[original.class == it.class] ])
    	
//        val originalModifiers = new ArrayList<Modifier>(oldModifiers);
//        val changedModifiers = new ArrayList<Modifier>(newModifiers);
//        originalModifiers.removeAll(matchResult.)
//
//        for (Modifier changedModifier : newModifiers) {
//        	val origModifier = oldModifiers.findFirst[changedModifier.class == it.class]
//            if (origModifier != null) {
//                originalModifiers.remove(origModifier);
//                changedModifiers.remove(changedModifier);
//            }
//        }

		val unmatchedOriginalModifiers = matchResult.unmatchedOriginal
		val unmatchedChangedModifiers = matchResult.unmatchedChanged
        
        val changes = new ArrayList<EMFModelChange>();
        
        val originalVisiblities = unmatchedOriginalModifiers.filter[isVisibilityModifier]
        val changedVisibilities = unmatchedChangedModifiers.filter[isVisibilityModifier]
        if (originalVisiblities.size > 2 || changedVisibilities.size > 2) {
        	throw new UnsupportedDataTypeException("Two visibility modifiers are not allowed.")
        }
        
        if (originalVisiblities.size + changedVisibilities.size == 2) {
        	val oldValue = originalVisiblities.get(0)
        	val newValue = changedVisibilities.get(0)
        	val original = oldValue.eContainer
        	val changed = newValue.eContainer
        	changes.add(ChangeBuilder.createReplaceChangeInList(original, changed, oldValue.eContainingFeature, oldValue, newValue))
        }

		unmatchedOriginalModifiers.filter[!isVisibilityModifier].forEach[changes.add(ChangeBuilder.createDeleteChange(it, newParam))]
		unmatchedChangedModifiers.filter[!isVisibilityModifier].forEach[changes.add(ChangeBuilder.createCreateChange(it, oldParam));]
        return changes;
    }
    
    private static def isVisibilityModifier(Modifier mod) {
    	return mod instanceof Public || mod instanceof Private || mod instanceof Protected;
    }
    
    private static def dispatch boolean typeReferenceIsDifferent(TypeReference t1, TypeReference t2) {
    	return true;
    }
	
	private static class MatchingResult<T extends EObject> {
    	List<T> unmatchedOriginal
    	List<T> unmatchedChanged
    	List<Pair<T,T>> matchedPairs
    	
    	new (List<T> unmatchedOriginal, List<T> unmatchedChanged, List<Pair<T,T>> matchedPairs) {
			this.unmatchedOriginal = unmatchedOriginal
			this.unmatchedChanged = unmatchedChanged
			this.matchedPairs = matchedPairs    		
    	}
    }
    
    private static interface Matcher<T extends EObject> {
    	public def T matches(T element, Iterable<T> candidates)
    }
	
	private static def <T extends EObject> match(Collection<T> original, Collection<T> changed, JavaMethodParameterNumberNotChangedCompositeChangeRefiner.Matcher<T> matcher) {
    	val List<Pair<T, T>> unchanged = new ArrayList<Pair<T, T>>();
        val originalUnmatched = new ArrayList<T>(original);
        val changedUnmatched = new ArrayList<T>(changed);
        for (T originalElement : original) {
        	val changedElement = matcher.matches(originalElement, changed)
            if (changedElement != null) {
                unchanged.add(new Pair<T, T>(originalElement, changedElement));
                originalUnmatched.remove(originalElement);
                changedUnmatched.remove(changedElement);
            }
        }
        return new JavaMethodParameterNumberNotChangedCompositeChangeRefiner.MatchingResult(originalUnmatched, changedUnmatched, unchanged);
    }
	
}