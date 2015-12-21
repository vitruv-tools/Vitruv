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

class ResponseLanguageGenerator implements IGenerator {
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		generateDummyImportsClass(resource, fsa);
		generateDelegatorClass(resource, fsa);
		doGenerate(resource.contents.head as ResponseFile, fsa);
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
	
	private def doGenerate(ResponseFile file, IFileSystemAccess fsa) {
		for (response : file.responses) {
			fsa.generateFile(response.responseName + ".xtend", toXtendCode(response, response.responseName))
		}	
	}
	
	private def String getResponseName(Response response) '''
		ResponseTo«response.trigger.event.responseNameForEvent»'''
	
	private def dispatch String getResponseNameForEvent(Event event) {
		throw new UnsupportedOperationException("Response name fragment is not defined for this event type.")
	}
	
	private def dispatch String getResponseNameForEvent(ModelChangeEvent event) '''
		«event.name»Of«event.feature.feature.name.toFirstUpper»In«event.feature.element.name.toFirstUpper»'''

	private def getGeneratedClassName(String sourceClassName) {
		return sourceClassName + "Responses"
	}
	
	private def toXtendCode(Response response, String className) {
		var ih = new ImportHelper();
		val classImplementation = '''
		public class «className» implements «ih.typeRef(ResponseRealization)» {
			private def checkPrecondition(«ih.typeRef(Event)» event) { 
				// TODO implement precondition check
				return false;
			}
			
			private def «ih.typeRef(List)»<«ih.typeRef(EClass)»> determineAffectedModels() {
				// Implement affected model examination
				return new «ih.typeRef(ArrayList)»<«ih.typeRef(EClass)»>();
			}
			
			public override applyEvent(«ih.typeRef(Event)» event) {
				val logger = «ih.typeRef(Logger)».getLogger(«className»);
				logger.setLevel(«ih.typeRef(Level)».DEBUG);
				logger.debug("Called response " + this + " with event " + event);
				if (!checkPrecondition(event)) {
					return new «ih.typeRef(TransformationResult)»();
				}
				
				val affectedModels = determineAffectedModels();
				affectedModels.forEach[affectedModel | performResponseTo(event, affectedModel)];
				return new «ih.typeRef(TransformationResult)»();
			}
			
			private def performResponseTo(«ih.typeRef(Event)» event, «ih.typeRef(EClass)» affectedModel) «response.effects.toXtendCode»
		}
		'''
		
		return '''
		«ih.generateImportCode»
		
		«classImplementation»'''
	}
	
	private def toXtendCode(Effects effects) {
		NodeModelUtils.getNode(effects.code).text}
	
	
	private def getClassName(Resource res) {
		var name = res.URI.lastSegment
		return name.substring(0, name.indexOf('.'))
	}
	
}