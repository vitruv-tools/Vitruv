package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.emfstore.impl.VVModelElementIdUtilImpl
import org.eclipse.emf.ecore.EObject

interface VVModelElementIdUtil {
	VVModelElementIdUtil instance = VVModelElementIdUtilImpl::init

	def VVModelElementId getModelElementId(EObject object)
}
