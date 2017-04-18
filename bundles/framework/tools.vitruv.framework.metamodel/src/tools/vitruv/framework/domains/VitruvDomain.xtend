package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Collection
import java.util.Map

interface VitruvDomain extends TuidAwareVitruvDomain {
	public static String EXTENSION_POINT_ID = "tools.vitruv.framework.metamodel.domain"

	def String getName();

	def EPackage getMetamodelRootPackage();

	def Collection<EPackage> getFurtherRootPackages();
	
	def Map<Object, Object> getDefaultLoadOptions();
	
	def Map<Object, Object> getDefaultSaveOptions();
}
