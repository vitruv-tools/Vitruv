package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ArbitraryModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ArbitraryTargetMetamodelInstanceUpdate
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicRootObjectChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicConcreteModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MirBaseFactory
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.SimpleTextXBlockExpression
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MultiValuedFeatureInsertChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.InsertRootChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelUpdate
import org.eclipse.emf.ecore.resource.Resource

final class ResponseLanguageGeneratorUtils {
	private static val FSA_SEPARATOR = "/";
	private static val XTEND_FILE_EXTENSION = ".java";
	private static val RESPONSES_PACKAGE = "responses";
	
	private new() {}
	
	private static def getFilePath(String qualifiedClassName) '''
		«qualifiedClassName.replace('.', FSA_SEPARATOR)»«XTEND_FILE_EXTENSION»'''
	
	static def String getBasicResponsesPackageQualifiedName() '''
		«RESPONSES_PACKAGE»'''
	
	private static def String getSourceFilePackageQualifiedName(String sourceFileName) '''
		«basicResponsesPackageQualifiedName».«sourceFileName»'''
	
	private static def String getMetamodelPairName(Response response) {
		return response.sourceTargetPair?.metamodelPairName;
	}
	
	private static def String getMetamodelPairName(Pair<VURI, VURI> modelPair) '''
		«IF modelPair.first.lastSegment.nullOrEmpty»«modelPair.first.EMFUri.toString.split("\\.").last.toFirstUpper»«ELSE»«modelPair.first.lastSegment.split("\\.").get(0).toFirstUpper»«ENDIF»To«
		IF modelPair.second.lastSegment.nullOrEmpty»«modelPair.second.EMFUri.toString.split("\\.").last.toFirstUpper»«ELSE»«modelPair.second.lastSegment.split("\\.").get(0).toFirstUpper»«ENDIF»'''
	
	private static def String getPackageName(Pair<VURI, VURI> modelPair) '''
		responses«modelPair.metamodelPairName»'''
	
	private static def String getPackageQualifiedName(String sourceFileName, Pair<VURI, VURI> modelPair) '''
		«basicResponsesPackageQualifiedName».«sourceFileName».«modelPair?.packageName»'''
	
	static def String getChange2CommandTransformingProvidingName() '''
		ResponseChange2CommandTransformingProviding'''
		
	private static def String getChange2CommandTransformingProvidingQualifiedName() '''
		«basicResponsesPackageQualifiedName».«change2CommandTransformingProvidingName»'''
		
	static def String getChange2CommandTransformingProvidingFilePath() '''
		«change2CommandTransformingProvidingQualifiedName.filePath»'''
			
	static def String getExecutorName(Pair<VURI, VURI> modelPair) '''
		Response«modelPair.metamodelPairName»Executor'''
		
	static def String getExecutorQualifiedPackageName(Resource responseResource, Pair<VURI, VURI> modelPair) '''
		«basicResponsesPackageQualifiedName».«responseResource.packageNameForResource».«modelPair.packageName»'''
		
	static def String getExecutorQualifiedName(Resource responseResource, Pair<VURI, VURI> modelPair) '''
		«responseResource.getExecutorQualifiedPackageName(modelPair)».«modelPair.executorName»'''
	
	static def String getExecutorFilePath(Resource responseResource, Pair<VURI, VURI> modelPair) '''
		«responseResource.getExecutorQualifiedName(modelPair).filePath»'''
	
	static def String getChange2CommandTransformingName(Pair<VURI, VURI> modelPair) '''
		Response«modelPair.metamodelPairName»Change2CommandTransforming'''
		
	static def String getChange2CommandTransformingQualifiedPackageName(Pair<VURI, VURI> modelPair) '''
		«basicResponsesPackageQualifiedName»'''	
	
	static def String getChange2CommandTransformingQualifiedName(Pair<VURI, VURI> modelPair) '''
		«modelPair.change2CommandTransformingQualifiedPackageName».«modelPair.change2CommandTransformingName»'''
	
	static def String getChange2CommandTransformingFilePath(Pair<VURI, VURI> modelPair) '''
		«modelPair.change2CommandTransformingQualifiedName.filePath»'''
	
	static def String getResponseQualifiedName(Response response) '''
		«getPackageQualifiedName(response.eResource.getPackageNameForResource, response.sourceTargetPair)».«response.responseName»'''
	
	static def String getResponseFilePath(Response response) '''
		«getResponseQualifiedName(response).filePath»'''
	
	private static def String getPackageNameForResource(Resource resource) {
		return resource.URI.lastSegment.split("\\.").get(0);
	}
	
	static def generateClass(String packageName, XtendImportHelper importHelper, CharSequence classImplementation) '''
		package «packageName»;
		
		«importHelper.generateImportCode»
		
		«classImplementation»
		'''

	static def Pair<VURI, VURI> getSourceTargetPair(Response response) {
		val sourceVURI = response.sourceVURI;
		val targetVURI = response.targetVURI;
		if (sourceVURI != null && targetVURI != null) {
			return new Pair<VURI, VURI>(sourceVURI, targetVURI);
		} else {
			return null;
		}		
	}
	
	private static def VURI getSourceVURI(Response response) {
		val sourceURI = response?.trigger?.sourceMetamodel;
		return sourceURI.VURI;
	}
	
