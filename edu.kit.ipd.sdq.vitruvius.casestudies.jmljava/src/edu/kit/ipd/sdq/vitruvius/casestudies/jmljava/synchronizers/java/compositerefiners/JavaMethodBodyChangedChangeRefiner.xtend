package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.custom.JavaMethodBodyChangedTransformation
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateContainmentEReference
import java.util.ArrayList
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.StatementsPackage
import org.emftext.language.java.members.Method

class JavaMethodBodyChangedChangeRefiner extends CompositeChangeRefinerBase {

	new(ShadowCopyFactory shadowCopyFactory) {
		super(shadowCopyFactory)
	}

	override match(CompositeChange change) {
		if (change.changes.filter(EMFModelChange).filter[EChange instanceof UpdateContainmentEReference<?>].size !=
			change.changes.size) {
			return false
		}

		val echanges = new ArrayList<UpdateContainmentEReference<?>>()
		change.changes.filter(EMFModelChange).forEach[echanges.add(it.EChange as UpdateContainmentEReference<?>)]

		val firstChange = echanges.get(0)
		if (!(firstChange.oldAffectedEObject instanceof Method) ||
			!(firstChange.newAffectedEObject instanceof Method)) {
			return false
		}

		if (!echanges.forall[affectedFeature == StatementsPackage.eINSTANCE.statementListContainer_Statements]) {
			return false
		}

		if (!echanges.forall[newAffectedEObject == firstChange.newAffectedEObject]) {
			return false
		}

		if (!echanges.forall[oldAffectedEObject == firstChange.oldAffectedEObject]) {
			return false
		}

		val deleteChanges = echanges.filter(DeleteNonRootEObjectInList)
		val addChanges = echanges.filter(CreateNonRootEObjectInList)

		if (addChanges.size + deleteChanges.size != change.changes.size) {
			return false
		}

		if (!deleteChanges.forall[oldValue instanceof Statement]) {
			return false
		}

		if (!addChanges.forall[newValue instanceof Statement]) {
			return false
		}

		return true
	}

	override refine(CompositeChange change) {
		if (!change.match) {
			throw new IllegalArgumentException
		}
		val featureChange = (change.changes.get(0) as EMFModelChange).EChange as EFeatureChange<?>
		val oldMethod = featureChange.oldAffectedEObject as ClassMethod
		val newMethod = featureChange.newAffectedEObject as ClassMethod
		return new CompositeChangeRefinerResultTransformation(new JavaMethodBodyChangedTransformation(shadowCopyFactory, oldMethod, newMethod))
	}

}
