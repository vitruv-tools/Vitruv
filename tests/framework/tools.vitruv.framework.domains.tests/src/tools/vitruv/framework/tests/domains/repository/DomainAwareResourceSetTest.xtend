package tools.vitruv.framework.tests.domains.repository

import java.util.Map
import java.io.IOException
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Delegate
import java.io.InputStream
import java.io.OutputStream
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.domains.VitruvDomain
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static extension tools.vitruv.testutils.domains.DomainModelCreators.*
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import java.util.List
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProjectManager
import org.junit.jupiter.api.BeforeAll
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import org.junit.jupiter.api.DisplayName
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import org.junit.jupiter.api.BeforeEach
import static tools.vitruv.testutils.metamodels.AllElementTypes2Creators.aet2

@ExtendWith(TestProjectManager)
class DomainAwareResourceSetTest {
	static var URI aetResourceUri
	static var URI aet2ResourceUri
	var ResourceSet domainAwareResourceSet = new ResourceSetImpl => [
		resourceFactoryRegistry = new TestResource.Factory.Registry
		awareOfDomains(TestDomain.repository)
	]

	@BeforeAll
	def static void writeResourceToLoad(@TestProject Path testProject) {
		aetResourceUri = URI.createFileURI(testProject.resolve("example".allElementTypes).toString)
		aet2ResourceUri = URI.createFileURI(testProject.resolve("example".allElementTypes2).toString)
		new ResourceSetImpl().withGlobalFactories() => [
			createResource(aetResourceUri) => [
				contents += aet.Root
				save(emptyMap)
			]
			createResource(aet2ResourceUri) => [
				contents += aet2.Root2
				save(emptyMap)
			]
		]
	}

	@BeforeEach
	def void resetTestObjects() {
		TestResource.lastLoadOptions = null
		TestResource.lastSaveOptions = null
		TestDomain.instance.defaultLoadOptions = emptyMap
		TestDomain.instance.defaultSaveOptions = emptyMap
	}

	@Test
	@DisplayName("does not affect load options for other domains")
	def void noInfluenceOnLoadingOtherDomains() {
		domainAwareResourceSet.getResource(aet2ResourceUri, true)
		assertThat(TestResource.lastLoadOptions, is(<Object, Object>emptyMap))

		domainAwareResourceSet.getEObject(aet2ResourceUri.appendFragment('/'), true)
		assertThat(TestResource.lastLoadOptions, is(<Object, Object>emptyMap))

		domainAwareResourceSet.createResource(aet2ResourceUri).load(emptyMap)
		assertThat(TestResource.lastLoadOptions, is(<Object, Object>emptyMap))

		val contentStream = domainAwareResourceSet.URIConverter.createInputStream(aet2ResourceUri)
		domainAwareResourceSet.createResource(aet2ResourceUri).load(contentStream, emptyMap)
		assertThat(TestResource.lastLoadOptions, is(<Object, Object>emptyMap))
	}

	@Test
	@DisplayName("does not affect save options for other domains")
	def void noInfluenceOnSavingOtherDomains() {
		domainAwareResourceSet.createResource(aet2ResourceUri) => [
			contents += aet2.Root2
			save(emptyMap)
		]
		assertThat(TestResource.lastSaveOptions, is(<Object, Object>emptyMap))

		try(val outputStream = domainAwareResourceSet.URIConverter.createOutputStream(aetResourceUri)) {
			domainAwareResourceSet.createResource(aet2ResourceUri) => [
				contents += aet2.Root2
				save(outputStream, emptyMap)
			]
		}

		assertThat(TestResource.lastSaveOptions, is(<Object, Object>emptyMap))
	}

