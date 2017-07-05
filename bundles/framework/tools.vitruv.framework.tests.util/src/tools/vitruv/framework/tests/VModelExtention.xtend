package tools.vitruv.framework.tests

import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.tests.impl.VModelExtentionImpl
import tools.vitruv.framework.util.datatypes.VURI

interface VModelExtention {
	VModelExtention instance = VModelExtentionImpl::init

	def String getPlatformModelPath(IProject currentTestProject, String modelPathWithinProject)

	def VURI getModelVuri(IProject currentTestProject, String modelPathWithinProject)

	def Resource createModelResource(
		IProject currentTestProject,
		ResourceSet resourceSet,
		String modelPathWithinProject
	)

	def Resource getModelResource(IProject currentTestProject, String modelPathWithinProject, ResourceSet resourceSet)

	def Resource getModelResource(ResourceSet resourceSet, URI modelUri)

	def Resource getModelResource(IProject currentTestProject, ResourceSet resourceSet, String modelPathWithinProject)

	def EObject getFirstRootElement(IProject currentTestProject, String modelPathWithinProject, ResourceSet resourceSet)
}
