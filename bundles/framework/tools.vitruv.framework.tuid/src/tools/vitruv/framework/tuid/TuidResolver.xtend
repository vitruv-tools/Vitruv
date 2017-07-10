package tools.vitruv.framework.tuid

import org.eclipse.emf.ecore.EObject

interface TuidResolver {
	def EObject resolveEObjectFromTuid(Tuid tuid);
}