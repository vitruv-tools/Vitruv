package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Delegate

// FIXME MK replace with xtend version with delegate annotations
abstract class AbstractDelegatingCorrespondenceInstanceDecorator<D> implements CorrespondenceInstanceDecorator {
	@Delegate protected CorrespondenceInstanceDecorator correspondenceInstance
	final Class<D> decoratorObjectType

	new(CorrespondenceInstanceDecorator correspondenceInstance, Class<D> decoratorObjectType) {
		this.correspondenceInstance = correspondenceInstance
		this.decoratorObjectType = decoratorObjectType
	}

	def protected abstract String getDecoratorFileExtPrefix()

	def protected abstract D getDecoratorObject()

	def protected abstract void initializeFromDecoratorObject(D object)

	def protected abstract void initializeWithoutDecoratorObject()

	override Correspondence createAndAddCorrespondence(List<EObject> ^as, List<EObject> bs) {
		return this.correspondenceInstance.createAndAddCorrespondence(^as, bs)
	}

	override Map<String, Object> getFileExtPrefix2ObjectMapForSave() {
		var Map<String, Object> fileExtPrefix2ObjectMap = this.correspondenceInstance.
			getFileExtPrefix2ObjectMapForSave()
		fileExtPrefix2ObjectMap.put(getDecoratorFileExtPrefix(), getDecoratorObject())
		return fileExtPrefix2ObjectMap
	}

	override Set<String> getFileExtPrefixesForObjectsToLoad() {
		var Set<String> fileExtPrefixes = this.correspondenceInstance.getFileExtPrefixesForObjectsToLoad()
		fileExtPrefixes.add(getDecoratorFileExtPrefix())
		return fileExtPrefixes
	}

	override void initialize(Map<String, Object> fileExtPrefix2ObjectMap) {
		var Object object = fileExtPrefix2ObjectMap.get(getDecoratorFileExtPrefix())
		if (this.decoratorObjectType.isInstance(object)) {
			initializeFromDecoratorObject(this.decoratorObjectType.cast(object))
		} else if (object === null) {
			initializeWithoutDecoratorObject()
		} else {
			throw new RuntimeException('''Cannot initialize decorator '«»«this»' with the decorator object '«»«object»' because it is not an instance of '«»«this.decoratorObjectType»'!''')
		}
	}

	override <T extends CorrespondenceInstanceDecorator> T getFirstCorrespondenceInstanceDecoratorOfTypeInChain(
		Class<T> type) {
		if (type.isInstance(this)) {
			return type.cast(this)
		} else {
			if (this.correspondenceInstance === null) {
				return null
			} else {
				return this.correspondenceInstance.getFirstCorrespondenceInstanceDecoratorOfTypeInChain(type)
			}
		}
	}

}
