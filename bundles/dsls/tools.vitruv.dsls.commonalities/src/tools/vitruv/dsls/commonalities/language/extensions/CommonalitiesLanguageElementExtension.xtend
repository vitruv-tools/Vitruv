package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility
package class CommonalitiesLanguageElementExtension {

	/**
	 * @return the container of the given type
	 * @throws RuntimeException if no container of the given type is found
	 */
	package static def <T extends EObject> T getContainer(EObject object, Class<T> containerType) {
		val typedContainer = object.getOptionalContainer(containerType)
		if (typedContainer !== null) {
			return typedContainer
		}
		throw new RuntimeException('''The given «object.class.simpleName» («object») is not contained inside any «
			containerType.simpleName»!''')
	}

	/**
	 * @return the container of the given type, or <code>null</code> if no such container is found
	 */
	package static def <T extends EObject> T getOptionalContainer(EObject object, Class<T> containerType) {
		val typedContainer = object.getOptionalDirectContainer(containerType)
		if (typedContainer !== null) {
			return typedContainer
		}
		return object.eContainer?.getOptionalContainer(containerType)
	}

	package static def <T extends EObject> T getDirectContainer(EObject object, Class<T> containerType) {
		val typedContainer = object.getOptionalDirectContainer(containerType)
		if (typedContainer !== null) {
			return typedContainer
		}
		throw new RuntimeException('''The given «object.class.simpleName» («object
			») is not directly contained inside a «containerType.simpleName»!''')
	}

	package static def <T extends EObject> T getOptionalDirectContainer(EObject object, Class<T> containerType) {
		val container = object.eContainer
		if (container === null) {
			return null
		}
		if (containerType.isInstance(container)) {
			return containerType.cast(container)
		}
		return null
	}

	static def CommonalityFile getOptionalContainingCommonalityFile(EObject object) {
		return object.getOptionalContainer(CommonalityFile)
	}

	static def CommonalityFile getContainingCommonalityFile(EObject object) {
		return object.getContainer(CommonalityFile)
	}

	static def Commonality getContainingCommonality(EObject object) {
		return object.getContainer(Commonality)
	}

	static def CommonalityFile getContainedCommonalityFile(Resource resource) {
		val result = resource.optionalContainedCommonalityFile
		if (result !== null) {
			return result
		}
		throw new IllegalStateException('''The resource ‹«resource»› is expected to contain only a commonality file, «
			»but it«IF (result === null)» is empty.«ELSE» contains «resource.contents».«ENDIF»''')
	}

	static def CommonalityFile getOptionalContainedCommonalityFile(Resource resource) {
		val result = resource.contents.head
		if (result instanceof CommonalityFile) {
			return result
		}
		return null
	}
}
