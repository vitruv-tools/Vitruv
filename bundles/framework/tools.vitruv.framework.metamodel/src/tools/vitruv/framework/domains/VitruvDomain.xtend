package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Collection

interface VitruvDomain {
	public static String EXTENSION_POINT_ID = "tools.vitruv.framework.metamodel.domain"

	def String getName();

	def Collection<String> getFileExtensions();

	def EPackage getMetamodelRootPackage();

	def Collection<EPackage> getFurtherRootPackages();

}
