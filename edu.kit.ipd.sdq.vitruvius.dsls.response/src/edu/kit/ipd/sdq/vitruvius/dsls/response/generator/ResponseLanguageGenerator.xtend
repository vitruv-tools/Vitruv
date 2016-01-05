package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
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

class ResponseLanguageGenerator implements IGenerator {
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		generateDummyImportsClass(resource, fsa);
		generateDelegatorClass(resource, fsa);
		val modelCorrepondenceToResponseMap = generateResponses(resource.contents.head as ResponseFile, fsa);
		generateExecutors(modelCorrepondenceToResponseMap, fsa)
	}
	
	private def generateExecutors(Map<Pair<VURI, VURI>, List<String>> modelCorrepondenceToResponseMap, IFileSystemAccess fsa) {
		for (modelCombination : modelCorrepondenceToResponseMap.keySet) {
			val executorContent = generateExecutor(modelCombination, modelCorrepondenceToResponseMap.get(modelCombination));
			fsa.generateFile(modelCombination.executorName + ".xtend", executorContent);
		}
	}
	
	private def getExecutorName(Pair<VURI, VURI> modelPair) '''
		ResponseFor«modelPair.first.fileExtension»To«modelPair.second.fileExtension»Executor'''
	
	def generateExecutor(Pair<VURI, VURI> modelPair, List<String> responseNames) {
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
		
		return ih.generateImportCode + classImplementation
	}
			
	private def generateDummyImportsClass(Resource resource, IFileSystemAccess fsa) {
		val file = (resource.contents.head as ResponseFile);
		val ih = new ImportHelper();
		
		val classImplementation = '''
		//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
		
		public class «resource.className.generatedClassName» {
			// Used metamodels
			«FOR imp : file.imports»
				// import «VURI.getInstance(imp.package.nsURI).toString»
				public final static String MM_«imp.name.toUpperCase» = "«imp.package.nsURI»";
			«ENDFOR»
			«FOR id : file.imports.map[name.toUpperCase]»
				public final static «ih.typeRef(VURI)» VURI_«id» = «ih.typeRef(VURI)».getInstance(MM_«id»);
			«ENDFOR»
						
		}
		'''
		
		fsa.generateFile(resource.className.generatedClassName + ".xtend", '''
		«ih.generateImportCode»
		
		«classImplementation»'''
		);
	}
	
//	private def generateExecutorClass(Resource resource, IFileSystemAccess fsa) {
//		val ih = new ImportHelper();
//		val classImplementation = '''
//		public class «resource.className.generatedClassName»Executor implements «ih.typeRef(AbstractResponseExecutor)» {
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
	
	private def generateDelegatorClass(Resource resource, IFileSystemAccess fsa) {
		val ih = new ImportHelper();
		val classImplementation = '''
		public class «resource.className.generatedClassName»Delegator implements «ih.typeRef(Change2CommandTransforming)» {
			public override void transformChanges2Commands(«ih.typeRef(Blackboard)» blackboard) {
				// Walk through changes and find fitting Responses from a HashMap or similar
			}
			
			public override «ih.typeRef(List)»<«ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)
				»>> getTransformableMetamodels() {
				// Hold a map that contains response model combinations
				return null;
			}
		}
		'''
		
		fsa.generateFile(resource.className.generatedClassName + "Delegator.xtend", '''
		«ih.generateImportCode»
		
		«classImplementation»'''
		);
	}
	
	private def Map<Pair<VURI, VURI>, List<String>> generateResponses(ResponseFile file, IFileSystemAccess fsa) {
		val modelCorrespondenceToResponseNameMap = new HashMap<Pair<VURI, VURI>, List<String>>;
		for (response : file.responses) {
			val responseName = response.responseName;
			val source = VURI.getInstance(response.trigger.metamodel.package.nsURI);
			// TODO HK correctly implement target calculation
			val target = VURI.getInstance(response.trigger.metamodel.package.nsURI);
			val sourceTargetPair = new Pair<VURI, VURI>(source, target);
			if (!modelCorrespondenceToResponseNameMap.containsKey(sourceTargetPair)) {
				modelCorrespondenceToResponseNameMap.put(sourceTargetPair, new ArrayList<String>());
			}
			modelCorrespondenceToResponseNameMap.get(sourceTargetPair).add(responseName);
			fsa.generateFile(response.responseName + ".xtend", toXtendCode(response, responseName))
		}	
		return modelCorrespondenceToResponseNameMap;
	}
	
	private def String getResponseName(Response response) '''
		ResponseTo«response.trigger.event.responseNameForEvent»'''
	
	private def dispatch String getResponseNameForEvent(Event event) {
		throw new UnsupportedOperationException("Response name fragment is not defined for this event type.")
	}
	
	private def dispatch String getResponseNameForEvent(ModelChangeEvent event) '''
		«event.change.name»Of«event.feature.feature.name.toFirstUpper»In«event.feature.element.name.toFirstUpper»'''

	private def getGeneratedClassName(String sourceClassName) {
		return sourceClassName + "Responses"
	}
	
	private def toXtendCode(Response response, String className) {
		var ih = new ImportHelper();
		val classImplementation = '''
		public class «className» implements «ih.typeRef(ResponseRealization)» {
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
			
			private def «ih.typeRef(List)»<«ih.typeRef(EClass)»> determineAffectedModels() {
				// Implement affected model examination
				return new «ih.typeRef(ArrayList)»<«ih.typeRef(EClass)»>();
			}
			
			public override applyEvent(«ih.typeRef(EChange)» event) {
				val logger = «ih.typeRef(Logger)».getLogger(«className»);
				logger.setLevel(«ih.typeRef(Level)».DEBUG);
				logger.debug("Called response " + this + " with event " + event);
				if (!checkPrecondition(event)) {
					return new «ih.typeRef(TransformationResult)»();
				}
				
				logger.debug("Execute response " + this + " due to matching precondition");
				val affectedModels = determineAffectedModels();
				affectedModels.forEach[affectedModel | performResponseTo(event, affectedModel)];
				return new «ih.typeRef(TransformationResult)»();
			}
			
			private def performResponseTo(«ih.typeRef(EChange)» event, «ih.typeRef(EClass)» affectedModel) «response.effects.toXtendCode»
		}
		'''
		
		return '''
		«ih.generateImportCode»
		
		«classImplementation»'''
	}
	
	private def toXtendCode(Effects effects) {
		NodeModelUtils.getNode(effects.code.code).text}
	
	
	private def getClassName(Resource res) {
		var name = res.URI.lastSegment
		return name.substring(0, name.indexOf('.'))
	}
	
}