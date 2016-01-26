package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChangeEvent

import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import static edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorConstants.*;
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorUtils.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import java.util.ArrayList
import java.util.List
import edu.kit.ipd.sdq.vitruvius.dsls.response.executor.ResponseRuntimeHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import org.apache.log4j.Logger
import org.apache.log4j.Level
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject
import org.eclipse.emf.ecore.EClass
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseRealization

class SingleOldChangeResponseGenerator implements ISingleResponseGenerator {
	private Response response;
	private ModelChangeEvent changeEvent;
	private XtendImportHelper ih;
	private boolean hasAffectedModel;
	private boolean hasPreconditionBlock;
	
	protected new(Response response) {
		if (!(response.trigger instanceof ModelChangeEvent)) {
			throw new IllegalArgumentException("Response must be triggered by a change event")
		}
		this.response = response;
		this.changeEvent = response.trigger as ModelChangeEvent;
		this.hasAffectedModel = response.effects.targetModel != null;
		this.hasPreconditionBlock = response.effects.perModelPrecondition != null;
		this.ih = new XtendImportHelper();
	}
	
	
	public override generateResponseClass(String packageName, String className) {
		val classImplementation = '''
		public class «className» implements «ih.typeRef(ResponseRealization)» {
			«generateLoggerInitialization(className)»
			
			«generateMethodGetTrigger()»
			
			«generateMethodCheckPrecondition()»
			
			«IF hasAffectedModel»
			«generateMethodDetermineAffectedModels(response.effects.targetModel.rootModelElement.modelElement)»
			
			«ENDIF»
			«IF hasPreconditionBlock»
			«generateMethodCheckPerModelPrecondition»
			
			«ENDIF»
			«generateMethodApplyChange»
			
			«generateMethodPerformResponse»
		}
		'''
		
		return generateClass(packageName, ih, classImplementation);
	}
	
	private def generateLoggerInitialization(String className) '''
		private static val «ih.typeRef(Logger)» LOGGER = {
			val initializedLogger = «ih.typeRef(Logger)».getLogger(«className»);
			initializedLogger.setLevel(«ih.typeRef(Level)».DEBUG);
			return initializedLogger;
		}
	'''
	
	private def generateMethodCheckPrecondition() '''
		private def checkPrecondition(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME») { 
			if («CHANGE_PARAMETER_NAME» instanceof «ih.typeRef(changeEvent.change)»«
				val change = changeEvent.change»«
				IF !change.instanceClass.equals(EChange)»<?>«ENDIF») {
				«IF EFeatureChange.isAssignableFrom(change.instanceClass)»
				val feature = («CHANGE_PARAMETER_NAME» as «ih.typeRef(EFeatureChange)»<?>).affectedFeature;
				«/* TODO HK We could compare something more safe like <MM>PackageImpl.eINSTANCE.<ELEMENT>_<FEATURE>.*/»
				if (feature.name.equals("«changeEvent.feature.feature.name»")
					&& «CHANGE_PARAMETER_NAME».oldAffectedEObject instanceof «ih.typeRef(changeEvent.feature.element)») {
					return true;
				}
				«ELSE»
					«IF CreateRootEObject.isAssignableFrom(change.instanceClass)»
					val element = («CHANGE_PARAMETER_NAME» as «ih.typeRef(CreateRootEObject)»<?>).newValue;
					«ELSEIF DeleteRootEObject.isAssignableFrom(change.instanceClass)»
					val element = («CHANGE_PARAMETER_NAME» as «ih.typeRef(DeleteRootEObject)»<?>).oldValue
					«ELSEIF ReplaceRootEObject.isAssignableFrom(change.instanceClass)»
					val element = («CHANGE_PARAMETER_NAME» as «ih.typeRef(ReplaceRootEObject)»<?>).oldValue;
					«ELSE»throw new «ih.typeRef(IllegalStateException)»()«ENDIF»
					if (element instanceof «ih.typeRef(changeEvent.feature.element)») {
						return true;
					}
				«ENDIF»
			}
			return false;
		}
	'''
	
