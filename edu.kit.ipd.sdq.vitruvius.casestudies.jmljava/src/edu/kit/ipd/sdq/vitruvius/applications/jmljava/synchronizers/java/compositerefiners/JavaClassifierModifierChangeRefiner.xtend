package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.modifiers.Modifier

class JavaClassifierModifierChangeRefiner extends JavaModifiersChangeRefinerBase {

	new(ShadowCopyFactory shadowCopyFactory) {
		super(shadowCopyFactory)
	}

	override protected getAffectedEObjectClass() {
		return Classifier
	}

	override protected findMatchingDeleteChange(CreateNonRootEObjectInList<EObject> addChange,
		List<DeleteNonRootEObjectInList<EObject>> deleteChanges) {
		if (!Utilities.isVisibilityModifier(addChange.newValue as Modifier)) {
			return null
		}

		val addField = (addChange.newAffectedEObject as Classifier)
		return deleteChanges.findFirst[
			Utilities.isVisibilityModifier(oldValue as Modifier) &&
				classifiersMatch(addField, oldAffectedEObject as Classifier)]
	}

	private static def classifiersMatch(Classifier ca, Classifier cb) {
		return ca.name == cb.name
	}

}
