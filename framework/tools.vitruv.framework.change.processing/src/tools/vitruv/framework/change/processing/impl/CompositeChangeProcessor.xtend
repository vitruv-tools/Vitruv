package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.change.processing.ChangeProcessor
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.TransformationResult
import org.apache.log4j.Logger

abstract class CompositeChangeProcessor extends AbstractChangeProcessor {
	private static val logger = Logger.getLogger(CompositeChangeProcessor);
	
	private val List<ChangeProcessor> changePreprocessors;
	private val List<ChangeProcessor> changeMainprocessors;

	new(UserInteracting userInteracting) {
		super(userInteracting);
		changePreprocessors = new ArrayList<ChangeProcessor>();
		changeMainprocessors = new ArrayList<ChangeProcessor>();
	}
	
	/** 
	 * Adds the specified change processor as a preprocessor, which is executed before the mainprocessors.
	 * The preprocessors are executed in the order in which they are added.
	 */
	protected def addChangePreprocessor(ChangeProcessor changePropagationSpecifcation) {
		assertMetamodelsCompatible(changePropagationSpecifcation);
		changePreprocessors += changePropagationSpecifcation;
	}
	
	/** 
	 * Adds the specified change processor as a main processor, which is executed after the preprocessors.
	 * The main processors are executed in the order in which they are added.
	 */
	protected def addChangeMainprocessor(ChangeProcessor changePropagationSpecifcation) {
		assertMetamodelsCompatible(changePropagationSpecifcation);
		changeMainprocessors += changePropagationSpecifcation;
	}
	
	private def boolean assertMetamodelsCompatible(ChangeProcessor potentialChangeProcessor) {
		if (!this.metamodelPair.equals(potentialChangeProcessor.metamodelPair)) {
			throw new IllegalArgumentException("ChangeProcessor metamodels are not compatible");
		}
	}
	
	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val propagationResult = new TransformationResult();
		for (changeProcessor : changePreprocessors + changeMainprocessors) {
			logger.debug('''Calling change processor «changeProcessor» for change event «change»''');
			val currentPropagationResult = changeProcessor.propagateChange(change, correspondenceModel);
			propagationResult.integrateTransformationResult(currentPropagationResult);
		}
		return propagationResult;
	}
	
	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		for (changeProcessor : changePreprocessors + changeMainprocessors) {
			if (changeProcessor.doesHandleChange(change, correspondenceModel)) {
				return true;
			}
		}
		return false;
	}
	
}