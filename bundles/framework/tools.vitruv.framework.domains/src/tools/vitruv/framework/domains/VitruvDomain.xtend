package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.URIHaving

interface VitruvDomain extends URIHaving, Comparable<URIHaving> {
	def String getName()

	def EPackage getMetamodelRootPackage()
	def Set<EPackage> getFurtherRootPackages()
	def Set<String> getFileExtensions()
	def Set<String> getNsUris()

	def Map<Object, Object> getDefaultLoadOptions()
	def Map<Object, Object> getDefaultSaveOptions()

	def boolean isInstanceOfDomainMetamodel(EObject object)

	/**
	 * Whether this domain should be visible to users. Some domains exist only
	 * for technical or demonstration purposes, in which case {@code false}
	 * should be returned by this method. 
	 */
	def boolean isUserVisible()

	/**
	 * Whether any changes made to meta models of this domain should be
	 * propagated to other domains through registered change propagation
	 * specifications.
	 */
	def boolean shouldTransitivelyPropagateChanges()

	/**
	 * Returns the state change propagation strategy responsible for the
	 * propagation of state diff based changes. 
	 */
	def StateBasedChangeResolutionStrategy getStateChangePropagationStrategy()

}
