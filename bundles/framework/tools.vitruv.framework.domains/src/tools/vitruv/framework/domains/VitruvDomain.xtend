package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.URIHaving
import java.util.Collection

interface VitruvDomain extends URIHaving, Comparable<URIHaving> {
	def String getName();

	/**
	 * Returns the state change propagation strategy responsible for the
	 * propagation of state diff based changes. 
	 */
	def StateBasedChangeResolutionStrategy getStateChangePropagationStrategy();

	def EPackage getMetamodelRootPackage();

	def Set<EPackage> getFurtherRootPackages();

	def Set<String> getNsUris();

	def boolean isInstanceOfDomainMetamodel(EObject object);

	def Map<Object, Object> getDefaultLoadOptions();

	def Map<Object, Object> getDefaultSaveOptions();

	def VitruviusProjectBuilderApplicator getBuilderApplicator();

	def Collection<String> getFileExtensions();

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
	 * Returns whether this domain supports the usage of UUIDs for object
	 * identification. This especially depends on whether EMF changes are 
	 * recorded and applied or if only a subset of changes is recorded and
	 * the resource is reloaded instead, which does not provide UUIDs
	 * for all elements.
	 */
	def boolean supportsUuids()

}
