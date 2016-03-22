package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl;

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess
import java.util.Set
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorUtils.*;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import java.util.Map
import java.util.HashMap
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.IResponseEnvironmentGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseExecutor
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile
import com.google.inject.Inject
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.xtext.ui.resource.IResourceSetProvider
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.io.File
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorUtils
import org.eclipse.xtext.generator.IGenerator
import java.util.Collections
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import org.eclipse.xtext.resource.DerivedStateAwareResource
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses

class ResponseEnvironmentGenerator implements IResponseEnvironmentGenerator {
	private static final Logger LOGGER = Logger.getLogger(ResponseEnvironmentGenerator);
	
	@Inject
	private IGenerator generator;
	
	@Inject
	private IResourceSetProvider resourceSetProvider;
		
	private List<Resource> resources;
	private List<Resource> tempResources;
	
	private IProject project;
	
	public new() {
		this.tempResources = newArrayList();
		this.resources = newArrayList();
	}
	
	public override cleanAndSetProject(IProject project) {
		finishGeneration();
		this.project = project;
	}
	
	public override void addResponse(String sourceFileName, Response response) {
		if (project == null) {
			throw new IllegalStateException("Project must be set");
		}
		if (response == null) {
			throw new IllegalArgumentException("Response must not be null");
		}
		val resource = getOrCreateTempResource(sourceFileName);
		// TODO HK This is really ugly
		if ((resource.contents.get(0) as ResponseFile).affectedMetamodels.size == 0) {
			(resource.contents.get(0) as ResponseFile).affectedMetamodels += (response.eContainer as MetamodelPairResponses).affectedMetamodels
		}/* else if (!(resource.contents.get(0) as ResponseFile).affectedMetamodels.equals((response.eContainer as MetamodelPairResponses).affectedMetamodels)) {
			throw new IllegalStateException("Responses from the same source file must have the same two metamodels associated");
		}*/
		(resource.contents.get(0) as ResponseFile).responses += response;
	}
	
	private def Resource getOrCreateTempResource(String sourceFileName) {
		for (res : tempResources) {
			if (res.URI.segmentsList.last.equals(sourceFileName + ".response")) {
				return res;
			}
		}
		return generateTempResource(project, sourceFileName);
	}
	
	public override void addResponses(String sourceFileName, Iterable<Response> responses) {
		responses.forEach[addResponse(sourceFileName, it)];
	}
	
	public def override addResponses(Resource responseResource) {
		if (responseResource == null || !(responseResource.contents.get(0) instanceof ResponseFile)) {
			throw new IllegalArgumentException("The given resource is not a response file");
		}
		this.resources.add(responseResource);
	}
	
	public override void generateEnvironment(IFileSystemAccess2 fsa) {
		if (project == null) {
			throw new IllegalStateException("Project must be set");
		}
		prepareGeneration();
		generate(fsa);
		finishGeneration();
	}
	
	private def prepareGeneration() {
		cleanGeneratedFiles();
	}
	
	private def cleanGeneratedFiles() {
		project.getFolder("src-gen").getFolder(ResponseLanguageGeneratorUtils.basicResponsesPackageQualifiedName.replace(".", File.separator)).delete(0, new NullProgressMonitor());
	}
	
	private def finishGeneration() {
		removeTempResources();
		clearResponsesAndResources();
	}
	
	private def Resource generateTempResource(IProject project, String sourceFileName) {
		val responseFile = ResponseLanguageFactory.eINSTANCE.createResponseFile();
		val resSet = resourceSetProvider.get(project);
		val singleResponseResource = resSet.createResource(URI.createFileURI(System.getProperty("java.io.tmpdir") + "/" + sourceFileName + ".response"));
		singleResponseResource.contents.add(responseFile);
		tempResources += singleResponseResource;
		return singleResponseResource;
	}
			
