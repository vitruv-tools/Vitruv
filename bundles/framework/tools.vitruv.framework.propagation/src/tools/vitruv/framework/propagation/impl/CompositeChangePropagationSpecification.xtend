package tools.vitruv.framework.propagation.impl

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import org.apache.log4j.Logger
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.propagation.ChangePropagationObserver
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.propagation.ResourceAccess
import org.eclipse.xtend.lib.annotations.Accessors

abstract class CompositeChangePropagationSpecification extends AbstractChangePropagationSpecification implements ChangePropagationObserver {
	static val logger = Logger.getLogger(CompositeChangePropagationSpecification);

	@Accessors(PROTECTED_GETTER)
	val List<ChangePropagationSpecification> changePreprocessors;
	@Accessors(PROTECTED_GETTER)
	val List<ChangePropagationSpecification> changeMainprocessors;

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
		changePropagationSpecifcation.userInteractor = userInteractor;
		changePropagationSpecifcation.registerObserver(this);
	}

	/** 
	 * Adds the specified change processor as a main processor, which is executed after the preprocessors.
	 * The main processors are executed in the order in which they are added.
	 */
	protected def addChangeMainprocessor(ChangePropagationSpecification changePropagationSpecifcation) {
		assertMetamodelsCompatible(changePropagationSpecifcation);
		changeMainprocessors += changePropagationSpecifcation;
		changePropagationSpecifcation.userInteractor = userInteractor;
		changePropagationSpecifcation.registerObserver(this);
	}

	private def void assertMetamodelsCompatible(ChangePropagationSpecification potentialChangeProcessor) {
		if (!this.sourceDomain.equals(potentialChangeProcessor.sourceDomain) ||
			!this.targetDomain.equals(potentialChangeProcessor.targetDomain)) {
			throw new IllegalArgumentException("ChangeProcessor metamodels are not compatible");
		}
	}

	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		propagateChangeViaPreprocessors(change, correspondenceModel, resourceAccess);
		propagateChangeViaMainprocessors(change, correspondenceModel, resourceAccess);
	}
	
	protected def propagateChangeViaPreprocessors(TransactionalChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		for (changeProcessor : changePreprocessors) {
			logger.trace('''Calling change preprocessor «changeProcessor» for change event «change»''');
			changeProcessor.propagateChange(change, correspondenceModel, resourceAccess);
		}
	}
	
	protected def propagateChangeViaMainprocessors(TransactionalChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		for (changeProcessor : changeMainprocessors) {
			logger.trace('''Calling change mainprocessor «changeProcessor» for change event «change»''');
			changeProcessor.propagateChange(change, correspondenceModel, resourceAccess);
		}
	}

	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		for (changeProcessor : allProcessors) {
			if (changeProcessor.doesHandleChange(change, correspondenceModel)) {
				return true;
			}
		}
		return false;
	}

	override setUserInteractor(UserInteractor userInteractor) {
		super.setUserInteractor(userInteractor)
		for (changeProcessor : allProcessors) {
			changeProcessor.setUserInteractor(userInteractor);
		}
	}

	private def getAllProcessors() {
		val processors = new ArrayList<ChangePropagationSpecification>();
		// processor arrays can be null when calling setUserInteractor from the super constructor
		if (changePreprocessors !== null) processors += changePreprocessors;
		if (changeMainprocessors !== null) processors += changeMainprocessors;
		return processors;
	}

	override objectCreated(EObject createdObject) {
		notifyObjectCreated(createdObject)
	}

}
