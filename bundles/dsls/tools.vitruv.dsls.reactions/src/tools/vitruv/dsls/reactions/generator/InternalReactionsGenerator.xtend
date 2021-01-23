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
import org.eclipse.emf.ecore.resource.ResourceSet
import java.io.IOException
import org.eclipse.xtext.util.RuntimeIOException
import edu.kit.ipd.sdq.activextendannotations.CloseResource
import java.io.OutputStream
import java.io.InputStream
import java.io.PipedOutputStream
import java.io.PipedInputStream
import java.util.concurrent.Executors
import java.util.concurrent.ExecutionException
import static java.util.concurrent.TimeUnit.SECONDS
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class InternalReactionsGenerator implements IReactionsGenerator {
	static val SYNTHETIC_RESOURCES = URI.createHierarchicalURI("synthetic", null, null, #[], null, null)

	// whether this generator was already used to generate
	var used = false;

	@Inject
	IGenerator generator;

	@Inject
	IResourceFactory resourceFactory

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
		resourcesToGenerate += resourceSet.resources.filter[containsReactionsFile]
	}

	override addReactionsFile(FluentReactionsFileBuilder reactionBuilder) {
		checkState(artificialReactionsResourceSet !== null,
			"A resource set must be provided in order to add artificial reactions files!")
		val resource = createSyntheticResource(reactionBuilder.fileName)
		reactionBuilder.attachTo(resource)
	}

	override addReactionsFile(String sourceFileName, ReactionsFile reactionsFile) {
		checkState(artificialReactionsResourceSet !== null,
			"A resource set must be provided in order to add artificial reactions files!")
		val reactionsFileResource = createSyntheticResource(sourceFileName)
		reactionsFileResource.contents.add(reactionsFile);
		resourcesToGenerate += reactionsFileResource
	}

	override writeReactions(IFileSystemAccess2 fsa) throws IOException {
		writeReactions(fsa, null)
	}

	override writeReactions(IFileSystemAccess2 fsa, String subPath) throws IOException {
		val pathPrefix = if(subPath === null) '' else if(subPath.endsWith('/')) subPath else subPath + '/'
		val  serializationExecutor = Executors.newCachedThreadPool([r | new Thread(r, "Reactions Serializer")])
		
		try {
			resourcesToGenerate.mapFixed [ resource |
				val serializationInput = new PipedOutputStream
				val serializationOutput = new PipedInputStream(serializationInput, 102400 /* 100 kiB */)
				serializationExecutor.submit [resource.writeTo(serializationInput)]
				
				resource -> serializationOutput
			].forEach [
				val resource = key
				val output = value
				
				/*
				 * When this method is called from a generator in Eclipse, the
				 * current thread holds a lock, preventing other threads from
				 * writing through the FSA. Because of that, we must not make
				 * write in another thread. Note that this is not a problem
				 * if the FSA supports asynchronous writing.
				 */
				output.writeTo(fsa, pathPrefix + resource.URI.lastSegment)
			]
			
			serializationExecutor.shutdown()
			serializationExecutor.awaitTermination(10, SECONDS)
		} catch (RuntimeIOException runtimeIoError) {
			throw mapRuntimeIoException(runtimeIoError)
		} catch (ExecutionException writerError) {
			throw mapWriteException(writerError.cause)
		} finally {
			serializationExecutor.shutdownNow()
		}
	}

	// both methods return something so referencing them creates a Callable,
	// which, unlike a Runnable, may throw exceptions.
	def private static Void writeTo(Resource resource, @CloseResource OutputStream outputStream) throws IOException {
		resource.save(outputStream, emptyMap)
		null
	}

	def private static Void writeTo(@CloseResource InputStream inputStream, IFileSystemAccess2 fsa,
		String path) throws RuntimeIOException {
		fsa.generateFile(path, inputStream)
		null
	}

	// makes sure Xtext doesn’t sneaky throw exceptions
	def private IOException mapWriteException(Throwable executionException) {
		switch (executionException) {
			IOException: throw executionException
			RuntimeIOException: mapRuntimeIoException(executionException)
			default: throw new IOException(executionException)
		}
	}

	def private IOException mapRuntimeIoException(RuntimeIOException runtimeIOException) {
		val realException = runtimeIOException.cause
		switch (realException) {
			IOException: throw realException
			default: throw new IOException(realException)
		}
	}

	override useResourceSet(ResourceSet resourceSet) {
		checkNotNull(resourceSet)
		artificialReactionsResourceSet = resourceSet
		reactionFileResourceSets += resourceSet
	}
}
