package tools.vitruvius.dsls.response.environment;

import tools.vitruvius.dsls.response.responseLanguage.Response
import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.dsls.response.helper.XtendImportHelper
import tools.vitruvius.framework.util.datatypes.Pair;
import java.util.Map
import java.util.HashMap
import tools.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import tools.vitruvius.dsls.response.api.generator.IResponseEnvironmentGenerator
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruvius.dsls.response.responseLanguage.ResponseFile
import com.google.inject.Inject
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.xtext.ui.resource.IResourceSetProvider
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.io.File
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.resource.DerivedStateAwareResource
import static extension tools.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import static extension tools.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruvius.dsls.response.responseLanguage.ResponsesSegment
import tools.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransforming
import tools.vitruvius.framework.change.processing.Change2CommandTransforming

class ResponseEnvironmentGenerator implements IResponseEnvironmentGenerator {
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
	
	private static def ResponseFile getResponseFileInResource(Resource resource) {
		if (!(resource?.contents.get(0) instanceof ResponseFile)) {
			throw new IllegalStateException("The given resource must contain a ResponseFile element.");
		}
		
		return resource.contents.get(0) as ResponseFile;
	}
	
	
	public override void addResponse(String sourceFileName, Response response) {
		if (project == null) {
			throw new IllegalStateException("Project must be set");
		}
		if (response == null) {
			throw new IllegalArgumentException("Response must not be null");
		}
		val responsesSegment = getCorrespondingResponsesSegmentInTempResource(sourceFileName, response.responsesSegment);
		responsesSegment.responses += response;
	}
	
	private def ResponsesSegment getCorrespondingResponsesSegmentInTempResource(String sourceFileName, ResponsesSegment responsesSegment) {
		for (res : tempResources) {
			if (res.URI.segmentsList.last.equals(sourceFileName + ".response")) {
				val responseFile = res.responseFileInResource;
				var ResponsesSegment foundSegment = null;
				for (segment :  responseFile.responsesSegments) {
					if (segment.fromMetamodel == responsesSegment.fromMetamodel
						&& segment.toMetamodel == responsesSegment.toMetamodel
					) {
						foundSegment = segment;
					}	
				}
				if (foundSegment == null) {
					foundSegment = addResponsesSegment(responseFile, responsesSegment, sourceFileName);
				}
				
				return foundSegment;
			}
		}
		val newFile = generateTempResourceWithResponseFile(project, sourceFileName);
		return addResponsesSegment(newFile, responsesSegment, sourceFileName);
	}
	
	private def ResponsesSegment addResponsesSegment(ResponseFile fileToAddTo, ResponsesSegment originalSegment, String segmentName) {
		val newSegment = ResponseLanguageFactory.eINSTANCE.createResponsesSegment();
		newSegment.fromMetamodel = originalSegment.fromMetamodel;
		newSegment.toMetamodel = originalSegment.toMetamodel;
		newSegment.name = segmentName;
		fileToAddTo.responsesSegments += newSegment;
		return newSegment;
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
	
	private def ResponseFile generateTempResourceWithResponseFile(IProject project, String sourceFileName) {
		val responseFile = ResponseLanguageFactory.eINSTANCE.createResponseFile();
		val resSet = resourceSetProvider.get(project);
		val singleResponseResource = resSet.createResource(URI.createFileURI(System.getProperty("java.io.tmpdir") + "/" + sourceFileName + ".response"));
		singleResponseResource.contents.add(responseFile);
		tempResources += singleResponseResource;
		return responseFile;
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
	
	private def reinitializeDerivedStateOfTemporaryResources() {
		for (res : tempResources) {
			(res as DerivedStateAwareResource).discardDerivedState();
			(res as DerivedStateAwareResource).installDerivedState(false);
		}
	}
	
	private def generate(IFileSystemAccess2 fsa) {
		reinitializeDerivedStateOfTemporaryResources();		
		val modelCorrespondencesToExecutors = new HashMap<Pair<VURI, VURI>, List<String>>;
		for (resource : resources + tempResources) {
			for (responseSegment : resource.responseFileInResource.responsesSegments) {
				val modelCombination = responseSegment.sourceTargetPair;
				if (!modelCorrespondencesToExecutors.containsKey(modelCombination)) {
					modelCorrespondencesToExecutors.put(modelCombination, <String>newArrayList());
				}
				val executorNameGenerator = responseSegment.executorClassNameGenerator;
				modelCorrespondencesToExecutors.get(modelCombination).add(executorNameGenerator.qualifiedName);
				generateResponses(resource, fsa);
			}
		}
		
		generateChange2CommandTransformings(modelCorrespondencesToExecutors, fsa)
	}
	
	private def void generateChange2CommandTransformings(Map<Pair<VURI, VURI>, List<String>> modelCorrepondenceToExecutors, IFileSystemAccess fsa) {
		for (modelCombination : modelCorrepondenceToExecutors.keySet) {
			val change2ComandTransformingContent = generateChangeToCommandTransforming(modelCombination, modelCorrepondenceToExecutors.get(modelCombination));
			val change2CommandTransformingNameGenerator = modelCombination.change2CommandTransformingClassNameGenerator;
			fsa.generateFile(change2CommandTransformingNameGenerator.qualifiedName.filePath, change2ComandTransformingContent);
		}
	}
		
	private def generateChangeToCommandTransforming(Pair<VURI, VURI> modelPair, List<String> executorsNames) {
		val ih = new XtendImportHelper();	
		val change2CommandTransformingNameGenerator = modelPair.change2CommandTransformingClassNameGenerator;
		val classImplementation = '''
		/**
		 * The {@link «Change2CommandTransforming»} for transformations between the metamodels «modelPair.first.EMFUri.toString» and «modelPair.second.EMFUri.toString».
		 * To add further change processors overwrite the setup method.
		 */
		public abstract class «change2CommandTransformingNameGenerator.simpleName» extends «ih.typeRef(AbstractChange2CommandTransforming)» {
			public «change2CommandTransformingNameGenerator.simpleName»() {
				super («ih.typeRef(VURI)».getInstance("«modelPair.first.EMFUri.toString»"),
					«ih.typeRef(VURI)».getInstance("«modelPair.second.EMFUri.toString»"));
			}
«««			public «ih.typeRef(List)»<«ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»>> getTransformableMetamodels() {
«««				«ih.typeRef(VURI)» sourceVURI = «ih.typeRef(VURI)».getInstance("«modelPair.first.EMFUri.toString»");
«««				«ih.typeRef(VURI)» targetVURI = «ih.typeRef(VURI)».getInstance("«modelPair.second.EMFUri.toString»");
«««				«ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»> pair = new «ih.typeRef(Pair)»<«ih.typeRef(VURI)», «ih.typeRef(VURI)»>(sourceVURI, targetVURI);
«««				return «ih.typeRef(Collections)».singletonList(pair);
«««			}
			
			/**
			 * Adds the response change processors to this {@link «change2CommandTransformingNameGenerator.simpleName»}.
			 * For adding further change processors overwrite this method and call the super method at the right place.
			 */
			protected void setup() {
				«FOR executorName : executorsNames»
					this.addChangeProcessor(new «executorName»(getUserInteracting()));
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