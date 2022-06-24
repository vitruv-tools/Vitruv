package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.EPackage
import java.util.Set

interface VitruvDomain {
	def String getName()

	def EPackage getMetamodelRootPackage()
	def Set<String> getFileExtensions()
	def Set<String> getNsUris()
}
