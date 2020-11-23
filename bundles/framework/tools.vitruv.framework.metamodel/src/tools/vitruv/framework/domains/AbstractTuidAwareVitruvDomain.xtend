package tools.vitruv.framework.domains

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractTuidAwareVitruvDomain extends AbstractVitruvDomain implements TuidAwareVitruvDomain, TuidUpdateListener {
	val TuidCalculatorAndResolver tuidCalculatorAndResolver

	new(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages, TuidCalculatorAndResolver tuidCalculator,
		String... fileExtensions) {
		super(name, metamodelRootPackage, furtherRootPackages, fileExtensions)
		this.tuidCalculatorAndResolver = tuidCalculator;
	}

	new(String name, EPackage metamodelRootPackage, TuidCalculatorAndResolver tuidCalculator, String... fileExtensions) {
		super(name, metamodelRootPackage, fileExtensions)
		this.tuidCalculatorAndResolver = tuidCalculator;
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

	override boolean hasTuid(String tuid) {
		return this.tuidCalculatorAndResolver.isValidTuid(tuid)
	}

	override performPreAction(Tuid oldTuid) {
		if (this.hasTuid(oldTuid.toString)) {
			removeIfRootAndCached(oldTuid.toString);
		}
	}

	override performPostAction(Tuid newTuid) {
		// Do nothing
	}

	override registerAtTuidManagement() {
		TuidManager.instance.addTuidCalculator(this);
		TuidManager.instance.addTuidUpdateListener(this);
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

	def void removeRootFromTuidCache(EObject root) {
		this.tuidCalculatorAndResolver.removeRootFromCache(root)
	}

	def void removeIfRootAndCached(String tuid) {
		this.tuidCalculatorAndResolver.removeIfRootAndCached(tuid)
	}

	override canCalculateTuid(EObject object) {
		return isInstanceOfDomainMetamodel(object);
	}

	override calculateTuid(EObject object) {
		return Tuid.getInstance(calculateTuidFromEObject(object));
	}

}
