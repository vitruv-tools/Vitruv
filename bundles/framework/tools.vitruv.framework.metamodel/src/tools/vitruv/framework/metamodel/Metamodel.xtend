package tools.vitruv.framework.metamodel

import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.AbstractURIHaving
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.framework.tuid.TuidCalculator
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.tuid.TuidManager
import java.util.ArrayList
import org.eclipse.emf.ecore.EPackage

class Metamodel extends AbstractURIHaving implements TuidCalculator, TuidUpdateListener {
	List<String> fileExtensions
	TUIDCalculatorAndResolver tuidCalculatorAndResolver
	Set<String> nsURIs
	Map<Object, Object> defaultLoadOptions
	Map<Object, Object> defaultSaveOptions

	/**
	 * Returns the namespace URI of the given {@link EPackage} and all subpackages.
	 */
	protected static def Set<String> getNsURIsRecursive(EPackage rootPackage) {
		return (#[rootPackage.nsURI] + rootPackage.ESubpackages.map[it.nsURIsRecursive].flatten).toSet;
	}

	def protected static String getTUIDPrefix(Iterable<String> nsURIs) {
		if (nsURIs !== null && nsURIs.size() > 0) {
			return nsURIs.iterator().next()
		} else {
			throw new RuntimeException(
				'''Cannot get a TUID prefix from the set of namespace URIs '«»«nsURIs»'!'''.toString)
		}
	}

	/** 
	 * Convenience method if the metamodel consists of only a single namespace
	 */
	new(VURI mainNamespaceUri, String namespaceUri, TUIDCalculatorAndResolver tuidCalculator, String... fileExtensions) {
		super(mainNamespaceUri);
		initialize(newHashSet(namespaceUri), tuidCalculator, Collections::emptyMap(), Collections::emptyMap(), fileExtensions)
	}

	new(VURI mainNamespaceUri, Set<String> namespaceUris, TUIDCalculatorAndResolver tuidCalculator, String... fileExtensions) {
		super(mainNamespaceUri);
		initialize(namespaceUris, tuidCalculator, Collections::emptyMap(), Collections::emptyMap(), fileExtensions)
	}

	protected def void initialize(Set<String> nsURIs, TUIDCalculatorAndResolver tuidCalculator, Map<Object, Object> defaultLoadOptions, Map<Object, Object> defaultSaveOptions, String... fileExtensions) {
		this.fileExtensions = fileExtensions
		this.tuidCalculatorAndResolver = tuidCalculator;
		this.nsURIs = nsURIs
		this.defaultLoadOptions = defaultLoadOptions
		this.defaultSaveOptions = defaultSaveOptions
		TuidManager.instance.addTuidCalculator(this);
		TuidManager.instance.addTuidUpdateListener(this);
	}

	def List<String> getFileExtensions() {
		return new ArrayList<String>(this.fileExtensions);
	}

	def boolean hasTUID(EObject eObject) {
		return tuidCalculatorAndResolver.hasTUID(eObject);
	}

	def String calculateTUIDFromEObject(EObject eObject) {
		return this.tuidCalculatorAndResolver.calculateTUIDFromEObject(eObject)
	}

	/** 
	 * syntactic sugar for map[{@link #calculateTUIDFromEObject(EObject)}]
	 * @param eObjects
	 * @return
	 */
	def List<String> calculateTUIDsFromEObjects(List<EObject> eObjects) {
		return eObjects.map[calculateTUIDFromEObject(it)].toList
	}

	def String calculateTUIDFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		return this.tuidCalculatorAndResolver.calculateTUIDFromEObject(eObject, virtualRootObject, prefix)
	}

	def VURI getModelVURIContainingIdentifiedEObject(String tuid) {
		val modelVURI = this.tuidCalculatorAndResolver.getModelVURIContainingIdentifiedEObject(tuid)
		if (null == modelVURI) {
			return null;
		}
		return VURI::getInstance(modelVURI)
	}

	def EObject resolveEObjectFromRootAndFullTUID(EObject root, String tuid) {
		return this.tuidCalculatorAndResolver.resolveEObjectFromRootAndFullTUID(root, tuid)
	}

	def void removeRootFromTUIDCache(EObject root) {
		this.tuidCalculatorAndResolver.removeRootFromCache(root)
	}

	def void removeIfRootAndCached(String tuid) {
		this.tuidCalculatorAndResolver.removeIfRootAndCached(tuid)
	}

	def boolean hasMetaclassInstances(List<EObject> eObjects) {
		for (EObject eObject : eObjects) {
			if (null === eObject || null === eObject.eClass() || null === eObject.eClass().getEPackage() ||
				null === eObject.eClass().getEPackage().getNsURI() ||
				!this.nsURIs.contains(eObject.eClass().getEPackage().getNsURI())) {
				return false
			}

		}
		return true
	}

	def boolean hasTUID(String tuid) {
		return this.tuidCalculatorAndResolver.isValidTUID(tuid)
	}

	def Set<String> getNsURIs() {
		return this.nsURIs
	}

	def Map<Object, Object> getDefaultLoadOptions() {
		return this.defaultLoadOptions
	}

	def Map<Object, Object> getDefaultSaveOptions() {
		return this.defaultSaveOptions
	}

	override canCalculateTuid(EObject object) {
		return hasMetaclassInstances(#[object]) && hasTUID(object);
	}

	override calculateTuid(EObject object) {
		return TUID.getInstance(calculateTUIDFromEObject(object));
	}

	override performPreAction(TUID oldTuid) {
		if (this.hasTUID(oldTuid.toString)) {
			removeIfRootAndCached(oldTuid.toString);
		}
	}

	override performPostAction(TUID newTuid) {
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

}
