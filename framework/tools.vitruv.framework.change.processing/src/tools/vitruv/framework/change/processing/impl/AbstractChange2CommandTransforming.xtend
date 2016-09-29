package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.userinteraction.UserInteracting
import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.ChangeProcessor
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.processing.Change2CommandTransforming
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.TransformationResult

package abstract class AbstractChange2CommandTransforming implements Change2CommandTransforming {
	private final static val LOGGER = Logger.getLogger(AbstractChange2CommandTransforming);
	
	private UserInteracting userInteracting;
	private val MetamodelPair metamodelPair;
	private val List<ChangeProcessor> changePreprocessors;
	private val List<ChangeProcessor> changeMainprocessors;
	
	new(VURI fromMetamodel, VURI toMetamodel) {
		this.metamodelPair = new MetamodelPair(fromMetamodel, toMetamodel);
		this.changePreprocessors = new ArrayList<ChangeProcessor>();
		this.changeMainprocessors = new ArrayList<ChangeProcessor>();
	}
	 
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
	
	override MetamodelPair getTransformableMetamodels() {
		return metamodelPair;
	}
	
	/** 
	 * Adds the specified change processor as a preprocessor, which is executed before the mainprocessors.
	 * The preprocessors are executed in the order in which they are added.
	 */
	protected def addChangePreprocessor(ChangeProcessor changeProcessor) {
		this.changePreprocessors += changeProcessor;
	}
	
	/** 
	 * Adds the specified change processor as a main processor, which is executed after the preprocessors.
	 * The main processors are executed in the order in which they are added.
	 */
	protected def addChangeMainprocessor(ChangeProcessor changeProcessor) {
		this.changeMainprocessors += changeProcessor;
	}
	
	override transformChange2Commands(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val propagationResult = new TransformationResult();
		for (changeProcessor : changePreprocessors + changeMainprocessors) {
			LOGGER.debug('''Calling change processor «changeProcessor» for change event «change»''');
			val currentPropagationResult = changeProcessor.propagateChange(change, correspondenceModel);
			propagationResult.integrateTransformationResult(currentPropagationResult);
		}
		return propagationResult;
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
		this.cleanAndSetup();
	}

	private def void cleanAndSetup() {
		this.changePreprocessors.clear();
		this.changeMainprocessors.clear();
		setup();
	}
	
	/**
	 * Adds the {@link ChangePreprocessor}s in order in which they are to be executed.
	 */
	protected abstract def void setup();
	
}