	private def void removeTempResources() {
		try {
			tempResources.forEach[delete(emptyMap)];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private def clearResponsesAndResources() {
		this.resources.clear();
		this.tempResources.clear();
	}
	
	private def generate(IFileSystemAccess2 fsa) {
		// TODO HK: This is a temporary hack! Fix it.
		for (res : tempResources) {
			(res as DerivedStateAwareResource).discardDerivedState();
			(res as DerivedStateAwareResource).installDerivedState(false);
		}
		val modelCorrespondenceToExecutors = new HashMap<Pair<VURI, VURI>, List<String>>;
		for (resource : resources + tempResources) {
			val modelCorrepondenceToResponseMap = generateResponses(resource, fsa);
			generateExecutors(resource, modelCorrepondenceToResponseMap, fsa, modelCorrespondenceToExecutors);
		}
		
		generateChange2CommandTransformings(modelCorrespondenceToExecutors, fsa)
		generateChange2CommandTransformingProviding(modelCorrespondenceToExecutors.keySet, fsa);
	}

	private def void generateChange2CommandTransformingProviding(Set<Pair<VURI, VURI>> modelCorrespondences, IFileSystemAccess fsa) {
		val ih = new XtendImportHelper();	

		val classImplementation = '''
		public class «change2CommandTransformingProvidingName» extends «ih.typeRef(AbstractResponseChange2CommandTransformingProviding)» {
			public «change2CommandTransformingProvidingName»() {
				«ih.typeRef(List)»<«ih.typeRef(Change2CommandTransforming)»> change2CommandTransformings = new «ih.typeRef(ArrayList)»<«ih.typeRef(Change2CommandTransforming)»>();
				«FOR modelCorrespondence : modelCorrespondences»
					change2CommandTransformings.add(new «ih.typeRef(modelCorrespondence.change2CommandTransformingQualifiedName)»());
				«ENDFOR»
				initializeChange2CommandTransformationMap(change2CommandTransformings);
			}
			
		}
		'''
		
		val classContent = generateClass(basicResponsesPackageQualifiedName, ih, classImplementation);
		fsa.generateFile(change2CommandTransformingProvidingFilePath, classContent);
	}
	
	private def void generateExecutors(Resource responseResource, Map<Pair<VURI, VURI>, List<String>> modelCorrepondenceToResponseMap, IFileSystemAccess fsa, Map<Pair<VURI, VURI>, List<String>> modelCorrespondencesToExecutors) {
		for (modelCombination : modelCorrepondenceToResponseMap.keySet) {
			val executorContent = generateExecutor(responseResource, modelCombination, modelCorrepondenceToResponseMap.get(modelCombination));
			if (!modelCorrespondencesToExecutors.containsKey(modelCombination)) {
				modelCorrespondencesToExecutors.put(modelCombination, <String>newArrayList());
			}
			modelCorrespondencesToExecutors.get(modelCombination).add(getExecutorQualifiedName(responseResource, modelCombination));
			fsa.generateFile(getExecutorFilePath(responseResource, modelCombination), executorContent);
		}
	}
	
	private def void generateChange2CommandTransformings(Map<Pair<VURI, VURI>, List<String>> modelCorrepondenceToExecutors, IFileSystemAccess fsa) {
		for (modelCombination : modelCorrepondenceToExecutors.keySet) {
			val change2ComandTransformingContent = generateChangeToCommandTransforming(modelCombination, modelCorrepondenceToExecutors.get(modelCombination));
			fsa.generateFile(modelCombination.change2CommandTransformingFilePath, change2ComandTransformingContent);
		}
	}
		
	private def generateChangeToCommandTransforming(Pair<VURI, VURI> modelPair, List<String> executorsNames) {
		val ih = new XtendImportHelper();	

		val classImplementation = '''
		public class «modelPair.change2CommandTransformingName» extends «ih.typeRef(AbstractResponseChange2CommandTransforming)» {
			public «ih.typeRef(List)»<«ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»>> getTransformableMetamodels() {
				«ih.typeRef(VURI)» sourceVURI = «ih.typeRef(VURI)».getInstance("«modelPair.first.EMFUri.toString»");
				«ih.typeRef(VURI)» targetVURI = «ih.typeRef(VURI)».getInstance("«modelPair.second.EMFUri.toString»");
				«ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»> pair = new «ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»>(sourceVURI, targetVURI);
				return «ih.typeRef(Collections)».singletonList(pair);
			}
			
			protected void setup() {
				«FOR executorName : executorsNames»
					this.addResponseExecutor(new «executorName»(userInteracting));
				«ENDFOR»		
			}
			
		}
		'''
		
		return generateClass(modelPair.change2CommandTransformingQualifiedPackageName, ih, classImplementation);
	}
	
	private def generateExecutor(Resource responsesResource, Pair<VURI, VURI> modelPair, List<String> responseNames) {
		val ih = new XtendImportHelper();	
		val classImplementation = '''
		public class «modelPair.executorName» extends «ih.typeRef(AbstractResponseExecutor)» {
			public «modelPair.executorName»(«ih.typeRef(UserInteracting)» userInteracting) {
				super(userInteracting);
			}
			
			protected void setup() {
				«FOR response : responseNames»
				this.addResponse(«ih.typeRef(response)».getTrigger(), new «ih.typeRef(response)»(userInteracting));
				«ENDFOR»
			}
		}
		'''
		
		return generateClass(responsesResource.getExecutorQualifiedPackageName(modelPair), ih, classImplementation);
	}
	
	private def Map<Pair<VURI, VURI>, List<String>> generateResponses(Resource responseResource, IFileSystemAccess fsa) {
		val modelCorrespondenceToResponseNameMap = new HashMap<Pair<VURI, VURI>, List<String>>;
		addResponsesToCorrespondenceMap(responseResource, modelCorrespondenceToResponseNameMap);
		
		generator.doGenerate(responseResource, fsa);
		return modelCorrespondenceToResponseNameMap;
	}
	
	private def void addResponsesToCorrespondenceMap(Resource responseResource, Map<Pair<VURI, VURI>, List<String>> modelCorrespondenceToResponseNameMap) {
		if (!(responseResource.contents.get(0) instanceof ResponseFile)) {
			throw new IllegalArgumentException("The given resource is not a response file.");
		}
		
		for (response : (responseResource.contents.get(0) as ResponseFile).responses) {
			val responseName = response.responseName;
			val sourceTargetPair = response.getSourceTargetPair();
			if (!modelCorrespondenceToResponseNameMap.containsKey(sourceTargetPair)) {
				modelCorrespondenceToResponseNameMap.put(sourceTargetPair, new ArrayList<String>());
			}
			if (modelCorrespondenceToResponseNameMap.get(sourceTargetPair).contains(responseName)) {
				LOGGER.error("There are at least two responses with the name " + responseName + " overwriting each other.");
			}
			modelCorrespondenceToResponseNameMap.get(sourceTargetPair).add(responseName);
			/*if (response.hasOppositeResponse) {
				modelCorrespondenceToResponseNameMap.get(sourceTargetPair).add(response.oppositeResponse.responseName);
			}*/
		}
	}
	
}