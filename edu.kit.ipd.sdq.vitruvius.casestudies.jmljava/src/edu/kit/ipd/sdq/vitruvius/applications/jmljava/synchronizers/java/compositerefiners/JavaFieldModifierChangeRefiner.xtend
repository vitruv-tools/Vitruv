package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.members.Field
import org.emftext.language.java.modifiers.Modifier

class JavaFieldModifierChangeRefiner extends JavaModifiersChangeRefinerBase {

	new(ShadowCopyFactory shadowCopyFactory) {
		super(shadowCopyFactory)
	}

	override protected getAffectedEObjectClass() {
		return Field
	}

	override protected findMatchingDeleteChange(CreateNonRootEObjectInList<EObject> addChange,
		List<DeleteNonRootEObjectInList<EObject>> deleteChanges) {
		if (!Utilities.isVisibilityModifier(addChange.newValue as Modifier)) {
			return null
		}

		val addField = (addChange.newAffectedEObject as Field)
		return deleteChanges.findFirst[
			Utilities.isVisibilityModifier(oldValue as Modifier) && fieldsMatch(addField, oldAffectedEObject as Field)]
	}

	private static def fieldsMatch(Field fa, Field fb) {
		return fa.name == fb.name
	}
}
