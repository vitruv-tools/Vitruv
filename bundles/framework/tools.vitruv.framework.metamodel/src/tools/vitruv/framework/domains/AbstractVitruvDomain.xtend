package tools.vitruv.framework.domains

import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Set

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage

import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.tuid.TuidCalculator
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.util.datatypes.AbstractURIHaving
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractVitruvDomain extends AbstractURIHaving implements TuidCalculator, TuidUpdateListener, VitruvDomain {
	@Accessors(PUBLIC_GETTER)
	val Collection<EPackage> furtherRootPackages
	val Collection<String> fileExtensions
	@Accessors(PUBLIC_GETTER)
	val EPackage metamodelRootPackage
	@Accessors(PUBLIC_GETTER)
	val Map<Object, Object> defaultLoadOptions
	@Accessors(PUBLIC_GETTER)
	val Map<Object, Object> defaultSaveOptions
	val Set<String> nsURIs
	@Accessors(PUBLIC_GETTER)
	val String name
	val TuidCalculatorAndResolver tuidCalculatorAndResolver

	/**
	 * Returns the namespace URI of the given {@link EPackage} and all subpackages.
	 */
	protected static def Set<String> getNsURIsRecursive(EPackage rootPackage) {
		(#[rootPackage.nsURI] + rootPackage.ESubpackages.map[nsURIsRecursive].flatten).toSet
	}

	def protected static String getTuidPrefix(Iterable<String> nsURIs) {
		if (nsURIs === null || nsURIs.size() > 0)
			throw new RuntimeException(
				'''
				Cannot get a Tuid prefix from the set of namespace URIs '«nsURIs»'!
			''')
		return nsURIs.iterator.next
	}

	/** 
	 * Convenience method if the metamodel consists of only a single namespace
	 */
	new(
		String name,
		EPackage metamodelRootPackage,
		TuidCalculatorAndResolver tuidCalculator,
		String... fileExtensions
	) {
		this(
			name,
			metamodelRootPackage,
			Collections::emptySet,
			tuidCalculator,
			Collections::emptyMap,
			Collections::emptyMap,
			fileExtensions
		)
	}

	new(
		String name,
		EPackage metamodelRootPackage,
		Set<EPackage> furtherRootPackages,
		TuidCalculatorAndResolver tuidCalculator,
		String... fileExtensions
	) {
		this(
			name,
			metamodelRootPackage,
			furtherRootPackages,
			tuidCalculator,
			Collections::emptyMap,
			Collections::emptyMap,
			fileExtensions
		)
	}

	private new(
		String name,
		EPackage metamodelRootPackage,
		Set<EPackage> furtherRootPackages,
		TuidCalculatorAndResolver tuidCalculator,
		Map<Object, Object> defaultLoadOptions,
		Map<Object, Object> defaultSaveOptions,
		String... fileExtensions
	) {
		super(VURI::getInstance(metamodelRootPackage.nsURI))
		this.defaultLoadOptions = defaultLoadOptions
		this.defaultSaveOptions = defaultSaveOptions
		this.fileExtensions = fileExtensions
		this.furtherRootPackages = furtherRootPackages
		this.metamodelRootPackage = metamodelRootPackage
		this.name = name
		this.tuidCalculatorAndResolver = tuidCalculator
		nsURIs = (metamodelRootPackage.nsURIsRecursive + furtherRootPackages.map[nsURIsRecursive].flatten).toSet
	}

	override Collection<String> getFileExtensions() {
		newArrayList(fileExtensions)
	}

	def String calculateTuidFromEObject(EObject eObject) {
		tuidCalculatorAndResolver.calculateTuidFromEObject(eObject)
	}

	override String calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		tuidCalculatorAndResolver.calculateTuidFromEObject(eObject, virtualRootObject, prefix)
	}

	override VURI getModelVURIContainingIdentifiedEObject(Tuid tuid) {
		val modelVURI = tuidCalculatorAndResolver.getModelVURIContainingIdentifiedEObject(tuid.toString)
		if (null === modelVURI)
			return null
		return VURI::getInstance(modelVURI)
	}

	override EObject resolveEObjectFromRootAndFullTuid(EObject root, Tuid tuid) {
		tuidCalculatorAndResolver.resolveEObjectFromRootAndFullTuid(root, tuid.toString)
	}

	override boolean isInstanceOfDomainMetamodel(EObject eObject) {
		if (eObject === null)
			return false
		val eClass = if(eObject instanceof EClass) eObject else eObject.eClass

		if (null === eClass || null === eClass.getEPackage() || null === eClass.getEPackage().getNsURI() ||
			!nsURIs.contains(eClass.getEPackage().getNsURI()))
			return false
		return true
	}

	override boolean hasTuid(String tuid) {
		tuidCalculatorAndResolver.isValidTuid(tuid)
	}

	override Set<String> getNsUris() {
		nsURIs
	}

	override canCalculateTuid(EObject object) {
		isInstanceOfDomainMetamodel(object)
	}

	override calculateTuid(EObject object) {
		Tuid::getInstance(calculateTuidFromEObject(object))
	}

	override performPreAction(Tuid oldTuid) {
		if (hasTuid(oldTuid.toString))
			removeIfRootAndCached(oldTuid.toString)
	}

	override performPostAction(Tuid newTuid) {
		// Do nothing
	}

	override toString() '''Metamodel for namespaces: «nsURIs»'''

	override registerAtTuidManagement() {
		TuidManager::instance.addTuidCalculator(this)
		TuidManager::instance.addTuidUpdateListener(this)
	}

	def boolean isMetamodelForVuri(VURI metamodelVURI) {
		nsURIs.exists[VURI::getInstance(it) == metamodelVURI]
	}

	def boolean isMetamodelFor(VURI modelVuri) {
		fileExtensions.contains(modelVuri.fileExtension)
	}

	def void removeRootFromTuidCache(EObject root) {
		tuidCalculatorAndResolver.removeRootFromCache(root)
	}

	def void removeIfRootAndCached(String tuid) {
		tuidCalculatorAndResolver.removeIfRootAndCached(tuid)
	}

	/** 
	 * syntactic sugar for map[{@link #calculateTuidFromEObject(EObject)}]
	 * @param eObjects
	 * @return
	 */
	def List<String> calculateTuidsFromEObjects(List<EObject> eObjects) {
		eObjects.map[calculateTuidFromEObject].toList
	}
}
