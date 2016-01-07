package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import org.eclipse.emf.ecore.EClass
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Event
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChangeEvent
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effects
import org.apache.log4j.Logger
import org.apache.log4j.Level
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
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

class ResponseLanguageGenerator implements IGenerator {
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		//generateDummyImportsClass(resource, fsa);
		//generateDelegatorClass(resource, fsa);
		val modelCorrepondenceToResponseMap = generateResponses(resource.contents.head as ResponseFile, fsa);
		generateExecutorsAndChange2CommandTransformings(modelCorrepondenceToResponseMap, fsa)
		generateChange2CommandTransformingProviding(modelCorrepondenceToResponseMap.keySet, fsa);
	}
	
	private def void generateChange2CommandTransformingProviding(Set<Pair<VURI, VURI>> modelCorrespondences, IFileSystemAccess fsa) {
		val ih = new ImportHelper();	

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
		val ih = new ImportHelper();	

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
		val ih = new ImportHelper();	
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
		for (response : file.responses) {
			val responseName = response.responseName;
			val sourceTargetPair = response.sourceTargetPair;
			if (!modelCorrespondenceToResponseNameMap.containsKey(sourceTargetPair)) {
				modelCorrespondenceToResponseNameMap.put(sourceTargetPair, new ArrayList<String>());
			}
			modelCorrespondenceToResponseNameMap.get(sourceTargetPair).add(responseName);
			fsa.generateFile(sourceTargetPair.getResponseFilePath(responseName), generateResponse(response, responseName));
		}	
		return modelCorrespondenceToResponseNameMap;
	}
	
	private def Pair<VURI, VURI> getSourceTargetPair(Response response) {
		val source = VURI.getInstance(response.trigger.metamodel.package.nsURI);
		val target = VURI.getInstance(response.trigger.metamodel.package.nsURI);
		val affectedModel = response.effects.affectedModel;
		if (affectedModel!= null) {
			// TODO HK correctly implement target calculation 
		}
		val sourceTargetPair = new Pair<VURI, VURI>(source, target);
		return sourceTargetPair
	}
		
	private def generateResponse(Response response, String className) {
		var ih = new ImportHelper();
		val classImplementation = '''
		public class «className» implements «ih.typeRef(ResponseRealization)» {
			private static val «ih.typeRef(Logger)» LOGGER = {
				val initializedLogger = «ih.typeRef(Logger)».getLogger(«className»);
				initializedLogger.setLevel(«ih.typeRef(Level)».DEBUG);
				return initializedLogger;
			}
		
			public static def Class<? extends EChange> getTrigger() {
				«IF response.trigger.event instanceof ModelChangeEvent»
				return «ih.typeRef((response.trigger.event as ModelChangeEvent).change)»;
				«ELSE»
				return null;
				«ENDIF»
			}
		
			private def checkPrecondition(«ih.typeRef(EChange)» event) { 
				«IF response.trigger.event instanceof ModelChangeEvent»
				if (event instanceof «ih.typeRef((response.trigger.event as ModelChangeEvent).change)»«
					//ih.typeRef((response.trigger.event as ModelChangeEvent).feature.element.EStructuralFeatures.filter(ft | ft.name = (response.trigger.event as ModelChangeEvent).feature.feature
					//ih.typeRef((response.trigger.event as ModelChangeEvent).feature.feature.EType)
					»«IF !(response.trigger.event as ModelChangeEvent).change.instanceClass.equals(EChange)»«
					»<?>«ENDIF») {
					«IF EFeatureChange.isAssignableFrom((response.trigger.event as ModelChangeEvent).change.instanceClass)»
					val feature = (event as «ih.typeRef(EFeatureChange)»<?>).affectedFeature;
					«val modelChangeEvent = (response.trigger.event as ModelChangeEvent)»
					if (feature.name.equals("«modelChangeEvent.feature.feature.name»")
						&& event.oldAffectedEObject instanceof «ih.typeRef(modelChangeEvent.feature.element)») {
						return true;
					}
					«ENDIF»
				}
				«ENDIF»
				return false;
			}
		
			«IF response.effects.affectedModel != null»
			private def «ih.typeRef(List)»<«ih.typeRef(EClass)»> determineAffectedModels() {
				val affectedModels = new «ih.typeRef(ArrayList)»<«ih.typeRef(EClass)»>();
				«/* TODO HK implement affecteModel determination code here */» 
				return affectedModels;
			}
			
			«ENDIF»
			public override applyEvent(«ih.typeRef(EChange)» event) {
				LOGGER.debug("Called response " + this.class.name + " with event " + event);
				
				// Check if the event matches the trigger of the response
				if (!checkPrecondition(event)) {
					return new «ih.typeRef(TransformationResult)»();
				}
				LOGGER.debug("Passed precondition check of response " + this.class.name);
				
				«IF response.effects.affectedModel != null»
				val affectedModels = determineAffectedModels();
				affectedModels.forEach[affectedModel | 
					LOGGER.debug("Execute response " + this.class.name + " for model " + affectedModel);
					performResponseTo(event, affectedModel)];
				«ELSE»
					LOGGER.debug("Execute response " + this.class.name + " with no affected model");
					performResponseTo(event);
				«ENDIF»
				
				return new «ih.typeRef(TransformationResult)»();
			}
			
			private def performResponseTo(«ih.typeRef(EChange)» event«
				IF response.effects.affectedModel != null», «ih.typeRef(EClass)» affectedModel«ENDIF
				»)«response.effects.toXtendCode»
		}
		'''
		
		return generateClass(response.sourceTargetPair.packageQualifiedName, ih, classImplementation);
	}
	
	private def String getResponseName(Response response) '''
		ResponseTo«response.trigger.event.responseNameForEvent»'''
	
	private def dispatch String getResponseNameForEvent(Event event) {
		throw new UnsupportedOperationException("Response name fragment is not defined for this event type.")
	}
	
	private def dispatch String getResponseNameForEvent(ModelChangeEvent event) '''
		«event.change.name»Of«event.feature.feature.name.toFirstUpper»In«event.feature.element.name.toFirstUpper»'''
		
	private def toXtendCode(Effects effects) {
		NodeModelUtils.getNode(effects.code.code).text}
	
