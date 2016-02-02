package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile
import java.util.Map
import java.util.HashMap
import edu.kit.ipd.sdq.vitruvius.dsls.response.executor.AbstractResponseExecutor
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationExecuter
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor
import edu.kit.ipd.sdq.vitruvius.dsls.response.executor.DefaultEObjectMappingTransformation
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorUtils.*;
import java.util.Set
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.dsls.response.executor.AbstractResponseChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.dsls.response.executor.AbstractResponseChange2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse.SingleResponseGeneratorFactory
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MultiValuedFeatureInsertChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.InsertRootChange

class ResponseLanguageGenerator implements IGenerator {
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		val modelCorrepondenceToResponseMap = generateResponses(resource.contents.head as ResponseFile, fsa);
		generateExecutorsAndChange2CommandTransformings(modelCorrepondenceToResponseMap, fsa)
		generateChange2CommandTransformingProviding(modelCorrepondenceToResponseMap.keySet, fsa);
	}
	
	private def void generateChange2CommandTransformingProviding(Set<Pair<VURI, VURI>> modelCorrespondences, IFileSystemAccess fsa) {
		val ih = new XtendImportHelper();	

		val classImplementation = '''
		public class «change2CommandTransformingProvidingName» extends «ih.typeRef(AbstractResponseChange2CommandTransformingProviding)» {
			new() {
				val transformationExecutingList = new «ih.typeRef(ArrayList)»<«ih.typeRef(Change2CommandTransforming)»>();
				«FOR modelCorrespondence : modelCorrespondences»
					transformationExecutingList.add(new «ih.typeRef(modelCorrespondence.change2CommandTransformingQualifiedName)»());
				«ENDFOR»
				initializeTransformationExecuterMap(transformationExecutingList);
			}
			
		}
		'''
		
		val classContent = generateClass(basicResponsesPackageQualifiedName, ih, classImplementation);
		fsa.generateFile(change2CommandTransformingProvidingFilePath, classContent);
	}
	
	private def void generateExecutorsAndChange2CommandTransformings(Map<Pair<VURI, VURI>, List<String>> modelCorrepondenceToResponseMap, IFileSystemAccess fsa) {
		for (modelCombination : modelCorrepondenceToResponseMap.keySet) {
			val executorContent = generateExecutor(modelCombination, modelCorrepondenceToResponseMap.get(modelCombination));
			val change2ComandTransformingContent = generateChangeToCommandTransforming(modelCombination);
			fsa.generateFile(modelCombination.executorFilePath, executorContent);
			fsa.generateFile(modelCombination.change2CommandTransformingFilePath, change2ComandTransformingContent);
		}
	}
		
	private def generateChangeToCommandTransforming(Pair<VURI, VURI> modelPair) {
		val ih = new XtendImportHelper();	

		// TODO HK replace DefaultEObjectMappingTransforming with correct one
		val classImplementation = '''
		public class «modelPair.change2CommandTransformingName» extends «ih.typeRef(AbstractResponseChange2CommandTransforming)» {
			protected «ih.typeRef(UserInteractor)» userInteracting;
			
			new() {
				super();
				this.userInteracting = new «ih.typeRef(UserInteractor)»();
				
				// Mapping for EObjects in order to avoid runtime exceptions
				this.transformationExecuter.addMapping(new «ih.typeRef(DefaultEObjectMappingTransformation)»());
				
				// set userInteractor
				this.transformationExecuter.setUserInteracting(this.userInteracting);
			}
			
			public def «ih.typeRef(TransformationExecuter)» getTransformationExecutor() {
				return transformationExecuter;
			}
			
			public override «ih.typeRef(List)»<«ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»>> getTransformableMetamodels() {
				val sourceVURI = «ih.typeRef(VURI)».getInstance("«modelPair.first.EMFUri.toString»");
				val targetVURI = «ih.typeRef(VURI)».getInstance("«modelPair.second.EMFUri.toString»");
				val pair = new «ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»>(sourceVURI, targetVURI);
				return newArrayList(pair);
			}
			
			protected override setup() {
				this.addResponseExecutor(new «modelPair.executorName»());		
			}
			
		}
		'''
		
		return generateClass(modelPair.packageQualifiedName, ih, classImplementation);
	}
	
	private def generateExecutor(Pair<VURI, VURI> modelPair, List<String> responseNames) {
		val ih = new XtendImportHelper();	
		val classImplementation = '''
		public class «modelPair.executorName» extends «ih.typeRef(AbstractResponseExecutor)» {
			protected override setup() {
				«FOR response : responseNames»
				this.addResponse(«ih.typeRef(response)».getTrigger(), new «ih.typeRef(response)»());
				«ENDFOR»
			}
		}
		'''
		
		return generateClass(modelPair.packageQualifiedName, ih, classImplementation);
	}
			
	private def Map<Pair<VURI, VURI>, List<String>> generateResponses(ResponseFile file, IFileSystemAccess fsa) {
		val modelCorrespondenceToResponseNameMap = new HashMap<Pair<VURI, VURI>, List<String>>;
		val List<Response> deleteResponses = <Response>newArrayList();
		for (response : file.responses) {
			deleteResponses += generateResponse(file, response, modelCorrespondenceToResponseNameMap, fsa);
		}
		for (response : deleteResponses) {
			generateResponse(file, response, modelCorrespondenceToResponseNameMap, fsa);
		}
		return modelCorrespondenceToResponseNameMap;
	}
	
	private def Iterable<Response> generateResponse(ResponseFile file, Response response, Map<Pair<VURI, VURI>, List<String>> modelCorrespondenceToResponseNameMap, IFileSystemAccess fsa) {
		val responseName = response.responseName;
		val sourceTargetPair = file.getSourceTargetPair(response);
		if (!modelCorrespondenceToResponseNameMap.containsKey(sourceTargetPair)) {
			modelCorrespondenceToResponseNameMap.put(sourceTargetPair, new ArrayList<String>());
		}
		modelCorrespondenceToResponseNameMap.get(sourceTargetPair).add(responseName);
		val responseGenerator = SingleResponseGeneratorFactory.INSTANCE.createGenerator(response);
		fsa.generateFile(sourceTargetPair.getResponseFilePath(responseName), 
			responseGenerator.generateResponseClass(file.getSourceTargetPair(response).packageQualifiedName, responseName)
		);
		return getRootDeleteIfCreate(response)
	}
	
	private def List<Response> getRootDeleteIfCreate(Response response) {
		val deleteTrigger = response.trigger.deleteTrigger;
		val targetChange = response.effects.targetChange;
		if (targetChange instanceof ConcreteTargetModelRootCreate && deleteTrigger != null) {
			val createTargetChange = targetChange as ConcreteTargetModelRootCreate;
			if (createTargetChange.autodelete) {
				val deleteResponse = ResponseLanguageFactory.eINSTANCE.createResponse();
				deleteResponse.name = "OppositeResponseForDeleteTo" + response.name;
				deleteResponse.trigger = deleteTrigger;
				val deleteEffects = ResponseLanguageFactory.eINSTANCE.createEffects();
				val deleteTargetChange = ResponseLanguageFactory.eINSTANCE.createConcreteTargetModelRootDelete();
				deleteTargetChange.rootModelElement = createTargetChange.rootModelElement;
				deleteEffects.targetChange = deleteTargetChange;
				deleteResponse.effects = deleteEffects;
				return #[deleteResponse];
			}
		}
		return #[];
	}
	
	private def dispatch Trigger getDeleteTrigger(Trigger change) {
		return null;
	}
	
	private def dispatch Trigger getDeleteTrigger(MultiValuedFeatureInsertChange change) {
		val deleteTrigger = ResponseLanguageFactory.eINSTANCE.createMultiValuedFeatureRemoveChange();
		deleteTrigger.changedFeature = change.changedFeature;
		return deleteTrigger;
	}
	
	private def dispatch Trigger getDeleteTrigger(InsertRootChange change) {
		val deleteTrigger = ResponseLanguageFactory.eINSTANCE.createRemoveRootChange();
		deleteTrigger.changedElement = change.changedElement;
		return deleteTrigger;
	}
	
}