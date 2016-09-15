package tools.vitruv.applications.jmljava.synchronizers

import com.google.inject.Inject
import tools.vitruv.domains.jml.language.jML.Expression
import tools.vitruv.domains.jml.language.jML.JMLMemberModifier
import tools.vitruv.domains.jml.language.jML.JMLSpecifiedElement
import tools.vitruv.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruv.applications.jmljava.metamodels.JMLMetaModelProvider
import tools.vitruv.applications.jmljava.metamodels.JaMoPPMetaModelProvider
import tools.vitruv.applications.jmljava.synchronizers.helpers.DummyTransformations
import tools.vitruv.applications.jmljava.synchronizers.java.AbortableEObjectMappingTransformationBase
import tools.vitruv.applications.jmljava.synchronizers.java.JavaClassTransformations
import tools.vitruv.applications.jmljava.synchronizers.java.JavaCompilationUnitTransformations
import tools.vitruv.applications.jmljava.synchronizers.java.JavaFieldTransformations
import tools.vitruv.applications.jmljava.synchronizers.java.JavaMethodTransformations
import tools.vitruv.applications.jmljava.synchronizers.java.JavaParameterTransformations
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.CompositeChangeRefinerResult
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.CompositeChangeRefinerResultAtomicTransformations
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.JavaClassifierModifierChangeRefiner
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.JavaFieldModifierChangeRefiner
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.JavaMethodModifierChangeRefiner
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.JavaMethodParameterNumberChangedByOneCompositeChangeRefiner
import tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners.JavaMethodParameterNumberNotChangedCompositeChangeRefiner
import tools.vitruv.applications.jmljava.synchronizers.jml.JMLInvariantExpressionTransformations
import tools.vitruv.applications.jmljava.synchronizers.jml.JMLMemberDeclarationWithModifierTransformations
import tools.vitruv.applications.jmljava.synchronizers.jml.JMLMethodDeclarationTransformations
import tools.vitruv.applications.jmljava.synchronizers.jml.JMLVariableDeclarationTransformations
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.processing.Change2CommandTransforming 
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.imports.Import
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import java.util.concurrent.Callable
import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.TransformationExecutor

/**
 * Synchronizer for Java and JML. It initializes the transformations and composite
 * change refiners. Additionally creates the transformation result, which is relevant
 * for the model handling (e.g. which model has to be saved).
 */
class CSSynchronizer extends TransformationExecutor implements Change2CommandTransforming {

	static val Logger LOGGER = Logger.getLogger(CSSynchronizer)
	val compositeChangeRefiners = new ArrayList<CompositeChangeRefiner>()
	val SynchronisationAbortedListener syncAbortedListener;
	val UserInteracting userinteracting;

	@Inject
	new(ShadowCopyFactory shadowCopyFactory, UserInteracting userInteracting,
		SynchronisationAbortedListener syncAbortedListener) {
		// Java -> JML
		addMapping(new JavaCompilationUnitTransformations(shadowCopyFactory, syncAbortedListener))
		addMapping(new JavaClassTransformations(shadowCopyFactory, syncAbortedListener))
		addMapping(new JavaMethodTransformations(shadowCopyFactory, syncAbortedListener))
		addMapping(new JavaParameterTransformations(shadowCopyFactory, syncAbortedListener))
		addMapping(new JavaFieldTransformations(shadowCopyFactory, syncAbortedListener))

		addCompositeChangeRefiner(new JavaMethodParameterNumberNotChangedCompositeChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaMethodParameterNumberChangedByOneCompositeChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaMethodModifierChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaFieldModifierChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaClassifierModifierChangeRefiner(shadowCopyFactory))
		addCompositeChangeRefiner(new JavaMethodBodyChangedChangeRefiner(shadowCopyFactory))

		// JML -> JML / JML -> Java
		addMapping(new JMLMethodDeclarationTransformations(shadowCopyFactory, syncAbortedListener))
		addMapping(new JMLInvariantExpressionTransformations(shadowCopyFactory, syncAbortedListener))
		addMapping(new JMLMemberDeclarationWithModifierTransformations(shadowCopyFactory, syncAbortedListener))
		addMapping(new JMLVariableDeclarationTransformations(shadowCopyFactory, syncAbortedListener))

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

/*
 * TODO This must be implemented for each direction separately!
 */
	override getTransformableMetamodels() {
		val List<Pair<VURI, VURI>> result = new ArrayList<Pair<VURI, VURI>>();

		result.add(new Pair<VURI, VURI>(JaMoPPMetaModelProvider.URI, JMLMetaModelProvider.URI));
		result.add(new Pair<VURI, VURI>(JMLMetaModelProvider.URI, JaMoPPMetaModelProvider.URI));

		return result;
	}

	override transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel) {
		val List<VitruviusRecordingCommand> commands = new ArrayList<VitruviusRecordingCommand>()
		if (change instanceof CompositeChange) {
			commands.addAll(executeTransformation(change as CompositeChange, correspondenceModel))
		} else {
			commands.add(transformEMFModelChange2Command(change as ConcreteChange, correspondenceModel))
		}
		return commands
	}

	def transformEMFModelChange2Command(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		LOGGER.info("Synchronization of change " + change.class.simpleName + " started.")

		this.correspondenceModel = correspondenceModel

		val modelChange = (change as ConcreteChange).EChanges.get(0)
		this.setSyncAbortChange(change as ConcreteChange)
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
			override call() {
				val res = executeTransformationForChange(modelChange)
				return res
			}
		})
		return command
	}

	def void setSyncAbortChange(ConcreteChange change) {
		this.mappingTransformations.values.filter(AbortableEObjectMappingTransformationBase).forEach [
			setSyncAbortChange(change)
		]
	}

	def List<VitruviusRecordingCommand> executeTransformation(CompositeChange compositeChange, CorrespondenceModel correspondenceModel) {
		LOGGER.info("Synchronization of composite change (" + compositeChange.allChanges.size +
			" changes included) started.")

		this.correspondenceModel = correspondenceModel;

		var CompositeChangeRefinerResult refinementResult;
		val matchingProcessor = compositeChangeRefiners.findFirst[match(compositeChange)]
		if (matchingProcessor != null) {
			LOGGER.info("Found matching processor " + matchingProcessor.class.simpleName)
			refinementResult = matchingProcessor.refine(compositeChange)
		} else {
			LOGGER.info("Using default handling for composite change.")
			refinementResult = new CompositeChangeRefinerResultAtomicTransformations(compositeChange.allChanges)
		}
		val command = refinementResult.apply(this, correspondenceModel, userinteracting, syncAbortedListener)
		return command
	}

	private def addCompositeChangeRefiner(CompositeChangeRefiner refiner) {
		compositeChangeRefiners.add(refiner)
	}

	private static def getAllChanges(CompositeChange compositeChange) {
		val changes = new ArrayList<ConcreteChange>()
		val queue = new LinkedList<VitruviusChange>()
		queue.add(compositeChange)
		while (!queue.empty) {
			val change = queue.pop()
			if (change instanceof ConcreteChange) {
				changes.add(change as ConcreteChange)
			} else if (change instanceof CompositeChange) {
				queue.addAll((change as CompositeChange).changes)
			} else {
				// not supported
				LOGGER.warn("The composite change contains an invalid change type " + change.class + ". It is skipped.")
			}
		}
		return changes
	}

	override setUserInteracting(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
}
