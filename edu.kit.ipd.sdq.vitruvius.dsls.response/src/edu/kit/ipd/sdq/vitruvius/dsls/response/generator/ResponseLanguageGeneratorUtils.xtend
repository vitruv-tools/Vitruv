package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ArbitraryModelElementChange
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicRootObjectChange
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses

final class ResponseLanguageGeneratorUtils extends GeneratorUtils {
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
		«modelPair.first.metamodelIdentifier»To«modelPair.second.metamodelIdentifier»'''
	
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
		if (!(response.eContainer instanceof MetamodelPairResponses)) {
			throw new IllegalStateException();
		}
		val metamodels = (response.eContainer as MetamodelPairResponses).affectedMetamodels.map[model.package];
		val sourceMetamodel = response?.trigger?.sourceMetamodel;
		var potentialModels = metamodels.filter[!it.VURI.equals(sourceMetamodel.VURI)];
		if (potentialModels.size > 1) {
			throw new IllegalStateException();
		} else if (potentialModels.size == 0) {
			potentialModels = #[sourceMetamodel];
		}
		
		val targetPackage = potentialModels.get(0);
		return targetPackage.VURI;
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
	
	private static def String getMetamodelIdentifier(URI uri) {
		if (uri.lastSegment.nullOrEmpty) {
			return uri.toString.split("\\.").last.toFirstUpper;
		} else {
			return uri.lastSegment.replace(".", "_").toFirstUpper;
		}
	}
	
	private static def String getMetamodelIdentifier(VURI uri) {
		return uri.EMFUri.metamodelIdentifier;
	}
	
}