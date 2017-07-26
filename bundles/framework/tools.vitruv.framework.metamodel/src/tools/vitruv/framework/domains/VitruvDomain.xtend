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
	
}