	@Test
	@DisplayName("respects domain load options when loading through ResourceSet#getResource")
	def void loadThroughResourceSetGetResource() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1)
		domainAwareResourceSet.getResource(aetResourceUri, true)

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1)))
	}

	@Test
	@DisplayName("respects domain load options and resource set default load options when loading through ResourceSet#getResource")
	def void loadThroughResourceSetGetResourceWithResourceSetDefault() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1, "commonKey", 2)
		domainAwareResourceSet.loadOptions += Map.of("commonKey", "overridden", "key2", 2)
		domainAwareResourceSet.getResource(aetResourceUri, true)

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1, "commonKey", "overridden", "key2", 2)))
	}

	@Test
	@DisplayName("respects domain load options when loading through ResourceSet#getEObject")
	def void loadThroughResourceSetGetEObject() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1)
		domainAwareResourceSet.getEObject(aetResourceUri.appendFragment('/'), true)

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1)))
	}

	@Test
	@DisplayName("respects domain load options and resource set default load options when loading through ResourceSet#getEObject")
	def void loadThroughResourceSetGetEObjectWithResourceSetDefault() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1, "commonKey", 2)
		domainAwareResourceSet.loadOptions += Map.of("commonKey", "overridden", "key2", 2)
		domainAwareResourceSet.getEObject(aetResourceUri.appendFragment('/'), true)

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1, "commonKey", "overridden", "key2", 2)))
	}

	@Test
	@DisplayName("respects domain load options when loading through Resource#load")
	def void loadThroughResourceLoad() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1)
		domainAwareResourceSet.createResource(aetResourceUri).load(emptyMap)

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1)))
	}

	@Test
	@DisplayName("respects domain load options and direct options when loading through Resource#load")
	def void loadThroughResourceLoadWithDirectOptions() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1, "commonKey", 2)
		domainAwareResourceSet.createResource(aetResourceUri).load(Map.of("commonKey", "overridden", "key2", 2))

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1, "commonKey", "overridden", "key2", 2)))
	}

	@Test
	@DisplayName("respects domain load options when loading through Resource#load with an input stream")
	def void loadThroughResourceLoadInputStream() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1)
		val contentStream = domainAwareResourceSet.URIConverter.createInputStream(aetResourceUri)
		domainAwareResourceSet.createResource(aetResourceUri).load(contentStream, emptyMap)

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1)))
	}

	@Test
	@DisplayName("respects domain load options and direct options when loading through Resource#load with an input stream")
	def void loadThroughResourceLoadInpputStreamWithDirectOptions() {
		TestDomain.instance.defaultLoadOptions = Map.of("key1", 1, "commonKey", 2)
		val contentStream = domainAwareResourceSet.URIConverter.createInputStream(aetResourceUri)
		domainAwareResourceSet.createResource(aetResourceUri).load(contentStream,
			Map.of("commonKey", "overridden", "key2", 2))

		assertThat(TestResource.lastLoadOptions, is(Map.of("key1", 1, "commonKey", "overridden", "key2", 2)))
	}

	@Test
	@DisplayName("respects domain save options when saving through Resource#save")
	def void saveThroughResourceSave() {
		TestDomain.instance.defaultSaveOptions = Map.of("key1", 1)
		domainAwareResourceSet.createResource(aetResourceUri) => [
			contents += aet.Root
			save(emptyMap)
		]

		assertThat(TestResource.lastSaveOptions, is(Map.of("key1", 1)))
	}

	@Test
	@DisplayName("respects domain save options and direct options when saving through Resource#save")
	def void saveThroughResourceSaveWithDirectOptions() {
		TestDomain.instance.defaultSaveOptions = Map.of("key1", 1)
		domainAwareResourceSet.createResource(aetResourceUri) => [
			contents += aet.Root
			save(Map.of("commonKey", "overridden", "key2", 2))
		]

		assertThat(TestResource.lastSaveOptions, is(Map.of("key1", 1, "commonKey", "overridden", "key2", 2)))
	}

	@Test
	@DisplayName("respects domain save options when saving through Resource#save with an OutputStream")
	def void saveThroughResourceSaveWithOutputStream() {
		TestDomain.instance.defaultSaveOptions = Map.of("key1", 1)
		try(val outputStream = domainAwareResourceSet.URIConverter.createOutputStream(aetResourceUri)) {
			domainAwareResourceSet.createResource(aetResourceUri) => [
				contents += aet.Root
				save(outputStream, emptyMap)
			]
		}

		assertThat(TestResource.lastSaveOptions, is(Map.of("key1", 1)))
	}

	@Test
	@DisplayName("respects domain save options and direct options when saving through Resource#save with an OutputStream")
	def void saveThroughResourceSaveWithOutputStreamWithDirectOptions() {
		TestDomain.instance.defaultSaveOptions = Map.of("key1", 1)
		try(val outputStream = domainAwareResourceSet.URIConverter.createOutputStream(aetResourceUri)) {
			domainAwareResourceSet.createResource(aetResourceUri) => [
				contents += aet.Root
				save(outputStream, Map.of("commonKey", "overridden", "key2", 2))
			]
		}

		assertThat(TestResource.lastSaveOptions, is(Map.of("key1", 1, "commonKey", "overridden", "key2", 2)))
	}

	static class TestDomain implements VitruvDomain {
		static val instance = new TestDomain
		static val repository = new VitruvDomainRepositoryImpl(List.of(instance))
		@Accessors
		var Map<Object, Object> defaultLoadOptions
		@Accessors
		var Map<Object, Object> defaultSaveOptions
		@Delegate val VitruvDomain delegate = aet.domain
	}

	static class TestResource implements Resource.Internal {
		@Delegate val Resource.Internal delegate
		static var Map<?, ?> lastLoadOptions
		static var Map<?, ?> lastSaveOptions

		new(URI uri) {
			delegate = new XMIResourceImpl(uri)
		}

		override load(Map<?, ?> options) throws IOException {
			lastLoadOptions = options
			delegate.load(options)
		}

		override load(InputStream inputStream, Map<?, ?> options) throws IOException {
			lastLoadOptions = options
			delegate.load(inputStream, options)
		}

		override save(Map<?, ?> options) {
			lastSaveOptions = options
			delegate.save(options)
		}

		override save(OutputStream outputStream, Map<?, ?> options) {
			lastSaveOptions = options
			delegate.save(outputStream, options)
		}

		static class Factory implements Resource.Factory {
			override createResource(URI uri) {
				new TestResource(uri)
			}

			static class Registry implements Resource.Factory.Registry {
				@Delegate val Resource.Factory.Registry delegate = Resource.Factory.Registry.INSTANCE

				override getFactory(URI uri) {
					new TestResource.Factory
				}

				override getFactory(URI uri, String contentType) {
					new TestResource.Factory
				}
			}
		}
	}
}
