package tools.vitruv.dsls.reactions.generator;

import org.eclipse.xtext.generator.IFileSystemAccess
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import org.eclipse.emf.ecore.resource.Resource
import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGenerator
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import static com.google.common.base.Preconditions.*
import java.util.ArrayList
import org.eclipse.xtext.resource.XtextResourceSet
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder
import org.eclipse.xtext.resource.IResourceFactory
import org.eclipse.emf.ecore.util.EcoreUtil
import java.util.Collections
import org.eclipse.emf.ecore.resource.ResourceSet

class InternalReactionsGenerator implements IReactionsGenerator {

	static val SYNTHETIC_RESOURCES = URI.createHierarchicalURI("synthetic", null, null, #[], null, null)

	// whether this generator was already used to generate
	var used = false;

	@Inject
	IGenerator generator;

	@Inject
	IResourceFactory resourceFactory

	@Inject extension ReactionsEnvironmentGenerator environmentGenerator

	// the resource set we put artificially created reactions in
	ResourceSet artificialReactionsResourceSet

	val reactionFileResourceSets = new ArrayList<ResourceSet>
	val resourcesToGenerate = new ArrayList<Resource>

	def private addReaction(String sourceFileName, Reaction reaction) {
		checkNotNull(reaction, "Reaction must not be null!")
		checkState(artificialReactionsResourceSet !== null,
			"A resource set must be provided in order to add artificial reactions!")
		val reactionsSegment = getCorrespondingReactionsSegmentInTempResource(sourceFileName,
			reaction.reactionsSegment);
		reactionsSegment.reactions += reaction;
	}

	def private ReactionsSegment getCorrespondingReactionsSegmentInTempResource(String sourceFileName,
		ReactionsSegment reactionsSegment) {
		for (res : artificialReactionsResourceSet.resources) {
			if (res.getURI.segmentsList.last.equals(sourceFileName + ".reactions")) {
				val reactionsFile = res.reactionsFile
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
		val newFile = createSyntheticResourceWithReactionsFile(sourceFileName);
		return addReactionsSegment(newFile, reactionsSegment, sourceFileName);
	}

	def private ReactionsSegment addReactionsSegment(ReactionsFile fileToAddTo, ReactionsSegment originalSegment,
		String segmentName) {
		val newSegment = ReactionsLanguageFactory.eINSTANCE.createReactionsSegment() => [
			fromDomain = originalSegment.fromDomain.copy()
			toDomain = originalSegment.toDomain.copy()
			name = segmentName;
		]
		fileToAddTo.reactionsSegments += newSegment;
		return newSegment;
	}

	def private copy(DomainReference referenceToCopy) {
		MirBaseFactory.eINSTANCE.createDomainReference => [
			domain = referenceToCopy.domain
		]
	}

	override addReaction(String sourceFileName, Reaction... reactions) {
		addReaction(sourceFileName, reactions.toList)
	}

	override addReaction(String sourceFileName, Iterable<? extends Reaction> reactions) {
		reactions.forEach[addReaction(sourceFileName, it)];
	}

	override generate(IFileSystemAccess2 fsa) {
		checkState(!used, "This generator was already used to generate reactions!")
		used = true

		resourcesToGenerate.forEach[generateReactions(fsa)]

		reactionFileResourceSets.forEach[generateEnvironment(fsa)]
		artificialReactionsResourceSet?.generateEnvironment(fsa)
	}
	
	def private createSyntheticResource(String sourceFileName) {
		var uriAppendix = 1
		var resourceUri = SYNTHETIC_RESOURCES.appendSegment(sourceFileName).appendFileExtension("reactions")
		while (artificialReactionsResourceSet.getResource(resourceUri, false) !== null) {
			resourceUri = SYNTHETIC_RESOURCES.appendSegment(sourceFileName + uriAppendix).
				appendFileExtension("reactions")
			uriAppendix++
		}
		val resource = resourceFactory.createResource(resourceUri)
		artificialReactionsResourceSet.resources += resource
		resourcesToGenerate += resource
		return resource
	}

	def private createSyntheticResourceWithReactionsFile(String sourceFileName) {
		val singleReactionResource = createSyntheticResource(sourceFileName)
		val reactionsFile = ReactionsLanguageFactory.eINSTANCE.createReactionsFile
		singleReactionResource.contents.add(reactionsFile);
		return reactionsFile;
	}

	def private void generateReactions(Resource reactionsResource, IFileSystemAccess fsa) {
		generator.doGenerate(reactionsResource, fsa);
	}

	override addReactionsFiles(XtextResourceSet resourceSet) {
		reactionFileResourceSets.add(resourceSet)
		resourcesToGenerate += resourceSet.resources.filter [containsReactionsFile]
	}

	override addReactionsFile(FluentReactionsFileBuilder reactionBuilder) {
		checkState(artificialReactionsResourceSet !== null,
			"A resource set must be provided in order to add artificial reactions files!")
		val resource = createSyntheticResource(reactionBuilder.fileName)
		reactionBuilder.attachTo(resource)
	}

	override writeReactions(IFileSystemAccess2 fsa) {
		writeReactions(fsa, null)
	}
	
	override writeReactions(IFileSystemAccess2 fsa, String subPath) {
		val pathPrefix = if (subPath === null) '' else if (subPath.endsWith('/')) subPath else subPath + '/'
		resourcesToGenerate.map [ resource |
			val outputUri = fsa.getURI(pathPrefix + resource.URI.lastSegment)
			val newResource = resource.resourceSet.getResource(outputUri, false) ?:
				resource.resourceSet.createResource(outputUri)
			newResource.contents.clear()
			newResource.contents += EcoreUtil.copy(resource.reactionsFile)
			newResource
		].forEach[save(Collections.emptyMap)]
	}

	override useResourceSet(ResourceSet resourceSet) {
		checkNotNull(resourceSet)
		artificialReactionsResourceSet = resourceSet
		reactionFileResourceSets += resourceSet
	}
	
}
