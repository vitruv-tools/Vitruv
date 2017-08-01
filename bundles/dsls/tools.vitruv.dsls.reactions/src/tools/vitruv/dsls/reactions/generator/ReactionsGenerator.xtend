package tools.vitruv.dsls.reactions.generator;

import org.eclipse.xtext.generator.IFileSystemAccess
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import org.eclipse.emf.ecore.resource.Resource
import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGenerator
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import static com.google.common.base.Preconditions.*
import java.util.ArrayList
import org.eclipse.xtext.resource.XtextResourceSet
import com.google.inject.Provider
import edu.kit.ipd.sdq.activextendannotations.Lazy
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory

class ReactionsGenerator implements IReactionsGenerator {

	// whether this generator was already used to generate
	var used = false;

	@Inject
	IGenerator generator;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider

	@Inject extension ReactionsEnvironmentGenerator environmentGenerator

	// the resource set we put artificially created reactions in
	@Lazy XtextResourceSet tmpResourceSet = createTmpResourceSet()

	val tempResources = new ArrayList<Resource>
	val reactionFileResourceSets = new ArrayList<XtextResourceSet>

	def private createTmpResourceSet() {
		val resourceSet = resourceSetProvider.get()
		reactionFileResourceSets.add(resourceSet)
		resourceSet
	}

	def private addReaction(String sourceFileName, Reaction reaction) {
		checkNotNull(reaction, "Reaction must not be null!")
		val reactionsSegment = getCorrespondingReactionsSegmentInTempResource(sourceFileName,
			reaction.reactionsSegment);
		reactionsSegment.reactions += reaction;
	}

	def private ReactionsSegment getCorrespondingReactionsSegmentInTempResource(String sourceFileName,
		ReactionsSegment reactionsSegment) {
		for (res : tempResources) {
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
		val newFile = generateTempResourceWithReactionsFile(sourceFileName);
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

		// the resource set contents will be changed while we generate, so we
		// must copy them
		val reactionFileResourcesCopy = reactionFileResourceSets.flatMap[resources].toList
		// only compile reaction files. This *will* generate the necessary java
		// classes but *will not* copy referenced classes
		val resourcesToGenerate = tempResources + (reactionFileResourcesCopy.filter[containsReactionsFile])
		resourcesToGenerate.forEach[generateReactions(fsa)]

		reactionFileResourceSets.forEach[generateEnvironment(fsa)]
	}

	def private ReactionsFile generateTempResourceWithReactionsFile(String sourceFileName) {
		val reactionsFile = ReactionsLanguageFactory.eINSTANCE.createReactionsFile()
		val singleReactionResource = tmpResourceSet.createResource(
			URI.createFileURI(System.getProperty("java.io.tmpdir") + "/" + sourceFileName + ".reactions"));
		singleReactionResource.contents.add(reactionsFile);
		tempResources += singleReactionResource;
		return reactionsFile;
	}

	def private void generateReactions(Resource reactionsResource, IFileSystemAccess fsa) {
		generator.doGenerate(reactionsResource, fsa);
	}

	override addReactionFiles(XtextResourceSet resourceSet) {
		reactionFileResourceSets.add(resourceSet)
	}

}
