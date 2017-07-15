package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.impl.ConflictImpl
import tools.vitruv.framework.versioning.MultiChangeConflict
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import org.eclipse.emf.ecore.InternalEObject
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.change.description.ChangeCloner

@Data
class MultiChangeConflictImpl extends ConflictImpl implements MultiChangeConflict {
	EChange sourceRepresentative
	EChange targetRepresentative
	List<EChange> sourceChanges
	List<EChange> targetChanges
	List<EChange> triggeredSourceChanges
	List<EChange> triggeredTargetChanges

	override resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches,
		EList<ChangeMatch> rejectedRemoteOperations) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override getDefaultSolution() {
		if (solvability !== ConflictSeverity::SOFT)
			throw new IllegalStateException
		if (type === ConflictType::INSERTING_IN_SAME_CONTANER) {
			val x = sourceRepresentative as CreateAndInsertNonRoot<?, ?>
			val insertedUri = (x.insertChange.affectedEObject as InternalEObject).eProxyURI.toString
			val featureName = x.insertChange.affectedFeature.name
			val oldIndex = x.insertChange.index
			val newIndex = oldIndex + 1
			x.insertChange.index = newIndex
			sourceChanges.forEach[processEchange(insertedUri, featureName, oldIndex, newIndex)]
			val changeClone = new ChangeCloner
			val newTargetChanges = targetChanges.map[changeClone.cloneEChange(it)]
			newTargetChanges.forEach[processTargetEChange(myVURI.EMFUri.toString, theirVURI.EMFUri.toString)]
			return (newTargetChanges + sourceChanges).toList
		}
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	private static dispatch def processTargetEChange(EChange e, String myVURIString, String theirVURIString) {
	}

	private static dispatch def processTargetEChange(ReplaceSingleValuedEAttribute<?, ?> e, String myVURIString,
		String theirVURIString) {
		val proxyString = (e.affectedEObject as InternalEObject).eProxyURI.toString
		if (proxyString.contains(theirVURIString)) {
			val newProxyString = proxyString.replace(theirVURIString, myVURIString)
			val newUri = URI::createURI(newProxyString)
			(e.affectedEObject as InternalEObject).eSetProxyURI(newUri)
		}
	}

	private static dispatch def processTargetEChange(CreateAndInsertNonRoot<?, ?> e, String myVURIString,
		String theirVURIString) {
		val proxyString = (e.insertChange.affectedEObject as InternalEObject).eProxyURI.toString
		if (proxyString.contains(theirVURIString)) {
			val newProxyString = proxyString.replace(theirVURIString, myVURIString)
			val newUri = URI::createURI(newProxyString)
			(e.insertChange.affectedEObject as InternalEObject).eSetProxyURI(newUri)
		}
	}

	private static dispatch def processEchange(EChange e, String uriString, String featureName, int oldIndex,
		int newIndex) {
	}

	private static dispatch def processEchange(ReplaceSingleValuedEAttribute<?, ?> e, String uriString,
		String featureName, int oldIndex, int newIndex) {
		val proxyString = (e.affectedEObject as InternalEObject).eProxyURI.toString
		if (proxyString.contains(uriString)) {
			val prefix = '''«uriString»/@«featureName».'''
			val stringToReplace = prefix + oldIndex
			val newString = prefix + newIndex
			val newProxyString = proxyString.replace(stringToReplace, newString)
			val newUri = URI::createURI(newProxyString)
			(e.affectedEObject as InternalEObject).eSetProxyURI(newUri)
		}
	}

}
