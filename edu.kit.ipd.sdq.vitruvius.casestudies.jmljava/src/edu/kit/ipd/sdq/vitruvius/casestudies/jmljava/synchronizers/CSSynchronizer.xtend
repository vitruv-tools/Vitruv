package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JMLMetaModelProvider
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JaMoPPMetaModelProvider
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.helpers.DummyTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.JavaClassTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.JavaCompilationUnitTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.JavaFieldTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.JavaMethodTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.JavaParameterTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.CompositeChangeRefinerResult
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.CompositeChangeRefinerResultAtomicTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaClassifierModifierChangeRefiner
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaFieldModifierChangeRefiner
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodModifierChangeRefiner
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodParameterNumberNotChangedCompositeChangeRefiner
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.jml.JMLInvariantExpressionTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.jml.JMLMemberDeclarationWithModifierTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.jml.JMLMethodDeclarationTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.jml.JMLVariableDeclarationTransformations
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.Expression
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLMemberModifier
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSpecifiedElement
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.ChangeSynchronizer
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.imports.Import
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodParameterNumberChangedByOneCompositeChangeRefiner
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming

/**
 * Synchronizer for Java and JML. It initializes the transformations and composite
 * change refiners. Additionally creates the transformation result, which is relevant
 * for the model handling (e.g. which model has to be saved).
 */
class CSSynchronizer extends ChangeSynchronizer implements Change2CommandTransforming {
	
	static val Logger LOGGER = Logger.getLogger(CSSynchronizer)
	val compositeChangeRefiners = new ArrayList<CompositeChangeRefiner>()
	val SynchronisationAbortedListener syncAbortedListener;
	val UserInteracting userinteracting;
	
	@Inject
	new(ShadowCopyFactory shadowCopyFactory, UserInteracting userInteracting, SynchronisationAbortedListener syncAbortedListener) {
		// Java -> JML
		addMapping(new JavaCompilationUnitTransformations(shadowCopyFactory))
		addMapping(new JavaClassTransformations(shadowCopyFactory))
		addMapping(new JavaMethodTransformations(shadowCopyFactory))
		addMapping(new JavaParameterTransformations(shadowCopyFactory))
		addMapping(new JavaFieldTransformations(shadowCopyFactory))
		
		addCompositeChangeRefiner(new JavaMethodParameterNumberNotChangedCompositeChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaMethodParameterNumberChangedByOneCompositeChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaMethodModifierChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaFieldModifierChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaClassifierModifierChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaMethodBodyChangedChangeRefiner(shadowCopyFactory))
		
		// JML -> JML / JML -> Java
		addMapping(new JMLMethodDeclarationTransformations(shadowCopyFactory))
		addMapping(new JMLInvariantExpressionTransformations(shadowCopyFactory))
		addMapping(new JMLMemberDeclarationWithModifierTransformations(shadowCopyFactory))
		addMapping(new JMLVariableDeclarationTransformations(shadowCopyFactory))
		
		// dummy transformations for create/remove EObject calls from base class	
		addMapping(new DummyTransformations(Type))
		addMapping(new DummyTransformations(TypeReference))
		addMapping(new DummyTransformations(Modifier))
		addMapping(new DummyTransformations(Import))
		addMapping(new DummyTransformations(Statement))
		addMapping(new DummyTransformations(Expression))
		addMapping(new DummyTransformations(JMLSpecifiedElement))
		addMapping(new DummyTransformations(JMLMemberModifier))
		
		this.userInteracting = userInteracting
		this.userinteracting = userInteracting
		this.syncAbortedListener = syncAbortedListener
	}

	override getTransformableMetamodels() {
		val List<Pair<VURI, VURI>> result = new ArrayList<Pair<VURI, VURI>>();

		result.add(new Pair<VURI, VURI>(JaMoPPMetaModelProvider.URI, JMLMetaModelProvider.URI));
		result.add(new Pair<VURI, VURI>(JMLMetaModelProvider.URI, JaMoPPMetaModelProvider.URI));

		return result;
	}
	
	override transformChanges2Commands(EMFModelChange change, CorrespondenceInstance correspondenceInstance) {
		LOGGER.info("Synchronization of change " + change.class.simpleName + " started.")
		
		this.setCorrespondenceInstance(correspondenceInstance)
		
		val modelChange = (change as EMFModelChange).EChange
		val result = synchronizeChange(modelChange)
		if (result.transformationAborted) {
			syncAbortedListener.synchronisationAborted(change as EMFModelChange)
		}
		
		val vurisToSave = new HashSet<VURI>()
		result.existingObjectsToSave.forEach[vurisToSave.add(VURI.getInstance(eResource))]
		
		//TODO vurisToAdd is not supported because no URI affecting changes are supported atm
		
		//FIXME remove the following workaround after a concept for this has been added to Vitruvius
//		if (!result.transformationAborted && ChangeSynchronizerRegistry.getInstance != null) {
//			ChangeSynchronizerRegistry.getInstance.changeSynchronizer.modelProvidingDirtyMarker.markAllModelInstancesDirty(JaMoPPMetaModelProvider.URI)
//		}
		
		return new EMFChangeResult(vurisToSave, #[].toSet, result.existingObjectsToDelete)
	}
	
	override executeTransformation(CompositeChange compositeChange, CorrespondenceInstance correspondenceInstance) {
		LOGGER.info("Synchronization of composite change (" + compositeChange.allChanges.size + " changes included) started.")
		
		this.setCorrespondenceInstance(correspondenceInstance)
		
		var CompositeChangeRefinerResult refinementResult;
		val matchingProcessor = compositeChangeRefiners.findFirst[match(compositeChange)]
		if (matchingProcessor != null) {
			LOGGER.info("Found matching processor " + matchingProcessor.class.simpleName)
			refinementResult = matchingProcessor.refine(compositeChange)
		} else {
			LOGGER.info("Using default handling for composite change.")
			refinementResult = new CompositeChangeRefinerResultAtomicTransformations(compositeChange.allChanges)
		}
		
		return refinementResult.apply(this, correspondenceInstance, userinteracting, syncAbortedListener)
	}
	
	private def addCompositeChangeRefiner(CompositeChangeRefiner refiner) {
		compositeChangeRefiners.add(refiner)
	}
	
	private static def getAllChanges(CompositeChange compositeChange) {
		val changes = new ArrayList<EMFModelChange>()
		val queue = new LinkedList<Change>()
		queue.add(compositeChange)
		while (!queue.empty) {
			val change = queue.pop()
			if (change instanceof EMFModelChange) {
				changes.add(change as EMFModelChange)
			} else if (change instanceof CompositeChange) {
				queue.addAll((change as CompositeChange).changes)
			} else {
				// not supported
				LOGGER.warn("The composite change contains an invalid change type " + change.class + ". It is skipped.")
			}
		}
		return changes
	}

}