	private def generateMethodDetermineAffectedModels(EClass affectedElementClass) '''
		private def «ih.typeRef(List)»<«ih.typeRef(affectedElementClass)»> determineAffectedModels(«
			changeEventTypeString» «CHANGE_PARAMETER_NAME», «ih.typeRef(Blackboard)» blackboard) {
			val affectedModels = new «ih.typeRef(ArrayList)»<«ih.typeRef(affectedElementClass)»>();
			«IF EFeatureChange.isAssignableFrom(changeEvent.change.instanceClass)»
			val changedObject = «CHANGE_PARAMETER_NAME».oldAffectedEObject;
			val root = changedObject.«ih.callExtensionMethod(ResponseRuntimeHelper, "modelRoot")»; 
			affectedModels += blackboard.correspondenceInstance.«ih.callExtensionMethod(ResponseRuntimeHelper,
				'''getCorrespondingObjectsOfType(root, «ih.typeRef(affectedElementClass)»)''')»;
			«ENDIF»
			return affectedModels;
		}
	'''
	
	private def generateMethodGetTrigger() '''
		public static def Class<? extends EChange> getTrigger() {
			«IF response.trigger instanceof ModelChangeEvent»
			return «ih.typeRef((response.trigger as ModelChangeEvent).change)»;
			«ELSE»
			return null;
			«ENDIF»
		}
	'''

	private def generateMethodCheckPerModelPrecondition() '''
		private def boolean checkPerModelPrecondition(«changeEventTypeString» «CHANGE_PARAMETER_NAME»«
			IF response.effects.targetModel != null», «ih.typeRef(response.effects.targetModel.rootModelElement.modelElement)» affectedModel«ENDIF
			»)«response.effects.perModelPrecondition.xtendCode»
	'''
			
	private def generateMethodApplyChange() '''
		public override «RESPONSE_APPLY_METHOD_NAME»(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME», «
			ih.typeRef(Blackboard)» blackboard) {
			LOGGER.debug("Called response " + this.class.name + " with event " + «CHANGE_PARAMETER_NAME»);
			
			// Check if the event matches the trigger of the response
			if (!checkPrecondition(«CHANGE_PARAMETER_NAME»)) {
				return new «ih.typeRef(TransformationResult)»();
			}
			LOGGER.debug("Passed precondition check of response " + this.class.name);
			
			val typedChange = «CHANGE_PARAMETER_NAME» as «changeEventTypeString»;
			«IF hasAffectedModel»
			val affectedModels = determineAffectedModels(typedChange, blackboard);
			for (affectedModel : affectedModels) {
				LOGGER.debug("Execute response " + this.class.name + " for model " + affectedModel);
				var preconditionPassed = true;
				«IF hasPreconditionBlock»
				preconditionPassed = checkPerModelPrecondition(typedChange, affectedModel);
				«ENDIF»
				if (preconditionPassed) {
					performResponseTo(typedChange, affectedModel);
				}
			}
			«ELSE»
			LOGGER.debug("Execute response " + this.class.name + " with no affected model");
			var preconditionPassed = true;
			«IF hasPreconditionBlock»
			preconditionPassed = checkPerModelPrecondition(typedChange);
			«ENDIF»
			if (preconditionPassed) {
				performResponseTo(typedChange);
			}
			«ENDIF»
			
			return new «ih.typeRef(TransformationResult)»();
		}
	'''	
	
	private def generateMethodPerformResponse() '''
		private def performResponseTo(«changeEventTypeString» «CHANGE_PARAMETER_NAME»«
			IF hasAffectedModel», «ih.typeRef(response.effects.targetModel.rootModelElement.modelElement)» affectedModel«ENDIF
			»)«response.effects.codeBlock.xtendCode»
	'''

	private def getChangeEventTypeString() '''
		«ih.typeRef(changeEvent.change)»<«ih.typeRef(changeEvent.change.getGenericTypeParameterFQNOfChange(changeEvent.feature))»>'''
		
}
