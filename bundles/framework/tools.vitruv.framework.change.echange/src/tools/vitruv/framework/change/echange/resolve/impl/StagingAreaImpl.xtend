package tools.vitruv.framework.change.echange.resolve.impl

import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import tools.vitruv.framework.change.echange.resolve.StagingArea

final class StagingAreaImpl implements StagingArea {
	static val Map<ResourceSet, StagingArea> stagingAreas = newHashMap
	val Resource stagingAreaContent

	static def StagingArea getStagingArea(ResourceSet resourceSet) {
		if (resourceSet === null)
			return null
		if (!stagingAreas.containsKey(resourceSet))
			stagingAreas.put(resourceSet, new StagingAreaImpl)
		return stagingAreas.get(resourceSet)
	}

	private new() {
		stagingAreaContent = new ResourceImpl
	}

	override poll() {
		if (stagingAreaContent.contents.empty)
			throw new IllegalStateException
		return stagingAreaContent.contents.remove(0)
	}

	override peek() {
		if (stagingAreaContent.contents.empty)
			throw new IllegalStateException
		return stagingAreaContent.contents.get(0)
	}

	override add(EObject object) {
		stagingAreaContent.contents += object
	}

	override isEmpty() {
		stagingAreaContent.contents.empty
	}

	override clear() {
		stagingAreaContent.contents.clear
	}
}
