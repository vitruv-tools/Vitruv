package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper

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
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ChangeEvent
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.UpdatedModel
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CreatedModel
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge

class SingleChangeResponseGenerator implements ISingleResponseGenerator {
	private Response response;
	private ChangeEvent changeEvent;
	private EClass change;
	private XtendImportHelper ih;
	private boolean hasAffectedModel;
	private boolean hasPreconditionBlock;
	private boolean hasExecutionBlock;
	
	protected new(Response response) {
		if (!(response.trigger instanceof ChangeEvent)) {
			throw new IllegalArgumentException("Response must be triggered by a change event")
		}
		this.response = response;
		this.changeEvent = response.trigger as ChangeEvent;
		if (changeEvent.feature.feature != null) {
			this.change = changeEvent.change.generateEChange(changeEvent.feature.feature)
		} else {
			this.change = changeEvent.change.generateEChange(changeEvent.feature.element)
		}
		this.hasAffectedModel = response.effects.targetModel != null;
		this.hasPreconditionBlock = response.effects.perModelPrecondition != null;
		this.hasExecutionBlock = response.effects.codeBlock != null;
		this.ih = new XtendImportHelper();
	}
	
	
	public override generateResponseClass(String packageName, String className) {
		val classImplementation = '''
		«IF response.documentation != null»«response.documentation»«ENDIF»
		public class «className» implements «ih.typeRef(ResponseRealization)» {
			«generateLoggerInitialization(className)»
			
			«generateMethodGetTrigger()»
			
			«generateMethodCheckPrecondition()»
			
			«IF hasAffectedModel»
			«IF response.effects.targetModel instanceof UpdatedModel»
			«generateMethodDetermineAffectedModels(response.effects.targetModel as UpdatedModel)»
			
			«ELSEIF response.effects.targetModel instanceof CreatedModel»
			«generateMethodGenerateAffectedModel(response.effects.targetModel as CreatedModel)»
			
			«ENDIF»
			«ENDIF»
			«IF hasPreconditionBlock»
			«generateMethodCheckPerModelPrecondition»
			
			«ENDIF»
			«generateMethodApplyChange»

			«IF hasExecutionBlock»
			«generateMethodPerformResponse»
			
			«ENDIF»
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
			if («CHANGE_PARAMETER_NAME» instanceof «ih.typeRef(change)»«
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
	
	private def generateMethodGenerateAffectedModel(CreatedModel createdModel) '''
		«val affectedElementClass = createdModel.rootModelElement.modelElement»
		private def «ih.typeRef(affectedElementClass)» generateAffectedModel(«
			changeEventTypeString» «CHANGE_PARAMETER_NAME», «ih.typeRef(Blackboard)» blackboard) {
			«val createdQNClassName = affectedElementClass.instanceTypeName»
			«var createdClassPackage = createdQNClassName.substring(0, createdQNClassName.length - affectedElementClass.name.length - 1)»
			«var createdClassFactoryName = createdClassPackage + ".impl." + createdClassPackage.split("\\.").last.toFirstUpper + "FactoryImpl"»
			val newRoot = «ih.typeRef(createdClassFactoryName)».eINSTANCE.create«affectedElementClass.name»();
			«IF EFeatureChange.isAssignableFrom(change.instanceClass)»
				«/* TODO HK: This is not correct */»
				val sourceElement = change.newAffectedEObject;
			«ELSEIF CreateRootEObject.isAssignableFrom(change.instanceClass)»
				val sourceElement = change.newValue;
			«ELSEIF DeleteRootEObject.isAssignableFrom(change.instanceClass)»
				val sourceElement = change.oldValue;
			«ENDIF»
			val newModelFileSegments = "«createdModel.name»".split("/")
			«val newModelFileSegments = createdModel.name.split("/")»
			«IF !newModelFileSegments.last.contains(".")»
				// No file extension was specified, add the first one that is the valid for the metamodel
				newModelFileSegments.set(newModelFileSegments.size - 1, newModelFileSegments.last 
					+ "." + blackboard.correspondenceInstance.mapping.metamodelB.fileExtensions.get(0));
			«ENDIF»
			val newResourceURI = sourceElement.eResource.URI.trimSegments(1).appendSegments(newModelFileSegments);
			
			val newModelResource = new «ih.typeRef(ResourceSetImpl)»().createResource(newResourceURI);
			newRoot.id = sourceElement.id;
			newModelResource.contents.add(newRoot);
			blackboard.correspondenceInstance.createAndAddCorrespondence(#[sourceElement], #[newRoot]);
			«ih.typeRef(EcoreResourceBridge)».saveResource(newModelResource);
			return newRoot;
		}
	'''
	
	private def generateMethodDetermineAffectedModels(UpdatedModel updatedModel) '''
		«val affectedElementClass = updatedModel.rootModelElement.modelElement»
		private def «ih.typeRef(List)»<«ih.typeRef(affectedElementClass)»> determineAffectedModels(«
			changeEventTypeString» «CHANGE_PARAMETER_NAME», «ih.typeRef(Blackboard)» blackboard) {
			val affectedModels = new «ih.typeRef(ArrayList)»<«ih.typeRef(affectedElementClass)»>();
			val root =«updatedModel.correspondenceSource.object.xtendCode»
			affectedModels += blackboard.correspondenceInstance.«ih.callExtensionMethod(ResponseRuntimeHelper,
				'''getCorrespondingObjectsOfType(root, «ih.typeRef(affectedElementClass)»)''')»;
			return affectedModels;
		}
	'''
	
	private def generateMethodGetTrigger() '''
		public static def Class<? extends EChange> getTrigger() {
			return «ih.typeRef(change)»;
		}
	'''

	private def generateMethodCheckPerModelPrecondition() '''
		private def boolean checkPerModelPrecondition(«changeEventTypeString» «CHANGE_PARAMETER_NAME»«
			IF response.effects.targetModel != null && response.effects.targetModel instanceof UpdatedModel», «
			ih.typeRef(response.effects.targetModel.rootModelElement.modelElement)» affectedModel«ENDIF
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
			«IF response.effects.targetModel instanceof UpdatedModel»
			val affectedModels = determineAffectedModels(typedChange, blackboard);
			for (affectedModel : affectedModels) {
				var preconditionPassed = true;
				«IF hasPreconditionBlock»
				preconditionPassed = checkPerModelPrecondition(typedChange, affectedModel);
				«ENDIF»
				if (preconditionPassed) {
					LOGGER.debug("Execute response " + this.class.name + " for model " + affectedModel);
					«IF hasExecutionBlock»
					performResponseTo(typedChange, affectedModel);
					«ENDIF»
				}
			}
			«ELSE»
			var preconditionPassed = true;
			«IF hasPreconditionBlock»
				preconditionPassed = checkPerModelPrecondition(typedChange);
			«ENDIF»
			if (preconditionPassed) {
				val affectedModel = generateAffectedModel(typedChange, blackboard);
				LOGGER.debug("Execute response " + this.class.name + " for model " + affectedModel);
				«IF hasExecutionBlock»
					performResponseTo(typedChange, affectedModel);
				«ENDIF»
			}
			«ENDIF»
			«ELSE»
			LOGGER.debug("Execute response " + this.class.name + " with no affected model");
			var preconditionPassed = true;
			«IF hasPreconditionBlock»
			preconditionPassed = checkPerModelPrecondition(typedChange);
			«ENDIF»
			«IF hasExecutionBlock»
			if (preconditionPassed) {
				performResponseTo(typedChange);
			}
			«ENDIF»
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
		«ih.typeRef(change)»<«ih.typeRef(change.getGenericTypeParameterFQNOfChange(changeEvent.feature))»>'''
		
}
