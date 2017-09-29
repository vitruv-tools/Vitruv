package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Collection
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject

interface VitruvDomain extends TuidAwareVitruvDomain {
	def String getName();

	def EPackage getMetamodelRootPackage();
	def Collection<EPackage> getFurtherRootPackages();
	def Set<String> getNsUris();
	def boolean isInstanceOfDomainMetamodel(EObject object);	
	
	def Map<Object, Object> getDefaultLoadOptions();
	def Map<Object, Object> getDefaultSaveOptions();
	
	def VitruviusProjectBuilderApplicator getBuilderApplicator();
	
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
	
}
