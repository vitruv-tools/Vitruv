package tools.vitruv.framework.versioning.impl

import java.util.List

import org.apache.log4j.Level
import org.apache.log4j.Logger

import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.impl.ChangeClonerImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.MultiChangeConflict
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.versioning.impl.ConflictImpl

class MultiChangeConflictImpl extends ConflictImpl implements MultiChangeConflict {
	static extension ChangeCloner = new ChangeClonerImpl
	static extension Logger = Logger::getLogger(MultiChangeConflictImpl)
	static extension URIRemapper = URIRemapper::instance
	@Accessors(PUBLIC_GETTER)
	val EChange sourceRepresentative
	@Accessors(PUBLIC_GETTER)
	val EChange targetRepresentative
	@Accessors(PUBLIC_GETTER)
	val List<EChange> sourceChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> targetChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> triggeredSourceChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> triggeredTargetChanges

	val List<EChange> originalDefaultSolution
	val List<EChange> triggeredDefaultSolution

	boolean isDefaultSolutionComputed

	new(ConflictType type, ConflictSeverity solvability, EChange sourceRepresentative, EChange targetRepresentative,
		List<EChange> sourceChanges, List<EChange> targetChanges, VURI myVURI, VURI theirVURI,
		List<EChange> triggeredSourceChanges, List<EChange> triggeredTargetChanges, List<VURI> myTriggeredVURIs,
		List<VURI> theirTriggeredVURIs) {
		super(type, solvability, myVURI, theirVURI, myTriggeredVURIs, theirTriggeredVURIs)
		this.sourceChanges = sourceChanges
		this.originalDefaultSolution = newArrayList
		this.sourceRepresentative = sourceRepresentative
		this.targetChanges = targetChanges
		this.triggeredDefaultSolution = newArrayList
		this.targetRepresentative = targetRepresentative
		this.triggeredSourceChanges = triggeredSourceChanges
		this.triggeredTargetChanges = triggeredTargetChanges
		isDefaultSolutionComputed = false
	}

	override getSourceDefaultSolution() {
		if (!isDefaultSolutionComputed)
			computeDefaultSolution
		return originalDefaultSolution
	}

	override getTriggeredDefaultSolution() {
		if (!isDefaultSolutionComputed)
			computeDefaultSolution
		return triggeredDefaultSolution
	}

	private def computeDefaultSolution() {
		level = Level::WARN
		originalDefaultSolution.clear
		triggeredDefaultSolution.clear
		if (solvability !== ConflictSeverity::SOFT)
			throw new IllegalStateException
		if (type === ConflictType::INSERTING_IN_SAME_CONTANER) {
			val originalCreateAndInsertNonRoot = sourceRepresentative as CreateAndInsertNonRoot<?, ?>

			fillDefaultSolution(originalCreateAndInsertNonRoot, sourceChanges, targetChanges, originalDefaultSolution,
				myOriginalVURI, theirOriginalVURI)
			val triggeredSourceRepresentative = triggeredSourceChanges.filter [
				it instanceof CreateAndInsertNonRoot<?, ?>
			].map[it as CreateAndInsertNonRoot<?, ?>].get(0)
			val myTriggeredVURI = myTriggeredVURIs.get(0)
			val theirTriggeredVURI = theirTriggeredVURIs.get(0)
			fillDefaultSolution(triggeredSourceRepresentative, triggeredSourceChanges, triggeredTargetChanges,
				triggeredDefaultSolution, myTriggeredVURI, theirTriggeredVURI)
			isDefaultSolutionComputed = true
			return
		}
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	private static def fillDefaultSolution(CreateAndInsertNonRoot<?, ?> createAndInsertNonRoot, List<EChange> sChanges,
		List<EChange> tChanges, List<EChange> defaultSolution, VURI myVURI, VURI theirVURI) {
		val insertedUri = (createAndInsertNonRoot.insertChange.affectedEObject as InternalEObject).eProxyURI.toString
		val featureName = createAndInsertNonRoot.insertChange.affectedFeature.name
		val oldIndex = createAndInsertNonRoot.insertChange.index
		val newIndex = oldIndex + 1
		createAndInsertNonRoot.insertChange.index = newIndex
		warn('''index «oldIndex» has been been 
						replaced with «newIndex» in EChanges depending on «createAndInsertNonRoot»''')
		val prefix = '''«insertedUri»/@«featureName».'''
		val newString = prefix + newIndex
		val stringToReplace = prefix + oldIndex

		val processEchange = createEChangeRemapFunction(stringToReplace, newString)
		sChanges.forEach[processEchange.accept(it)]
		val newTargetChanges = tChanges.map[cloneEChange(it)]

		val processTargetEChange = createEChangeRemapFunction(theirVURI, myVURI)
		newTargetChanges.forEach[processTargetEChange.accept(it)]
		defaultSolution += (newTargetChanges + sChanges)
	}

}
