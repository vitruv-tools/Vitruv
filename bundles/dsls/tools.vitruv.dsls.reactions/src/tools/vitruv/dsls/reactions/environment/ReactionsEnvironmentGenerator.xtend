package tools.vitruv.dsls.reactions.environment;

import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.dsls.reactions.helper.XtendImportHelper
import tools.vitruv.framework.util.datatypes.Pair;
import java.util.Map
import java.util.HashMap
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
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
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.userinteraction.impl.UserInteractor
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.api.generator.IReactionsEnvironmentGenerator

class ReactionsEnvironmentGenerator implements IReactionsEnvironmentGenerator {
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
	
	private static def ReactionsFile getReactionsFileInResource(Resource resource) {
		if (!(resource?.contents.get(0) instanceof ReactionsFile)) {
			throw new IllegalStateException("The given resource must contain a ReactionsFile element.");
		}
		
		return resource.contents.get(0) as ReactionsFile;
	}
	
	
	public override void addReaction(String sourceFileName, Reaction reaction) {
		if (project == null) {
			throw new IllegalStateException("Project must be set");
		}
		if (reaction == null) {
			throw new IllegalArgumentException("Reaction must not be null");
		}
		val reactionsSegment = getCorrespondingReactionsSegmentInTempResource(sourceFileName, reaction.reactionsSegment);
		reactionsSegment.reactions += reaction;
	}
	
	private def ReactionsSegment getCorrespondingReactionsSegmentInTempResource(String sourceFileName, ReactionsSegment reactionsSegment) {
		for (res : tempResources) {
			if (res.URI.segmentsList.last.equals(sourceFileName + ".reactions")) {
				val reactionsFile = res.reactionsFileInResource;
				var ReactionsSegment foundSegment = null;
				for (segment :  reactionsFile.reactionsSegments) {
					if (segment.fromMetamodel == reactionsSegment.fromMetamodel
						&& segment.toMetamodel == reactionsSegment.toMetamodel
					) {
						foundSegment = segment;
					}	
				}
				if (foundSegment == null) {
					foundSegment = addReactionsSegment(reactionsFile, reactionsSegment, sourceFileName);
				}
				
				return foundSegment;
			}
		}
		val newFile = generateTempResourceWithReactionsFile(project, sourceFileName);
		return addReactionsSegment(newFile, reactionsSegment, sourceFileName);
	}
	
	private def ReactionsSegment addReactionsSegment(ReactionsFile fileToAddTo, ReactionsSegment originalSegment, String segmentName) {
		val newSegment = ReactionsLanguageFactory.eINSTANCE.createReactionsSegment();
		newSegment.fromMetamodel = originalSegment.fromMetamodel;
		newSegment.toMetamodel = originalSegment.toMetamodel;
		newSegment.name = segmentName;
		fileToAddTo.reactionsSegments += newSegment;
		return newSegment;
	}
	
	public override void addReactions(String sourceFileName, Iterable<Reaction> reactions) {
		reactions.forEach[addReaction(sourceFileName, it)];
	}
	
	public def override addReactions(Resource reactionsResource) {
		if (reactionsResource == null || !(reactionsResource.contents.get(0) instanceof ReactionsFile)) {
			throw new IllegalArgumentException("The given resource is not a reactions file");
		}
		this.resources.add(reactionsResource);
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
		clearReactionsAndResources();
	}
	
	private def ReactionsFile generateTempResourceWithReactionsFile(IProject project, String sourceFileName) {
		val reactionsFile = ReactionsLanguageFactory.eINSTANCE.createReactionsFile();
		val resSet = resourceSetProvider.get(project);
		val singleReactionResource = resSet.createResource(URI.createFileURI(System.getProperty("java.io.tmpdir") + "/" + sourceFileName + ".reactions"));
		singleReactionResource.contents.add(reactionsFile);
		tempResources += singleReactionResource;
		return reactionsFile;
	}
			
	private def void removeTempResources() {
		try {
			tempResources.forEach[delete(emptyMap)];
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private def clearReactionsAndResources() {
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
			for (reactionsSegment : resource.reactionsFileInResource.reactionsSegments) {
				val modelCombination = reactionsSegment.sourceTargetPair;
				if (!modelCorrespondencesToExecutors.containsKey(modelCombination)) {
					modelCorrespondencesToExecutors.put(modelCombination, <String>newArrayList());
				}
				val executorNameGenerator = reactionsSegment.executorClassNameGenerator;
				modelCorrespondencesToExecutors.get(modelCombination).add(executorNameGenerator.qualifiedName);
				generateReactions(resource, fsa);
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
			 * Adds the reactions change processors to this {@link «changePropagationSpecificationNameGenerator.simpleName»}.
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
	
	private def void generateReactions(Resource reactionsResource, IFileSystemAccess fsa) {
		generator.doGenerate(reactionsResource, fsa);
	}
	
}