//	
//	private def getClassName(Resource res) {
//		var name = res.URI.lastSegment
//		return name.substring(0, name.indexOf('.'))
//	}

//	private def getGeneratedClassName(String sourceClassName) {
//		return sourceClassName + "Responses"
//	}
	
//	private def generateDummyImportsClass(Resource resource, IFileSystemAccess fsa) {
//		val file = (resource.contents.head as ResponseFile);
//		val ih = new ImportHelper();
//		
//		val classImplementation = '''
//		//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
//		
//		public class «resource.className.generatedClassName» {
//			// Used metamodels
//			«FOR imp : file.imports»
//				// import «VURI.getInstance(imp.package.nsURI).toString»
//				public final static String MM_«imp.name.toUpperCase» = "«imp.package.nsURI»";
//			«ENDFOR»
//			«FOR id : file.imports.map[name.toUpperCase]»
//				public final static «ih.typeRef(VURI)» VURI_«id» = «ih.typeRef(VURI)».getInstance(MM_«id»);
//			«ENDFOR»
//						
//		}
//		'''
//		
//		fsa.generateFile(resource.className.generatedClassName + ".xtend", '''
//		«ih.generateImportCode»
//		
//		«classImplementation»'''
//		);
//	}
	
//	private def generateDelegatorClass(Resource resource, IFileSystemAccess fsa) {
//		val ih = new ImportHelper();
//		val classImplementation = '''
//		public class «resource.className.generatedClassName»Delegator implements «ih.typeRef(Change2CommandTransforming)» {
//			public override void transformChanges2Commands(«ih.typeRef(Blackboard)» blackboard) {
//				// Walk through changes and find fitting Responses from a HashMap or similar
//			}
//			
//			public override «ih.typeRef(List)»<«ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)
//				»>> getTransformableMetamodels() {
//				// Hold a map that contains response model combinations
//				return null;
//			}
//		}
//		'''
//		
//		fsa.generateFile(resource.className.generatedClassName + "Delegator.xtend", '''
//		«ih.generateImportCode»
//		
//		«classImplementation»'''
//		);
//	}
}