package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import org.apache.log4j.Logger
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import org.eclipse.emf.ecore.EObject

abstract class CompositeChangePropagationSpecification extends AbstractChangePropagationSpecification implements ChangePropagationObserver {
	private static val logger = Logger.getLogger(CompositeChangePropagationSpecification);
	
	private val List<ChangePropagationSpecification> changePreprocessors;
	private val List<ChangePropagationSpecification> changeMainprocessors;

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain);
		changePreprocessors = new ArrayList<ChangePropagationSpecification>();
		changeMainprocessors = new ArrayList<ChangePropagationSpecification>();
	}
	
	/** 
	 * Adds the specified change processor as a preprocessor, which is executed before the mainprocessors.
	 * The preprocessors are executed in the order in which they are added.
	 */
	protected def addChangePreprocessor(ChangePropagationSpecification changePropagationSpecifcation) {
		assertMetamodelsCompatible(changePropagationSpecifcation);
		changePreprocessors += changePropagationSpecifcation;
		changePropagationSpecifcation.userInteracting = userInteracting;
		changePropagationSpecifcation.registerObserver(this);
	}
	
	/** 
	 * Adds the specified change processor as a main processor, which is executed after the preprocessors.
	 * The main processors are executed in the order in which they are added.
	 */
	protected def addChangeMainprocessor(ChangePropagationSpecification changePropagationSpecifcation) {
		assertMetamodelsCompatible(changePropagationSpecifcation);
		changeMainprocessors += changePropagationSpecifcation;
		changePropagationSpecifcation.userInteracting = userInteracting;
		changePropagationSpecifcation.registerObserver(this);
	}
	
	private def void assertMetamodelsCompatible(ChangePropagationSpecification potentialChangeProcessor) {
		if (!this.sourceDomain.equals(potentialChangeProcessor.sourceDomain) ||
			!this.targetDomain.equals(potentialChangeProcessor.targetDomain)) {
			throw new IllegalArgumentException("ChangeProcessor metamodels are not compatible");
		}
	}
	
	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val propagationResult = new ChangePropagationResult();
		for (changeProcessor : allProcessors) {
			logger.debug('''Calling change processor «changeProcessor» for change event «change»''');
			val currentPropagationResult = changeProcessor.propagateChange(change, correspondenceModel);
			propagationResult.integrateResult(currentPropagationResult);
		}
		return propagationResult;
	}
	
	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		for (changeProcessor : allProcessors) {
			if (changeProcessor.doesHandleChange(change, correspondenceModel)) {
				return true;
			}
		}
		return false;
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		super.setUserInteracting(userInteracting)
		for (changeProcessor : allProcessors) {
			changeProcessor.setUserInteracting(userInteracting);
		}
	}
	
	private def getAllProcessors() {
		val processors = new ArrayList<ChangePropagationSpecification>();
		// processor arrays can be null when calling setUserInteracting from the super constructor
		if (changePreprocessors !== null) processors += changePreprocessors;
		if (changeMainprocessors !== null) processors += changeMainprocessors;
		return processors;
	}
	
	override objectCreated(EObject createdObject) {
		notifyObjectCreated(createdObject)
	}
	
}