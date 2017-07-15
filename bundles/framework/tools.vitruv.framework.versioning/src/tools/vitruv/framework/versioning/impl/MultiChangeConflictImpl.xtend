package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.function.Consumer
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.MultiChangeConflict
import tools.vitruv.framework.versioning.impl.ConflictImpl

@Data
class MultiChangeConflictImpl extends ConflictImpl implements MultiChangeConflict {
	static extension Logger = Logger::getLogger(MultiChangeConflictImpl)
	static extension ChangeCloner = new ChangeCloner
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
		level = Level::WARN
		if (solvability !== ConflictSeverity::SOFT)
			throw new IllegalStateException
		if (type === ConflictType::INSERTING_IN_SAME_CONTANER) {
			val x = sourceRepresentative as CreateAndInsertNonRoot<?, ?>
			val insertedUri = (x.insertChange.affectedEObject as InternalEObject).eProxyURI.toString
			val featureName = x.insertChange.affectedFeature.name
			val oldIndex = x.insertChange.index
			val newIndex = oldIndex + 1
			x.insertChange.index = newIndex
			warn('''index «oldIndex» has been been 
			replaced with «newIndex» in EChanges depending on «sourceRepresentative»''')
			val remapMyUriFunction = [ EObject e |
				val proxyString = (e as InternalEObject).eProxyURI.toString
				if (proxyString.contains(insertedUri)) {
					val prefix = '''«insertedUri»/@«featureName».'''
					val stringToReplace = prefix + oldIndex
					val newString = prefix + newIndex
					val newProxyString = proxyString.replace(stringToReplace, newString)
					val newUri = URI::createURI(newProxyString)
					(e as InternalEObject).eSetProxyURI(newUri)
				}
			]
			sourceChanges.forEach[processEchange(remapMyUriFunction)]
			val newTargetChanges = targetChanges.map[cloneEChange(it)]
			val myVURIString = myVURI.EMFUri.toString
			val theirVURIString = theirVURI.EMFUri.toString

			val remapTheirUriFunction = [ EObject e |
				val internalEObject = e as InternalEObject
				val proxyString = internalEObject.eProxyURI.toString
				if (proxyString.contains(theirVURIString)) {
					val newProxyString = proxyString.replace(theirVURIString, myVURIString)
					val newUri = URI::createURI(newProxyString)
					internalEObject.eSetProxyURI(newUri)
				}
			]

			newTargetChanges.forEach[processTargetEChange(it, remapTheirUriFunction)]
			return (newTargetChanges + sourceChanges).toList
		}
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	private static dispatch def processTargetEChange(EChange e, Consumer<EObject> cb) {
	}

	private static dispatch def processTargetEChange(ReplaceSingleValuedEAttribute<?, ?> e, Consumer<EObject> cb) {
		cb.accept(e.affectedEObject)
	}

	private static dispatch def processTargetEChange(CreateAndInsertNonRoot<?, ?> e, Consumer<EObject> cb) {
		cb.accept(e.insertChange.affectedEObject)
	}

	private static dispatch def processEchange(EChange e, Consumer<EObject> cb) {
	}

	private static dispatch def processEchange(ReplaceSingleValuedEAttribute<?, ?> e, Consumer<EObject> cb) {
		cb.accept(e.affectedEObject)
	}
}
