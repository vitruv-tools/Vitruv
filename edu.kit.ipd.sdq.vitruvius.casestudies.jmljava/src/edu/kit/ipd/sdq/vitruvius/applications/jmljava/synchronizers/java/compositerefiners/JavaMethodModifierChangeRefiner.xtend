package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier

class JavaMethodModifierChangeRefiner extends JavaModifiersChangeRefinerBase {

	new(ShadowCopyFactory shadowCopyFactory) {
		super(shadowCopyFactory)
	}

	override protected getAffectedEObjectClass() {
		Method
	}

	override protected findMatchingDeleteChange(CreateNonRootEObjectInList<EObject> addChange,
		List<DeleteNonRootEObjectInList<EObject>> deleteChanges) {
		if (!Utilities.isVisibilityModifier(addChange.newValue as Modifier)) {
			return null
		}

		val addMethod = (addChange.newAffectedEObject as Method)
		return deleteChanges.findFirst[
			Utilities.isVisibilityModifier(oldValue as Modifier) &&
				methodsMatch(addMethod, oldAffectedEObject as Method)]
	}

	private def methodsMatch(Method a, Method b) {
		if (!a.name.equals(b.name)) {
			return false
		}

		val aParams = a.parameters
		val bParams = b.parameters

		if (aParams.size != bParams.size) {
			return false
		}

		for (var i = 0; i < a.parameters.size; i++) {
			val aParam = aParams.get(i)
			val bParam = bParams.get(i)
			val typeReferencesAreEqual = aParam.typeReference.equals(bParam.typeReference) // fix xtend bug
			if (!typeReferencesAreEqual) {
				return false
			}
		}

		return true
	}

}
