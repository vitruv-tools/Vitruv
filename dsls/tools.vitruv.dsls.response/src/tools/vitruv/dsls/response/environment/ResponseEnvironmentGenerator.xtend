package tools.vitruv.dsls.response.environment;

import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.dsls.response.helper.XtendImportHelper
import tools.vitruv.framework.util.datatypes.Pair;
import java.util.Map
import java.util.HashMap
import tools.vitruv.dsls.response.responseLanguage.ResponseLanguageFactory
import tools.vitruv.dsls.response.api.generator.IResponseEnvironmentGenerator
import org.eclipse.emf.ecore.resource.Resource
import com.google.inject.Inject
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.xtext.ui.resource.IResourceSetProvider
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.io.File
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.resource.DerivedStateAwareResource
import static extension tools.vitruv.dsls.response.helper.ResponseLanguageHelper.*;
import static extension tools.vitruv.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.userinteraction.impl.UserInteractor
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification
import tools.vitruv.dsls.response.responseLanguage.ReactionsFile
import tools.vitruv.dsls.response.responseLanguage.Reaction
import tools.vitruv.dsls.response.responseLanguage.ReactionsSegment

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
	
	private static def ReactionsFile getResponseFileInResource(Resource resource) {
		if (!(resource?.contents.get(0) instanceof ReactionsFile)) {
			throw new IllegalStateException("The given resource must contain a ResponseFile element.");
		}
		
		return resource.contents.get(0) as ReactionsFile;
	}
	
	
	public override void addResponse(String sourceFileName, Reaction response) {
		if (project == null) {
			throw new IllegalStateException("Project must be set");
		}
		if (response == null) {
			throw new IllegalArgumentException("Response must not be null");
		}
		val responsesSegment = getCorrespondingResponsesSegmentInTempResource(sourceFileName, response.reactionsSegment);
		responsesSegment.reactions += response;
	}
	
	private def ReactionsSegment getCorrespondingResponsesSegmentInTempResource(String sourceFileName, ReactionsSegment responsesSegment) {
		for (res : tempResources) {
			if (res.URI.segmentsList.last.equals(sourceFileName + ".response")) {
				val responseFile = res.responseFileInResource;
				var ReactionsSegment foundSegment = null;
				for (segment :  responseFile.reactionsSegments) {
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
	
	private def ReactionsSegment addResponsesSegment(ReactionsFile fileToAddTo, ReactionsSegment originalSegment, String segmentName) {
		val newSegment = ResponseLanguageFactory.eINSTANCE.createReactionsSegment();
		newSegment.fromMetamodel = originalSegment.fromMetamodel;
		newSegment.toMetamodel = originalSegment.toMetamodel;
		newSegment.name = segmentName;
		fileToAddTo.reactionsSegments += newSegment;
		return newSegment;
	}
	
	public override void addResponses(String sourceFileName, Iterable<Reaction> responses) {
		responses.forEach[addResponse(sourceFileName, it)];
	}
	
	public def override addResponses(Resource responseResource) {
		if (responseResource == null || !(responseResource.contents.get(0) instanceof ReactionsFile)) {
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
	
	private def ReactionsFile generateTempResourceWithResponseFile(IProject project, String sourceFileName) {
		val responseFile = ResponseLanguageFactory.eINSTANCE.createReactionsFile();
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
			for (responseSegment : resource.responseFileInResource.reactionsSegments) {
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
			val changePropagationSpecificationContent = generateChangePropagationSpecification(modelCombination, modelCorrepondenceToExecutors.get(modelCombination));
			val changePropagationSpecificationNameGenerator = modelCombination.changePropagationSpecificationClassNameGenerator;
			fsa.generateFile(changePropagationSpecificationNameGenerator.qualifiedName.filePath, changePropagationSpecificationContent);
		}
	}
		
	private def generateChangePropagationSpecification(Pair<VURI, VURI> modelPair, List<String> executorsNames) {
		val ih = new XtendImportHelper();	
		val changePropagationSpecificationNameGenerator = modelPair.changePropagationSpecificationClassNameGenerator;
		val classImplementation = '''
		/**
		 * The {@link «CompositeChangePropagationSpecification»} for transformations between the metamodels «modelPair.first.EMFUri.toString» and «modelPair.second.EMFUri.toString».
		 * To add further change processors overwrite the setup method.
		 */
		public abstract class «changePropagationSpecificationNameGenerator.simpleName» extends «ih.typeRef(CompositeChangePropagationSpecification)» {
			private final «MetamodelPair.name» metamodelPair;
			
			public «changePropagationSpecificationNameGenerator.simpleName»() {
				super(new «UserInteractor.name»());
				this.metamodelPair = new «MetamodelPair.name»("«modelPair.first.EMFUri.toString»", "«modelPair.second.EMFUri.toString»");
				setup();
			}
			
			public «MetamodelPair.name» getMetamodelPair() {
				return metamodelPair;
			}	
			
			/**
			 * Adds the response change processors to this {@link «changePropagationSpecificationNameGenerator.simpleName»}.
			 * For adding further change processors overwrite this method and call the super method at the right place.
			 */
			protected void setup() {
				«FOR executorName : executorsNames»
					this.addChangeMainprocessor(new «executorName»(getUserInteracting()));
				«ENDFOR»		
			}
			
		}
		'''
		
		return generateClass(changePropagationSpecificationNameGenerator.packageName, ih, classImplementation);
	}
	
	private def void generateResponses(Resource responseResource, IFileSystemAccess fsa) {
		generator.doGenerate(responseResource, fsa);
	}
	
}