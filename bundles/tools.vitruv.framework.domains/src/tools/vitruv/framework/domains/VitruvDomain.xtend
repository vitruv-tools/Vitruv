package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject

interface VitruvDomain {
	def String getName()

	def EPackage getMetamodelRootPackage()
	def Set<EPackage> getFurtherRootPackages()
	def Set<String> getFileExtensions()
	def Set<String> getNsUris()

	def Map<Object, Object> getDefaultLoadOptions()
	def Map<Object, Object> getDefaultSaveOptions()

	/**
	 * Whether any changes made to models of this domain should be
	 * propagated to other domains through registered change propagation
	 * specifications.
	 */
	def boolean shouldTransitivelyPropagateChanges()
}
