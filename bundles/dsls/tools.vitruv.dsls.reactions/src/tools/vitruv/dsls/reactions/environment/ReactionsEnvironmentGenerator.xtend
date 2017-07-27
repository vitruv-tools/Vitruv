package tools.vitruv.dsls.reactions.environment;

import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess
import tools.vitruv.dsls.reactions.helper.XtendImportHelper
import edu.kit.ipd.sdq.commons.util.java.Pair;
import java.util.Map
import java.util.HashMap
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import org.eclipse.emf.ecore.resource.Resource
import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGenerator
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.api.generator.IReactionsEnvironmentGenerator
import tools.vitruv.framework.domains.VitruvDomain
import static com.google.common.base.Preconditions.*
import java.util.ArrayList
import org.eclipse.xtext.resource.XtextResourceSet
import com.google.inject.Provider

class ReactionsEnvironmentGenerator implements IReactionsEnvironmentGenerator {
	
	// whether this generator was already used to generate
	var used = false;
	
	@Inject
	IGenerator generator;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider

	val resources = new ArrayList<Resource>;
	val tempResources = new ArrayList<Resource>;

	def private static ReactionsFile getReactionsFileInResource(Resource resource) {
		val firstContentElement = resource?.contents.get(0)
		checkArgument(firstContentElement instanceof ReactionsFile,
			"The given resource must contain a ReactionsFile element (was %s).",
			firstContentElement?.class?.simpleName);

		return firstContentElement as ReactionsFile;
	}

	override void addReaction(String sourceFileName, Reaction reaction) {
		if (reaction === null) {
			throw new IllegalArgumentException("Reaction must not be null");
		}
		val reactionsSegment = getCorrespondingReactionsSegmentInTempResource(sourceFileName,
			reaction.reactionsSegment);
		reactionsSegment.reactions += reaction;
	}

	def private ReactionsSegment getCorrespondingReactionsSegmentInTempResource(String sourceFileName,
		ReactionsSegment reactionsSegment) {
		for (res : tempResources) {
			if (res.URI.segmentsList.last.equals(sourceFileName + ".reactions")) {
				val reactionsFile = res.reactionsFileInResource;
				var ReactionsSegment foundSegment = null;
				for (segment : reactionsFile.reactionsSegments) {
					if (segment.fromDomain.domain == reactionsSegment.fromDomain.domain &&
						segment.toDomain.domain == reactionsSegment.toDomain.domain) {
						foundSegment = segment;
					}
				}
				if (foundSegment === null) {
					foundSegment = addReactionsSegment(reactionsFile, reactionsSegment, sourceFileName);
				}

				return foundSegment;
			}
		}
		val newFile = generateTempResourceWithReactionsFile(sourceFileName);
		return addReactionsSegment(newFile, reactionsSegment, sourceFileName);
	}

	def private ReactionsSegment addReactionsSegment(ReactionsFile fileToAddTo, ReactionsSegment originalSegment,
		String segmentName) {
		val newSegment = ReactionsLanguageFactory.eINSTANCE.createReactionsSegment();
		newSegment.fromDomain = originalSegment.fromDomain;
		newSegment.toDomain = originalSegment.toDomain;
		newSegment.name = segmentName;
		fileToAddTo.reactionsSegments += newSegment;
		return newSegment;
	}

	override void addReactions(String sourceFileName, Iterable<Reaction> reactions) {
		reactions.forEach[addReaction(sourceFileName, it)];
	}

	override addReactions(Resource reactionsResource) {
		checkArgument(reactionsResource?.contents?.get(0) instanceof ReactionsFile,
			"The given resource is not a reactions file")
		this.resources.add(reactionsResource);
	}

	override generateEnvironment(IFileSystemAccess2 fsa) {
		checkState(!used, "This generator was already used to generate an environment")
		used = true
		prepareGeneration();
		generate(fsa);
	}

	private def prepareGeneration() {
		cleanGeneratedFiles();
	}

	def private cleanGeneratedFiles() {
		// project.getFolder("src-gen").getFolder(basicMirPackageQualifiedName.replace(".", File.separator)).delete(0, new NullProgressMonitor());
	}

	def private ReactionsFile generateTempResourceWithReactionsFile(String sourceFileName) {
		val reactionsFile = ReactionsLanguageFactory.eINSTANCE.createReactionsFile();
		val resSet = resourceSetProvider.get();
		val singleReactionResource = resSet.createResource(
			URI.createFileURI(System.getProperty("java.io.tmpdir") + "/" + sourceFileName + ".reactions"));
		singleReactionResource.contents.add(reactionsFile);
		tempResources += singleReactionResource;
		return reactionsFile;
	}

	def private generate(IFileSystemAccess2 fsa) {
		val modelCorrespondencesToExecutors = new HashMap<Pair<VitruvDomain, VitruvDomain>, List<String>>;
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

	def private void generateChange2CommandTransformings(
		Map<Pair<VitruvDomain, VitruvDomain>, List<String>> modelCorrepondenceToExecutors, IFileSystemAccess fsa) {
		for (modelCombination : modelCorrepondenceToExecutors.keySet) {
			val changePropagationSpecificationContent = generateChangePropagationSpecification(modelCombination,
				modelCorrepondenceToExecutors.get(modelCombination));
			val changePropagationSpecificationNameGenerator = modelCombination.
				changePropagationSpecificationClassNameGenerator;
			fsa.generateFile(changePropagationSpecificationNameGenerator.qualifiedName.filePath,
				changePropagationSpecificationContent);
		}
	}

	def private generateChangePropagationSpecification(Pair<VitruvDomain, VitruvDomain> modelPair,
		List<String> executorsNames) {
		val ih = new XtendImportHelper();
		val changePropagationSpecificationNameGenerator = modelPair.
			changePropagationSpecificationClassNameGenerator;
		val classImplementation = '''
			/**
			 * The {@link «CompositeChangePropagationSpecification»} for transformations between the metamodels «modelPair.first.name» and «modelPair.second.name».
			 * To add further change processors overwrite the setup method.
			 */
			public abstract class «changePropagationSpecificationNameGenerator.simpleName» extends «ih.typeRef(CompositeChangePropagationSpecification)» {
				public «changePropagationSpecificationNameGenerator.simpleName»() {
					super(new «ih.typeRef(modelPair.first.providerForDomain.class)»().getDomain(), 
						new «ih.typeRef(modelPair.second.providerForDomain.class)»().getDomain());
					setup();
				}
				
				/**
				 * Adds the reactions change processors to this {@link «changePropagationSpecificationNameGenerator.simpleName»}.
				 * For adding further change processors overwrite this method and call the super method at the right place.
				 */
				protected void setup() {
					«FOR executorName : executorsNames»
						this.addChangeMainprocessor(new «executorName»());
					«ENDFOR»		
				}
				
			}
		'''

		return generateClass(changePropagationSpecificationNameGenerator.packageName, ih, classImplementation);
	}

	def private void generateReactions(Resource reactionsResource, IFileSystemAccess fsa) {
		generator.doGenerate(reactionsResource, fsa);
	}

}
	