	private static def VURI getTargetVURI(Response response) {
		val targetChange = response?.effects?.targetChange;
		val targetPackage = if (targetChange instanceof ConcreteTargetModelCreate) {
			targetChange.rootElement?.elementType?.element?.EPackage;
		} else if (targetChange instanceof ConcreteTargetModelUpdate) {
			targetChange.identifyingElement?.elementType?.element?.EPackage;
		} else if (targetChange instanceof ArbitraryTargetMetamodelInstanceUpdate) {
			targetChange.metamodelReference?.model?.package;
		}
		
		if (targetPackage == null) {
			return response.sourceVURI;
		} else {
			return targetPackage.VURI;
		}
	}
	
	private static def VURI getVURI(EPackage pckg) {
		return if (pckg?.nsURI != null) {
			var topPckg = pckg;
			while (topPckg.ESuperPackage != null) {
				topPckg = pckg.ESuperPackage;
			}
			VURI.getInstance(topPckg.nsURI);
		} else {
			null;
		}
	}
	
	static def String getResponseName(Response response) {
		if (!response.name.nullOrEmpty) {
			return response.name + "Response";
		} else {
			return '''«response.trigger.responseNameForEvent»Response'''
		}
	}
	
	static def dispatch String getResponseNameForEvent(Trigger trigger) {
		throw new UnsupportedOperationException("Response name fragment is not defined for this event type.")
	}
	
	static def dispatch String getResponseNameForEvent(AtomicRootObjectChange change) {
		return '''«change.class.simpleName»Of«change.changedElement?.element?.name?.toFirstUpper»'''
	}
	
	static def dispatch String getResponseNameForEvent(AtomicFeatureChange change) {
		return '''«change.class.simpleName»Of«IF change.changedFeature?.feature != null»«
			change.changedFeature.feature.name.toFirstUpper»In«ENDIF»«change.changedFeature?.element?.name?.toFirstUpper»'''
	}
	
	static def dispatch String getResponseNameForEvent(ArbitraryModelElementChange event) {
		return '''«event.class.simpleName»In«IF event.changedModel?.model?.name != null»«
			event.changedModel.model.name.toFirstUpper»«ENDIF»'''
	}
	
	static def boolean hasOppositeResponse(Response response) {
		// TODO HK does currently always return false
		val sourceChange = response.trigger;
		val targetChange = response.effects.targetChange;
		if (targetChange instanceof ConcreteTargetModelCreate && 
			sourceChange instanceof AtomicConcreteModelElementChange) {
			val createTargetChange = targetChange as ConcreteTargetModelCreate;
			/*if (createTargetChange.autodelete) {
				return true;
			}*/
		}
		return false;
	}
	
	static def Response getOppositeResponse(Response response) {
		if (response.hasOppositeResponse) {
			val deleteTrigger = response.trigger.deleteTrigger;
			val createTargetChange = response.effects.targetChange as ConcreteTargetModelCreate;
			val deleteResponse = ResponseLanguageFactory.eINSTANCE.createResponse();
			deleteResponse.name = "OppositeResponseForDeleteTo" + response.name;
			deleteResponse.trigger = deleteTrigger;
			val deleteEffects = ResponseLanguageFactory.eINSTANCE.createEffects();
			val deleteTargetChange = ResponseLanguageFactory.eINSTANCE.createConcreteTargetModelUpdate();
			val targetChangeElement = MirBaseFactory.eINSTANCE.createModelElement();
			targetChangeElement.element = createTargetChange.rootElement.elementType.element;
			val correspondingModelElementSpecification = ResponseLanguageFactory.eINSTANCE.createCorrespondingModelElementDelete();
			correspondingModelElementSpecification.elementType = targetChangeElement;
			deleteTargetChange.identifyingElement = correspondingModelElementSpecification;
			correspondingModelElementSpecification.correspondenceSource = ResponseLanguageFactory.eINSTANCE.createCorrespondingObjectCodeBlock();
			correspondingModelElementSpecification.correspondenceSource.code = new SimpleTextXBlockExpression('''return change.getOldValue();''');
			deleteEffects.targetChange = deleteTargetChange;
			deleteResponse.effects = deleteEffects;
			return deleteResponse;
		}
		return null;
	}
	  
	private static def dispatch Trigger getDeleteTrigger(Trigger change) {
		return null;
	}
	
	private static def dispatch Trigger getDeleteTrigger(MultiValuedFeatureInsertChange change) {
		val deleteTrigger = ResponseLanguageFactory.eINSTANCE.createMultiValuedFeatureRemoveChange();
		val changedElement = MirBaseFactory.eINSTANCE.createFeatureOfElement();
		changedElement.element = change.changedFeature.element;
		changedElement.feature = change.changedFeature.feature;
		deleteTrigger.changedFeature = changedElement;
		return deleteTrigger;
	}
	
	private static def dispatch Trigger getDeleteTrigger(InsertRootChange change) {
		val deleteTrigger = ResponseLanguageFactory.eINSTANCE.createRemoveRootChange();
		val changedElement = MirBaseFactory.eINSTANCE.createModelElement();
		changedElement.element = change.changedElement.element;
		deleteTrigger.changedElement = changedElement;
		return deleteTrigger;
	}
}