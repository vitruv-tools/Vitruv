package tools.vitruv.framework.tests.impl

import tools.vitruv.framework.tests.VModelExtention
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.tests.impl.VModelExtentionImpl
import tools.vitruv.framework.util.datatypes.VURI

class VModelExtentionImpl implements VModelExtention {
	static def VModelExtention init() {
		new VModelExtentionImpl
	}

	private new() {
	}

	override String getPlatformModelPath(IProject currentTestProject, String modelPathWithinProject) {
		'''«currentTestProject.getName»/«modelPathWithinProject»'''
	}

	override VURI getModelVuri(IProject currentTestProject, String modelPathWithinProject) {
		VURI::getInstance(currentTestProject.getPlatformModelPath(modelPathWithinProject))
	}

	override Resource createModelResource(
		IProject currentTestProject,
		ResourceSet resourceSet,
		String modelPathWithinProject
	) {
		resourceSet.createResource(currentTestProject.getModelVuri(modelPathWithinProject).EMFUri)
	}

	override Resource getModelResource(IProject currentTestProject, String modelPathWithinProject,
		ResourceSet resourceSet) {
		resourceSet.getResource(currentTestProject.getModelVuri(modelPathWithinProject).EMFUri, true)
	}

	override Resource getModelResource(ResourceSet resourceSet, URI modelUri) {
		resourceSet.getResource(modelUri, true)
	}

	override Resource getModelResource(IProject currentTestProject, ResourceSet resourceSet,
		String modelPathWithinProject) {
		currentTestProject.getModelResource(modelPathWithinProject, resourceSet)
	}

	override EObject getFirstRootElement(IProject currentTestProject, String modelPathWithinProject,
		ResourceSet resourceSet) {
		val resourceContents = currentTestProject.getModelResource(modelPathWithinProject, resourceSet).getContents
		if (resourceContents.size < 1)
			throw new IllegalStateException("Model has no root")
		resourceContents.get(0)
	}
}
