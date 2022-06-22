package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Set

interface VitruvDomain {
	def String getName()

	def EPackage getMetamodelRootPackage()
	def Set<String> getFileExtensions()
	def Set<String> getNsUris()

	/**
	 * Whether any changes made to models of this domain should be
	 * propagated to other domains through registered change propagation
	 * specifications.
	 */
	def boolean shouldTransitivelyPropagateChanges()
}
