package tools.vitruv.framework.domains

import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.AbstractURIHaving
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.tuid.TuidCalculator
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.tuid.TuidManager
import java.util.ArrayList
import org.eclipse.emf.ecore.EPackage
import java.util.Collection
import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.emf.ecore.EClass

abstract class AbstractVitruvDomain extends AbstractURIHaving implements TuidCalculator, TuidUpdateListener, VitruvDomain {
	Collection<String> fileExtensions
	TuidCalculatorAndResolver tuidCalculatorAndResolver
	Set<String> nsURIs
	EPackage metamodelRootPackage;
	Collection<EPackage> furtherRootPackages;
	Map<Object, Object> defaultLoadOptions
	Map<Object, Object> defaultSaveOptions
	String name;
	
	/**
	 * Returns the namespace URI of the given {@link EPackage} and all subpackages.
	 */
	protected static def Set<String> getNsURIsRecursive(EPackage rootPackage) {
		return (#[rootPackage.nsURI] + rootPackage.ESubpackages.map[it.nsURIsRecursive].flatten).toSet;
	}

	def protected static String getTuidPrefix(Iterable<String> nsURIs) {
		if (nsURIs !== null && nsURIs.size() > 0) {
			return nsURIs.iterator().next()
		} else {
			throw new RuntimeException(
				'''Cannot get a Tuid prefix from the set of namespace URIs '«»«nsURIs»'!'''.toString)
		}
	}

	/** 
	 * Convenience method if the metamodel consists of only a single namespace
	 */
	new(String name, EPackage metamodelRootPackage, TuidCalculatorAndResolver tuidCalculator, String... fileExtensions) {
		super(VURI::getInstance(metamodelRootPackage.nsURI));
		initialize(name, metamodelRootPackage, Collections::emptySet(), tuidCalculator, Collections::emptyMap(), Collections::emptyMap(), fileExtensions)
	}
	
	new(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages, TuidCalculatorAndResolver tuidCalculator, String... fileExtensions) {
		super(VURI::getInstance(metamodelRootPackage.nsURI));
		initialize(name, metamodelRootPackage, furtherRootPackages, tuidCalculator, Collections::emptyMap(), Collections::emptyMap(), fileExtensions)
	}

	protected def void initialize(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages, TuidCalculatorAndResolver tuidCalculator, Map<Object, Object> defaultLoadOptions, Map<Object, Object> defaultSaveOptions, String... fileExtensions) {
		this.name = name;
		this.fileExtensions = fileExtensions
		this.tuidCalculatorAndResolver = tuidCalculator;
		this.metamodelRootPackage = metamodelRootPackage;
		this.furtherRootPackages = furtherRootPackages;
		this.nsURIs = (metamodelRootPackage.nsURIsRecursive + furtherRootPackages.map[nsURIsRecursive].flatten).toSet
		this.defaultLoadOptions = defaultLoadOptions
		this.defaultSaveOptions = defaultSaveOptions
	}

	override Collection<String> getFileExtensions() {
		return new ArrayList<String>(this.fileExtensions);
	}

	def String calculateTuidFromEObject(EObject eObject) {
		return this.tuidCalculatorAndResolver.calculateTuidFromEObject(eObject)
	}

	/** 
	 * syntactic sugar for map[{@link #calculateTuidFromEObject(EObject)}]
	 * @param eObjects
	 * @return
	 */
	def List<String> calculateTuidsFromEObjects(List<EObject> eObjects) {
		return eObjects.map[calculateTuidFromEObject(it)].toList
	}

	override String calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		return this.tuidCalculatorAndResolver.calculateTuidFromEObject(eObject, virtualRootObject, prefix)
	}

	override VURI getModelVURIContainingIdentifiedEObject(Tuid tuid) {
		val modelVURI = this.tuidCalculatorAndResolver.getModelVURIContainingIdentifiedEObject(tuid.toString)
		if (null === modelVURI) {
			return null;
		}
		return VURI::getInstance(modelVURI)
	}

	override EObject resolveEObjectFromRootAndFullTuid(EObject root, Tuid tuid) {
		return this.tuidCalculatorAndResolver.resolveEObjectFromRootAndFullTuid(root, tuid.toString)
	}

	def void removeRootFromTuidCache(EObject root) {
		this.tuidCalculatorAndResolver.removeRootFromCache(root)
	}

	def void removeIfRootAndCached(String tuid) {
		this.tuidCalculatorAndResolver.removeIfRootAndCached(tuid)
	}

	override boolean isInstanceOfDomainMetamodel(EObject eObject) {
		if (eObject === null) {
			return false;
		}
		val eClass = if (eObject instanceof EClass) eObject else eObject.eClass(); 
		
		if (null === eClass || null === eClass.getEPackage() ||	null === eClass.getEPackage().getNsURI() ||
			!this.nsURIs.contains(eClass.getEPackage().getNsURI())) {
			return false
		}
		return true
	}

	override boolean hasTuid(String tuid) {
		return this.tuidCalculatorAndResolver.isValidTuid(tuid)
	}

	override Set<String> getNsUris() {
		return this.nsURIs
	}

	override Map<Object, Object> getDefaultLoadOptions() {
		return this.defaultLoadOptions
	}

	override Map<Object, Object> getDefaultSaveOptions() {
		return this.defaultSaveOptions
	}

	override canCalculateTuid(EObject object) {
		return isInstanceOfDomainMetamodel(object);
	}

	override calculateTuid(EObject object) {
		return Tuid.getInstance(calculateTuidFromEObject(object));
	}

	override performPreAction(Tuid oldTuid) {
		if (this.hasTuid(oldTuid.toString)) {
			removeIfRootAndCached(oldTuid.toString);
		}
	}

	override performPostAction(Tuid newTuid) {
		// Do nothing
	}

	override toString() {
		return "Metamodel for namespaces: " + nsURIs;
	}

	def boolean isMetamodelForVuri(VURI metamodelVURI) {
		return nsURIs.exists[VURI.getInstance(it).equals(metamodelVURI)];
	}

	def boolean isMetamodelFor(VURI modelVuri) {
		return fileExtensions.contains(modelVuri.fileExtension);
	}
	
	override getMetamodelRootPackage() {
		return metamodelRootPackage;
	}
	
	override getFurtherRootPackages() {
		return furtherRootPackages;
	}
	
	override getName() {
		return name;
	}
	
	override registerAtTuidManagement() {
		TuidManager.instance.addTuidCalculator(this);
		TuidManager.instance.addTuidUpdateListener(this);
	}
	
}
