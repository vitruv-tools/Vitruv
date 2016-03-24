package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl;

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import java.util.Map
import java.util.HashMap
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.IResponseEnvironmentGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransforming
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile
import com.google.inject.Inject
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.xtext.ui.resource.IResourceSetProvider
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.io.File
import org.eclipse.xtext.generator.IGenerator
import java.util.Collections
import org.apache.log4j.Logger
import org.eclipse.xtext.resource.DerivedStateAwareResource
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.Change2CommandTransformingClassNameGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.ExecutorClassNameGenerator

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
	
	private static def MetamodelPairResponses getMetamodelPairResponsesInResource(Resource resource) {
		if (!(resource?.contents.get(0) instanceof MetamodelPairResponses)) {
			throw new IllegalStateException("The given resource must contain a MetamodelPairResponses element.");
		}
		
		return resource.contents.get(0) as MetamodelPairResponses;
	}
	
	
	public override void addResponse(String sourceFileName, Response response) {
		if (project == null) {
			throw new IllegalStateException("Project must be set");
		}
		if (response == null) {
			throw new IllegalArgumentException("Response must not be null");
		}
		val resource = getOrCreateTempResource(sourceFileName);
		val resourceMetamodelPair = resource.metamodelPairResponsesInResource;
		if (resourceMetamodelPair.fromMetamodel == null && resourceMetamodelPair.toMetamodel == null) {
			// TODO HK Remove the cast
			resourceMetamodelPair.fromMetamodel = (response.eContainer as MetamodelPairResponses).fromMetamodel;
			resourceMetamodelPair.toMetamodel = (response.eContainer as MetamodelPairResponses).toMetamodel;
		} else {
			throw new IllegalStateException("Responses from the same source file must have the same two metamodels associated");
		}
		resourceMetamodelPair.responses += response;
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
		project.getFolder("src-gen").getFolder(basicMirPackageQualifiedName.replace(".", File.separator)).delete(0, new NullProgressMonitor());
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
		val modelCorrespondencesToExecutors = new HashMap<Pair<VURI, VURI>, List<String>>;
		for (resource : resources + tempResources) {
			// TODO HK remove this cast
			val modelCombination = (resource.contents.get(0) as MetamodelPairResponses).sourceTargetPair
			if (!modelCorrespondencesToExecutors.containsKey(modelCombination)) {
				modelCorrespondencesToExecutors.put(modelCombination, <String>newArrayList());
			}
			val executorNameGenerator = new ExecutorClassNameGenerator(resource.contents.get(0) as MetamodelPairResponses);
			modelCorrespondencesToExecutors.get(modelCombination).add(executorNameGenerator.qualifiedName);
			generateResponses(resource, fsa);
		}
		
		generateChange2CommandTransformings(modelCorrespondencesToExecutors, fsa)
	}
	
	private def void generateChange2CommandTransformings(Map<Pair<VURI, VURI>, List<String>> modelCorrepondenceToExecutors, IFileSystemAccess fsa) {
		for (modelCombination : modelCorrepondenceToExecutors.keySet) {
			val change2ComandTransformingContent = generateChangeToCommandTransforming(modelCombination, modelCorrepondenceToExecutors.get(modelCombination));
			val change2CommandTransformingNameGenerator = new Change2CommandTransformingClassNameGenerator(modelCombination);
			fsa.generateFile(change2CommandTransformingNameGenerator.qualifiedName.filePath, change2ComandTransformingContent);
		}
	}
		
	private def generateChangeToCommandTransforming(Pair<VURI, VURI> modelPair, List<String> executorsNames) {
		val ih = new XtendImportHelper();	
		val change2CommandTransformingNameGenerator = new Change2CommandTransformingClassNameGenerator(modelPair);
		val classImplementation = '''
		public class «change2CommandTransformingNameGenerator.simpleName» extends «ih.typeRef(AbstractResponseChange2CommandTransforming)» {
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
		
		return generateClass(change2CommandTransformingNameGenerator.packageName, ih, classImplementation);
	}
	
	private def void generateResponses(Resource responseResource, IFileSystemAccess fsa) {
		generator.doGenerate(responseResource, fsa);
	}
	
}