package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility
package class CommonalitiesLanguageElementExtension {

	/**
	 * @return the container of the given type
	 * @throws RuntimeException if no container of the given type is found
	 */
	static def <T> T getEContainer(EObject object, Class<T> containerType) {
		val typedContainer = object.getOptionalEContainer(containerType)
		if (typedContainer !== null) {
			return typedContainer
		}
		throw new IllegalStateException('''The «object.eClass.name» ‹«object»› is not contained inside any «
			containerType.simpleName»!''')
	}

	/**
	 * @return the container of the given type, or <code>null</code> if no such container is found
	 */
	static def <T> T getOptionalEContainer(EObject object, Class<T> containerType) {
		 object.getOptionalDirectEContainer(containerType)
			?: object.eContainer?.getOptionalEContainer(containerType)
	}
	
	static def boolean hasEContainer(EObject object, Class<? extends EObject> containerType) {
		object.getOptionalEContainer(containerType) !== null
	}

	static def <T> T getDirectEContainer(EObject object, Class<T> containerType) {
		val typedContainer = object.getOptionalDirectEContainer(containerType)
		if (typedContainer !== null) {
			return typedContainer
		}
		throw new RuntimeException('''The «object.class.simpleName» ‹«object»› parent is not a «
			containerType.simpleName», but a «object.eContainer.eClass.name»!''')
	}

	static def <T> T getOptionalDirectEContainer(EObject object, Class<T> containerType) {
		val container = object.eContainer
		return if (containerType.isInstance(container)) {
			containerType.cast(container)
		} else {
			null
		}	
	}
	
	static def boolean hasDirectEContainer(EObject object, Class<? extends EObject> containerType) {
		object.getOptionalDirectEContainer(containerType) !== null
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
